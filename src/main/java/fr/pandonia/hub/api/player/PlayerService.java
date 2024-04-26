package fr.pandonia.hub.api.player;

import java.util.UUID;

public interface PlayerService {

    PandoniaPlayer cache(UUID playerId);

    void remove(UUID playerId);

    PandoniaPlayer get(UUID playerId);
}
