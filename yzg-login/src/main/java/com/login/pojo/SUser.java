package com.login.pojo;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *  YZG on 2017/4/22.
 */
@SuppressWarnings("all")
@Entity
@Table(name = "s_user",catalog = "dev_example")
public class SUser
{

    private String      id;
    private String      name;
    private String      email;
    private String      password;
    private Date        dob;
    private Set<SRole> sRoles = new HashSet<SRole>();

    public SUser() {
    }

    public SUser(String name, String email, String password, Date dob, Set<SRole> sRoles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.sRoles = sRoles;
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

    @Column(name = "name",length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "email",length = 20)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password",length = 30)
    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    @Temporal(TemporalType.DATE)
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "sUser")
    public Set<SRole> getsRoles() {
        return sRoles;
    }

    public void setsRoles(Set<SRole> sRoles) {
        this.sRoles = sRoles;
    }
}
