package fr.pandonia.hub.api.server.configuration;

public class GameConfiguration {

    private final GameType type;
    private final GameMode mode;
    private final int borderSize;
    private final int pvpTime;
    private final int borderReductionTime;
    private final boolean isNetherEnabled;

    public GameConfiguration(GameType type, GameMode mode, int borderSize, int pvpTime, int borderReductionTime, boolean isNetherEnabled) {
        this.type = type;
        this.mode = mode;
        this.borderSize = borderSize;
        this.pvpTime = pvpTime;
        this.borderReductionTime = borderReductionTime;
        this.isNetherEnabled = isNetherEnabled;
    }

    public GameType getType() {
        return type;
    }

    public GameMode getMode() {
        return mode;
    }

    public int getBorderSize() {
        return borderSize;
    }

    public int getPvpTime() {
        return pvpTime;
    }

    public int getBorderReductionTime() {
        return borderReductionTime;
    }

    public boolean isNetherEnabled() {
        return isNetherEnabled;
    }
}
