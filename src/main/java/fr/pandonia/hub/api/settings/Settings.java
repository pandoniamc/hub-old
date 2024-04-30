package fr.pandonia.hub.api.settings;

import fr.pandonia.hub.api.settings.type.BooleanFriendsOnlyType;
import fr.pandonia.hub.api.settings.type.BooleanType;

public class Settings {

    private final Setting<BooleanFriendsOnlyType> privateMessages;
    private final Setting<BooleanType> mentions;
    private final Setting<BooleanType> friendRequests;
    private final Setting<BooleanFriendsOnlyType> playerVisibility;

    public Settings(BooleanFriendsOnlyType privateMessages, BooleanType mentions, BooleanType friendRequests, BooleanFriendsOnlyType playerVisibility) {
        this.privateMessages = new Setting<>(privateMessages);
        this.mentions = new Setting<>(mentions);
        this.friendRequests = new Setting<>(friendRequests);
        this.playerVisibility = new Setting<>(playerVisibility);
    }

    public Setting<BooleanFriendsOnlyType> getPrivateMessages() {
        return privateMessages;
    }

    public Setting<BooleanType> getMentions() {
        return mentions;
    }

    public Setting<BooleanType> getFriendRequests() {
        return friendRequests;
    }

    public Setting<BooleanFriendsOnlyType> getPlayerVisibility() {
        return playerVisibility;
    }
}
