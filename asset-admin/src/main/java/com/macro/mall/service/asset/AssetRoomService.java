package com.macro.mall.service.asset;

import com.macro.mall.dto.*;
import com.macro.mall.model.AssetRoom;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 房间管理Service
 * Created by macro on 2018/4/26.
 */
public interface AssetRoomService {
    /**
     * 创建房间
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    int create(AssetRoomParam assetRoomParam);

    /**
     * 更新房间
     */
    @Transactional
    int update(Long id, AssetRoomParam assetRoomParam);

    /**
     * 分页查询房间
     */
    List<AssetRoom> list(AssetRoomQueryParam assetRoomQueryParam, Integer pageSize, Integer pageNum);


    /**
     * 批量修改房间上架状态
     */
    int updatePublishStatus(List<Long> ids, Integer publishStatus);

    /**
     * 批量修改房间推荐状态
     */
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    /**
     * 批量修改新品状态
     */
    int updateNewStatus(List<Long> ids, Integer newStatus);

    /**
     * 批量删除房间
     */
    int updateDeleteStatus(List<Long> ids, Integer deleteStatus);

    /**
     * 获取品牌详情
     */
    AssetRoom getBrand(Long id);

    /**
     * 根据房间名称或者货号模糊查询
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

    List<AssetRoom> getFj(Long floorId, String floor);

    List<AssetRoom> findByFloorId(Long floorId);

    Map<String, Object> homeRoomSum();

    List<Map<String, Object>> orderTj(Date beginTime, Date endTime);

    int downloadExcel(AssetRoomQueryParam assetRoomParam, HttpServletResponse response);

    void importExcel(MultipartFile file) throws Exception;
}
