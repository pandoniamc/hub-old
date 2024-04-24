package fr.pandonia.hub.guis;

import fr.mrmicky.fastinv.ItemBuilder;
import fr.pandonia.hub.api.game.Game;
import fr.pandonia.hub.api.game.GameType;
import fr.pandonia.hub.api.gui.Gui;
import fr.pandonia.hub.api.gui.GuiClick;
import fr.pandonia.hub.api.player.Group;
import fr.pandonia.hub.api.utils.ItemUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MainGui extends Gui {

    private static final int[] BACKGROUND_SLOTS = {0, 1, 2, 6, 7, 8, 45, 46, 47, 51, 52, 53};

    public MainGui(Group playerGroup) {
        super(playerGroup, 6, "§f(§c!§f) §aMenu Principal");

        for (int slot : BACKGROUND_SLOTS) {
            setItem(slot, new ItemBuilder(Material.STAINED_GLASS).data(DyeColor.ORANGE.ordinal()).name(ItemUtils.EMPTY_NAME).build());
        }

        // Side
        setItem(18,
                getMenuButton("§f§lServeur Customisé", Material.COMMAND_MINECART, Arrays.asList(
                        "§7Tout ce qu’il faut pour",
                        "§7démarrer et configurer",
                        "§7son §eserveur customisé §7!"
                ), GuiClick.OPEN_GUI),
                Group.LEGEND
        );

        setItem(26,
                getMenuButton("§6§lBoutique", Material.GOLD_INGOT, Arrays.asList(
                        "§7Cosmétiques ou grades, n’hésitez pas",
                        "§7à soutenir §3§lPandonia §7!",
                        "§7Tu peux obtenir divers avantages",
                        "§7en cliquant ici !"
                ), GuiClick.OPEN_GUI)
        );

        setItem(27,
                getMenuButton("§b§lInvitations", Material.SKULL_ITEM, Collections.singletonList(
                        "§7Vous avez §30 §7invitation(s)"
                ), GuiClick.OPEN_GUI)
        );

        setItem(35,
                getButton("§b§lLiens", Material.SIGN, Arrays.asList(
                        "          §b§l» §3§lPandonia §7§l: §dLiens §b§l«",
                        "§8▪ §fSite : §bhttps://pandonia.fr/",
                        "§8▪ §fDiscord : §bhttps://pandonia.fr/discord",
                        "§8▪ §fTwitter : §bhttps://twitter.com/PandoniaMC",
                        "§8▪ §fBoutique : §bhttps://store.pandonia.fr/"
                ))
        );

        // Games
        setItem(21, getGameButton(Game.ARENA));
        setItem(22, getGameButton(Game.UHC));
        setItem(23, getGameButton(Game.ENMU_PARTY));
        setItem(31, getGameButton(Game.CAPTURE_THE_SHEEP));

        // Footer
        setItem(48,
                getMenuButton("§2§lMenu Staff", Material.SKULL_ITEM, Collections.singletonList(
                        "§7Les meilleurs outils pour le Staff"
                ), GuiClick.OPEN_GUI),
                e -> new StaffGui(playerGroup).open((Player) e.getWhoClicked()),
                Group.STAFF
        );

        setItem(49, getButton("§a§lSpawn", Material.NETHER_STAR));

        setItem(50, getButton("§3§lProfil", Material.SKULL_ITEM));
    }

    private ItemStack getGameButton(Game game) {
        List<String> lore = new ArrayList<>();
        lore.add("§7Genre : §3" + game.getTypes().stream().map(GameType::getName).collect(Collectors.joining("/")));
        lore.add("");
        lore.add("§e§lDESCRIPTION");
        lore.addAll(game.getLore());
        lore.add("");
        lore.add("§e§lINFORMATIONS");
        lore.add("§fJoueurs : §90");
        lore.add("§fStatut : §aOuvert");
        lore.add("§fVersion : §91.8-1.20");
        lore.add("");
        lore.add("§3§l» §bCliquez pour rejoindre");

        return getButton(game.getName(), game.getMaterial(), lore);
    }
}
