package fr.pandonia.hub.api.settings;

import java.util.UUID;

public interface SettingsService {

    Settings getSettings(UUID playerId);

    void saveSettings(UUID playerId, Settings setting);
}
