package com.handy.mcmmo.util;

import com.handy.lib.api.LangMsgApi;
import com.handy.lib.api.MessageApi;
import com.handy.lib.constants.BaseConstants;
import com.handy.lib.core.StrUtil;
import com.handy.mcmmo.McMmoView;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * 配置加载
 *
 * @author handy
 */
public class ConfigUtil {
    public static FileConfiguration CONFIG, LANG_CONFIG;

    /**
     * 初始化加载文件
     */
    public static void enableConfig() {
        // 1:没有文件夹就创建
        if (!McMmoView.getInstance().getDataFolder().exists()) {
            boolean mkdir = McMmoView.getInstance().getDataFolder().mkdir();
            MessageApi.sendConsoleDebugMessage(mkdir + "error");
        }
        // 2:查询config没有就读取jar中的
        File configFile = new File(McMmoView.getInstance().getDataFolder(), "config.yml");
        if (!(configFile.exists())) {
            McMmoView.getInstance().saveDefaultConfig();
        }
        init();
    }

    /**
     * 加载全部配置
     */
    public static void init() {
        // 加载config
        loadConfig();
        // 加载语言文件
        loadLangConfig();
    }

    /**
     * 加载config
     */
    public static void loadConfig() {
        // 读取信息
        McMmoView.getInstance().reloadConfig();
        // 加载config
        CONFIG = McMmoView.getInstance().getConfig();
        BaseConstants.DEBUG = CONFIG.getBoolean("debug");
        String ip = CONFIG.getString("ip");
        if (StrUtil.isNotEmpty(ip)) {
            BaseConstants.IP = ip;
        }
    }

    /**
     * 加载lang文件
     */
    public static void loadLangConfig() {
        File file = new File(McMmoView.getInstance().getDataFolder(), "languages/" + CONFIG.getString("language") + ".yml");
        if (!(file.exists())) {
            McMmoView.getInstance().saveResource("languages/" + CONFIG.getString("language") + ".yml", false);
        }
        LANG_CONFIG = YamlConfiguration.loadConfiguration(file);
        // 加载语言到jar
        LangMsgApi.initLangMsg(LANG_CONFIG);
    }

}