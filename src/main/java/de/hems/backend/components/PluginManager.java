package de.hems.backend.components;

import de.hems.Plugin;
import de.hems.backend.InternalPlugin.PluginMain;
import de.hems.events.Event;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Component
public class PluginManager {

    private List<Plugin> plugins;
    private final ConfigurationManager configurationManager;
    private final FileManager fileManager;

    @Autowired
    public PluginManager(ConfigurationManager configurationManager, FileManager fileManager) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.configurationManager = configurationManager;
        this.fileManager = fileManager;
        plugins = new ArrayList<>();
        PluginMain coreplugin = new PluginMain();
        coreplugin.setup(fileManager::saveFile);
        coreplugin.onLoad();
        plugins.add(coreplugin);
        List<JarFile> jarFiles = new ArrayList<>();
        File pluginsFolder = new File("./plugins/");
        if (!pluginsFolder.exists()) pluginsFolder.mkdirs();
        File[] files = pluginsFolder.listFiles();
        if (files.length == 0) return;
        Arrays.stream(files).forEach(file -> {
            try {
                jarFiles.add(new JarFile(file));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        for (JarFile jarFile : jarFiles) {
            loadPlugin(jarFile);
        }
        plugins.forEach(Plugin::onLoad);
    }

    public void addPlugin(byte[] bytes, String name) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        File file = new File("./plugins/" + name);
        FileUtils.writeByteArrayToFile(file, bytes);
        loadPlugin(new JarFile(file));
    }

    private void loadPlugin(JarFile jarFile) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Enumeration<JarEntry> entries = jarFile.entries();
        Object plugin = null;
        while (entries.hasMoreElements()) {
            JarEntry e = entries.nextElement();
            if (e.getName().endsWith(".class")) {
                String className = e.getName().replace("/", ".").replace(".class", "");
                Class<?> clazz = this.getClass().getClassLoader().loadClass(className);
                if (Plugin.class.isAssignableFrom(clazz)) {
                    plugin = clazz.getDeclaredConstructor().newInstance();
                }
            }
        }
        if (plugin == null) {
            System.out.println("No plugin found in " + jarFile.getName());
            return;
        }
        plugins.add((Plugin) plugin);
        ((Plugin) plugin).setup(fileManager::saveFile);
        ((Plugin) plugin).onLoad();
    }

    public void enablePlugins() {
        plugins.forEach(Plugin::onEnable);
    }

    public void disablePlugins() {
        plugins.forEach(Plugin::onDisable);
    }

    public void callEvent(Event event) {
        plugins.forEach(plugin -> plugin.getEventManager().callEvent(event));
    }
}
