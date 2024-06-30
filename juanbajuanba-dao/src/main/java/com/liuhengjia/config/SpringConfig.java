package com.liuhengjia.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
//@ComponentScan(value = "com.liuhengjia", excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = RestController.class))
@ComponentScan(value = "com.liuhengjia")
@EnableTransactionManagement
@PropertySource("classpath:jdbc.properties")
@Import({JdbcConfig.class, MybatisConfig.class})
public class SpringConfig {

    @Bean("transactionManager")
    public PlatformTransactionManager getTxManager(@Autowired DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
