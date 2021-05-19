package me.pljr.mineflare_groups;

import lombok.Getter;
import me.pljr.mineflare_groups.command.group.GroupCommand;
import me.pljr.mineflare_groups.group.GroupManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Mineflare_Groups extends JavaPlugin {

    @Getter
    private static Mineflare_Groups instance;

    @Override
    public void onEnable() {
        instance = this;
        new GroupCommand( new GroupManager()).registerCommand(this);
    }
}
