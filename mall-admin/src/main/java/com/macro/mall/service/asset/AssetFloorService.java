package com.macro.mall.service.asset;

import com.macro.mall.dto.AssetFloorParam;
import com.macro.mall.dto.AssetRoomQueryParam;
import com.macro.mall.model.AssetFloor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 商品品牌管理Service
 * Created by macro on 2018/4/26.
 */
public interface AssetFloorService {
    /**
     * 获取所有品牌
     */
    List<AssetFloor> listAllBrand();

    /**
     * 创建资产
     */
    int createBrand(AssetFloorParam assetFloorParam);

    /**
     * 修改品牌
     */
    @Transactional
    int updateBrand(Long id, AssetFloorParam assetFloorParam);

    /**
     * 删除品牌
     */
    int deleteBrand(Long id);

    /**
     * 批量删除品牌
     */
    int deleteBrand(List<Long> ids);

    /**
     * 分页查询资产
     */
    List<AssetFloor> listBrand(String keyword, Integer showStatus, int pageNum, int pageSize);

    /**
     * 获取品牌详情
     */
    AssetFloor getBrand(Long id);

    /**
     * 修改显示状态
     */
    int updateShowStatus(List<Long> ids, Integer showStatus);

    /**
     * 修改展示状态
     */
    int updateFactoryStatus(List<Long> ids, String zszt);

    int updateSftjStatus(List<Long> ids, String sftj);

    List<AssetFloor> wxHostFloorList();

    List<AssetFloor> floorListAll(String syzt);

    Map<String,Object> getHometpAndGgtp();


    int downloadExcel(HttpServletResponse response) ;

    void importExcel(MultipartFile file) throws Exception;
}
