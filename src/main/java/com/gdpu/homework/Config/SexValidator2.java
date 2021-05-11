package com.gdpu.homework.Config;

import com.gdpu.homework.Entity.Student;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SexValidator2 implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        //判断是否为student类,是的话就验证
        System.out.println("该类在验证范围");
        System.out.println( Student.class.isAssignableFrom(aClass));
        return Student.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        System.out.println("进入此验证");
         Student student = (Student) o;
         if(!(student.getSex().equals("男")||student.getSex().equals("女"))){
             errors.rejectValue("sex",null,"性别必须是男或者女");
         }
    }
}
