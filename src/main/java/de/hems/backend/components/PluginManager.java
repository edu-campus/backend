package de.hems.backend.components;

import de.hems.Plugin;
import de.hems.backend.InternalPlugin.PluginMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PluginManager {

    private List<Plugin> plugins;
    private final ConfigurationManager configurationManager;

    @Autowired
    public PluginManager(ConfigurationManager configurationManager) throws ClassNotFoundException {
        this.configurationManager = configurationManager;
        if (configurationManager.getConfig().node("first-time").getBoolean()) return;
        /*
        List<JarFile> jarFiles = new ArrayList<>();
        for (JarFile jarFile : jarFiles) {
            Manifest manifest = jarFile.getManifest();
            Attributes attributes = manifest.getMainAttributes();
            String mainClass = attributes.getValue("Main-Class");
            Class<?> clazz = Class.forName(mainClass, true, new URLClassLoader(jarFile., this.getClass().getClassLoader()));
        }
         */
        plugins = new ArrayList<>();
        plugins.add(new PluginMain());
        plugins.forEach(Plugin::onLoad);
        System.out.println("plugins");
    }

    public void enablePlugins() {
        plugins.forEach(Plugin::onEnable);
    }

    public void disablePlugins() {
        plugins.forEach(Plugin::onDisable);
    }
}
