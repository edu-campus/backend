package de.hems.backend.InternalPlugin.events;

import de.hems.events.EventHandler;
import de.hems.events.Listener;
import de.hems.events.user.UserCreateEvent;

public class UserCreateHandler implements Listener {

    @EventHandler
    public void onUserCreate(UserCreateEvent event){
        System.out.println(event.getName());
    }
}
