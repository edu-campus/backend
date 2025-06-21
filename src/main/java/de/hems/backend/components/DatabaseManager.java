package de.hems.backend.components;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseManager {

    private EntityManagerFactory entityManagerFactory;
    private final ConfigurationManager configurationManager;

    @Autowired
    public DatabaseManager(EntityManagerFactory entityManagerFactory, ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
        if (configurationManager.getConfig().node("first-time").getBoolean()) return;
        this.entityManagerFactory = entityManagerFactory;
    }

    public SessionFactory getSessionFactory() {
        return entityManagerFactory.unwrap(SessionFactory.class);
    }
}
