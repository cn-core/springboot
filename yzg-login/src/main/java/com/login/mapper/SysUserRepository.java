package com.login.mapper;


import com.login.pojo.SUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * YZG on 2017/4/23.
 */
@Repository
public interface SysUserRepository extends JpaRepository<SUser,String>
{

    @Query("select u from SUser u where u.email = ?1 and u.password = ?2")
    SUser login(String email, String password);

    SUser findByEmailAndPassword(String email, String password);

    SUser findUserByEmail(String email);

}
