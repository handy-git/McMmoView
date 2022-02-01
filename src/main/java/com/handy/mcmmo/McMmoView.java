package com.handy.mcmmo;

import com.handy.lib.InitApi;
import com.handy.lib.api.MessageApi;
import com.handy.lib.constants.BaseConstants;
import com.handy.mcmmo.constants.McMmoViewConstants;
import com.handy.mcmmo.util.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 主类
 *
 * @author handy
 */
public class McMmoView extends JavaPlugin {
    private static McMmoView INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        InitApi initApi = InitApi.getInstance(this);
        // 加载配置文件
        ConfigUtil.enableConfig();
        initApi.initCommand("com.handy.mcmmo.command")
                .initSubCommand("com.handy.mcmmo.command")
                .initListener("com.handy.mcmmo.listener")
                .initClickEvent("com.handy.mcmmo.listener.gui")
                .addMetrics(14150)
                .checkVersion(ConfigUtil.CONFIG.getBoolean(BaseConstants.IS_CHECK_UPDATE), McMmoViewConstants.PLUGIN_VERSION_URL);
        ;
        MessageApi.sendConsoleMessage(ChatColor.GREEN + "已成功载入服务器！");
        MessageApi.sendConsoleMessage(ChatColor.GREEN + "Author:handy QQ群:1064982471");
    }

    @Override
    public void onDisable() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.closeInventory();
        }
        MessageApi.sendConsoleMessage("§a已成功卸载！");
        MessageApi.sendConsoleMessage("§aAuthor:handy QQ群:1064982471");
    }

    public static McMmoView getInstance() {
        return INSTANCE;
    }

}