package com.crasleepish.springbootbackend.bean;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class ClassInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    @NotBlank(message = "班级名称不能为空")
    private String className;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "ClassInfo{" +
                "id=" + id +
                ", className='" + className + '\'' +
                '}';
    }
}
