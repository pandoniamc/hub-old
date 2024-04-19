package fr.pandonia.hub.guis;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import fr.pandonia.hub.api.game.Game;
import fr.pandonia.hub.api.game.GameType;
import fr.pandonia.hub.api.gui.Gui;
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

    public MainGui() {
        super("§f(§c!§f) §aMenu Principal", 6);
    }

    @Override
    public void configure(Player player, SGMenu menu) {
        int[] backgroundSlots = {0, 1, 2, 6, 7, 8, 45, 46, 47, 51, 52, 53};

        for (int slot : backgroundSlots) {
            menu.setButton(slot, getButton(ItemUtils.EMPTY_NAME, new ItemStack(Material.STAINED_GLASS, 1, (byte) DyeColor.ORANGE.ordinal())));
        }

        // Side
        if (player.hasPermission("menu.server")) {
            menu.setButton(18,
                    getMenuButton("§f§lServeur Customisé", new ItemStack(Material.COMMAND_MINECART), Arrays.asList(
                            "§7Tout ce qu’il faut pour",
                            "§7démarrer et configurer",
                            "§7son §eserveur customisé §7!"
                    ), "Légende")
            );
        }

        menu.setButton(27,
                getMenuButton("§b§lInvitations", ItemUtils.getPlayerSkull(), Collections.singletonList(
                        "§7Vous avez §30 §7invitation(s)"
                ))
        );

        menu.setButton(26,
                getMenuButton("§6§lBoutique", new ItemStack(Material.GOLD_INGOT), Arrays.asList(
                        "§7Cosmétiques ou grades, n’hésitez pas",
                        "§7à soutenir §3§lPandonia §7!",
                        "§7Tu peux obtenir divers avantages",
                        "§7en cliquant ici !"
                ))
        );

        menu.setButton(35,
                getButton("§b§lLiens", new ItemStack(Material.SIGN), Arrays.asList(
                        "          §b§l» §3§lPandonia §7§l: §dLiens §b§l«",
                        "§8▪ §fSite : §bhttps://pandonia.fr/",
                        "§8▪ §fDiscord : §bhttps://pandonia.fr/discord",
                        "§8▪ §fTwitter : §bhttps://twitter.com/PandoniaMC",
                        "§8▪ §fBoutique : §bhttps://store.pandonia.fr/"
                ))
        );

        // Games
        menu.setButton(21, getGameButton(Game.ARENA));
        menu.setButton(22, getGameButton(Game.UHC));
        menu.setButton(23, getGameButton(Game.ENMU_PARTY));
        menu.setButton(31, getGameButton(Game.CAPTURE_THE_SHEEP));

        // Footer
        if (player.hasPermission("menu.staff")) {
            menu.setButton(48,
                    getMenuButton("§2§lMenu Staff", ItemUtils.getPlayerSkull(), Collections.singletonList(
                            "§7Les meilleurs outils pour le Staff"
                    ), "Staff")
            );
        }
        menu.setButton(49, getButton("§a§lSpawn", new ItemStack(Material.NETHER_STAR)));
        menu.setButton(50, getButton("§3§lProfil", ItemUtils.getPlayerSkull()));
    }

    private SGButton getButton(String name, ItemStack item, List<String> lore) {
        return new SGButton(
                new ItemBuilder(item)
                        .name(name)
                        .lore(lore)
                        .build()
        );
    }

    private SGButton getButton(String name, ItemStack item) {
        return getButton(name, item, Collections.emptyList());
    }

    private SGButton getMenuButton(String name, ItemStack item, List<String> lore) {
        List<String> formattedLore = new ArrayList<>(lore);
        formattedLore.add("");
        formattedLore.add("§3§l» §bCliquez pour y accéder");

        return getButton(name, item, formattedLore);
    }

    private SGButton getMenuButton(String name, ItemStack item, List<String> lore, String access) {
        List<String> formattedLore = new ArrayList<>();
        formattedLore.add("§8Accès : " + access);
        formattedLore.add("");
        formattedLore.addAll(lore);

        return getMenuButton(name, item, formattedLore);
    }

    private SGButton getGameButton(Game game) {
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

        return getButton(game.getName(), game.getItem(), lore);
    }
}
