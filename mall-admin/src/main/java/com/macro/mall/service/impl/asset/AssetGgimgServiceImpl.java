package com.macro.mall.service.impl.asset;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.macro.mall.dto.AssetGgimgParam;
import com.macro.mall.mapper.AssetGgimgMapper;
import com.macro.mall.model.AssetGgimg;
import com.macro.mall.model.AssetGgimgExample;
import com.macro.mall.service.asset.AssetGgimgService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品品牌管理Service实现类
 * Created by macro on 2018/4/26.
 */
@Service
public class AssetGgimgServiceImpl implements AssetGgimgService {
    @Autowired
    private AssetGgimgMapper AssetGgimgMapper;


    @Override
    public List<AssetGgimg> listAllBrand() {
        return AssetGgimgMapper.selectByExample(new AssetGgimgExample());
    }

    @Override
    public int createBrand(AssetGgimgParam AssetGgimgParam) {
        AssetGgimg AssetGgimg = new AssetGgimg();
        BeanUtils.copyProperties(AssetGgimgParam, AssetGgimg);
        return AssetGgimgMapper.insertSelective(AssetGgimg);
    }

    @Override
    public int updateBrand(Long id, AssetGgimgParam AssetGgimgParam) {
        AssetGgimg AssetGgimg = new AssetGgimg();
        BeanUtils.copyProperties(AssetGgimgParam, AssetGgimg);
        AssetGgimg.setId(id);
        //更新品牌时要更新商品中的品牌名称
        /*PmsProduct product = new PmsProduct();
        product.setBrandName(AssetGgimg.getName());
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andBrandIdEqualTo(id);
        productMapper.updateByExampleSelective(product,example);*/
        return AssetGgimgMapper.updateByPrimaryKeySelective(AssetGgimg);
    }

    @Override
    public int deleteBrand(Long id) {
        return AssetGgimgMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteBrand(List<Long> ids) {
        AssetGgimgExample AssetGgimgExample = new AssetGgimgExample();
        AssetGgimgExample.createCriteria().andIdIn(ids);
        return AssetGgimgMapper.deleteByExample(AssetGgimgExample);
    }

    @Override
    public List<AssetGgimg> listBrand(String keyword, Integer showStatus, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        AssetGgimgExample pmsBrandExample = new AssetGgimgExample();

        AssetGgimgExample.Criteria criteria = pmsBrandExample.createCriteria();
        if (!StrUtil.isEmpty(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
        }
        return AssetGgimgMapper.selectByExample(pmsBrandExample);
    }

    @Override
    public AssetGgimg getBrand(Long id) {
        return AssetGgimgMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        AssetGgimg AssetGgimg = new AssetGgimg();
        AssetGgimgExample AssetGgimgExample = new AssetGgimgExample();
        AssetGgimgExample.createCriteria().andIdIn(ids);
        return AssetGgimgMapper.updateByExampleSelective(AssetGgimg, AssetGgimgExample);
    }

    @Override
    public int updateFactoryStatus(List<Long> ids, String zszt) {
        AssetGgimgExample example = new AssetGgimgExample();
        AssetGgimgExample.Criteria criteria = example.createCriteria();
            criteria.andZsztEqualTo("1");
        List<AssetGgimg> assetGgimgs = AssetGgimgMapper.selectByExample(example);
        if(CollUtil.isNotEmpty(assetGgimgs)){
            if(zszt.equals("1")){
                AssetGgimg assetGgimg = new AssetGgimg();
                assetGgimg.setZszt("0");
                AssetGgimgExample AssetGgimgExample = new AssetGgimgExample();
                List<Long> collect = assetGgimgs.stream().map(AssetGgimg::getId).collect(Collectors.toList());
                AssetGgimgExample.createCriteria().andIdIn(collect);
                 AssetGgimgMapper.updateByExampleSelective(assetGgimg, AssetGgimgExample);
            }
        }

        AssetGgimg AssetGgimg = new AssetGgimg();
        AssetGgimg.setZszt(zszt);
        AssetGgimgExample AssetGgimgExample = new AssetGgimgExample();
        AssetGgimgExample.createCriteria().andIdIn(ids);
        return AssetGgimgMapper.updateByExampleSelective(AssetGgimg, AssetGgimgExample);
    }

}
