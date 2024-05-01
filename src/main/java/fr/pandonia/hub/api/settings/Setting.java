package fr.pandonia.hub.api.settings;

import fr.pandonia.hub.api.settings.type.SettingType;

public class Setting<T extends Enum<T> & SettingType<?>> {

    private T value;

    public Setting(T value) {
        this.value = value;
    }

    public T[] getValues() {
        return value.getDeclaringClass().getEnumConstants();
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
