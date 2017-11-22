package com.login.pojo;


import javax.persistence.*;
import java.io.Serializable;

/**
 *  YZG on 2017/4/22.
 */
@Entity
@Table(name = "s_role",catalog = "dev_example")
@SuppressWarnings("all")
public class SRole implements Serializable
{
    private String      id;
    private SUser sUser;
    private String      name;

    public SRole(){}

    public SRole(SUser sUser){
        this.sUser = sUser;
    }

    public SRole(SUser sUser, String name){
        this.sUser = sUser;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true,nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", nullable = false)
    public SUser getsUser() {
        return sUser;
    }

    public void setsUser(SUser sUser) {
        this.sUser = sUser;
    }

    @Column(name = "name",length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
