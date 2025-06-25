package de.hems.backend.config;

import de.hems.backend.components.ConfigurationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JPAConfig {

    private final ConfigurationManager manager;

    @Autowired
    public JPAConfig(ConfigurationManager manager) {
        this.manager = manager;//TODO: add ability for users to change db type
    }
    // Hardcoded database connection variables
    private final String dbUrl = "jdbc:postgresql://localhost:5432/test";
    private final String dbUsername = "test";
    private final String dbPassword = "test";
    private final String driverClassName = "org.postgresql.Driver";
    private final String hibernateDialect = "org.hibernate.dialect.PostgreSQLDialect";

    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url(dbUrl)
                .username(dbUsername)
                .password(dbPassword)
                .driverClassName(driverClassName)
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, DataSource dataSource) {

        // Directly configure the Hibernate properties
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.dialect", hibernateDialect);
        properties.put("hibernate.show_sql", "true");
        properties.put("spring.jpa.properties.hibernate.dialect", hibernateDialect);
        properties.put("hibernate.hbm2ddl.auto", "update");

        // Set up the Hibernate JpaVendorAdapter
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);
        vendorAdapter.setDatabasePlatform(hibernateDialect);
        LocalContainerEntityManagerFactoryBean factoryBean = builder
                .dataSource(dataSource)
                .packages("de.hems.backend.types.entitys")  // Your package where entities are defined
                .properties(properties)  // Set the Hibernate properties directly// Use HibernateJpaVendorAdapter
                .build();
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        System.out.println("JPA Properties: " + properties);
        System.out.println(factoryBean.getJpaDialect());
        return factoryBean;
    }

}
