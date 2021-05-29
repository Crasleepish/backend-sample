package com.crasleepish.springbootbackend.dao.mapper;

import com.crasleepish.springbootbackend.bean.Article;
import com.crasleepish.springbootbackend.bean.User;

import java.util.Date;
import java.util.List;

public interface UserArticleMapper {
    public User getUserById(Integer id);

    public List<User> getUsersByClassId(Integer clsId);

    public int addUser(User user);

    public int batchAddUser(List<User> users);

    public int updateUser(User user);

    public List<Article> getArticlesByAuthorId(Integer auId);

    public List<Article> getArticlesByCond(String titlePattern, Integer bonusMin, Integer bonusMax,
                                           Date commitTimeMin, Date commitTimeMax);
}
