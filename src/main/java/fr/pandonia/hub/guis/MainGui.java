package fr.pandonia.hub.guis;

import fr.mrmicky.fastinv.ItemBuilder;
import fr.pandonia.hub.HubPlugin;
import fr.pandonia.hub.api.game.Game;
import fr.pandonia.hub.api.game.GameType;
import fr.pandonia.hub.api.gui.Gui;
import fr.pandonia.hub.api.player.Group;
import fr.pandonia.hub.api.player.HubPlayer;
import fr.pandonia.hub.api.utils.ItemUtils;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MainGui extends Gui {

    private static final int[] BACKGROUND_SLOTS = {0, 1, 2, 6, 7, 8, 45, 46, 47, 51, 52, 53};

    public MainGui() {
        super(6, "§f(§c!§f) §aMenu Principal");
    }

    @Override
    protected void configure(HubPlayer player) {
        for (int slot : BACKGROUND_SLOTS) {
            setItem(slot, new ItemBuilder(Material.STAINED_GLASS_PANE).data(DyeColor.ORANGE.ordinal()).name(ItemUtils.EMPTY_NAME).build());
        }

        // Side
        setGui(18, getButton("§f§lServeur Customisé", Material.COMMAND_MINECART, Arrays.asList(
                "§7Tout ce qu’il faut pour",
                "§7démarrer et configurer",
                "§7son §eserveur customisé §7!"
        )), new ServerGui(), player, Group.LEGEND);

        setGui(26, getButton("§6§lBoutique", Material.GOLD_INGOT, Arrays.asList(
                "§7Cosmétiques ou grades, n’hésitez pas",
                "§7à soutenir §3§lPandonia §7!",
                "§7Tu peux obtenir divers avantages",
                "§7en cliquant ici !"
        )), new ShopGui(), player);

        setGui(27, getButton("§b§lInvitations", ItemUtils.getPlayerSkull(), Collections.singletonList(
                "§7Vous avez §30 §7invitation(s)"
        )), new InvitationGui(), player);

        setItem(35, getButton("§b§lLiens", Material.SIGN, Arrays.asList(
                "          §b§l» §3§lPandonia §7§l: §dLiens §b§l«",
                "§8▪ §fSite : §bhttps://pandonia.fr/",
                "§8▪ §fDiscord : §bhttps://pandonia.fr/discord",
                "§8▪ §fTwitter : §bhttps://twitter.com/PandoniaMC",
                "§8▪ §fBoutique : §bhttps://store.pandonia.fr/"
        )));

        // Games
        setItem(21, getGameButton(Game.ARENA));
        setItem(22, getGameButton(Game.UHC));
        setItem(23, getGameButton(Game.ENMU_PARTY));
        setItem(31, getGameButton(Game.CAPTURE_THE_SHEEP));

        // Footer
        setGui(48, getButton("§2§lMenu Staff", ItemUtils.getPlayerSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDE5NjAxODNhMzVmZmVlM2Y1NDY2ZTY2YmFhYWNiYWFiMzVkYzJkMTQzODVmMDE3OWVlNmIzYWEzYzhmN2QwYyJ9fX0="), Collections.singletonList(
                "§7Les meilleurs outils pour le Staff"
        )), new StaffGui(), player, Group.STAFF);

        setItem(49, getButton("§a§lSpawn", Material.NETHER_STAR), e -> e.getWhoClicked().teleport((Location) HubPlugin.getConfiguration().get("spawn")));

        setGui(50, getButton("§3§lProfil", ItemUtils.getPlayerSkull(player.asBukkit())), new ProfileGui(), player);
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

        return getButton(game.getName(), game.getItem(), lore);
    }
}
