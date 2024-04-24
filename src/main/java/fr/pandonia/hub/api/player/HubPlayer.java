package fr.pandonia.hub.api.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class HubPlayer {

    private final UUID id;
    private final Group group;

    public HubPlayer(UUID id, Group group) {
        this.id = id;
        this.group = group;
    }

    public UUID getId() {
        return id;
    }

    public Group getGroup() {
        return group;
    }

    public Player asBukkit() {
        return Bukkit.getPlayer(id);
    }

    public String getName() {
        return asBukkit().getName();
    }

    public boolean is(Group group) {
        return this.group.is(group);
    }
}
