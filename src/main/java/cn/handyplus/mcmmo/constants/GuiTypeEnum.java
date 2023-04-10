package cn.handyplus.mcmmo.constants;

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
    MC_MMO_VIEW("mc_mmo_view"),
    ;

    private final String type;

}