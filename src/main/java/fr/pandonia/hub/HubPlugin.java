package fr.pandonia.hub;

import fr.mrmicky.fastinv.FastInvManager;
import fr.pandonia.hub.configuration.Configuration;
import fr.pandonia.hub.api.friend.FriendService;
import fr.pandonia.hub.api.player.PlayerService;
import fr.pandonia.hub.scoreboard.ScoreboardManager;
import fr.pandonia.hub.api.server.ServerService;
import fr.pandonia.hub.api.host.HostService;
import fr.pandonia.hub.api.settings.SettingsService;
import fr.pandonia.hub.api.sql.HikariConnectionProvider;
import fr.pandonia.hub.api.staff.StaffService;
import fr.pandonia.hub.api.visibility.VisibilityManager;
import fr.pandonia.hub.listeners.entity.EntityDamageListener;
import fr.pandonia.hub.listeners.entity.FoodLevelChangeListener;
import fr.pandonia.hub.listeners.inventory.InventoryClickListener;
import fr.pandonia.hub.listeners.player.*;
import fr.pandonia.hub.listeners.weather.WeatherChangeListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HubPlugin extends JavaPlugin {

    private HikariConnectionProvider sqlConnectionProvider;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        Configuration configuration = new Configuration(getConfig());

        sqlConnectionProvider = new HikariConnectionProvider(configuration.getSqlCredentials());

        FastInvManager.register(this);

        PlayerService playerService = new PlayerService(sqlConnectionProvider);
        SettingsService settingsService = new SettingsService(sqlConnectionProvider);
        FriendService friendService = new FriendService(getLogger(), sqlConnectionProvider);
        ServerService serverService = new ServerService(getLogger(), sqlConnectionProvider);
        StaffService staffService = new StaffService(getLogger(), sqlConnectionProvider);
        HostService hostService = new HostService(getLogger(), sqlConnectionProvider, playerService, serverService);

        ScoreboardManager scoreboardManager = new ScoreboardManager(this, configuration, playerService);
        VisibilityManager visibilityManager = new VisibilityManager(friendService, settingsService);

        registerListeners(
                new EntityDamageListener(),
                new FoodLevelChangeListener(),
                new InventoryClickListener(),
                new PlayerChatListener(playerService),
                new PlayerDropItemListener(),
                new PlayerInteractListener(playerService),
                new PlayerJoinListener(playerService, settingsService, scoreboardManager, visibilityManager),
                new PlayerOpenGuiListener(hostService, playerService, serverService, settingsService, staffService),
                new PlayerQuitListener(playerService, settingsService, scoreboardManager),
                new PlayerTeleportListener(configuration),
                new PlayerUpdateSettingsListener(settingsService, visibilityManager),
                new WeatherChangeListener()
        );

        getLogger().info("Plugin enabled");
    }

    @Override
    public void onDisable() {
        if (sqlConnectionProvider != null) {
            sqlConnectionProvider.close();
        }

        getLogger().info("Plugin disabled");
    }

    private void registerListeners(Listener... listeners) {
        PluginManager pluginManager = Bukkit.getPluginManager();

        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, this);
        }
    }
}
