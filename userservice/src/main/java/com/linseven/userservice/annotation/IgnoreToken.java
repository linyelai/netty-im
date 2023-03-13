package com.linseven.userservice.annotation;


import java.lang.annotation.*;

@Target({ElementType.CONSTRUCTOR, ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreToken  {
}
