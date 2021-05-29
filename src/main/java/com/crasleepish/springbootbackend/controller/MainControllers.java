package com.crasleepish.springbootbackend.controller;

import com.crasleepish.springbootbackend.bean.Article;
import com.crasleepish.springbootbackend.bean.ClassInfo;
import com.crasleepish.springbootbackend.bean.User;
import com.crasleepish.springbootbackend.dao.WebappDao;
import com.crasleepish.springbootbackend.util.exception.WhatTheFuckException;
import com.crasleepish.springbootbackend.util.validator.ValidList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@RestController
public class MainControllers {

    private static Logger logger = LoggerFactory.getLogger(MainControllers.class);

    private WebappDao dao;

    private StringRedisTemplate myStringRedisTemplate;

    private RedisTemplate<String, Serializable> myRedisTemplate;

    public MainControllers(WebappDao dao, StringRedisTemplate myStringRedisTemplate, RedisTemplate<String, Serializable> myRedisTemplate) {
        this.dao = dao;
        this.myStringRedisTemplate = myStringRedisTemplate;
        this.myRedisTemplate = myRedisTemplate;
    }

    //接受Path Variables，返回Text
    @RequestMapping("/hello/{id1}/{id2}")
    public String helloController(@PathVariable String id1, @PathVariable String id2){
        logger.info("helloController Called...");
        return "Hello " + id1 + "," + id2;
    }


    //接受请求参数，返回JSON
    @GetMapping("/testAPIforParamInput")
    public Map<String, Object> testAPIforParamInput(@RequestParam Integer id, @RequestParam String name) {
        logger.info("id:" + id);
        logger.info("name:" + name);
        return new HashMap<String, Object>(){{
            put("id", id);
            put("name", name);
            put("date", new Date().getTime()/1000);
        }};
    }

    //接受Json，返回Json
    @PostMapping("/testAPIforJsonInput")
    public Map<String, Object> testAPIforJsonInput(@RequestBody Map<String, Object> jsonParam) {
        logger.info(jsonParam.toString());
        return new HashMap<String, Object>(){{
            put("list", Arrays.asList("北京", "上海", "新疆"));
            put("map", new HashMap<String, BigDecimal>(){{
                put("CNY", new BigDecimal("12.3577"));
                put("USD", new BigDecimal("75.6543"));
                put("JPY", new BigDecimal("202.6231"));
            }});
        }};
    }

    @RequestMapping("/classinfo/{id}")
    public Object classInfoController(@PathVariable("id") Integer id) {
        ClassInfo result = dao.getClassInfoById(id);
        if (result == null) {
            throw new WhatTheFuckException("Cannot even found one.");
        }
        return result;
    }

    @PostMapping("/classinfo/insert")
    public ClassInfo insertClassInfoController(@RequestBody @Valid ClassInfo ci) {
        dao.insertClassInfo(ci);
        return ci;
    }

    @PostMapping("/classinfo/update")
    public int updateClassInfoController(@RequestBody @Valid ClassInfo ci) {
        return dao.updateClassInfo(ci);
    }

    @PostMapping(value = "/classinfo/del", params = {"id"})
    public int deleteClassInfoController(@RequestParam Integer id) {
        return dao.deleteClassInfo(id);
    }

    @RequestMapping("/user/query/{id}")
    public User getUserController(@PathVariable Integer id) {
        return dao.getUserById(id);
    }

    @RequestMapping("/user/list/{clsId}")
    public List<User> getUserListByClsId(@PathVariable Integer clsId) {
        return dao.getUsersByClassId(clsId);
    }

    @PostMapping("/user/add")
    public User addUser(@RequestBody @Valid User user) {
        return dao.addUser(user);
    }

    @PostMapping("/user/update")
    public int updateUser(@RequestBody @Valid User user) {
        User oriUser = dao.getUserById(user.getId());
        return dao.updateUser(oriUser, user);
    }

//    @Valid注解只能验证JavaBean，不能直接验证List，如果把List包装成为一个JavaBean的字段就可以验证了
//  改前：  public int batchAddUser(@RequestBody @Valid List<User> users) {
    @PostMapping("/user/import")
    public int batchAddUser(@RequestBody @Valid ValidList<User> users) {
        return dao.batchAddUser(users);
    }

    @RequestMapping("/articles/author/{auId}")
    public List<Article> getArticlesByAuthorId(@PathVariable Integer auId) {
        return dao.getArticlesByAuthorId(auId);
    }

    @PostMapping("/articles/cond")
    public List<Article> getArticlesByCond(@RequestBody Map<String, Object> cond) {
        return dao.getArticlesByCond(cond);
    }

    @RequestMapping("/testRedis")
    public String testRedisController() {
        myStringRedisTemplate.opsForValue().set("testSpringRedis", "testSpringRedisOK");
        System.out.println(myStringRedisTemplate.opsForValue().get("testSpringRedis"));

        User testUser = new User();
        testUser.setId(123);
        testUser.setName("haha");
        testUser.setAge(22);
        testUser.setClassInfo(new ClassInfo());
        testUser.setGender(User.Gender.MALE);
        testUser.setType(User.Type.STU);
        myRedisTemplate.opsForValue().set("testRedis02", testUser);
        System.out.println(myRedisTemplate.opsForValue().get("testRedis02"));

        myRedisTemplate.opsForHash().put("TestHash", "First Element", "Hello, redis, hash");
        System.out.println(myRedisTemplate.opsForHash().get("TestHash", "First Element"));
        return "OK";
    }

    @RequestMapping("/testRedisPipeline")
    public Object testRedisPipeline() {
        // 1.executePipelined 重写 入参 RedisCallback 的doInRedis方法
        List<Object> resultList = myStringRedisTemplate.executePipelined(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                // 2.connection 打开管道
                redisConnection.openPipeline();

                // 3.connection 给本次管道内添加 要一次性执行的多条命令

                // 3.1 一个set操作, 传入参数类型要求是byte数组
                byte[] key1 = "mykey1".getBytes();
                byte[] value1 = "myvalue1".getBytes();
                redisConnection.set(key1,value1);

                // 3.2一个批量hmset操作, 添加一个HASH类型的数据
                Map<byte[],byte[]> tuple = new HashMap<>();
                tuple.put("hashkey1".getBytes(),"hashvalue1".getBytes());
                tuple.put("hashkey2".getBytes(),"hashvalue2".getBytes());
                tuple.put("hashkey3".getBytes(),"hashvalue3".getBytes());
                redisConnection.hMSet("myhashkey".getBytes(), tuple);

                // 3.3两个get操作
                redisConnection.get("mykey1".getBytes());
                redisConnection.hMGet("myhashkey".getBytes(),
                        "hashkey1".getBytes(),
                        "hashkey2".getBytes(),
                        "hashkey3".getBytes());

                // 不需要redisConnection.closePipeline()否则拿不到返回值

                //这里一定要返回null，最终pipeline的执行结果，才会返回给最外层
                return null;
            }
        });

        // 5.最后对redis pipeline管道操作返回结果进行判断和业务补偿
        for (Object str : resultList) {
            System.out.println(String.valueOf(str));
        }
        return resultList;
    }
}
