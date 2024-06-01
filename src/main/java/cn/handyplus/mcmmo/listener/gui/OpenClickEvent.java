package cn.handyplus.mcmmo.listener.gui;

import cn.handyplus.lib.core.StrUtil;
import cn.handyplus.lib.expand.adapter.PlayerSchedulerUtil;
import cn.handyplus.lib.inventory.HandyInventory;
import cn.handyplus.lib.inventory.IHandyClickEvent;
import cn.handyplus.mcmmo.constants.GuiTypeEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

/**
 * @author handy
 */
public class OpenClickEvent implements IHandyClickEvent {

    @Override
    public String guiType() {
        return GuiTypeEnum.MC_MMO_VIEW.getType();
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    @Override
    public void rawSlotClick(HandyInventory handyInventory, InventoryClickEvent event) {
        int rawSlot = event.getRawSlot();
        Player player = handyInventory.getPlayer();
        Map<Integer, String> strMap = handyInventory.getStrMap();
        String command = strMap.get(rawSlot);
        if (StrUtil.isEmpty(command)) {
            return;
        }
        handyInventory.syncClose();
        command = command.replace("${player}", player.getName());
        PlayerSchedulerUtil.syncPerformCommand(player, command.toLowerCase());
    }

}