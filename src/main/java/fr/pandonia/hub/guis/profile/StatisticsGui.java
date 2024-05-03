package fr.pandonia.hub.guis.profile;

import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.statistics.Statistics;
import fr.pandonia.hub.guis.ChildGui;
import fr.pandonia.hub.guis.GuiType;
import fr.pandonia.hub.guis.enums.Game;
import org.bukkit.DyeColor;

import java.util.Arrays;
import java.util.List;

public class StatisticsGui extends ChildGui {

    private static final int[] BACKGROUND_SLOTS = {0, 1, 6, 7};

    public StatisticsGui(PandoniaPlayer player, Statistics statistics) {
        super(1, "Statistiques", player, GuiType.PROFILE);

        setBackground(DyeColor.WHITE, BACKGROUND_SLOTS);

        setReturn(8);

        setStatItem(2, Game.ARENA, Arrays.asList(
                "§8▪ §7Kills : §c" + statistics.getArenaStatistics().getKills(),
                "§8▪ §7Morts : §c" + statistics.getArenaStatistics().getDeaths(),
                "§8▪ §7KillStreak : §e" + statistics.getArenaStatistics().getStreak()
        ));

        setStatItem(3, Game.UHC, Arrays.asList(
                "§8▪ §7Kills : §c" + statistics.getUhcStatistics().getKills(),
                "§8▪ §7Morts : §c" + statistics.getUhcStatistics().getDeaths(),
                "",
                "§8▪ §7Parties jouées : §e" + statistics.getUhcStatistics().getGamesPlayed(),
                "§8▪ §7Parties gagnées : §a" + statistics.getUhcStatistics().getWins(),
                "§8▪ §7Parties perdues : §c" + (statistics.getUhcStatistics().getLosses())
        ));

        setStatItem(4, Game.ENMU_PARTY, Arrays.asList(
                "§8▪ §7Kills : §c" + statistics.getEnmuPartyStatistics().getKills(),
                "§8▪ §7Morts : §c" + statistics.getEnmuPartyStatistics().getDeaths(),
                "",
                "§8▪ §7Parties jouées : §e" + statistics.getEnmuPartyStatistics().getGamesPlayed(),
                "§8▪ §7Parties gagnées : §a" + statistics.getEnmuPartyStatistics().getWins(),
                "§8▪ §7Parties perdues : §c" + (statistics.getEnmuPartyStatistics().getLosses())
        ));

        setStatItem(5, Game.CAPTURE_THE_SHEEP, Arrays.asList(
                "§8▪ §7Kills : §c" + statistics.getCaptureTheSheepStatistics().getKills(),
                "§8▪ §7Morts : §c" + statistics.getCaptureTheSheepStatistics().getDeaths(),
                "",
                "§8▪ §7Parties jouées : §e" + statistics.getCaptureTheSheepStatistics().getGamesPlayed(),
                "§8▪ §7Parties gagnées : §a" + statistics.getCaptureTheSheepStatistics().getWins(),
                "§8▪ §7Parties perdues : §c" + (statistics.getCaptureTheSheepStatistics().getLosses()),
                "§8▪ §7Moutons capturés : §a" + statistics.getCaptureTheSheepStatistics().getCaptures()
        ));
    }

    private void setStatItem(int slot, Game game, List<String> lore) {
        setItem(slot, getItem(game.getDisplayName(), game.getItem(), lore));
    }
}
