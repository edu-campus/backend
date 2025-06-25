package de.hems.backend.endpoints;

import de.hems.backend.components.PluginManager;
import de.hems.events.user.UserCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class Test {
    //TODO: remove

    private final PluginManager pluginManager;

    @Autowired
    public Test(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    @GetMapping("/enable")
    public void enable() {
        pluginManager.enablePlugins();
    }

    @GetMapping("/disable")
    public void disable() {
        pluginManager.disablePlugins();
    }

    @GetMapping("/test")
    public String test() {
        pluginManager.callEvent(new UserCreateEvent(UUID.randomUUID(), "Test", "<EMAIL>"));
        return "Test";
    }
}
