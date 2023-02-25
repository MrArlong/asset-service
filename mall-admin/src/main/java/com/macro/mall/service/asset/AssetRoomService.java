package com.macro.mall.service.asset;

import com.macro.mall.dto.*;
import com.macro.mall.model.AssetFloor;
import com.macro.mall.model.AssetRoom;
import com.macro.mall.model.PmsProduct;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 房间管理Service
 * Created by macro on 2018/4/26.
 */
public interface AssetRoomService {
    /**
     * 创建商品
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    int create(AssetRoomParam assetRoomParam);

    /**
     * 更新商品
     */
    @Transactional
    int update(Long id, AssetRoomParam assetRoomParam);

    /**
     * 分页查询商品
     */
    List<AssetRoom> list(AssetRoomQueryParam assetRoomQueryParam, Integer pageSize, Integer pageNum);

    /**
     * 批量修改审核状态
     * @param ids 产品id
     * @param verifyStatus 审核状态
     * @param detail 审核详情
     */
    @Transactional
    int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail);

    /**
     * 批量修改商品上架状态
     */
    int updatePublishStatus(List<Long> ids, Integer publishStatus);

    /**
     * 批量修改商品推荐状态
     */
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    /**
     * 批量修改新品状态
     */
    int updateNewStatus(List<Long> ids, Integer newStatus);

    /**
     * 批量删除商品
     */
    int updateDeleteStatus(List<Long> ids, Integer deleteStatus);

    /**
     * 获取品牌详情
     */
    AssetRoom getBrand(Long id);

    /**
     * 根据商品名称或者货号模糊查询
     */
    List<AssetRoom> list(String keyword);

    int updateFactoryStatus(List<Long> ids, String zszt);

    int updateIsOccupancy(List<Long> ids, String zszt);

    /**
     * 删除房间
     */
    int deleteBrand(Long id);

    Long getOrderNum();

    List<String> getLc(Long floorId);
    List<AssetRoom> getFj(Long floorId,String floor);

    List<AssetRoom> findByFloorId(Long floorId);

    Map<String, Object> homeRoomSum();

    List<Map<String, Object>> orderTj(Date beginTime, Date endTime);
}
