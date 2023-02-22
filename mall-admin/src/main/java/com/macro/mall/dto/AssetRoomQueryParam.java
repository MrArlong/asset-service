package com.macro.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 商品查询参数
 * Created by macro on 2018/4/27.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AssetRoomQueryParam {
    @ApiModelProperty("订单编号")
    private String keyword;
    @ApiModelProperty("资产编号")
    private Long floorId;

    private Date beginTime;
    private Date endTime;
}
