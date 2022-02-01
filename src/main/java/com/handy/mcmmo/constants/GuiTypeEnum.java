package com.handy.mcmmo.constants;

import com.handy.lib.util.BaseUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * gui类型
 *
 * @author handy
 */
@Getter
@AllArgsConstructor
public enum GuiTypeEnum {
    /**
     * gui类型
     */
    MC_MMO_VIEW("mc_mmo_view", BaseUtil.getLangMsg("title")),
    ;

    private final String type;
    private final String title;

}