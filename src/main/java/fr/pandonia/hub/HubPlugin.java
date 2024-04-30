package fr.pandonia.hub;

import fr.mrmicky.fastinv.FastInvManager;
import fr.pandonia.hub.api.player.PlayerService;
import fr.pandonia.hub.api.player.PlayerServiceImpl;
import fr.pandonia.hub.api.scoreboard.ScoreboardManager;
import fr.pandonia.hub.api.server.ServerService;
import fr.pandonia.hub.api.server.ServerServiceImpl;
import fr.pandonia.hub.api.server.configuration.GameService;
import fr.pandonia.hub.api.server.configuration.GameServiceImpl;
import fr.pandonia.hub.api.settings.SettingsService;
import fr.pandonia.hub.api.settings.SettingsServiceImpl;
import fr.pandonia.hub.api.sql.HikariConnectionProvider;
import fr.pandonia.hub.api.sql.SqlCredentials;
import fr.pandonia.hub.api.staff.StaffService;
import fr.pandonia.hub.api.staff.StaffServiceImpl;
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

        SqlCredentials sqlCredentials = SqlCredentials.fromConfiguration(getConfig());
        sqlConnectionProvider = new HikariConnectionProvider(sqlCredentials);

        FastInvManager.register(this);

        GameService gameService = new GameServiceImpl(getLogger(), sqlConnectionProvider);
        PlayerService playerService = new PlayerServiceImpl(sqlConnectionProvider);
        ServerService serverService = new ServerServiceImpl(getLogger(), sqlConnectionProvider);
        SettingsService settingsService = new SettingsServiceImpl(sqlConnectionProvider);
        StaffService staffService = new StaffServiceImpl(getLogger(), sqlConnectionProvider);

        ScoreboardManager scoreboardManager = new ScoreboardManager(this, playerService);

        registerListeners(
                new EntityDamageListener(),
                new FoodLevelChangeListener(),
                new InventoryClickListener(),
                new PlayerChatListener(playerService),
                new PlayerDropItemListener(),
                new PlayerInteractListener(playerService),
                new PlayerJoinListener(this, playerService, scoreboardManager),
                new PlayerOpenGuiListener(gameService, playerService, serverService, settingsService, staffService),
                new PlayerQuitListener(playerService, scoreboardManager),
                new PlayerTeleportListener(getConfig()),
                new PlayerUpdateSettingsListener(settingsService),
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
