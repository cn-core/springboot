package com.login;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * yangzhiguo on 2017/8/30.
 */
@EntityScan(basePackages = {"com.domain"})
@ComponentScan(basePackages = {"com.login"})
@EnableJpaRepositories
@EnableAsync
public class ScanPackage {
}
