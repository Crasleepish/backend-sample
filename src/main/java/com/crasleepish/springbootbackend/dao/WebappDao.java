package com.crasleepish.springbootbackend.dao;

import com.crasleepish.springbootbackend.bean.Article;
import com.crasleepish.springbootbackend.bean.ClassInfo;
import com.crasleepish.springbootbackend.bean.User;
import com.crasleepish.springbootbackend.dao.mapper.ClassInfoMapper;
import com.crasleepish.springbootbackend.dao.mapper.UserArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@CacheConfig(cacheNames="default")
public class WebappDao {

    @Autowired(required = false)
    private ClassInfoMapper classInfoMapper;

    @Autowired(required = false)
    private UserArticleMapper userArticleMapper;

    @Cacheable(key = "'ClassInfo:' + #id")
    public ClassInfo getClassInfoById(Integer id) {
        return classInfoMapper.getClassInfoById(id);
    }

    @Cacheable(key = "'ClassInfo:allClasses'")
    public List<ClassInfo> getAllClasses() {
        return classInfoMapper.getAllClasses();
    }

    @Transactional
    @CachePut(key = "'ClassInfo:' + #ci.id")
    @CacheEvict(key = "'ClassInfo:allClasses'")
    public ClassInfo insertClassInfo(ClassInfo ci) {
        classInfoMapper.insertClassInfo(ci);
        return ci;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(key = "'ClassInfo:' + #ci.id"),
            @CacheEvict(key = "'ClassInfo:allClasses'")
    })
    public int updateClassInfo(ClassInfo ci) {
        return classInfoMapper.updateClassInfo(ci);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(key = "'ClassInfo:' + #id"),
            @CacheEvict(key = "'ClassInfo:allClasses'")
    })
    public int deleteClassInfo(Integer id) {
        return classInfoMapper.deleteClassInfoById(id);
    }

    @Cacheable(cacheNames = "userCache", key = "'User:' + #id")
    public User getUserById(Integer id) {
        return userArticleMapper.getUserById(id);
    }

    @Cacheable(cacheNames = "ListOfUsers", key = "#clsId")
    public List<User> getUsersByClassId(Integer clsId) {
        return userArticleMapper.getUsersByClassId(clsId);
    }

    @Transactional
    @CachePut(cacheNames = "userCache", key = "'User:' + #user.id")
    public User addUser(User user) {
        userArticleMapper.addUser(user);
        return userArticleMapper.getUserById(user.getId());
    }

    @Transactional
    @CacheEvict(cacheNames = "ListOfUsers", allEntries = true)
    public int batchAddUser(List<User> users) {
        return userArticleMapper.batchAddUser(users);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "userCache", key = "'User:' + #user.id"),
            @CacheEvict(cacheNames = "ListOfUsers", key = "#user.classInfo.id"),
            @CacheEvict(cacheNames = "ListOfUsers", key = "#oriUser.classInfo.id")
    })
    public int updateUser(User oriUser, User user) {
        return userArticleMapper.updateUser(user);
    }

    @Cacheable(key = "'getArticlesByAuthorId:' + #auId")
    public List<Article> getArticlesByAuthorId(Integer auId) {
        return userArticleMapper.getArticlesByAuthorId(auId);
    }

    @Cacheable(key = "'getArticlesByCond:' + #cond.hashCode()")
    public List<Article> getArticlesByCond(Map<String, Object> cond) {
        String titlePattern = cond.get("titlePattern") != null ? cond.get("titlePattern").toString() : null;
        Integer bonusMin = cond.get("bonusMin") != null ? (Integer) cond.get("bonusMin") : null;
        Integer bonusMax = cond.get("bonusMax") != null ? (Integer) cond.get("bonusMax") : null;
        Date commitTimeMin = cond.get("commitTimeMin") != null ?
                new Date(1000 * ((Integer) cond.get("commitTimeMin")).longValue()) : null;
        Date commitTimeMax = cond.get("commitTimeMax") != null ?
                new Date(1000 * ((Integer) cond.get("commitTimeMax")).longValue()) : null;

        return userArticleMapper.getArticlesByCond(titlePattern, bonusMin, bonusMax, commitTimeMin, commitTimeMax);
    }
}
