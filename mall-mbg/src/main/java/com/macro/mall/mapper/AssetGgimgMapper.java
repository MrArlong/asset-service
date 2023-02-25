package com.macro.mall.mapper;

import com.macro.mall.model.AssetGgimg;
import com.macro.mall.model.AssetGgimgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AssetGgimgMapper {
    long countByExample(AssetGgimgExample example);

    int deleteByExample(AssetGgimgExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AssetGgimg row);

    int insertSelective(AssetGgimg row);

    List<AssetGgimg> selectByExample(AssetGgimgExample example);

    AssetGgimg selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") AssetGgimg row, @Param("example") AssetGgimgExample example);

    int updateByExample(@Param("row") AssetGgimg row, @Param("example") AssetGgimgExample example);

    int updateByPrimaryKeySelective(AssetGgimg row);

    int updateByPrimaryKey(AssetGgimg row);
}