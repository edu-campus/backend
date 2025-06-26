package de.hems.backend.components;

import de.hems.backend.types.entitys.FileStorageEntity;
import de.hems.backend.types.enums.FileStorageType;
import de.hems.utils.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.util.UUID;

@Component
public class FileManager {

    private final ConfigurationManager configurationManager;
    private final DatabaseManager databaseManager;
    private FileStorageType storageType;

    @Autowired
    public FileManager(ConfigurationManager configurationManager, DatabaseManager databaseManager) {
        this.configurationManager = configurationManager;
        this.databaseManager = databaseManager;
        if (configurationManager.getConfig().node("first-time").getBoolean()) return;
        storageType = FileStorageType.valueOf(configurationManager.getConfig().node("settings", "storageType").getString());
    }

    public UploadedFile saveFile(byte[] file, String type, String name) {
        switch (storageType) {
            case DATABASE -> {
                FileStorageEntity fileStorageEntity = new FileStorageEntity(file, type, name);
                databaseManager.getSessionFactory().inTransaction(session -> {
                    session.persist(fileStorageEntity);
                });
                return new UploadedFile(fileStorageEntity.getId(), configurationManager.getConfig().node("url").getString());
            }
        }
        throw new IllegalStateException("Unexpected value: " + storageType);
    }

    public UploadedFile saveFile(File f) throws IOException {
        if (!f.exists()) throw new FileNotFoundException();
        byte[] bytes = Files.readAllBytes(f.toPath());
        return saveFile(bytes, f.getName().split("\\.")[1], f.getName());
    }

    public void loadFile() {
    }
}
