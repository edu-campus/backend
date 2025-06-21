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
        if (configurationManager.getConfig().node("first-time").getBoolean()) return;
        storageType = FileStorageType.valueOf(configurationManager.getConfig().node("settings", "storageType").getString());
    }

    public void saveFile() {

    }

    public void loadFile() {
    }
}
