package com.macro.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品查询参数
 * Created by macro on 2018/4/27.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AssetRoomQueryParam {
    @ApiModelProperty("房间号模糊关键字")
    private String keyword;
    @ApiModelProperty("资产编号")
    private Long floorId;
}
