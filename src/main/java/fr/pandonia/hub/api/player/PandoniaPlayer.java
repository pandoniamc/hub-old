package fr.pandonia.hub.api.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class PandoniaPlayer {

    private final UUID id;
    private final Group group;
    private final Date startGroupDate;

    public PandoniaPlayer(UUID id, Group group, Date startGroupDate) {
        this.id = id;
        this.group = group;
        this.startGroupDate = startGroupDate;
    }

    public UUID getId() {
        return id;
    }

    public Group getGroup() {
        return group;
    }

    public boolean is(Group group) {
        return this.group.is(group);
    }

    public Optional<Date> getEndGroupDate() {
        return group.getDuration().map(duration -> new Date(startGroupDate.getTime() + duration * 1000));
    }

    public Player asBukkit() {
        return Bukkit.getPlayer(id);
    }

    public String getName() {
        return asBukkit().getName();
    }

    public void teleport(Location location) {
        asBukkit().teleport(location);
    }
}
