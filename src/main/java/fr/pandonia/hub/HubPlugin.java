package fr.pandonia.hub;

import com.samjakob.spigui.SpiGUI;
import fr.pandonia.hub.api.gui.GuiManager;
import fr.pandonia.hub.api.player.PlayerService;
import fr.pandonia.hub.api.player.SqlPlayerService;
import fr.pandonia.hub.api.scoreboard.ScoreboardManager;
import fr.pandonia.hub.api.sql.HikariConnectionProvider;
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

    private HikariConnectionProvider connectionProvider;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // Sql
        connectionProvider = new HikariConnectionProvider(getConfig());

        // Gui
        SpiGUI spigui = new SpiGUI(this);
        GuiManager guiManager = new GuiManager(spigui);

        // Services
        PlayerService playerService = new SqlPlayerService(connectionProvider);

        ScoreboardManager scoreboardManager = new ScoreboardManager(this, playerService);

        registerListeners(
                new EntityDamageListener(),
                new FoodLevelChangeListener(),
                new InventoryClickListener(),
                new PlayerChatListener(playerService),
                new PlayerDropItemListener(),
                new PlayerInteractListener(guiManager),
                new PlayerJoinListener(scoreboardManager),
                new PlayerQuitListener(scoreboardManager),
                new WeatherChangeListener()
        );

        getLogger().info("Plugin enabled");
    }

    @Override
    public void onDisable() {
        if (connectionProvider != null) {
            connectionProvider.close();
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
