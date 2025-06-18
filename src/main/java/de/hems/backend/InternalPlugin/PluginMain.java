package de.hems.backend.InternalPlugin;

import de.hems.Plugin;

public class PluginMain extends Plugin {

    public PluginMain() {
        super();
    }

    @Override
    public void onEnable() {
        System.out.println("PluginMain enabled");
        super.onEnable();
    }

    @Override
    public void onLoad() {
        System.out.println("PluginMain loaded");
        super.onLoad();
    }

    @Override
    public void onDisable() {
        System.out.println("PluginMain disabled");
        super.onDisable();
    }
}
