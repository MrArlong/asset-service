package com.macro.mall.dto;

import com.macro.mall.validator.FlagValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * 品牌请求参数
 * Created by macro on 2018/4/26.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AssetFloorParam {
    @NotEmpty
    @ApiModelProperty(value = "资产名称",required = true)
    private String name;

    @ApiModelProperty(value = "资产地址")
    private String address;

    @ApiModelProperty(value = "所属区域")
    private String region;

    @ApiModelProperty(value = "联系人")
    private String lxr;

    @ApiModelProperty(value = "联系电话")
    private String lxdh;

    @ApiModelProperty(value = "使用状态")
    private String syzt;

    @ApiModelProperty(value = "展示状态")
    private String zszt;

    @ApiModelProperty(value = "轮播图片")
    private String lbtp;

    @ApiModelProperty(value = "是否完工")
    private String isFinish;

    @Min(value = 0)
    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "地图经纬度")
    private String longitudeAndLatitude;


}
