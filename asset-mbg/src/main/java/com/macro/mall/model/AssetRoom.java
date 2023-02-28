package com.macro.mall.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AssetRoom implements Serializable {
    private Long id;

    @ApiModelProperty(value = "资产id")
    private String floorName;

    @ApiModelProperty(value = "资产id")
    private Long floorId;

    @ApiModelProperty(value = "房间号")
    private String roomNum;

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

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "展示状态0下架1上架")
    private String zszt;

    @ApiModelProperty(value = "房间描述")
    private String description;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(String floorNum) {
        this.floorNum = floorNum;
    }

    public String getAcreage() {
        return acreage;
    }

    public void setAcreage(String acreage) {
        this.acreage = acreage;
    }

    public String getDecorationType() {
        return decorationType;
    }

    public void setDecorationType(String decorationType) {
        this.decorationType = decorationType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIsOccupancy() {
        return isOccupancy;
    }

    public void setIsOccupancy(String isOccupancy) {
        this.isOccupancy = isOccupancy;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getZszt() {
        return zszt;
    }

    public void setZszt(String zszt) {
        this.zszt = zszt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", floorId=").append(floorId);
        sb.append(", roomNum=").append(roomNum);
        sb.append(", floorNum=").append(floorNum);
        sb.append(", acreage=").append(acreage);
        sb.append(", decorationType=").append(decorationType);
        sb.append(", price=").append(price);
        sb.append(", isOccupancy=").append(isOccupancy);
        sb.append(", sort=").append(sort);
        sb.append(", zszt=").append(zszt);
        sb.append(", description=").append(description);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}