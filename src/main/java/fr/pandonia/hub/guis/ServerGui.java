package fr.pandonia.hub.guis;

import fr.pandonia.hub.HubPlugin;
import fr.pandonia.hub.api.gui.Gui;
import fr.pandonia.hub.api.player.Group;
import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.player.PlayerService;
import fr.pandonia.hub.api.server.Server;
import fr.pandonia.hub.api.server.ServerService;
import fr.pandonia.hub.api.server.configuration.GameConfiguration;
import fr.pandonia.hub.api.server.configuration.GameScenario;
import fr.pandonia.hub.api.server.configuration.GameService;
import fr.pandonia.hub.api.utils.skull.SkullTypes;
import fr.pandonia.hub.api.utils.skull.SkullUtils;
import fr.pandonia.hub.guis.server.ServerCreationGui;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerGui extends Gui {

    private static final int[] BACKGROUND_SLOTS = {0, 1, 7, 8, 9, 17, 27, 35, 36, 37, 43, 44};

    private final GameService gameService = HubPlugin.getInstance().getGameService();
    private final PlayerService playerService = HubPlugin.getInstance().getPlayerService();
    private final ServerService serverService = HubPlugin.getInstance().getServerService();

    public ServerGui() {
        super( 5, "Choix du serveur");
    }

    @Override
    protected void configure(PandoniaPlayer player) {
        setBackground(DyeColor.BROWN.ordinal(), BACKGROUND_SLOTS);

        setReturn(40);

        setProfile(2, player);

        setItem(4, getButton("§e§lExplications", Material.PAPER, Arrays.asList(
                "§9▪ §7Serveur customisé:",
                "§7Il s’agit d’un serveur",
                "§7accessible par §atous§7."
        )));

        setGui(6, getButton("§9§lCréer un serveur", SkullUtils.getSkull(SkullTypes.SERVER_CREATION), Arrays.asList(
                "§7Démarre ton serveur",
                "§7customisé §9public §7et amuse-toi !",
                "",
                "§c§l[!] Abus sanctionnables."
        )), new ServerCreationGui(), player);

        List<Server> servers = serverService.getServersFromOwner(player.getId());

        for (int i = 0; i < servers.size(); i++) {
            Server server = servers.get(i);

            int row = 11 + (i / 5) * 9;
            int column = i % 5;

            setItem(row + column, getServerButton(server));
        }
    }

    private ItemStack getServerButton(Server server) {
        PandoniaPlayer owner = playerService.get(server.getOwnerId());

        int configurationId = server.getGameConfigurationId();
        GameConfiguration configuration = gameService.getConfiguration(configurationId);

        List<String> lore = new ArrayList<>(Arrays.asList(
                "§8Type de jeu " + configuration.getType().getColoredName(),
                "",
                "§fHôte: §e§l" + owner.getName(),
                "§fNom: " + server.getColoredName(),
                "§fPhase: " + server.getState().getColoredName(),
                String.format("§fJoueurs: §b%d§7/§9%d", server.getPlayerCount(), server.getMaxPlayers()),
                "",
                "§8Contenu",
                "§fMode: §a" + configuration.getMode().getName(),
                "§fBordure: §a" + configuration.getBorderSize(),
                "§fTemps:",
                "    §8▪ §7PvP: §e" + configuration.getPvpTime(),
                "    §8▪ §Bordure: §e" + configuration.getBorderReductionTime(),
                "§fNether: " + (configuration.isNetherEnabled() ? "§a✔" : "§c✖"),
                "",
                "§8Scénario(s)"
        ));

        List<GameScenario> scenarios = gameService.getScenarios(configurationId);

        for (GameScenario scenario : scenarios) {
            lore.add("§8▪ §7" + scenario.getName());
        }

        lore.addAll(Arrays.asList(
                "",
                "§c§l» §cTouche DROP pour fermer le serveur",
                "§3§l» §bCliquez pour rejoindre"
        ));

        return getButton(
                String.format("§e§lUHC-%d", server.getId()),
                SkullUtils.getSkull(owner.getGroup() == Group.FAMOUS ? SkullTypes.FAMOUS_OWNER : server.getState().getSkullType()),
                lore
        );
    }
}
