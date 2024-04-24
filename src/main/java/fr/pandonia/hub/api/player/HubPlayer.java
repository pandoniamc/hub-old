package fr.pandonia.hub.api.player;

public class HubPlayer {

    private final Group group;

    public HubPlayer(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public boolean is(Group group) {
        return this.group.is(group);
    }
}
