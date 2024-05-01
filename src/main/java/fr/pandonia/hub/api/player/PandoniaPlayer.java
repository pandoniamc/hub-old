package fr.pandonia.hub.api.player;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class PandoniaPlayer {

    private final UUID id;
    private final Group group;
    private final Date startGroupDate;
    private final int kamas;
    private final int hosts;
    private final int preWhitelist;
    private final int lootboxs;

    public PandoniaPlayer(UUID id, Group group, Date startGroupDate, int kamas, int hosts, int preWhitelist, int lootboxs) {
        this.id = id;
        this.group = group;
        this.startGroupDate = startGroupDate;
        this.kamas = kamas;
        this.hosts = hosts;
        this.preWhitelist = preWhitelist;
        this.lootboxs = lootboxs;
    }

    public UUID getId() {
        return id;
    }

    public Group getGroup() {
        return group;
    }

    public boolean is(Group group) {
        return this.group.is(group);
    }

    public Optional<Date> getEndGroupDate() {
        return group.getDuration().map(duration -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startGroupDate);
            calendar.add(Calendar.SECOND, Math.toIntExact(duration.getSeconds()));

            return calendar.getTime();
        });
    }

    public int getKamas() {
        return kamas;
    }

    public int getHosts() {
        return hosts;
    }

    public int getPreWhitelist() {
        return preWhitelist;
    }

    public int getLootboxs() {
        return lootboxs;
    }
}
