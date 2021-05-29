package com.crasleepish.springbootbackend;

import com.crasleepish.springbootbackend.bean.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@SpringBootTest
class SpringbootBackendApplicationTests {

    @Autowired
    DataSource source;

    @Autowired
    ConversionService conversionService;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void contextLoads() throws SQLException {
        Connection connection = source.getConnection();
        System.out.println(connection);
        PreparedStatement statement = connection.prepareStatement("select * from websites where id>?");
        statement.setInt(1, 1);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            System.out.println(resultSet.getString(1) +
                    "," + resultSet.getString(2) +
                    "," + resultSet.getString(3));
        }
        resultSet.close();
        connection.close();
    }

    @Test
    void casualTest() throws JsonProcessingException {
        String json = "{\"username\":\"tom\",\"company\":{\"companyName\":\"中华\",\"address\":\"北京\"},\"cars\":[\"奔驰\",\"宝马\"]}";
        String arrayJson = "[{\"number\":64,\"result\":\"SUCCESS\"},{\"number\":65,\"result\":\"FAILURE\"},{\"number\":66,\"result\":\"ABORTED\"},{\"number\":67,\"result\":\"SUCCESS\"}]";

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(json,
                new TypeReference<Map<String,Object>>(){});
        jsonMap.keySet().forEach((String key) -> {
            System.out.println(key + ":" + jsonMap.get(key));
        });
    }

    enum MyEnum {
        SUCCESS,FAILED
    }

    @Test
    void enumTest() throws ClassNotFoundException {
        Enum me = Enum.valueOf(User.Gender.class, "MALE");
    }

    @Test
    void testBuildInConverters() {
        System.out.println(conversionService.canConvert(String.class, User.Type.class));
        System.out.println(conversionService.convert("STU", User.Type.class));
    }
}
