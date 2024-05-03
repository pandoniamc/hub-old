package fr.pandonia.hub.guis;

import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import fr.pandonia.hub.events.PlayerOpenGuiEvent;
import fr.pandonia.hub.api.host.Host;
import fr.pandonia.hub.api.host.Scenario;
import fr.pandonia.hub.api.player.Group;
import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.server.Server;
import fr.pandonia.hub.api.utils.BukkitUtils;
import fr.pandonia.hub.api.utils.DateUtils;
import fr.pandonia.hub.api.utils.skull.SkullTypes;
import fr.pandonia.hub.api.utils.skull.SkullUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public abstract class Gui extends FastInv {

    protected final PandoniaPlayer player;

    public Gui(int rows, String title, PandoniaPlayer player) {
        super(9 * rows, title);

        this.player = player;
    }

    public ItemStack getHostItem(Host host) {
        Server server = host.getServer();
        PandoniaPlayer owner = host.getOwner();
        Collection<Scenario> scenarios = host.getScenarios();

        List<String> lore = new ArrayList<>(Arrays.asList(
                "§8Type de jeu : " + host.getType().getDisplayName(),
                "",
                "§fHôte : §e§l" + BukkitUtils.getPlayer(owner).getName(),
                "§fNom : " + host.getDisplayName(),
                "§fPhase : " + server.getState().getDisplayName(),
                String.format("§fJoueurs : §b%d§7/§9%d", server.getPlayerCount(), server.getMaxPlayers()),
                "",
                "§8Contenu",
                "§fMode : §a" + host.getTeamSize().getName(),
                "§fBordure : §a" + host.getBorderSize(),
                "§fTemps :",
                "    §8▪ §7PvP : §e" + host.getPvpTime(),
                "    §8▪ §7Bordure : §e" + host.getBorderReductionTime(),
                "§fNether : " + (host.isNetherEnabled() ? "§a✔" : "§c✖"),
                "",
                "§8Scénario(s)"
        ));

        for (Scenario scenario : scenarios) {
            lore.add("§8▪ §7" + scenario.getName());
        }

        lore.add("");

        if (owner == player) {
            lore.add("§c§l» §cTouche DROP pour fermer le serveur");
        }

        lore.add("§3§l» §bCliquez pour y accéder");

        return getItem(
                String.format("§eUHC-%d", server.getId()),
                SkullUtils.getSkull(owner.getGroup() == Group.FAMOUS ? SkullTypes.FAMOUS_OWNER : server.getState().getSkullType()),
                lore
        );
    }

    public ItemStack getServerItem(Server server) {
        return getItem(
                String.format("%s-%d", server.getType().getDisplayPrefix(), server.getId()),
                SkullUtils.getSkull(server.getState().getSkullType()),
                Arrays.asList(
                        "§8Type de jeu : " + server.getType().getDisplayName(),
                        "",
                        "§fPhase : " + server.getState().getDisplayName(),
                        String.format("§fJoueurs : §b%d§7/§9%d", server.getPlayerCount(), server.getMaxPlayers()),
                        "",
                        "§8Contenu",
                        "§fMode : §aFFA",
                        "§fTemps :",
                        "    §8▪ §7PvP : §aActivé ✔",
                        "",
                        "§3§l» §bCliquez pour y accéder"
                )
        );
    }

    protected void setBackground(int color, int[] slots) {
        for (int slot : slots) {
            setItem(slot, new ItemBuilder(Material.STAINED_GLASS_PANE).data(color).name(" ").build());
        }
    }

    protected void setProfile(int slot) {
        setItem(slot, getItem("§3§lProfil", SkullUtils.getPlayerSkull(player), Arrays.asList(
                "§8▪ §fGrade : " + player.getGroup().getDisplayName(),
                "§8▪ §fTemps Restant : §3" + player.getEndGroupDate().map(DateUtils::format).orElse("Aucune Expiration"),
                "",
                String.format("§8▪ §fKamas : §e%d ⛁", player.getKamas()),
                String.format("§8▪ §fHosts : §6%d ✯", player.getHosts()),
                String.format("§8▪ §fPré-Wl : §c%d", player.getPreWhitelist())
        )));
    }

    protected void setGui(int slot, ItemStack item, GuiType type, Group requiredGroup) {
        if (!requiredGroup.is(Group.STAFF) || player.is(requiredGroup)) {
            ItemMeta meta = item.getItemMeta();
            List<String> lore = meta.getLore();

            if (requiredGroup != Group.PLAYER) {
                lore.add(0, "§8Accès : " + requiredGroup.getName());
                lore.add(1, "");
            }

            if (!lore.isEmpty()) {
                lore.add("");
                lore.add("§3§l» §bCliquez pour y accéder");
            }

            meta.setLore(lore);
            item.setItemMeta(meta);

            setItem(slot, item, e -> {
                if (player.is(requiredGroup)) {
                    openGui((Player) e.getWhoClicked(), type);
                }
            });
        }
    }

    protected void setGui(int slot, ItemStack item, GuiType gui) {
        setGui(slot, item, gui, Group.PLAYER);
    }

    protected ItemStack getItem(String name, ItemStack item, List<String> lore) {
        return new ItemBuilder(item)
                .name(name)
                .lore(lore)
                .flags()
                .build();
    }

    protected ItemStack getItem(String name, Material material, List<String> lore) {
        return getItem(name, new ItemStack(material), lore);
    }

    protected ItemStack getItem(String name, Material material) {
        return getItem(name, material, Collections.emptyList());
    }

    protected ItemStack getItem(String name, ItemStack item) {
        return getItem(name, item, Collections.emptyList());
    }

    protected void openGui(Player viewer, GuiType type) {
        BukkitUtils.callEvent(new PlayerOpenGuiEvent(viewer, player, type));
    }
}
