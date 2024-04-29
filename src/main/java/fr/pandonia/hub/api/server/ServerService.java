package fr.pandonia.hub.api.server;

import java.util.List;
import java.util.UUID;

public interface ServerService {

    List<Server> getServersFromOwner(UUID ownerId);
}
