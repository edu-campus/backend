package de.hems.backend.endpoints;

import de.hems.backend.components.ConfigurationManager;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@RestController
@RequestMapping("/api/onboarding")
public class OnboardingEndpoint {

    private final ConfigurationManager configurationManager;

    @Autowired
    public OnboardingEndpoint(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @PostMapping(value = "/smtp", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity smtp(@RequestParam String host,
                               @RequestParam String port,
                               @RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String protocol,
                               @RequestParam String auth,
                               @RequestParam String enableTTLS,
                               @RequestParam String debug) throws SerializationException {
        ConfigurationNode config = configurationManager.getConfig();
        if (config.node("first-time").getBoolean()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        config.node("secrets", "smtp", "host").set(host);
        config.node("secrets", "smtp", "port").set(Integer.parseInt(port));

        config.node("secrets", "smtp", "username").set(username);
        config.node("secrets", "smtp", "password").set(password);

        config.node("secrets", "smtp", "properties", "mail", "transport", "protocol").set(protocol);
        config.node("secrets", "smtp", "properties", "mail", "smtp", "auth").set(auth);
        config.node("secrets", "smtp", "properties", "mail", "smtp", "starttls", "enable").set(enableTTLS);
        config.node("secrets", "smtp", "properties", "mail", "debug").set(debug);
        return ResponseEntity.ok().build();
    }
}
