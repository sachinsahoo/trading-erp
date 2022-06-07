package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

@Configuration
@EnableJdbcHttpSession
public class SessionConfig
        extends AbstractHttpSessionApplicationInitializer {

//    @Bean
//    public DataSource dataSource() {
//        DataSourceBuilder factory = DataSourceBuilder
//                .create().driverClassName("org.postgresql.Driver")
//                .username("sachinsahoo")
//                .password("postgres")
//                .url("jdbc:postgresql://localhost:5432/postgres");
//        DataSource ds = factory.build();
//        return ds;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
}