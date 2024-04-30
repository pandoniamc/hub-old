package fr.pandonia.hub.api.server.game;

public enum GameScenario {

    CITY_WORLD("City-World");

    private final String name;

    GameScenario(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
