package cn.handyplus.mcmmo;

import cn.handyplus.lib.InitApi;
import cn.handyplus.lib.constants.BaseConstants;
import cn.handyplus.lib.util.BaseUtil;
import cn.handyplus.lib.util.MessageUtil;
import cn.handyplus.mcmmo.util.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
                .checkVersion(ConfigUtil.CONFIG.getBoolean(BaseConstants.IS_CHECK_UPDATE));

        MessageUtil.sendConsoleMessage(ChatColor.GREEN + "已成功载入服务器!");
        MessageUtil.sendConsoleMessage(ChatColor.GREEN + "Author:handy WIKI: https://ricedoc.handyplus.cn/wiki/McMmoView");
    }

    @Override
    public void onDisable() {
        InitApi.disable();
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