package com.macro.mall.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class AssetOrderRoomDto  {
    private Long id;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "资产")
    private String floorName;

    @ApiModelProperty(value = "楼层")
    private String floorNum;

    @ApiModelProperty(value = "房间")
    private String roomNum;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "租赁开始时间")
    private Date beginTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "租赁结束时间")
    private Date endTime;

    @ApiModelProperty(value = "租赁单价")
    private BigDecimal price;

    private static final long serialVersionUID = 1L;
}