package com.crasleepish.springbootbackend.util.validator;

import com.crasleepish.springbootbackend.bean.ClassInfo;
import com.crasleepish.springbootbackend.dao.WebappDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;

public class GenderValidator implements ConstraintValidator<ExistClassInfo, ClassInfo> {

    @Autowired
    private WebappDao dao;

    private List<Integer> allClassId;

    @Override
    public void initialize(ExistClassInfo constraintAnnotation) {
        allClassId = dao.getAllClasses().stream().map(
                p -> p.getId()
        ).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(ClassInfo classInfo, ConstraintValidatorContext constraintValidatorContext) {
        return allClassId.contains(classInfo.getId());
    }
}
