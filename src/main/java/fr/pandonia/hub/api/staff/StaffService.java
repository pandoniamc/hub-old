package fr.pandonia.hub.api.staff;

import fr.pandonia.hub.api.player.PandoniaPlayer;

import java.util.List;

public interface StaffService {

    List<PandoniaPlayer> getStaffList(boolean connectedOnly);
}
