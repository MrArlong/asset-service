package com.macro.mall.mapper;

import com.macro.mall.dto.AssetOrderRoomDto;
import com.macro.mall.model.AssetOrderRoom;
import com.macro.mall.model.AssetOrderRoomExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AssetOrderRoomMapper {
    long countByExample(AssetOrderRoomExample example);

    int deleteByExample(AssetOrderRoomExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AssetOrderRoom row);

    int insertSelective(AssetOrderRoom row);

    List<AssetOrderRoom> selectByExample(AssetOrderRoomExample example);

    AssetOrderRoom selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") AssetOrderRoom row, @Param("example") AssetOrderRoomExample example);

    int updateByExample(@Param("row") AssetOrderRoom row, @Param("example") AssetOrderRoomExample example);

    int updateByPrimaryKeySelective(AssetOrderRoom row);

    int updateByPrimaryKey(AssetOrderRoom row);

    List<AssetOrderRoomDto> selectByOrderId(Long id);
}