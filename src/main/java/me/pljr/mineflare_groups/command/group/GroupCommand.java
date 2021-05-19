package me.pljr.mineflare_groups.command.group;

import me.pljr.mineflare_groups.command.group.subcommand.AcceptSubCommand;
import me.pljr.mineflare_groups.command.group.subcommand.CreateSubCommand;
import me.pljr.mineflare_groups.command.group.subcommand.InviteSubCommand;
import me.pljr.mineflare_groups.command.group.subcommand.LeaveSubCommand;
import me.pljr.mineflare_groups.group.Group;
import me.pljr.mineflare_groups.group.GroupManager;
import me.pljr.pljrapispigot.command.MainCommand;
import me.pljr.pljrapispigot.player.PlayerUtilKt;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GroupCommand extends MainCommand {

    private final GroupManager groupManager;

    public GroupCommand(GroupManager groupManager) {
        super("group", "group.use");
        getSubCommands().add(new CreateSubCommand(groupManager));
        getSubCommands().add(new LeaveSubCommand(groupManager));
        getSubCommands().add(new InviteSubCommand(groupManager));
        getSubCommands().add(new AcceptSubCommand(groupManager));
        this.groupManager = groupManager;
    }

    @Override
    public void onPlayerCommand(@NotNull Player player, @NotNull List<String> args) {
        if (args.size() == 1) {
            String targetName = args.get(0);
            if (PlayerUtilKt.isPlayer(targetName)) {
                Player target = Bukkit.getPlayer(targetName);
                Group targetGroup = groupManager.getGroup(target.getUniqueId());
                if (targetGroup == null) {
                    player.sendMessage(target.getName() + " neni v zadne skupine.");
                } else {
                    player.sendMessage("Skupina " + target.getName());
                    player.sendMessage("Clenove:");
                    targetGroup.getMembers().forEach(member -> player.sendMessage(PlayerUtilKt.getName(member)));
                }
                return;
            }
        }
        player.sendMessage("Prikazy:");
        player.sendMessage("/group create - Vytvoreni skupiny");
        player.sendMessage("/group leave - Opusteni skupiny");
        player.sendMessage("/group invite {hrac} - Pozvani hrace/ky do skupiny");
        player.sendMessage("/group accept {hrac} - Prijmuti pozvanky do skupiny");
        player.sendMessage("/group {hrac} - Informace o skupine");
    }
}
