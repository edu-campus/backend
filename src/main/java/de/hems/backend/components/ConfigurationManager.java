package de.hems.backend.components;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * This class is responsible for managing the configuration of the application.
 * <p>
 * It uses YamlConfiguration to load and save the configuration from a file.
 */
@Component
public class ConfigurationManager {

    ConfigurationNode config;

    public ConfigurationManager() throws IOException {
        Path configPath = new File("config.yml").toPath();

        // Create a YAML loader
        ConfigurationLoader<? extends ConfigurationNode> loader = YamlConfigurationLoader.builder().path(configPath).build();

        // Load or create configuration
        config = loader.load();


        // Save it back to disk
        loader.save(config);
    }

    public ConfigurationNode getConfig() {
        return config;
    }
}
