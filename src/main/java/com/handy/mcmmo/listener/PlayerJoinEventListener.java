package com.handy.mcmmo.listener;

import com.handy.lib.annotation.HandyListener;
import com.handy.lib.constants.BaseConstants;
import com.handy.lib.util.HandyHttpUtil;
import com.handy.mcmmo.constants.McMmoViewConstants;
import com.handy.mcmmo.util.ConfigUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * 登录事件
 *
 * @author handy
 */
@HandyListener
public class PlayerJoinEventListener implements Listener {

    /**
     * op进入服务器发送更新提醒
     *
     * @param event 事件
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onOpPlayerJoin(PlayerJoinEvent event) {
        // op登录发送更新提醒
        if (!ConfigUtil.CONFIG.getBoolean(BaseConstants.IS_CHECK_UPDATE_TO_OP_MSG)) {
            return;
        }
        HandyHttpUtil.checkVersion(event.getPlayer(), McMmoViewConstants.PLUGIN_VERSION_URL);
    }

}