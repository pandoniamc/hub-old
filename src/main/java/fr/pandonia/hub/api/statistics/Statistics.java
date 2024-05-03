package fr.pandonia.hub.api.statistics;

import fr.pandonia.hub.api.statistics.game.ArenaStatistics;
import fr.pandonia.hub.api.statistics.game.CaptureTheSheepStatistics;
import fr.pandonia.hub.api.statistics.game.EnmuPartyStatistics;
import fr.pandonia.hub.api.statistics.game.UHCStatistics;

public class Statistics {

    private final ArenaStatistics arenaStatistics;
    private final CaptureTheSheepStatistics captureTheSheepStatistics;
    private final EnmuPartyStatistics enmuPartyStatistics;
    private final UHCStatistics uhcStatistics;

    public Statistics(ArenaStatistics arenaStatistics, CaptureTheSheepStatistics captureTheSheepStatistics, EnmuPartyStatistics enmuPartyStatistics, UHCStatistics uhcStatistics) {
        this.arenaStatistics = arenaStatistics;
        this.captureTheSheepStatistics = captureTheSheepStatistics;
        this.enmuPartyStatistics = enmuPartyStatistics;
        this.uhcStatistics = uhcStatistics;
    }

    public ArenaStatistics getArenaStatistics() {
        return arenaStatistics;
    }

    public CaptureTheSheepStatistics getCaptureTheSheepStatistics() {
        return captureTheSheepStatistics;
    }

    public EnmuPartyStatistics getEnmuPartyStatistics() {
        return enmuPartyStatistics;
    }

    public UHCStatistics getUhcStatistics() {
        return uhcStatistics;
    }
}
