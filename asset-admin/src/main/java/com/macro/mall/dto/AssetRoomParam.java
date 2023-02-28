package com.macro.mall.dto;

import com.macro.mall.model.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 房间的请求参数
 * Created by macro on 2018/4/26.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AssetRoomParam {
    private Long id;

    @NotEmpty
    @ApiModelProperty(value = "资产id")
    private Long floorId;

    @NotEmpty
    @ApiModelProperty(value = "房间号")
    private String roomNum;

    @NotEmpty
    @ApiModelProperty(value = "楼层号")
    private String floorNum;

    @ApiModelProperty(value = "面积")
    private String acreage;

    @ApiModelProperty(value = "装修")
    private String decorationType;

    @ApiModelProperty(value = "价格")
    private String price;

    @ApiModelProperty(value = "是否已租")
    private String isOccupancy;

    @Min(value = 0)
    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "房间描述")
    private String description;

    @ApiModelProperty(value = "展示状态")
    private String zszt;
}
