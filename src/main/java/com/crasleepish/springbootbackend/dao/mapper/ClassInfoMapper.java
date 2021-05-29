package com.crasleepish.springbootbackend.dao.mapper;

import com.crasleepish.springbootbackend.bean.ClassInfo;

import java.util.List;

public interface ClassInfoMapper {
    public ClassInfo getClassInfoById(Integer id);

    public List<ClassInfo> getAllClasses();

    public int insertClassInfo(ClassInfo ci);

    public int updateClassInfo(ClassInfo ci);

    public int deleteClassInfoById(Integer id);

}
