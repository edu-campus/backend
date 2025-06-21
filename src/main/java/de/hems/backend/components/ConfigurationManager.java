package de.hems.backend.components;

import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * This class is responsible for managing the configuration of the application.
 * <p>
 * It uses YamlConfiguration to load and save the configuration from a file.
 */
@Component
public class ConfigurationManager {

    ConfigurationNode config;
    Path configPath;

    public ConfigurationManager() throws IOException {
        File config = new File("config.yml");
        if (!config.exists()) {
            config.createNewFile();
            FileWriter writer = new FileWriter(config);
            InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("./defaultConfig.yml");
            byte[] bytes = resourceAsStream.readAllBytes();
            String defaultConfig = new String(bytes);
            writer.write(defaultConfig);
            writer.flush();
            writer.close();
        }
        configPath = config.toPath();

        // Create a YAML loader
        ConfigurationLoader<? extends ConfigurationNode> loader = YamlConfigurationLoader.builder().path(configPath).build();

        // Load or create configuration
        this.config = loader.load();

        // Save it back to disk
        loader.save(this.config);
    }

    public ConfigurationNode getConfig() {
        return config;
    }

    public void set(Object... pathAndValue) throws ConfigurateException {
        Object[] objects = Arrays.stream(pathAndValue).limit(pathAndValue.length - 1).toArray();
        config.node(objects).set(pathAndValue[pathAndValue.length - 1]);
        save();
    }

    public void save() throws ConfigurateException {
        ConfigurationLoader<? extends ConfigurationNode> loader = YamlConfigurationLoader.builder().path(configPath).build();
        loader.save(this.config);
    }
}
