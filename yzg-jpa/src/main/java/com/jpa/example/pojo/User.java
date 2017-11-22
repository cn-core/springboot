package com.jpa.example.pojo;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author yangzhiguo  2017/11/2.
 */
@Data
@Entity
@Table(name = "user")
public class User implements Serializable{

    @Id
    @GeneratedValue
    private String  id;
    @Column
    private String  userName;
    @Column
    private String  password;
    @Column
    private String  address;
}
