package com.macro.mall.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;

public class AssetFloor implements Serializable {
    private Long id;

    @ApiModelProperty(value = "资产名称")
    private String name;

    @ApiModelProperty(value = "资产地址")
    private String address;

    @ApiModelProperty(value = "所属区域")
    private String region;

    @ApiModelProperty(value = "联系人")
    private String lxr;

    @ApiModelProperty(value = "联系电话")
    private String lxdh;

    @ApiModelProperty(value = "使用状态（2出租，1建设中，0筹划中）")
    private String syzt;

    @ApiModelProperty(value = "展示状态（1上架0下架）")
    private String zszt;

    @ApiModelProperty(value = "轮播图片")
    private String lbtp;

    @ApiModelProperty(value = "首页图片")
    private String hometp;

    @ApiModelProperty(value = "是否完工（1是0否）")
    private String isfinish;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "毛坯价格")
    private BigDecimal price;

    @ApiModelProperty(value = "精装价格")
    private BigDecimal jzprice;

    @ApiModelProperty(value = "简介")
    private String remark;

    @ApiModelProperty(value = "是否热门推荐")
    private String sftj;

    @ApiModelProperty(value = "总数")
    private Integer sum;
    @ApiModelProperty(value = "已租")
    private Integer totalLet;
    @ApiModelProperty(value = "未租")
    private Integer totalUnlet;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getTotalLet() {
        return totalLet;
    }

    public void setTotalLet(Integer totalLet) {
        this.totalLet = totalLet;
    }

    public Integer getTotalUnlet() {
        return totalUnlet;
    }

    public void setTotalUnlet(Integer totalUnlet) {
        this.totalUnlet = totalUnlet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSftj() {
        return sftj;
    }

    public void setSftj(String sftj) {
        this.sftj = sftj;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getSyzt() {
        return syzt;
    }

    public void setSyzt(String syzt) {
        this.syzt = syzt;
    }

    public String getZszt() {
        return zszt;
    }

    public void setZszt(String zszt) {
        this.zszt = zszt;
    }

    public String getLbtp() {
        return lbtp;
    }

    public void setLbtp(String lbtp) {
        this.lbtp = lbtp;
    }

    public String getHometp() {
        return hometp;
    }

    public void setHometp(String hometp) {
        this.hometp = hometp;
    }

    public String getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(String isfinish) {
        this.isfinish = isfinish;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public BigDecimal getJzprice() {
        return jzprice;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public void setJzprice(BigDecimal jzprice) {
        this.jzprice = jzprice;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", address=").append(address);
        sb.append(", region=").append(region);
        sb.append(", lxr=").append(lxr);
        sb.append(", lxdh=").append(lxdh);
        sb.append(", syzt=").append(syzt);
        sb.append(", zszt=").append(zszt);
        sb.append(", lbtp=").append(lbtp);
        sb.append(", isfinish=").append(isfinish);
        sb.append(", sort=").append(sort);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", price=").append(price);
        sb.append(", jzprice=").append(jzprice);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}