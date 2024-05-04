package fr.pandonia.hub.guis.root;

import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.server.Server;
import fr.pandonia.hub.api.utils.skull.SkullTypes;
import fr.pandonia.hub.api.utils.skull.SkullUtils;
import fr.pandonia.hub.guis.Gui;
import org.bukkit.DyeColor;

import java.util.Arrays;
import java.util.List;

public class HubGui extends Gui {

    private static final int[] BACKGROUND_SLOTS = {0, 1, 7, 8, 9, 17, 18, 19, 25, 26};

    public HubGui(PandoniaPlayer player, int currentHubId, List<Server> servers) {
        super(3, "Hubs", player);

        setBackground(DyeColor.BLUE, BACKGROUND_SLOTS);

        for (int i = 0; i < servers.size(); i++) {
            Server server = servers.get(i);

            setItem(11 + i, getItem("§bHub #" + server.getId(), SkullUtils.getSkull(SkullTypes.HUB), Arrays.asList(
                    "§7Joueurs : " + server.getPlayerCount(),
                    "§7Population : " + getPopulation(server),
                    "",
                    currentHubId == server.getId() ? "§cVous êtes déjà sur ce lobby" : "§3§l» §bCliquez pour rejoindre"
            )));
        }
    }

    private String getPopulation(Server server) {
        int playerCount = server.getPlayerCount();
        int maxPlayers = server.getMaxPlayers();

        if (playerCount < maxPlayers / 3) {
            return "§aFaible";
        } else if (playerCount < maxPlayers / 2) {
            return "§6Moyenne";
        } else {
            return "§cÉlevée";
        }
    }
}
