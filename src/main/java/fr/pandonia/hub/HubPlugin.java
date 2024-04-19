package fr.pandonia.hub;

import fr.pandonia.hub.listeners.entity.EntityDamageListener;
import fr.pandonia.hub.listeners.entity.FoodLevelChangeListener;
import fr.pandonia.hub.listeners.inventory.InventoryClickListener;
import fr.pandonia.hub.listeners.player.PlayerDropItemListener;
import fr.pandonia.hub.listeners.player.PlayerJoinListener;
import fr.pandonia.hub.listeners.weather.WeatherChangeListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HubPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        registerListeners(
                new EntityDamageListener(),
                new FoodLevelChangeListener(),
                new InventoryClickListener(),
                new PlayerDropItemListener(),
                new PlayerJoinListener(),
                new WeatherChangeListener()
        );

        getLogger().info("Plugin enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled");
    }

    private void registerListeners(Listener... listeners) {
        PluginManager pluginManager = Bukkit.getPluginManager();

        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, this);
        }
    }
}
