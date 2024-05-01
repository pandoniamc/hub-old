package fr.pandonia.hub.api.settings.type;

public interface SettingType<T> {

    String getName();

    String getDisplayName();

    T getPersistedValue();
}
