package fr.pandonia.hub.guis.root;

import fr.pandonia.hub.api.server.ServerType;
import fr.pandonia.hub.configuration.TeleportLocation;
import fr.pandonia.hub.events.PlayerSendToServerEvent;
import fr.pandonia.hub.events.PlayerTeleportEvent;
import fr.pandonia.hub.api.player.Group;
import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.utils.BukkitUtils;
import fr.pandonia.hub.api.utils.skull.SkullTypes;
import fr.pandonia.hub.api.utils.skull.SkullUtils;
import fr.pandonia.hub.guis.Gui;
import fr.pandonia.hub.guis.GuiType;
import fr.pandonia.hub.guis.enums.Game;
import fr.pandonia.hub.guis.enums.GameType;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MainGui extends Gui {

    private static final int[] BACKGROUND_SLOTS = {0, 1, 2, 6, 7, 8, 45, 46, 47, 51, 52, 53};

    private final Map<ServerType, Integer> players;

    public MainGui(PandoniaPlayer player, Map<ServerType, Integer> players) {
        super(6, "Menu Principal", player);

        this.players = players;

        setBackground(DyeColor.ORANGE, BACKGROUND_SLOTS);

        setGui(18, getItem("§f§lServeur Customisé", Material.COMMAND_MINECART, Arrays.asList(
                "§7Tout ce qu’il faut pour",
                "§7démarrer et configurer",
                "§7son §eserveur customisé §7!"
        )), GuiType.HOST, Group.LEGEND);

        setGui(26, getItem("§6§lBoutique", Material.GOLD_INGOT, Arrays.asList(
                "§7Cosmétiques ou grades, n’hésitez pas",
                "§7à soutenir §3§lPandonia §7!",
                "§7Tu peux obtenir divers avantages",
                "§7en cliquant ici !"
        )), GuiType.SHOP);

        setGui(27, getItem("§b§lInvitations", SkullUtils.getPlayerSkull(), Collections.singletonList(
                "§7Vous avez §30 §7invitation(s)"
        )), GuiType.INVITATION);

        setItem(35, getItem("§b§lLiens", Material.SIGN, Arrays.asList(
                "          §b§l» §3§lPandonia §7§l: §dLiens §b§l«",
                "§8▪ §fSite : §bhttps://pandonia.fr/",
                "§8▪ §fDiscord : §bhttps://pandonia.fr/discord",
                "§8▪ §fTwitter : §bhttps://twitter.com/PandoniaMC",
                "§8▪ §fBoutique : §bhttps://store.pandonia.fr/"
        )));

        setGame(21, Game.ARENA, e -> BukkitUtils.callEvent(new PlayerSendToServerEvent((Player) e.getWhoClicked(), "arena")));
        setGame(22, Game.UHC, GuiType.UHC);
        setGame(23, Game.ENMU_PARTY, GuiType.ENMU_PARTY);
        setGame(31, Game.CAPTURE_THE_SHEEP, GuiType.CAPTURE_THE_SHEEP);

        setGui(48, getItem("§2§lMenu Staff", SkullUtils.getSkull(SkullTypes.STAFF), Collections.singletonList(
                "§7Les meilleurs outils pour le Staff"
        )), GuiType.STAFF, Group.STAFF);

        setItem(49, getItem("§a§lSpawn", Material.NETHER_STAR), e -> BukkitUtils.callEvent(new PlayerTeleportEvent((Player) e.getWhoClicked(), TeleportLocation.SPAWN)));

        setGui(50, getItem("§3§lProfil", SkullUtils.getPlayerSkull(player)), GuiType.PROFILE);
    }

    private void setGame(int slot, Game game, Consumer<InventoryClickEvent> handler) {
        List<String> lore = new ArrayList<>();
        lore.add("§7Genre : §3" + game.getTypes().stream().map(GameType::getName).collect(Collectors.joining("/")));
        lore.add("");
        lore.add("§e§lDESCRIPTION");
        lore.addAll(game.getLore());
        lore.add("");
        lore.add("§e§lINFORMATIONS");
        lore.add("§fJoueurs : §9" + players.getOrDefault(game.getServerType(), 0));
        lore.add("§fStatut : " + game.getStatus().getDisplayName());
        lore.add("§fVersion : §91.8-1.20");
        lore.add("");
        lore.add("§3§l» §bCliquez pour rejoindre");

        setItem(slot, getItem(game.getDisplayName(), game.getItem(), lore), handler);
    }

    private void setGame(int slot, Game game, GuiType gui) {
        setGame(slot, game, e -> openGui((Player) e.getWhoClicked(), gui));
    }
}
