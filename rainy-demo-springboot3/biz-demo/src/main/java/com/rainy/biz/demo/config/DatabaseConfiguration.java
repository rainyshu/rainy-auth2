package com.rainy.biz.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Run.Shu
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.rainy.biz..*.dao"})
@EnableTransactionManagement
@ComponentScan(basePackages = "com.rainy.biz")
public class DatabaseConfiguration {

}
