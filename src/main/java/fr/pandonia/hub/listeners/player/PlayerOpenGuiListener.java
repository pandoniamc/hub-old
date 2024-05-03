package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.events.PlayerOpenGuiEvent;
import fr.pandonia.hub.guis.Gui;
import fr.pandonia.hub.guis.GuiType;
import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.player.PlayerService;
import fr.pandonia.hub.api.host.Host;
import fr.pandonia.hub.api.host.HostService;
import fr.pandonia.hub.api.server.Server;
import fr.pandonia.hub.api.server.ServerService;
import fr.pandonia.hub.api.server.ServerType;
import fr.pandonia.hub.api.settings.Settings;
import fr.pandonia.hub.api.settings.SettingsService;
import fr.pandonia.hub.api.staff.StaffService;
import fr.pandonia.hub.guis.enums.Game;
import fr.pandonia.hub.guis.main.GameGui;
import fr.pandonia.hub.guis.main.HostGui;
import fr.pandonia.hub.guis.root.MainGui;
import fr.pandonia.hub.guis.root.ProfileGui;
import fr.pandonia.hub.guis.main.StaffGui;
import fr.pandonia.hub.guis.profile.SettingsGui;
import fr.pandonia.hub.guis.staff.StaffListGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerOpenGuiListener implements Listener {

    private final HostService hostService;
    private final PlayerService playerService;
    private final ServerService serverService;
    private final SettingsService settingsService;
    private final StaffService staffService;

    public PlayerOpenGuiListener(
            HostService hostService,
            PlayerService playerService, ServerService serverService,
            SettingsService settingsService,
            StaffService staffService
    ) {
        this.hostService = hostService;
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
                Map<ServerType, Integer> players = serverService.getPlayersCountByType();

                new MainGui(player, players).open(viewer);

                break;

            case PROFILE:
                new ProfileGui(player).open(viewer);

                break;

            case HOST: {
                List<Host> hosts = hostService.getHostsFromOwner(player);

                new HostGui(player, hosts).open(viewer);

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

            case CAPTURE_THE_SHEEP: {
                openGameGui(viewer, player, Game.CAPTURE_THE_SHEEP);

                break;
            }

            case ENMU_PARTY: {
                openGameGui(viewer, player, Game.ENMU_PARTY);

                break;
            }

            case UHC: {
                List<Host> hosts = hostService.getHosts();

                new GameGui<>(player, Game.UHC, hosts, Gui::getHostItem).open(viewer);

                break;
            }
        }
    }

    private void openGameGui(Player viewer, PandoniaPlayer player, Game game) {
        List<Server> servers = serverService.getServersByType(game.getServerType());

        new GameGui<>(player, game, servers, Gui::getServerItem).open(viewer);
    }

    private void openStaffListGui(Player viewer, PandoniaPlayer player, boolean connectedOnly) {
        List<PandoniaPlayer> staffList = staffService.getStaffList(connectedOnly).stream()
                .map(playerService::getPlayer)
                .collect(Collectors.toList());

        new StaffListGui(player, connectedOnly, staffList).open(viewer);
    }
}
