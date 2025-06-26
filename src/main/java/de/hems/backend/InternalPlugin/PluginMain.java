package de.hems.backend.InternalPlugin;

import de.hems.Plugin;
import de.hems.backend.InternalPlugin.events.UserCreateHandler;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class PluginMain extends Plugin {

    @Override
    public void onLoad() {
        System.out.println("Loading");
        System.out.println(getName());
        getEventManager().registerEvents(new UserCreateHandler());
        File file = new File("/home/bestimmtnichtben/Documents/hehe.png");
        try {
            byte[] bytes = FileUtils.readFileToByteArray(file);
            getFileUpload().upload(bytes, "image/png", "hehe.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
