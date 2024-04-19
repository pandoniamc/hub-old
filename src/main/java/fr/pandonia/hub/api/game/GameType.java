package fr.pandonia.hub.api.game;

public enum GameType {

    PVP("PvP"),
    SURVIVAL("Survival"),
    UHC("UHC");

    private final String name;

    GameType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
