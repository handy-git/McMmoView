package cn.handyplus.mcmmo.command.player;

import cn.handyplus.lib.command.IHandyCommandEvent;
import cn.handyplus.lib.util.AssertUtil;
import cn.handyplus.lib.util.BaseUtil;
import cn.handyplus.mcmmo.McMmoView;
import cn.handyplus.mcmmo.inventory.OpenGui;
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
        Player player = AssertUtil.notPlayer(sender, BaseUtil.getLangMsg("noPlayerFailureMsg"));
        new BukkitRunnable() {
            @Override
            public void run() {
                Inventory inventory = OpenGui.getInstance().createGui(player);
                Bukkit.getScheduler().runTask(McMmoView.getInstance(), () -> player.openInventory(inventory));
            }
        }.runTaskAsynchronously(McMmoView.getInstance());
    }

}