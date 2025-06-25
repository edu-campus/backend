package de.hems.backend.types.entitys;

import at.favre.lib.crypto.bcrypt.BCrypt;
import de.hems.backend.types.staticManager.StaticAuthenticationManager;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.UUID;

//Currently only for password authentication and 2fa app authentication
@Entity
public class AuthenticationHandler {

    @Id
    @GeneratedValue
    private UUID id;
    private MainAuthenticationType type;
    private SecondAuthenticationType secondType;
    private String password;
    private String saml;
    private String provider;
    private String appSecret;
    private String mail;

    public AuthenticationHandler(String first, String second, MainAuthenticationType type, SecondAuthenticationType secondType) {
        BCrypt.Hasher hasher = BCrypt.withDefaults();
        switch (type) {
            case MainAuthenticationType.PASSWORD -> {
                this.password = new String(hasher.hash(11, first.toCharArray()));
            }
            case MainAuthenticationType.SAML -> {
                this.saml = first;
            }
            case MainAuthenticationType.PROVIDER -> {
                this.provider = first;
            }
            default -> throw new IllegalArgumentException("Invalid Authentication Type");
        }
        switch (secondType) {
            case SecondAuthenticationType.MAIL -> {
                this.mail = second;
            }
            case SecondAuthenticationType.AUTHENTICATOR_APP -> {
                this.appSecret = second;
            }
        }
        this.type = type;
        this.secondType = secondType;
    }

    public AuthenticationHandler() {

    }

    public boolean authenticate(String first, String second) {
        return authenticateFirst(first) && authenticateSecond(second);
    }

    private boolean authenticateFirst(String first) {
        return switch (this.type) {
            case PASSWORD -> BCrypt.verifyer().verify(this.password.toCharArray(), first.toCharArray()).verified;
            case SAML -> this.saml.equals(first);//TODO:
            case PROVIDER -> this.provider.equals(first); //TODO:
        };
    }

    private boolean authenticateSecond(String second) {
        return switch (this.secondType) {
            case MAIL -> this.mail.equals(second);
            case AUTHENTICATOR_APP -> {
                String totpCode = StaticAuthenticationManager.getTOTPCode(this.appSecret);
                totpCode = totpCode.replaceAll(" ", "");
                yield totpCode.equals(second);
            }
            case null -> true;
        };
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public MainAuthenticationType getType() {
        return type;
    }

    public void setType(MainAuthenticationType type) {
        this.type = type;
    }

    public SecondAuthenticationType getSecondType() {
        return secondType;
    }

    public void setSecondType(SecondAuthenticationType secondType) {
        this.secondType = secondType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSaml() {
        return saml;
    }

    public void setSaml(String saml) {
        this.saml = saml;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}

enum MainAuthenticationType {
    PASSWORD, SAML, PROVIDER,
}
enum SecondAuthenticationType {
    MAIL, AUTHENTICATOR_APP,
}
