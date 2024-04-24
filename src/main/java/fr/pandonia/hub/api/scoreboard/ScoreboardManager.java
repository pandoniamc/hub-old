package fr.pandonia.hub.api.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import fr.pandonia.hub.api.player.HubPlayer;
import fr.pandonia.hub.api.player.PlayerService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManager {

    private static final String[] COLORS = {"§f", "§3", "§b", "§3"};

    private final Map<UUID, FastBoard> scoreboards = new HashMap<>();

    private final PlayerService playerService;

    public ScoreboardManager(Plugin plugin, PlayerService playerService) {
        this.playerService = playerService;

        new LobbyScoreboardRunnable().runTaskTimer(plugin, 0, 20);
    }

    public void addPlayer(Player player) {
        FastBoard board = new FastBoard(player);
        board.updateTitle("§9§lPandonia");

        scoreboards.put(player.getUniqueId(), board);
    }

    public void removePlayer(Player player) {
        FastBoard board = scoreboards.remove(player.getUniqueId());

        if (board != null) {
            board.delete();
        }
    }

    class LobbyScoreboardRunnable extends BukkitRunnable {

        private int tick = 0;

        @Override
        public void run() {
            tick = (tick + 1) % COLORS.length;

            scoreboards.values().forEach(this::updateBoard);
        }

        private void updateBoard(FastBoard board) {
            Player player = board.getPlayer();
            HubPlayer hubPlayer = playerService.getPlayer(player.getUniqueId());

            board.updateLines(
                    "§a§l┃ PROFIL",
                    String.format(" §7» §fPseudo §8▪ §3%s", player.getName()),
                    String.format(" §7» §fGrade §8▪ %s", hubPlayer.getGroup().getColoredName()),
                    "",
                    "§e§l┃ MONNAIES",
                    " §7» §fKamas §8▪ §e<kamas> ⛁",
                    " §7» §fHosts §8▪ §6<hosts> ✯",
                    " §7» §fPré-Wl §8▪ §c<prewl>",
                    " §7» §fLootbox §8▪ §d<lootbox>",
                    "",
                    "§3§l┃ SERVEUR",
                    " §7» §fHub §8▪ §fHub §9#<hub>",
                    String.format(" §7» §fJoueurs §8▪ §9%s", Bukkit.getOnlinePlayers().size()),
                    "",
                    String.format("      %smc.pandonia.fr", COLORS[tick])
            );
        }
    }
}
