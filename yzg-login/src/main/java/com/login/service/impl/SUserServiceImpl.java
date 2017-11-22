package com.login.service.impl;


import com.login.mapper.SysUserRepository;
import com.login.pojo.SUser;
import com.login.service.SUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YZG on 2017/4/22.
 */
@Service("suserService")
public class SUserServiceImpl implements SUserService
{

    @Autowired

    private SysUserRepository sysUserRepository;


    @Override
    public List<SUser> findAll()
    {
        return sysUserRepository.findAll();
    }

    @Override
    public SUser create(SUser user)
    {
        return sysUserRepository.save(user);
    }

    @Override
    public SUser findUserById(String id)
    {
        return sysUserRepository.findOne(id);
    }

    @Override
    public SUser login(String email, String password)
    {
        return  sysUserRepository.findByEmailAndPassword(email,password);
    }

    @Override
    public SUser updata(SUser user)
    {
        return sysUserRepository.save(user);
    }

    @Override
    public void deleteUser(String id)
    {
        sysUserRepository.delete(id);
    }

    @Override
    public SUser findUserByEmail(String email)
    {
        return sysUserRepository.findUserByEmail(email);
    }
}
