package com.domain.login;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yangzhiguo  2017/11/20.
 */
@Data
@Builder
public class User implements Serializable {
    private String  name;
    private String  age;
    private String  birthday;
    private String  eat;
    private String  address;
    private String ages;
}
