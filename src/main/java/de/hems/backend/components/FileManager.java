package de.hems.backend.components;

import de.hems.backend.types.enums.FileStorageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileManager {

    private final ConfigurationManager configurationManager;
    private FileStorageType storageType;

    @Autowired
    public FileManager(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
        storageType = FileStorageType.valueOf(configurationManager.getConfig().getString("storage.type"));
    }

    public void saveFile() {

    }

    public void loadFile() {
    }
}
