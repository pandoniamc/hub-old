package fr.pandonia.hub.api.server.configuration;

public enum GameMode {

    FFA("FFA"),
    TO2("2v2"),
    TO3("3v3"),
    TO4("4v4");

    private final String name;

    GameMode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
