package com.gdpu.homework.Entity;

//import com.gdpu.homework.Config.Sex;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Student {
    @NotNull
    private String id;
    @NotNull
    private String campus;
    @NotNull
    private String college;
    @NotNull
    private  String grade;
    @NotNull
    private  String major;
    @NotNull
    private String name;
    //自定义的验证sex注解
//    @Sex
    private String sex;
    @NotNull
    private String weChat;
    @NotNull
    private String phoneNumber;

}
