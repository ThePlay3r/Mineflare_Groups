package me.pljr.mineflare_groups.command.group.subcommand;

import me.pljr.mineflare_groups.group.GroupManager;
import me.pljr.pljrapispigot.command.SubCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CreateSubCommand extends SubCommand {

    private final GroupManager groupManager;

    public CreateSubCommand(GroupManager groupManager) {
        super("create", "group.create.use");
        this.groupManager = groupManager;
    }

    @Override
    public void onPlayerCommand(@NotNull Player player, @NotNull List<String> args) {
        if (groupManager.getGroup(player.getUniqueId()) != null) {
            player.sendMessage("Jiz mate skupinu!");
        } else {
            groupManager.create(player.getUniqueId());
            player.sendMessage("Skupina byla uspesne vytvorena!");
            player.sendMessage("Pro pozvani hracu, pouzijte:");
            player.sendMessage("/group invite {hrac}");
        }
    }
}
