package com.springsecuritymodule.entities;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class User {

    /**
     */
    @NotNull
    private String email;

    /**
     */
    @NotNull
    private String password;
}
