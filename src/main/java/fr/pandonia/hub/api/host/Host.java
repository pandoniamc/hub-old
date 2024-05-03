package fr.pandonia.hub.api.host;

import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.server.Server;
import org.bukkit.ChatColor;

import java.util.Collection;

public class Host {

    private final String name;
    private final ChatColor color;
    private final Server server;
    private final HostType type;
    private final PandoniaPlayer owner;
    private final TeamSize teamSize;
    private final int borderSize;
    private final int pvpTime;
    private final int borderReductionTime;
    private final boolean isNetherEnabled;
    private final Collection<Scenario> scenarios;

    public Host(
            String name,
            ChatColor color,
            Server server,
            HostType type,
            PandoniaPlayer owner,
            TeamSize teamSize,
            int borderSize,
            int pvpTime,
            int borderReductionTime,
            boolean isNetherEnabled, Collection<Scenario> scenarios
    ) {
        this.name = name;
        this.color = color;
        this.server = server;
        this.type = type;
        this.owner = owner;
        this.teamSize = teamSize;
        this.borderSize = borderSize;
        this.pvpTime = pvpTime;
        this.borderReductionTime = borderReductionTime;
        this.isNetherEnabled = isNetherEnabled;
        this.scenarios = scenarios;
    }

    public String getDisplayName() {
        return color + name;
    }

    public Server getServer() {
        return server;
    }

    public HostType getType() {
        return type;
    }

    public PandoniaPlayer getOwner() {
        return owner;
    }

    public TeamSize getTeamSize() {
        return teamSize;
    }

    public int getBorderSize() {
        return borderSize;
    }

    public int getPvpTime() {
        return pvpTime;
    }

    public int getBorderReductionTime() {
        return borderReductionTime;
    }

    public boolean isNetherEnabled() {
        return isNetherEnabled;
    }

    public Collection<Scenario> getScenarios() {
        return scenarios;
    }
}
