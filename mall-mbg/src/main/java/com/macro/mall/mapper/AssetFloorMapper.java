package com.macro.mall.mapper;

import com.macro.mall.model.AssetFloor;
import com.macro.mall.model.AssetFloorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AssetFloorMapper {
    long countByExample(AssetFloorExample example);

    int deleteByExample(AssetFloorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AssetFloor row);

    int insertSelective(AssetFloor row);

    List<AssetFloor> selectByExample(AssetFloorExample example);

    AssetFloor selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") AssetFloor row, @Param("example") AssetFloorExample example);

    int updateByExample(@Param("row") AssetFloor row, @Param("example") AssetFloorExample example);

    int updateByPrimaryKeySelective(AssetFloor row);

    int updateByPrimaryKey(AssetFloor row);
}