package de.hems.backend.InternalPlugin;

import de.hems.Plugin;
import de.hems.backend.InternalPlugin.events.UserCreateHandler;

public class PluginMain extends Plugin {


    @Override
    public void onLoad() {
        getEventManager().registerEvents(new UserCreateHandler());
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
