package de.hems.backend.types.enums;

public enum DatabaseType {
    POSTGRES, MYSQL;
    public static String getDatabaseDriver(DatabaseType type) {
        return switch (type) {
            case POSTGRES -> "org.postgresql.Driver";
            case MYSQL -> "com.mysql.cj.jdbc.Driver";
        };
    }
    public static String getDatabaseUrlName(DatabaseType type) {
        return switch (type) {
            case POSTGRES -> "postgresql";
            case MYSQL -> "mysql";
        };
    }

    public static String getHibernateDialect(DatabaseType type) {
        return switch (type) {
            case POSTGRES -> "org.hibernate.dialect.PostgreSQLDialect";
            case MYSQL -> "org.hibernate.dialect.MySQL57Dialect";
        };
    }
}
