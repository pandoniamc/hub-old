package fr.pandonia.hub.api.host;

public enum Scenario {

    CITY_WORLD("City-World");

    private final String name;

    Scenario(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
