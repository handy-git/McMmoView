package cn.handyplus.mcmmo.listener.gui;

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
    public void rawSlotClick(HandyInventory handyInventory, InventoryClickEvent event) {
        int rawSlot = event.getRawSlot();
        Player player = handyInventory.getPlayer();
        Map<Integer, Object> objMap = handyInventory.getObjMap();
        Object obj = objMap.get(rawSlot);
        if (obj == null) {
            return;
        }
        String command = (String) obj;
        player.closeInventory();
        player.performCommand(command);
    }

}