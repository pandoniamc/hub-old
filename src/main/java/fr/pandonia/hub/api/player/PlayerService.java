package fr.pandonia.hub.api.player;

import java.util.List;
import java.util.UUID;

public interface PlayerService {

    HubPlayer loadData(UUID id);

    void unloadData(UUID id);

    HubPlayer getPlayer(UUID id);

    List<HubPlayer> getStaffList();
}
