package com.liuhengjia.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Properties;

public class MybatisConfig {
    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(@Autowired DataSource dataSource, @Autowired Interceptor pageInterceptor) {
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        ssfb.setTypeAliasesPackage("com.liuhengjia.entity");
        ssfb.setDataSource(dataSource);
        ssfb.setPlugins(new Interceptor[]{pageInterceptor});
        return ssfb;
    }

    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer() {
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setBasePackage("com.liuhengjia.mapper");
        return msc;
    }

    @Bean("pageInterceptor")
    public Interceptor getPageInterceptor() {
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("reasonable", "true");
        Interceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }
}
