package me.pljr.mineflare_groups.command.group.subcommand;

import me.pljr.mineflare_groups.Mineflare_Groups;
import me.pljr.mineflare_groups.group.Group;
import me.pljr.mineflare_groups.group.GroupManager;
import me.pljr.pljrapispigot.command.SubCommand;
import me.pljr.pljrapispigot.player.PlayerUtilKt;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InviteSubCommand extends SubCommand {
    private static final int CONFIRM_TIME = 2400; // 2 Minutes

    private final GroupManager groupManager;

    public InviteSubCommand(GroupManager groupManager){
        super("invite", "group.invite.use");
        this.groupManager = groupManager;
    }

    @Override
    public void onPlayerCommand(@NotNull Player player, @NotNull List<String> args) {
        if (args.size() != 1) {
            player.sendMessage("Pouziti: /group invite {hrac}");
            return;
        }

        Group playerGroup = groupManager.getGroup(player.getUniqueId());
        if (playerGroup == null || playerGroup.getLeader() != player.getUniqueId()) {
            player.sendMessage("Nevlastnite zadnou skupinu!");
            return;
        }

        String targetName = args.get(0);
        if (!PlayerUtilKt.isPlayer(targetName)) {
            player.sendMessage(targetName + " neni na serveru!");
            return;
        }

        Player target = Bukkit.getPlayer(targetName);
        if (groupManager.getGroup(target.getUniqueId()) != null) {
            player.sendMessage(target.getName() + " jiz je v nejake skupine!");
            return;
        }


        if (playerGroup.getInvited().contains(target.getUniqueId())) {
            player.sendMessage("Tohoto hrace jste jiz pozvali!");
            return;
        }

        playerGroup.getInvited().add(target.getUniqueId());
        groupManager.update(playerGroup);
        player.sendMessage(target.getName() + " byl/a pozvan/a do skupiny!");
        target.sendMessage("Byli jste pozvani do skupiny!");
        target.sendMessage("Prijmete pomoci /group accept " + target.getName());

        Bukkit.getScheduler().runTaskLaterAsynchronously(Mineflare_Groups.getInstance(), () -> {
            if (playerGroup.getInvited().contains(target.getUniqueId())) {
                playerGroup.getInvited().remove(target.getUniqueId());
                groupManager.update(playerGroup);
                player.sendMessage(target.getName() + " neprijmul/a Vasi zadost!");
                target.sendMessage("Pozvanka do skupiny " + target.getName() + " vyprsela!");
            }
        }, CONFIRM_TIME);
    }
}
