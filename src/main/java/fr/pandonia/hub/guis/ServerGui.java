package fr.pandonia.hub.guis;

import fr.pandonia.hub.api.gui.ChildGui;
import fr.pandonia.hub.api.gui.GuiType;
import fr.pandonia.hub.api.player.Group;
import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.server.Server;
import fr.pandonia.hub.api.server.configuration.GameConfiguration;
import fr.pandonia.hub.api.server.configuration.GameScenario;
import fr.pandonia.hub.api.utils.BukkitUtils;
import fr.pandonia.hub.api.utils.Pair;
import fr.pandonia.hub.api.utils.skull.SkullTypes;
import fr.pandonia.hub.api.utils.skull.SkullUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerGui extends ChildGui {

    private static final int[] BACKGROUND_SLOTS = {0, 1, 7, 8, 9, 17, 27, 35, 36, 37, 43, 44};

    private final List<Pair<Server, Pair<GameConfiguration, List<GameScenario>>>> servers;

    public ServerGui(PandoniaPlayer player, List<Pair<Server, Pair<GameConfiguration, List<GameScenario>>>> servers) {
        super(5, "Choix du serveur", player, GuiType.MAIN);

        this.servers = servers;

        setBackground(DyeColor.BROWN.ordinal(), BACKGROUND_SLOTS);

        setReturn(40);

        setProfile(2);

        setItem(4, getItem("§e§lExplications", Material.PAPER, Arrays.asList(
                "§9▪ §7Serveur customisé:",
                "§7Il s’agit d’un serveur",
                "§7accessible par §atous§7."
        )));

        setGui(6, getItem("§9§lCréer un serveur", SkullUtils.getSkull(SkullTypes.SERVER_CREATION), Arrays.asList(
                "§7Démarre ton serveur",
                "§7customisé §9public §7et amuse-toi !",
                "",
                "§c§l[!] Abus sanctionnables."
        )), GuiType.SERVER_CREATION);

        for (int i = 0; i < servers.size(); i++) {
            int row = 20 + (i / 5) * 9;
            int column = i % 5;

            setItem(row + column, getServerButton(player, i));
        }
    }

    private ItemStack getServerButton(PandoniaPlayer owner, int serverIndex) {
        Server server = servers.get(serverIndex).first();
        GameConfiguration configuration = servers.get(serverIndex).second().first();
        List<GameScenario> scenarios = servers.get(serverIndex).second().second();

        List<String> lore = new ArrayList<>(Arrays.asList(
                "§8Type de jeu " + configuration.getType().getColoredName(),
                "",
                "§fHôte: §e§l" + BukkitUtils.getPlayer(owner).getName(),
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

        for (GameScenario scenario : scenarios) {
            lore.add("§8▪ §7" + scenario.getName());
        }

        lore.addAll(Arrays.asList(
                "",
                "§c§l» §cTouche DROP pour fermer le serveur",
                "§3§l» §bCliquez pour rejoindre"
        ));

        return getItem(
                String.format("§e§lUHC-%d", server.getId()),
                SkullUtils.getSkull(owner.getGroup() == Group.FAMOUS ? SkullTypes.FAMOUS_OWNER : server.getState().getSkullType()),
                lore
        );
    }
}
