package de.hems.backend.types.entitys;

import de.hems.backend.types.enums.PermissionLevel;
import jakarta.persistence.*;
import org.checkerframework.common.aliasing.qual.Unique;

import java.util.UUID;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue
    private UUID id;
    @Unique
    private String username;
    @Unique
    private String email;
    @OneToOne
    private AuthenticationHandler authenticationHandler;
    private PermissionLevel permissionLevel;

    public User(@Unique String username, @Unique String email, AuthenticationHandler authenticationHandler, PermissionLevel permissionLevel) {
        this.username = username;
        this.email = email;
        this.authenticationHandler = authenticationHandler;
        this.permissionLevel = permissionLevel;
    }

    public User() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AuthenticationHandler getAuthenticationHandler() {
        return authenticationHandler;
    }

    public void setAuthenticationHandler(AuthenticationHandler authenticationHandler) {
        this.authenticationHandler = authenticationHandler;
    }

    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(PermissionLevel permissionLevel) {
        this.permissionLevel = permissionLevel;
    }
}
