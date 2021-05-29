package com.crasleepish.springbootbackend.bean;

import com.crasleepish.springbootbackend.util.validator.ExistClassInfo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Gender { FEMALE, MALE };
    public enum Type { STU, TEACHER };

    private int id;

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$", message = "用户名只能包含中英文数字下划线")
    private String name;

    private Gender gender;

    @Min(value = 0, message = "年龄只能介于0-120")
    @Max(value = 120, message = "年龄只能介于0-120")
    private int age;

    @ExistClassInfo
    private ClassInfo classInfo;

    private Type type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
