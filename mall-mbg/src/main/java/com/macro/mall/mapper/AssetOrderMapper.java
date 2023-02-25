package com.macro.mall.mapper;

import com.macro.mall.model.AssetOrder;
import com.macro.mall.model.AssetOrderExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AssetOrderMapper {
    long countByExample(AssetOrderExample example);

    int deleteByExample(AssetOrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AssetOrder row);

    int insertSelective(AssetOrder row);

    List<AssetOrder> selectByExample(AssetOrderExample example);

    AssetOrder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") AssetOrder row, @Param("example") AssetOrderExample example);

    int updateByExample(@Param("row") AssetOrder row, @Param("example") AssetOrderExample example);

    int updateByPrimaryKeySelective(AssetOrder row);

    int updateByPrimaryKey(AssetOrder row);


    List<Map<String, Object>> selectOrderTj(AssetOrderExample assetOrderExample);
}