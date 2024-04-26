package fr.pandonia.hub.guis;

import fr.pandonia.hub.api.gui.Gui;
import fr.pandonia.hub.api.player.HubPlayer;

public class ServerGui extends Gui {

    public ServerGui() {
        super( 5, "Choix du serveur");
    }

    @Override
    protected void configure(HubPlayer player) {

    }
}
