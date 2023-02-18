package com.macro.mall.mapper;

import com.macro.mall.model.AssetRoom;
import com.macro.mall.model.AssetRoomExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AssetRoomMapper {
    long countByExample(AssetRoomExample example);

    int deleteByExample(AssetRoomExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AssetRoom row);

    int insertSelective(AssetRoom row);

    List<AssetRoom> selectByExampleWithBLOBs(AssetRoomExample example);

    List<AssetRoom> selectByExample(AssetRoomExample example);

    AssetRoom selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") AssetRoom row, @Param("example") AssetRoomExample example);

    int updateByExampleWithBLOBs(@Param("row") AssetRoom row, @Param("example") AssetRoomExample example);

    int updateByExample(@Param("row") AssetRoom row, @Param("example") AssetRoomExample example);

    int updateByPrimaryKeySelective(AssetRoom row);

    int updateByPrimaryKeyWithBLOBs(AssetRoom row);

    int updateByPrimaryKey(AssetRoom row);
}