package cn.handyplus.mcmmo;

import cn.handyplus.lib.InitApi;
import cn.handyplus.lib.api.MessageApi;
import cn.handyplus.lib.constants.BaseConstants;
import cn.handyplus.lib.inventory.HandyInventory;
import cn.handyplus.lib.util.BaseUtil;
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
    private static McMmoView INSTANCE;
    public static boolean USE_PAPI;

    @Override
    public void onEnable() {
        INSTANCE = this;
        InitApi initApi = InitApi.getInstance(this);
        // 加载Placeholder
        this.loadPlaceholder();
        // 加载配置文件
        ConfigUtil.init();
        initApi.initCommand("cn.handyplus.mcmmo.command")
                .initListener("cn.handyplus.mcmmo.listener")
                .initClickEvent("cn.handyplus.mcmmo.listener.gui")
                .addMetrics(14150)
                .checkVersion(ConfigUtil.CONFIG.getBoolean(BaseConstants.IS_CHECK_UPDATE), McMmoViewConstants.PLUGIN_VERSION_URL);

        MessageApi.sendConsoleMessage(ChatColor.GREEN + "已成功载入服务器！");
        MessageApi.sendConsoleMessage(ChatColor.GREEN + "Author:handy QQ群:1064982471");
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
        MessageApi.sendConsoleMessage("§a已成功卸载！");
        MessageApi.sendConsoleMessage("§aAuthor:handy QQ群:1064982471");
    }

    public static McMmoView getInstance() {
        return INSTANCE;
    }

    /**
     * 加载Placeholder
     */
    public void loadPlaceholder() {
        if (Bukkit.getPluginManager().getPlugin(BaseConstants.PLACEHOLDER_API) != null) {
            USE_PAPI = true;
            MessageApi.sendConsoleMessage(BaseUtil.getLangMsg("placeholderAPISucceedMsg"));
            return;
        }
        USE_PAPI = false;
        MessageApi.sendConsoleMessage(BaseUtil.getLangMsg("placeholderAPIFailureMsg"));
    }


}