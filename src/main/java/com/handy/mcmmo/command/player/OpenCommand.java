package com.handy.mcmmo.command.player;

import com.handy.lib.command.IHandyCommandEvent;
import com.handy.lib.util.AssertUtil;
import com.handy.lib.util.BaseUtil;
import com.handy.mcmmo.McMmoView;
import com.handy.mcmmo.inventory.ViewGui;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 打开菜单
 *
 * @author handy
 */
public class OpenCommand implements IHandyCommandEvent {

    @Override
    public String command() {
        return "open";
    }

    @Override
    public String permission() {
        return "mcMmoView.open";
    }

    @Override
    public void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // 是否为玩家
        AssertUtil.notPlayer(sender, BaseUtil.getLangMsg("noPlayerFailureMsg"));
        Player player = (Player) sender;
        new BukkitRunnable() {
            @Override
            public void run() {
                Inventory inventory = ViewGui.getInstance().createGui(player);
                Bukkit.getScheduler().runTask(McMmoView.getInstance(), () -> player.openInventory(inventory));
            }
        }.runTaskAsynchronously(McMmoView.getInstance());
    }

}