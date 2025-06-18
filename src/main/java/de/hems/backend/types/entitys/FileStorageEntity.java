package de.hems.backend.types.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class FileStorageEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private byte[] file;
    private String type;
    private String name;

    public FileStorageEntity(byte[] file, String type, String name) {
        this.file = file;
        this.type = type;
        this.name = name;
    }

    public FileStorageEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
