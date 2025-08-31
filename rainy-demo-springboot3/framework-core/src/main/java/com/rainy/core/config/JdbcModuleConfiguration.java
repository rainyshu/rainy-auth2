package com.rainy.core.config;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author rainy.shu
 */
@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
@ComponentScan(value = {"com.rainy.component.mainboard.jdbc"})
@ImportAutoConfiguration(GlobalTransactionManager.class)
public class JdbcModuleConfiguration implements ModuleConfiguration, SmartInitializingSingleton {

    @Override
    public void afterSingletonsInstantiated() {
        System.out.println("JdbcModuleConfiguration");
    }
}
