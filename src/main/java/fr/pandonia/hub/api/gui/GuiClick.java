package fr.pandonia.hub.api.gui;

public enum GuiClick {

    OPEN_GUI("Cliquez pour y accéder"),
    SWITCH("Cliquez pour changer l'état");

    private final String message;

    GuiClick(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
