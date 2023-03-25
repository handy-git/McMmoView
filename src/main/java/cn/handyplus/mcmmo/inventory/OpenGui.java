package cn.handyplus.mcmmo.inventory;

import cn.handyplus.lib.constants.BaseConstants;
import cn.handyplus.lib.core.StrUtil;
import cn.handyplus.lib.inventory.HandyInventory;
import cn.handyplus.lib.inventory.HandyInventoryUtil;
import cn.handyplus.lib.util.BaseUtil;
import cn.handyplus.lib.util.ItemStackUtil;
import cn.handyplus.mcmmo.constants.GuiTypeEnum;
import cn.handyplus.mcmmo.hook.PlaceholderApiUtil;
import cn.handyplus.mcmmo.util.ConfigUtil;
import com.gmail.nossr50.api.ExperienceAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人详情
 *
 * @author handy
 */
public class OpenGui {
    private OpenGui() {
    }

    private final static OpenGui INSTANCE = new OpenGui();

    public static OpenGui getInstance() {
        return INSTANCE;
    }

    /**
     * 创建gui
     *
     * @param player 玩家
     * @return gui
     */
    public Inventory createGui(Player player) {
        int openSize = ConfigUtil.CONFIG.getInt("openSize", BaseConstants.GUI_SIZE_54);
        HandyInventory handyInventory = new HandyInventory(GuiTypeEnum.MC_MMO_VIEW.getType(), BaseUtil.getLangMsg("title"), openSize);
        // 设置数据
        handyInventory.setPlayer(player);
        this.setInventoryDate(handyInventory);
        return handyInventory.getInventory();
    }

    /**
     * 设置数据
     *
     * @param handyInventory gui
     */
    public void setInventoryDate(HandyInventory handyInventory) {
        // 基础设置
        handyInventory.setGuiType(GuiTypeEnum.MC_MMO_VIEW.getType());
        // 1. 刷新
        HandyInventoryUtil.refreshInventory(handyInventory.getInventory());
        // 2.设置功能性菜单
        this.setFunctionMenu(handyInventory);
    }

    /**
     * 设置功能性菜单
     *
     * @param handyInventory GUI
     */
    private void setFunctionMenu(HandyInventory handyInventory) {
        Inventory inventory = handyInventory.getInventory();
        Map<Integer, Object> objMap = handyInventory.getObjMap();
        Player player = handyInventory.getPlayer();
        // 获取菜单
        ConfigurationSection configurationSection = ConfigUtil.LANG_CONFIG.getConfigurationSection("skill");
        if (configurationSection == null) {
            return;
        }
        // 一级目录
        Map<String, Object> values = configurationSection.getValues(false);
        for (String key : values.keySet()) {
            // 二级目录
            MemorySection memorySection = (MemorySection) values.get(key);
            if (memorySection == null) {
                continue;
            }
            String indexStrList = memorySection.getString("index");
            List<Integer> indexList = StrUtil.strToIntList(indexStrList);
            String material = memorySection.getString("material");

            String name = memorySection.getString("name");
            List<String> loreList = memorySection.getStringList("lore");
            int customModelDataId = memorySection.getInt("custom-model-data");
            boolean enchant = memorySection.getBoolean("isEnchant", false);
            boolean hideFlag = memorySection.getBoolean("hideFlag", true);
            boolean hideEnchant = memorySection.getBoolean("hideEnchant", true);
            for (Integer index : indexList) {
                name = PlaceholderApiUtil.set(player, name);
                loreList = PlaceholderApiUtil.set(player, loreList);
                ItemStack itemStack = ItemStackUtil.getItemStack(material, name, loreList, enchant, customModelDataId, hideFlag, this.replaceMap(handyInventory.getPlayer(), key), hideEnchant);
                inventory.setItem(index, itemStack);
                if ("pane".equalsIgnoreCase(key) || "me".equalsIgnoreCase(key)) {
                    continue;
                }
                if ("back".equalsIgnoreCase(key)) {
                    objMap.put(index, memorySection.getString("command"));
                    continue;
                }
                objMap.put(index, key);
            }
        }
    }

    /**
     * 替换map
     *
     * @param player 玩家
     * @param key    类型
     * @return map
     */
    private Map<String, String> replaceMap(Player player, String key) {
        HashMap<String, String> replaceMap = new HashMap<>();
        if ("pane".equalsIgnoreCase(key) || "back".equalsIgnoreCase(key)) {
            return replaceMap;
        }
        if ("me".equalsIgnoreCase(key)) {
            replaceMap.put("all", ExperienceAPI.getPowerLevel(player) + "");
            return replaceMap;
        }
        if ("SALVAGE".equalsIgnoreCase(key) || "SMELTING".equalsIgnoreCase(key)) {
            replaceMap.put("xp", ExperienceAPI.getLevel(player, key) + "");
        } else {
            replaceMap.put("xp", ExperienceAPI.getLevel(player, key) + "");
            replaceMap.put("need", ExperienceAPI.getXPRemaining(player, key) + "");
            replaceMap.put("on", ExperienceAPI.getXPRaw(player, key) + "");
            replaceMap.put("next", ExperienceAPI.getXPToNextLevel(player, key) + "");
        }
        return replaceMap;
    }

}