package de.hems.backend.config;

import de.hems.backend.components.ConfigurationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
public class JPAConfig {
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            JpaProperties jpaProperties) {

        DataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/test").
                username("test").password("test").
                driverClassName("org.postgresql.Driver").
                build();
        System.out.println("URL");
        return builder
                .dataSource(dataSource)
                .packages("de.hems.backend.types.entitys")
                .properties(jpaProperties.getProperties())
                .build();
    }

}