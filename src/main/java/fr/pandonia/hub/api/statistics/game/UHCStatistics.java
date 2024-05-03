package fr.pandonia.hub.api.statistics.game;

public class UHCStatistics {

    private final int kills;
    private final int deaths;
    private final int gamesPlayed;
    private final int wins;

    public UHCStatistics(int kills, int deaths, int gamesPlayed, int wins) {
        this.kills = kills;
        this.deaths = deaths;
        this.gamesPlayed = gamesPlayed;
        this.wins = wins;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return gamesPlayed - wins;
    }
}
