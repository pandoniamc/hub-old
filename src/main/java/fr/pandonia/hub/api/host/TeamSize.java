package fr.pandonia.hub.api.host;

public enum TeamSize {

    FFA("FFA"),
    TO2("2v2"),
    TO3("3v3"),
    TO4("4v4");

    private final String name;

    TeamSize(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
