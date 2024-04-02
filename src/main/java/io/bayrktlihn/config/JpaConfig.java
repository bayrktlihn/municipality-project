package io.bayrktlihn.config;

import org.hibernate.cfg.JdbcSettings;
import org.hibernate.cfg.MappingSettings;
import org.hibernate.cfg.SchemaToolingSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class JpaConfig {

//    @Bean
//    LocalEntityManagerFactoryBean entityManagerFactoryBean() {
//        LocalEntityManagerFactoryBean localEntityManagerFactoryBean = new LocalEntityManagerFactoryBean();
//        localEntityManagerFactoryBean.setPersistenceUnitName("TestPersistence");
//        return localEntityManagerFactoryBean;
//    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("bayrktlihn");
        dataSource.setPassword("bayrktlihn");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/bayrktlihn");

        return dataSource;
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty(SchemaToolingSettings.HBM2DDL_AUTO, "create");
        properties.setProperty(JdbcSettings.SHOW_SQL, "true");
        properties.setProperty(JdbcSettings.FORMAT_SQL, "true");
        properties.setProperty(JdbcSettings.USE_SQL_COMMENTS, "true");
        properties.setProperty(JdbcSettings.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty(MappingSettings.PHYSICAL_NAMING_STRATEGY, "io.bayrktlihn.config.namingstrategy.CustomPhysicalNamingStrategy");
        return properties;
    }


    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("io.bayrktlihn.entity");
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());


        return em;
    }


    @Bean
    PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

}
