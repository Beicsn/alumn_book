//package com.gdpu.homework.Config;
//
//import com.auth0.jwt.interfaces.Payload;
//
//import javax.validation.Constraint;
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
////作用在字段
//@Target(ElementType.FIELD)
////生命周期:运行时期
//@Retention(RetentionPolicy.RUNTIME)
////指定实现验证逻辑的验证器
//@Constraint(validatedBy = SexValidator.class)
//public @interface Sex {
//    String message() default "性别必须是男或女";
//    Class<?>[] groups() default {};
//    Class<? extends Payload>[] payload() default {};
//}
