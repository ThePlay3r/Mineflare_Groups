package me.pljr.mineflare_groups.command.group.subcommand;

import me.pljr.mineflare_groups.group.Group;
import me.pljr.mineflare_groups.group.GroupManager;
import me.pljr.pljrapispigot.command.SubCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LeaveSubCommand extends SubCommand {

    private final GroupManager groupManager;

    public LeaveSubCommand(GroupManager groupManager) {
        super("leave", "group.leave.use");
        this.groupManager = groupManager;
    }

    @Override
    public void onPlayerCommand(@NotNull Player player, @NotNull List<String> args) {

        Group playerGroup = groupManager.getGroup(player.getUniqueId());

        if (playerGroup == null) {
            player.sendMessage("Nejste soucasti zadne skupiny!");
            return;
        }

        if (playerGroup.getLeader().equals(player.getUniqueId())) {
            playerGroup.broadcast("Skupina byla zrusena.");
        } else {
            playerGroup.broadcast(player.getName() + " opustil skupinu.");
        }
        groupManager.remove(player.getUniqueId());
    }
}
