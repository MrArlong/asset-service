package com.macro.mall.service.impl.asset;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.macro.mall.dao.*;
import com.macro.mall.dto.*;
import com.macro.mall.mapper.*;
import com.macro.mall.model.*;
import com.macro.mall.service.asset.AssetFloorService;
import com.macro.mall.service.asset.AssetOrderService;
import com.macro.mall.service.asset.AssetRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品管理Service实现类
 * Created by macro on 2018/4/26.
 */
@Service
public class AssetRoomServiceImpl implements AssetRoomService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssetRoomServiceImpl.class);
    @Autowired
    private AssetRoomMapper assetRoomMapper;
    @Autowired
    private AssetOrderRoomMapper assetOrderRoomMapper;
    @Autowired
    private AssetOrderService assetOrderService;
    @Autowired
    private AssetFloorMapper assetFloorMapper;
    @Autowired
    private PmsProductVertifyRecordDao productVertifyRecordDao;

    @Override
    public int create(AssetRoomParam assetRoomParam) {
        AssetRoom assetRoom = new AssetRoom();
        BeanUtils.copyProperties(assetRoomParam, assetRoom);
        return assetRoomMapper.insertSelective(assetRoom);
    }

    @Override
    public int update(Long id, AssetRoomParam assetRoomParam) {
        AssetRoom assetRoom = new AssetRoom();
        BeanUtils.copyProperties(assetRoomParam, assetRoom);
        assetRoom.setId(id);
        return assetRoomMapper.updateByPrimaryKeySelective(assetRoom);
    }

    @Override
    public List<AssetRoom> list(AssetRoomQueryParam assetRoomQueryParam, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        AssetRoomExample assetRoomExample = new AssetRoomExample();
        AssetRoomExample.Criteria criteria = assetRoomExample.createCriteria();
        if(assetRoomQueryParam.getKeyword() != null) {
            criteria.andRoomNumLike("%" + assetRoomQueryParam.getKeyword() + "%");
        }
        if(assetRoomQueryParam.getFloorId() != null) {
            criteria.andFloorIdEqualTo(assetRoomQueryParam.getFloorId());
        }
        List<AssetRoom> assetRooms = assetRoomMapper.selectByExample(assetRoomExample);
        assetRooms.stream().forEach(a -> {
            AssetFloor brand = assetFloorMapper.selectByPrimaryKey(a.getFloorId());
            if(Objects.nonNull(brand)){
                a.setFloorName(brand.getName());
            }
            List<AssetOrderRoom> assetOrderRooms = assetOrderService.orderRoomms(a.getId());
            if(CollUtil.isEmpty(assetOrderRooms)) {
                a.setIsOccupancy("0");
            }else {
                a.setIsOccupancy("1");
            }
        });
        return assetRooms;
    }

    @Override
    public int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail) {
        AssetRoom assetRoom = new AssetRoom();
        // assetRoom.setVerifyStatus(verifyStatus);
        AssetRoomExample example = new AssetRoomExample();
        example.createCriteria().andIdIn(ids);
        List<PmsProductVertifyRecord> list = new ArrayList<>();
        int count = assetRoomMapper.updateByExampleSelective(assetRoom, example);
        //修改完审核状态后插入审核记录
        for(Long id : ids) {
            PmsProductVertifyRecord record = new PmsProductVertifyRecord();
            record.setProductId(id);
            record.setCreateTime(new Date());
            record.setDetail(detail);
            record.setStatus(verifyStatus);
            record.setVertifyMan("test");
            list.add(record);
        }
        productVertifyRecordDao.insertList(list);
        return count;
    }

    @Override
    public int updatePublishStatus(List<Long> ids, Integer publishStatus) {
        AssetRoom assetRoom = new AssetRoom();
        //assetRoom.setPublishStatus(publishStatus);
        AssetRoomExample example = new AssetRoomExample();
        example.createCriteria().andIdIn(ids);
        return assetRoomMapper.updateByExampleSelective(assetRoom, example);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        AssetRoom assetRoom = new AssetRoom();
        //assetRoom.setRecommandStatus(recommendStatus);
        AssetRoomExample example = new AssetRoomExample();
        example.createCriteria().andIdIn(ids);
        return assetRoomMapper.updateByExampleSelective(assetRoom, example);
    }

    @Override
    public int updateNewStatus(List<Long> ids, Integer newStatus) {
        AssetRoom assetRoom = new AssetRoom();
        //assetRoom.setNewStatus(newStatus);
        AssetRoomExample example = new AssetRoomExample();
        example.createCriteria().andIdIn(ids);
        return assetRoomMapper.updateByExampleSelective(assetRoom, example);
    }

    @Override
    public int updateDeleteStatus(List<Long> ids, Integer deleteStatus) {
        AssetRoom assetRoom = new AssetRoom();
        //assetRoom.setDeleteStatus(deleteStatus);
        AssetRoomExample example = new AssetRoomExample();
        example.createCriteria().andIdIn(ids);
        return assetRoomMapper.updateByExampleSelective(assetRoom, example);
    }

    @Override
    public List<AssetRoom> list(String keyword) {
        AssetRoomExample assetRoomExample = new AssetRoomExample();
        AssetRoomExample.Criteria criteria = assetRoomExample.createCriteria();
        //criteria.andDeleteStatusEqualTo(0);
        if(!StrUtil.isEmpty(keyword)) {
            criteria.andRoomNumLike("%" + keyword + "%");
            //  assetRoomExample.or().andDeleteStatusEqualTo(0).andProductSnLike("%" + keyword + "%");
        }
        return assetRoomMapper.selectByExample(assetRoomExample);
    }

    @Override
    public AssetRoom getBrand(Long id) {
        return assetRoomMapper.selectByPrimaryKey(id);
    }

    /**
     * 建立和插入关系表操作
     * @param dao       可以操作的dao
     * @param dataList  要插入的数据
     * @param productId 建立关系的id
     */
    private void relateAndInsertList(Object dao, List dataList, Long productId) {
        try {
            if(CollectionUtils.isEmpty(dataList)) return;
            for(Object item : dataList) {
                Method setId = item.getClass().getMethod("setId", Long.class);
                setId.invoke(item, (Long) null);
                Method setProductId = item.getClass().getMethod("setProductId", Long.class);
                setProductId.invoke(item, productId);
            }
            Method insertList = dao.getClass().getMethod("insertList", List.class);
            insertList.invoke(dao, dataList);
        }catch(Exception e) {
            LOGGER.warn("创建产品出错:{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int updateFactoryStatus(List<Long> ids, String zszt) {
        AssetRoom assetRoom = new AssetRoom();
        assetRoom.setZszt(zszt);
        AssetRoomExample assetRoomExample = new AssetRoomExample();
        assetRoomExample.createCriteria().andIdIn(ids);
        return assetRoomMapper.updateByExampleSelective(assetRoom, assetRoomExample);
    }

    @Override
    public int updateIsOccupancy(List<Long> ids, String isOccupancy) {
        AssetRoom assetRoom = new AssetRoom();
        assetRoom.setIsOccupancy(isOccupancy);
        AssetRoomExample assetRoomExample = new AssetRoomExample();
        assetRoomExample.createCriteria().andIdIn(ids);
        return assetRoomMapper.updateByExampleSelective(assetRoom, assetRoomExample);
    }

    @Override
    public int deleteBrand(Long id) {
        return assetRoomMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Long getOrderNum() {
        return System.currentTimeMillis();
    }

    @Override
    public List<String> getLc(Long floorId) {

        AssetRoomExample assetRoomExample = new AssetRoomExample();
        AssetRoomExample.Criteria criteria = assetRoomExample.createCriteria();
        //criteria.andDeleteStatusEqualTo(0);
        if(floorId != null) {
            criteria.andFloorIdEqualTo(floorId);
        }
        List<AssetRoom> assetRooms = assetRoomMapper.selectByExample(assetRoomExample);
        Set<String> collect = assetRooms.stream().map(AssetRoom::getFloorNum).collect(Collectors.toSet());
        List<String> list = new ArrayList<>(collect);
        list.sort(Comparator.comparing(o -> o != null ? o : null, Comparator.nullsLast(String::compareTo)));
        return list;
    }

    @Override
    public List<AssetRoom> getFj(Long floorId, String floor) {

        AssetRoomExample assetRoomExample = new AssetRoomExample();
        AssetRoomExample.Criteria criteria = assetRoomExample.createCriteria();
        //criteria.andDeleteStatusEqualTo(0);
        if(floorId != null) {
            criteria.andFloorIdEqualTo(floorId);
        }
        if(StrUtil.isNotBlank(floor)) {
            criteria.andFloorNumEqualTo(floor);
        }
        List<AssetRoom> assetRooms = assetRoomMapper.selectByExample(assetRoomExample);
        return assetRooms;
    }

    @Override
    public List<AssetRoom> findByFloorId(Long floorId) {
        AssetRoomExample assetRoomExample = new AssetRoomExample();
        AssetRoomExample.Criteria criteria = assetRoomExample.createCriteria();
        criteria.andFloorIdEqualTo(floorId);
        criteria.andZsztEqualTo("1");

        List<AssetRoom> assetRooms = assetRoomMapper.selectByExample(assetRoomExample);
        assetRooms.stream().forEach(a -> {
            List<AssetOrderRoom> assetOrderRooms = assetOrderService.orderRoomms(a.getId());
            if(CollUtil.isEmpty(assetOrderRooms)) {
                a.setIsOccupancy("0");
            }else {
                a.setIsOccupancy("1");
            }
        });

        return assetRooms;
    }


}
