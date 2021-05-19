package me.pljr.mineflare_groups.group;

import java.util.HashMap;
import java.util.UUID;

public class GroupManager {

    private final HashMap<UUID, Group> groups = new HashMap<>();

    public void create(UUID uuid) {
        groups.put(uuid, new Group(uuid));
    }

    public void remove(UUID uuid) {
        if (groups.containsKey(uuid)) {
            Group group = groups.get(uuid);
            if (group.getLeader() == uuid) {
                group.getMembers().forEach(groups::remove);
            }
            groups.remove(uuid);
        }
    }

    public Group getGroup(UUID uuid) {
        return groups.getOrDefault(uuid, null);
    }

    public void update(Group group) {
        groups.put(group.getLeader(), group);
        group.getMembers().forEach(member -> groups.put(member, group));
    }
}
