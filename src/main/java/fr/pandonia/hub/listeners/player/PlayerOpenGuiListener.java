package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.api.events.PlayerOpenGuiEvent;
import fr.pandonia.hub.api.gui.GuiType;
import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.player.PlayerService;
import fr.pandonia.hub.api.server.Server;
import fr.pandonia.hub.api.server.ServerService;
import fr.pandonia.hub.api.server.game.GameConfiguration;
import fr.pandonia.hub.api.server.game.GameScenario;
import fr.pandonia.hub.api.server.game.GameService;
import fr.pandonia.hub.api.settings.Settings;
import fr.pandonia.hub.api.settings.SettingsService;
import fr.pandonia.hub.api.staff.StaffService;
import fr.pandonia.hub.api.utils.Pair;
import fr.pandonia.hub.guis.MainGui;
import fr.pandonia.hub.guis.ProfileGui;
import fr.pandonia.hub.guis.ServerGui;
import fr.pandonia.hub.guis.StaffGui;
import fr.pandonia.hub.guis.profile.SettingsGui;
import fr.pandonia.hub.guis.staff.StaffListGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerOpenGuiListener implements Listener {

    private final GameService gameService;
    private final PlayerService playerService;
    private final ServerService serverService;
    private final SettingsService settingsService;
    private final StaffService staffService;

    public PlayerOpenGuiListener(
            GameService gameService,
            PlayerService playerService,
            ServerService serverService,
            SettingsService settingsService,
            StaffService staffService
    ) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.serverService = serverService;
        this.settingsService = settingsService;
        this.staffService = staffService;
    }

    @EventHandler
    public void onPlayerOpenGui(PlayerOpenGuiEvent event) {
        Player viewer = event.getPlayer();
        PandoniaPlayer player = event.getPandoniaPlayer();
        GuiType type = event.getType();

        switch (type) {
            case MAIN:
                new MainGui(player).open(viewer);

                break;

            case PROFILE:
                new ProfileGui(player).open(viewer);

                break;

            case SERVER: {
                List<Pair<Server, Pair<GameConfiguration, List<GameScenario>>>> servers = new ArrayList<>();

                for (Server server : serverService.getServersFromOwner(player.getId())) {
                    GameConfiguration gameConfiguration = gameService.getConfiguration(server.getGameConfigurationId());
                    List<GameScenario> gameScenarios = gameService.getScenarios(server.getGameConfigurationId());

                    servers.add(new Pair<>(server, new Pair<>(gameConfiguration, gameScenarios)));
                }

                new ServerGui(player, servers).open(viewer);

                break;
            }

            case STAFF:
                new StaffGui(player).open(viewer);

                break;

            case SETTINGS:
                Settings settings = settingsService.getSettings(player.getId());

                new SettingsGui(player, settings).open(viewer);

                break;

            case STAFF_ALL_LIST:
                openStaffListGui(viewer, player, false);

                break;

            case STAFF_CONNECTED_LIST:
                openStaffListGui(viewer, player, true);

                break;
        }
    }

    private void openStaffListGui(Player viewer, PandoniaPlayer player, boolean connectedOnly) {
        List<PandoniaPlayer> staffList = staffService.getStaffList(connectedOnly).stream()
                .map(playerService::getPlayer)
                .collect(Collectors.toList());

        new StaffListGui(player, connectedOnly, staffList).open(viewer);
    }
}
