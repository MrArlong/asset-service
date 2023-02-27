package com.macro.mall.service.asset;

import com.macro.mall.dto.*;
import com.macro.mall.model.AssetOrder;
import com.macro.mall.model.AssetOrderRoom;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 房间管理Service
 * Created by macro on 2018/4/26.
 */
public interface AssetOrderService {
    /**
     * 创建商品
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    int create(AssetOrderParam assetOrderParam);

    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    int updateOrder(AssetOrderParam assetOrderParam);

    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    int deleteOrder(Long orderId);

    /**
     * 根据商品编号获取更新信息
     */
    AssetOrderParam getUpdateInfo(Long id);

    /**
     * 更新商品
     */
    @Transactional
    int update(Long id, AssetOrderParam assetRoomParam);

    /**
     * 分页查询商品
     */
    List<AssetOrder> list(AssetOrderQueryParam assetRoomQueryParam, Integer pageSize, Integer pageNum);



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
    AssetOrder getBrand(Long id);

    /**
     * 根据商品名称或者货号模糊查询
     */
    List<AssetOrder> list(String keyword);

    int updateFactoryStatus(List<Long> ids, String zszt);

    int updateIsOccupancy(List<Long> ids, String zszt);

    /**
     * 删除房间
     */
    int deleteBrand(Long id);

    Long getOrderNum();

    List<AssetOrderRoomDto> getOrderRoom(Long id);

    List<AssetOrderRoomDto> lqyj(AssetRoomQueryParam assetRoomQueryParam, Integer pageSize, Integer pageNum);
    List<AssetOrderRoom> selectIsOccupancyList();


    int updateAll();
    int updateIsOccupancy(List<Long> ids);

    List<AssetOrderRoom> orderRoomms(Long roomId);
    List<AssetOrderRoom> orderRoommsList(List<Long> roomId);

    int downloadExcel(AssetOrderQueryParam param, HttpServletResponse response) ;
}
