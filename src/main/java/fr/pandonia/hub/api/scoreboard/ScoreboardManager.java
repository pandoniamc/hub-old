package fr.pandonia.hub.api.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import fr.pandonia.hub.api.player.PandoniaPlayer;
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
        UUID playerId = player.getUniqueId();

        FastBoard board = new FastBoard(player);
        board.updateTitle("§9§lPandonia");

        scoreboards.put(playerId, board);
    }

    public void removePlayer(UUID playerId) {
        FastBoard board = scoreboards.remove(playerId);

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
            Player bukkitPlayer = board.getPlayer();
            UUID playerId = bukkitPlayer.getUniqueId();
            PandoniaPlayer player = playerService.get(playerId);

            board.updateLines(
                    "§a§l┃ PROFIL",
                    String.format(" §7» §fPseudo §8▪ §3%s", bukkitPlayer.getName()),
                    String.format(" §7» §fGrade §8▪ %s", player.getGroup().getColoredName()),
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
