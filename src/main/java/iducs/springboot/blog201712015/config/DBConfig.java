package iducs.springboot.blog201712015.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:db.properties")
public class DBConfig {
/*
    @Bean("dataSource2")
    @ConfigurationProperties(prefix="spring.datasource.hikari")
    public DataSource getDataSourceBuilder() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        return dataSourceBuilder.build();
    }*/

    @Primary
    @Bean(name="dataSource1", destroyMethod = "close")
    public HikariDataSource getHikariDataSource(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("oracle.jdbc.OracleDriver");
        hikariConfig.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:XE");
        hikariConfig.setUsername("system");
        hikariConfig.setPassword("1234");
        //hikariConfig.setMaximumPoolSize(5);
        //hikariConfig.setConnectionTestQuery("SELECT 1");
        //hikariConfig.setPoolName("springHikariCP");
        return new HikariDataSource(hikariConfig);
    }
}
