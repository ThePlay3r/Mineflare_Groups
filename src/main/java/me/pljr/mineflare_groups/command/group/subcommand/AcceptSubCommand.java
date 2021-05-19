package me.pljr.mineflare_groups.command.group.subcommand;

import me.pljr.mineflare_groups.group.Group;
import me.pljr.mineflare_groups.group.GroupManager;
import me.pljr.pljrapispigot.command.SubCommand;
import me.pljr.pljrapispigot.player.PlayerUtilKt;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AcceptSubCommand extends SubCommand {

    private final GroupManager groupManager;

    public AcceptSubCommand(GroupManager groupManager) {
        super("accept", "group.accept.use");
        this.groupManager = groupManager;
    }

    @Override
    public void onPlayerCommand(@NotNull Player player, @NotNull List<String> args) {
        if (args.size() != 1) {
            player.sendMessage("Pouziti: /group accept {hrac}");
            return;
        }

        if (groupManager.getGroup(player.getUniqueId()) != null) {
            player.sendMessage("Jiz jste ve skupine!");
            return;
        }

        String targetName = args.get(0);
        if (!PlayerUtilKt.isPlayer(targetName)) {
            player.sendMessage(targetName + " neni na serveru!");
            return;
        }

        Player target = Bukkit.getPlayer(targetName);
        Group targetGroup = groupManager.getGroup(target.getUniqueId());
        if (!targetGroup.getInvited().contains(player.getUniqueId())) {
            player.sendMessage("Nejste pozvani do teto skupiny!");
            return;
        }

        targetGroup.getInvited().remove(player.getUniqueId());
        targetGroup.getMembers().add(player.getUniqueId());
        groupManager.update(targetGroup);
        targetGroup.broadcast(player.getName() + " se pripojil/a!");
    }
}
