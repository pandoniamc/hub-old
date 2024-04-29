package fr.pandonia.hub.api.server.configuration;

import java.util.List;

public interface GameService {

    GameConfiguration getConfiguration(int gameId);

    List<GameScenario> getScenarios(int gameId);
}
