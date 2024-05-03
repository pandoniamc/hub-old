package fr.pandonia.hub.api.statistics.game;

public class ArenaStatistics {

    private final int kills;
    private final int deaths;
    private final int streak;

    public ArenaStatistics(int kills, int deaths, int streak) {
        this.kills = kills;
        this.deaths = deaths;
        this.streak = streak;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getStreak() {
        return streak;
    }
}
