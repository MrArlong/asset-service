package com.macro.mall.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AssetOrder implements Serializable {
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
    private Date zfsj;

    @ApiModelProperty(value = "支付方式")
    private String zffs;

    @ApiModelProperty(value = "总金额")
    private BigDecimal zje;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr;
    }

    public String getCzrlxdh() {
        return czrlxdh;
    }

    public void setCzrlxdh(String czrlxdh) {
        this.czrlxdh = czrlxdh;
    }

    public String getZlr() {
        return zlr;
    }

    public void setZlr(String zlr) {
        this.zlr = zlr;
    }

    public String getZlrlxdh() {
        return zlrlxdh;
    }

    public void setZlrlxdh(String zlrlxdh) {
        this.zlrlxdh = zlrlxdh;
    }

    public Date getZfsj() {
        return zfsj;
    }

    public void setZfsj(Date zfsj) {
        this.zfsj = zfsj;
    }

    public String getZffs() {
        return zffs;
    }

    public void setZffs(String zffs) {
        this.zffs = zffs;
    }

    public BigDecimal getZje() {
        return zje;
    }

    public void setZje(BigDecimal zje) {
        this.zje = zje;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", orderType=").append(orderType);
        sb.append(", czr=").append(czr);
        sb.append(", czrlxdh=").append(czrlxdh);
        sb.append(", zlr=").append(zlr);
        sb.append(", zlrlxdh=").append(zlrlxdh);
        sb.append(", zfsj=").append(zfsj);
        sb.append(", zffs=").append(zffs);
        sb.append(", zje=").append(zje);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}