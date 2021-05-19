package me.pljr.mineflare_groups.group;

import lombok.Getter;
import lombok.Setter;
import me.pljr.pljrapispigot.player.PlayerUtilKt;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Group {
    private final UUID leader;
    private String name;
    private List<UUID> members;
   private List<UUID> invited;

    public Group(UUID leader) {
        this.leader = leader;
        this.name = PlayerUtilKt.getName(leader);
        this.members = new ArrayList<>();
        this.invited = new ArrayList<>();
    }

    public void broadcast(String message) {
        members.forEach(member -> {
            OfflinePlayer player = Bukkit.getOfflinePlayer(member);
            if (player.isOnline()) {
                ((Player) player).sendMessage("[" + name + "] " + message);
            }
        });
    }
}
