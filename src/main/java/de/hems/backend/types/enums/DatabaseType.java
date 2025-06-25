package de.hems.backend.types.enums;

public enum DatabaseType {
    POSTGRES, MYSQL, MARIADB, SQLITE, CASSANDRA;
    public static String getDatabaseDriver(DatabaseType type) {
        return switch (type) {
            case POSTGRES -> "org.postgresql.Driver";
            case MYSQL -> "com.mysql.cj.jdbc.Driver";
            case MARIADB -> "org.mariadb.jdbc.Driver";
            case SQLITE -> "org.sqlite.JDBC";
            case CASSANDRA -> "com.datastax.oss.driver.api.core.CqlSession";
        };
    }
    public static String getDatabaseUrlName(DatabaseType type) {
        return switch (type) {
            case POSTGRES -> "postgresql";
            case MYSQL -> "mysql";
            case MARIADB -> "mariadb";
            case SQLITE -> "sqlite";
            case CASSANDRA -> "cassandra";
        };
    }

    public static String getHibernateDialect(DatabaseType type) {
        return switch (type) {
            case POSTGRES -> "org.hibernate.dialect.PostgreSQLDialect";
            case MYSQL -> "org.hibernate.dialect.MySQL57Dialect";
            case MARIADB -> "org.hibernate.dialect.MariaDBDialect";
            case SQLITE -> "org.hibernate.dialect.SQLiteDialect";
            case CASSANDRA -> "org.hibernate.dialect.CassandraDialect";
        };
    }
}
