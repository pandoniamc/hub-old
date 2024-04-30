package fr.pandonia.hub.api.staff;

import java.util.List;
import java.util.UUID;

public interface StaffService {

    List<UUID> getStaffList(boolean connectedOnly);
}
