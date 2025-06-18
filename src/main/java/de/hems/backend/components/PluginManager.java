package de.hems.backend.components;

import de.hems.Plugin;
import de.hems.backend.InternalPlugin.PluginMain;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PluginManager {

    private List<Plugin> plugins;

    public PluginManager() throws IOException, ClassNotFoundException {
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
    }

    public void enablePlugins() {
        plugins.forEach(Plugin::onEnable);
    }

    public void disablePlugins() {
        plugins.forEach(Plugin::onDisable);
    }
}
