package com.login.service;


import com.login.pojo.SUser;

import java.util.List;

public interface SUserService
{
    List<SUser> findAll();

    SUser create(SUser user);

    SUser findUserById(String id);

    SUser login(String email, String password);

    SUser updata(SUser user);

    void  deleteUser(String id);

    SUser findUserByEmail(String email);
}
