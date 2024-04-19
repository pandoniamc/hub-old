package fr.pandonia.hub;

import com.samjakob.spigui.SpiGUI;
import fr.pandonia.hub.api.gui.GuiManager;
import fr.pandonia.hub.listeners.entity.EntityDamageListener;
import fr.pandonia.hub.listeners.entity.FoodLevelChangeListener;
import fr.pandonia.hub.listeners.inventory.InventoryClickListener;
import fr.pandonia.hub.listeners.player.PlayerDropItemListener;
import fr.pandonia.hub.listeners.player.PlayerInteractListener;
import fr.pandonia.hub.listeners.player.PlayerJoinListener;
import fr.pandonia.hub.listeners.weather.WeatherChangeListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HubPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Gui
        SpiGUI spigui = new SpiGUI(this);
        GuiManager guiManager = new GuiManager(spigui);

        registerListeners(
                new EntityDamageListener(),
                new FoodLevelChangeListener(),
                new InventoryClickListener(),
                new PlayerDropItemListener(),
                new PlayerInteractListener(guiManager),
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
