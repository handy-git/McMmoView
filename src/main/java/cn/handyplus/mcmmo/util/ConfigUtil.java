package cn.handyplus.mcmmo.util;

import cn.handyplus.lib.util.HandyConfigUtil;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;

/**
 * 配置加载
 *
 * @author handy
 */
public class ConfigUtil {
    public static FileConfiguration CONFIG, LANG_CONFIG;

    /**
     * 加载全部配置
     */
    public static void init() {
        CONFIG = HandyConfigUtil.loadConfig();
        LANG_CONFIG = HandyConfigUtil.loadLangConfig(CONFIG.getString("language"));
        // 升级配置
        upConfig();
    }

    /**
     * 升级节点处理
     *
     * @since 1.2.8
     */
    public static void upConfig() {
        // 1.0.3 添加gui大小设置
        HandyConfigUtil.setPathIsNotContains(CONFIG, "openSize", 54, Collections.singletonList("open打开的Gui大小 一行9列,一共6行 如果修改了这个语言文件夹中对应的index必须修改正确!不然会报错"), "config.yml");
        CONFIG = HandyConfigUtil.load("config.yml");
        // 1.0.3 添加語言文件
        HandyConfigUtil.setPathIsNotContains(LANG_CONFIG, "placeholderAPISucceedMsg", "&a成功加载placeholderApi,启用自定义变量功能!", null, "languages/" + CONFIG.getString("language") + ".yml");
        HandyConfigUtil.setPathIsNotContains(LANG_CONFIG, "placeholderAPIFailureMsg", "&4未找到placeholderApi,未启用自定义变量功能!", null, "languages/" + CONFIG.getString("language") + ".yml");
        LANG_CONFIG = HandyConfigUtil.load("languages/" + CONFIG.getString("language") + ".yml");
    }

}