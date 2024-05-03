package fr.pandonia.hub.api.statistics.game;

public class CaptureTheSheepStatistics {

    private final int kills;
    private final int deaths;
    private final int gamesPlayed;
    private final int wins;
    private final int captures;

    public CaptureTheSheepStatistics(int kills, int deaths, int gamesPlayed, int wins, int captures) {
        this.kills = kills;
        this.deaths = deaths;
        this.gamesPlayed = gamesPlayed;
        this.wins = wins;
        this.captures = captures;
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

    public int getCaptures() {
        return captures;
    }
}
