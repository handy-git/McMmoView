package cn.handyplus.mcmmo;

import cn.handyplus.lib.InitApi;
import cn.handyplus.lib.constants.BaseConstants;
import cn.handyplus.lib.expand.adapter.HandySchedulerUtil;
import cn.handyplus.lib.inventory.HandyInventory;
import cn.handyplus.lib.util.BaseUtil;
import cn.handyplus.lib.util.MessageUtil;
import cn.handyplus.mcmmo.constants.McMmoViewConstants;
import cn.handyplus.mcmmo.util.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 主类
 *
 * @author handy
 */
public class McMmoView extends JavaPlugin {
    public static McMmoView INSTANCE;
    public static boolean USE_PAPI;

    @Override
    public void onEnable() {
        INSTANCE = this;
        InitApi initApi = InitApi.getInstance(this);
        // 加载配置文件
        ConfigUtil.init();
        // 加载Placeholder
        this.loadPlaceholder();
        initApi.initCommand("cn.handyplus.mcmmo.command")
                .initListener("cn.handyplus.mcmmo.listener")
                .initClickEvent("cn.handyplus.mcmmo.listener.gui")
                .addMetrics(14150)
                .checkVersion(ConfigUtil.CONFIG.getBoolean(BaseConstants.IS_CHECK_UPDATE), McMmoViewConstants.PLUGIN_VERSION_URL);

        MessageUtil.sendConsoleMessage(ChatColor.GREEN + "已成功载入服务器！");
        MessageUtil.sendConsoleMessage(ChatColor.GREEN + "Author:handy MCBBS:https://www.mcbbs.net/thread-1300127-1-1.html");
    }

    @Override
    public void onDisable() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            InventoryView openInventory = onlinePlayer.getOpenInventory();
            InventoryHolder holder = openInventory.getTopInventory().getHolder();
            if (holder instanceof HandyInventory) {
                onlinePlayer.closeInventory();
            }
        }
        HandySchedulerUtil.cancelTask();
        MessageUtil.sendConsoleMessage(ChatColor.GREEN + "已成功卸载！");
    }

    /**
     * 加载Placeholder
     */
    public void loadPlaceholder() {
        if (Bukkit.getPluginManager().getPlugin(BaseConstants.PLACEHOLDER_API) != null) {
            USE_PAPI = true;
            MessageUtil.sendConsoleMessage(BaseUtil.getMsgNotColor("placeholderAPISucceedMsg"));
            return;
        }
        USE_PAPI = false;
        MessageUtil.sendConsoleMessage(BaseUtil.getMsgNotColor("placeholderAPIFailureMsg"));
    }


}