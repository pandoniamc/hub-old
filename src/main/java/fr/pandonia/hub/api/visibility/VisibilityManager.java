package fr.pandonia.hub.api.visibility;

import fr.pandonia.hub.api.friend.FriendService;
import fr.pandonia.hub.api.settings.Settings;
import fr.pandonia.hub.api.settings.SettingsService;
import fr.pandonia.hub.api.settings.type.BooleanFriendsOnlyType;
import org.bukkit.entity.Player;

import java.util.UUID;

public class VisibilityManager {

    private final FriendService friendService;
    private final SettingsService settingsService;

    public VisibilityManager(FriendService friendService, SettingsService settingsService) {
        this.friendService = friendService;
        this.settingsService = settingsService;
    }

    public void hidePlayerIfNeeded(Player player, Player target) {
        if (player.equals(target)) {
            return;
        }

        UUID playerId = player.getUniqueId();
        UUID targetId = target.getUniqueId();

        boolean isFriend = friendService.isFriend(playerId, targetId);

        Settings settings = settingsService.addSettingsInCache(playerId);
        BooleanFriendsOnlyType playerVisibility = settings.getPlayerVisibility().get();

        if (playerVisibility == BooleanFriendsOnlyType.DISABLED || playerVisibility == BooleanFriendsOnlyType.FRIENDS_ONLY && !isFriend) {
            player.hidePlayer(target);
        } else {
            player.showPlayer(target);
        }
    }
}
