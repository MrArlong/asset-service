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
public class AssetOrderQueryParam {
    @ApiModelProperty("订单编号")
    private String orderNum;
    @ApiModelProperty("租赁人")
    private String zlr ;
}
