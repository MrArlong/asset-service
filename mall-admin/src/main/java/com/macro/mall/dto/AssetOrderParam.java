package com.macro.mall.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 房间的请求参数
 * Created by macro on 2018/4/26.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AssetOrderParam {
    private Long id;

    @ApiModelProperty(value = "订单编号")
    private String orderNum;

    @ApiModelProperty(value = "订单状态")
    private String orderType;

    @ApiModelProperty(value = "操作人")
    private String czr;

    @ApiModelProperty(value = "操作人联系电话")
    private String czrlxdh;

    @ApiModelProperty(value = "租赁人")
    private String zlr;

    @ApiModelProperty(value = "租赁人联系电话")
    private String zlrlxdh;

    @ApiModelProperty(value = "支付时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date zfsj;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "支付方式")
    private String zffs;

    @ApiModelProperty(value = "总金额")
    private BigDecimal zje;

    List<AssetOrderRoomParam> orderRoom;

}
