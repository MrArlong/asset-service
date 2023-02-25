package com.macro.mall.service.impl.asset;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.macro.mall.dto.AssetFloorParam;
import com.macro.mall.mapper.AssetGgimgMapper;
import com.macro.mall.mapper.AssetFloorMapper;
import com.macro.mall.model.*;
import com.macro.mall.service.asset.AssetFloorService;
import com.macro.mall.service.asset.AssetRoomService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品品牌管理Service实现类
 * Created by macro on 2018/4/26.
 */
@Service
public class AssetFloorServiceImpl implements AssetFloorService {
    @Autowired
    private AssetFloorMapper assetFloorMapper;
    @Autowired
    private AssetRoomService assetRoomService;
    @Autowired
    private AssetGgimgMapper assetGgimgMapper;

    @Override
    public List<AssetFloor> listAllBrand() {
        return assetFloorMapper.selectByExample(new AssetFloorExample());
    }

    @Override
    public int createBrand(AssetFloorParam assetFloorParam) {
        if(StrUtil.isBlank(assetFloorParam.getSftj())){
            assetFloorParam.setSftj("0");
        }
        AssetFloor assetFloor = new AssetFloor();
        BeanUtils.copyProperties(assetFloorParam, assetFloor);
        return assetFloorMapper.insertSelective(assetFloor);
    }

    @Override
    public int updateBrand(Long id, AssetFloorParam assetFloorParam) {
        AssetFloor assetFloor = new AssetFloor();
        BeanUtils.copyProperties(assetFloorParam, assetFloor);
        assetFloor.setId(id);
        //更新品牌时要更新商品中的品牌名称
        /*PmsProduct product = new PmsProduct();
        product.setBrandName(assetFloor.getName());
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andBrandIdEqualTo(id);
        productMapper.updateByExampleSelective(product,example);*/
        return assetFloorMapper.updateByPrimaryKeySelective(assetFloor);
    }

    @Override
    public int deleteBrand(Long id) {
        return assetFloorMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteBrand(List<Long> ids) {
        AssetFloorExample assetFloorExample = new AssetFloorExample();
        assetFloorExample.createCriteria().andIdIn(ids);
        return assetFloorMapper.deleteByExample(assetFloorExample);
    }

    @Override
    public List<AssetFloor> listBrand(String keyword, Integer showStatus, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        AssetFloorExample pmsBrandExample = new AssetFloorExample();
        pmsBrandExample.setOrderByClause("sort asc");
        AssetFloorExample.Criteria criteria = pmsBrandExample.createCriteria();
        if (!StrUtil.isEmpty(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
        }
        return assetFloorMapper.selectByExample(pmsBrandExample);
    }

    @Override
    public AssetFloor getBrand(Long id) {
        return assetFloorMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        AssetFloor assetFloor = new AssetFloor();
        AssetFloorExample assetFloorExample = new AssetFloorExample();
        assetFloorExample.createCriteria().andIdIn(ids);
        return assetFloorMapper.updateByExampleSelective(assetFloor, assetFloorExample);
    }

    @Override
    public int updateFactoryStatus(List<Long> ids, String zszt) {
        AssetFloor assetFloor = new AssetFloor();
        assetFloor.setZszt(zszt);
        AssetFloorExample assetFloorExample = new AssetFloorExample();
        assetFloorExample.createCriteria().andIdIn(ids);
        return assetFloorMapper.updateByExampleSelective(assetFloor, assetFloorExample);
    }

    @Override
    public int updateSftjStatus(List<Long> ids, String sftj) {
        AssetFloor assetFloor = new AssetFloor();
        assetFloor.setSftj(sftj);
        AssetFloorExample assetFloorExample = new AssetFloorExample();
        assetFloorExample.createCriteria().andIdIn(ids);
        return assetFloorMapper.updateByExampleSelective(assetFloor, assetFloorExample);
    }

    @Override
    public List<AssetFloor> wxHostFloorList() {
        AssetFloorExample assetFloorExample = new AssetFloorExample();
        assetFloorExample.setOrderByClause("sort asc");
        AssetFloorExample.Criteria criteria = assetFloorExample.createCriteria();
        // 必须为上架
        criteria.andZsztEqualTo("1");
        // 必须为出租状态
        criteria.andSyztEqualTo("2");
        criteria.andSftjEqualTo("1");

        PageHelper.startPage(1,100);
        List<AssetFloor> assetFloors = assetFloorMapper.selectByExample(assetFloorExample);
        assetFloors.stream().forEach(a->{
            List<AssetRoom> byFloorId = assetRoomService.findByFloorId(a.getId());
            a.setSum(byFloorId.size());
            long totalUnlet = byFloorId.stream().filter(b -> b.getIsOccupancy().equals("0")).count();
            a.setTotalUnlet(new Long(totalUnlet).intValue());
            long totalLet = byFloorId.stream().filter(b -> b.getIsOccupancy().equals("1")).count();
            a.setTotalLet(new Long(totalLet).intValue());
        });
        return assetFloors;
    }

    @Override
    public List<AssetFloor> floorListAll(String syzt) {
        AssetFloorExample assetFloorExample = new AssetFloorExample();
        assetFloorExample.setOrderByClause("sort asc");
        AssetFloorExample.Criteria criteria = assetFloorExample.createCriteria();
       if(StringUtils.isNotBlank(syzt)){
           criteria.andSyztEqualTo(syzt);
       }
        List<AssetFloor> assetFloors = assetFloorMapper.selectByExample(assetFloorExample);
        assetFloors.stream().forEach(a->{
            List<AssetRoom> byFloorId = assetRoomService.findByFloorId(a.getId());
            a.setSum(byFloorId.size());
            long totalUnlet = byFloorId.stream().filter(b -> b.getIsOccupancy().equals("0")).count();
            a.setTotalUnlet(new Long(totalUnlet).intValue());
            long totalLet = byFloorId.stream().filter(b -> b.getIsOccupancy().equals("1")).count();
            a.setTotalLet(new Long(totalLet).intValue());
        });
        return assetFloors;
    }

    @Override
    public Map<String, Object> getHometpAndGgtp() {
        AssetGgimgExample ggimgExample = new AssetGgimgExample();
        AssetGgimgExample.Criteria criteria = ggimgExample.createCriteria();
            criteria.andZsztEqualTo("1");
        List<AssetGgimg> assetGgimgs = assetGgimgMapper.selectByExample(ggimgExample);
        Map<String,Object>map=new HashMap<>();
        String hometp="";
        String ggtp="";
        for(AssetGgimg a:assetGgimgs) {
            if(StrUtil.isNotBlank(a.getHometp())){
                if(StrUtil.isNotBlank(hometp)){
                    hometp=hometp+",";
                }
                hometp=hometp+a.getHometp();
            }
            if(StrUtil.isNotBlank(a.getLbtp())){
                if(StrUtil.isNotBlank(ggtp)){
                    ggtp=ggtp+",";
                }
                ggtp=ggtp+a.getLbtp();
            }
        }

        map.put("hometp",hometp);
        map.put("ggtp",ggtp);
        return  map;
    }
}
