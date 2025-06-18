package de.hems.backend.endpoints;

import de.hems.backend.components.PluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
