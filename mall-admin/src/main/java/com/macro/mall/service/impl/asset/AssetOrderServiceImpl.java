package com.macro.mall.service.impl.asset;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.macro.mall.dao.PmsProductDao;
import com.macro.mall.dao.PmsProductVertifyRecordDao;
import com.macro.mall.dao.PmsSkuStockDao;
import com.macro.mall.dto.*;
import com.macro.mall.mapper.AssetOrderMapper;
import com.macro.mall.mapper.AssetOrderRoomMapper;
import com.macro.mall.mapper.AssetRoomMapper;
import com.macro.mall.mapper.PmsSkuStockMapper;
import com.macro.mall.model.*;
import com.macro.mall.service.asset.AssetOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品管理Service实现类
 * Created by macro on 2018/4/26.
 */
@Service
public class AssetOrderServiceImpl implements AssetOrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssetOrderServiceImpl.class);
    @Autowired
    private AssetOrderMapper assetOrderMapper;
    @Autowired
    private AssetRoomMapper assetRoomMapper;
    @Autowired
    private AssetOrderRoomMapper assetOrderRoomMapper;
    @Autowired
    private PmsSkuStockDao skuStockDao;
    @Autowired
    private PmsSkuStockMapper skuStockMapper;
    @Autowired
    private PmsProductDao productDao;
    @Autowired
    private PmsProductVertifyRecordDao productVertifyRecordDao;

    @Override
    public int create(AssetOrderParam assetOrderParam) {
        AssetOrder assetOrder = new AssetOrder();
        BeanUtils.copyProperties(assetOrderParam, assetOrder);
        List<AssetOrderRoomParam> orderRoom = assetOrderParam.getOrderRoom();
        int i = assetOrderMapper.insertSelective(assetOrder);
        for(AssetOrderRoomParam room: orderRoom) {
            AssetOrderRoom assetOrderRoom = new AssetOrderRoom();
            BeanUtils.copyProperties(room,assetOrderRoom);
            assetOrderRoom.setOrderId(assetOrder.getId());
            assetOrderRoomMapper.insertSelective(assetOrderRoom);
        }
        return 1;
    }

    private void handleSkuStockCode(List<PmsSkuStock> skuStockList, Long productId) {
        if(CollectionUtils.isEmpty(skuStockList))return;
        for(int i=0;i<skuStockList.size();i++){
            PmsSkuStock skuStock = skuStockList.get(i);
            if(StrUtil.isEmpty(skuStock.getSkuCode())){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                StringBuilder sb = new StringBuilder();
                //日期
                sb.append(sdf.format(new Date()));
                //四位商品id
                sb.append(String.format("%04d", productId));
                //三位索引id
                sb.append(String.format("%03d", i+1));
                skuStock.setSkuCode(sb.toString());
            }
        }
    }

    @Override
    public PmsProductResult getUpdateInfo(Long id) {
        return productDao.getUpdateInfo(id);
    }

    @Override
    public int update(Long id, AssetOrderParam assetRoomParam) {
        AssetOrder assetRoom = new AssetOrder();
        BeanUtils.copyProperties(assetRoomParam, assetRoom);
        assetRoom.setId(id);
        return assetOrderMapper.updateByPrimaryKeySelective(assetRoom);
    }

    private void handleUpdateSkuStockList(Long id, PmsProductParam productParam) {
        //当前的sku信息
        List<PmsSkuStock> currSkuList = productParam.getSkuStockList();
        //当前没有sku直接删除
        if(CollUtil.isEmpty(currSkuList)){
            PmsSkuStockExample skuStockExample = new PmsSkuStockExample();
            skuStockExample.createCriteria().andProductIdEqualTo(id);
            skuStockMapper.deleteByExample(skuStockExample);
            return;
        }
        //获取初始sku信息
        PmsSkuStockExample skuStockExample = new PmsSkuStockExample();
        skuStockExample.createCriteria().andProductIdEqualTo(id);
        List<PmsSkuStock> oriStuList = skuStockMapper.selectByExample(skuStockExample);
        //获取新增sku信息
        List<PmsSkuStock> insertSkuList = currSkuList.stream().filter(item->item.getId()==null).collect(Collectors.toList());
        //获取需要更新的sku信息
        List<PmsSkuStock> updateSkuList = currSkuList.stream().filter(item->item.getId()!=null).collect(Collectors.toList());
        List<Long> updateSkuIds = updateSkuList.stream().map(PmsSkuStock::getId).collect(Collectors.toList());
        //获取需要删除的sku信息
        List<PmsSkuStock> removeSkuList = oriStuList.stream().filter(item-> !updateSkuIds.contains(item.getId())).collect(Collectors.toList());
        handleSkuStockCode(insertSkuList,id);
        handleSkuStockCode(updateSkuList,id);
        //新增sku
        if(CollUtil.isNotEmpty(insertSkuList)){
            relateAndInsertList(skuStockDao, insertSkuList, id);
        }
        //删除sku
        if(CollUtil.isNotEmpty(removeSkuList)){
            List<Long> removeSkuIds = removeSkuList.stream().map(PmsSkuStock::getId).collect(Collectors.toList());
            PmsSkuStockExample removeExample = new PmsSkuStockExample();
            removeExample.createCriteria().andIdIn(removeSkuIds);
            skuStockMapper.deleteByExample(removeExample);
        }
        //修改sku
        if(CollUtil.isNotEmpty(updateSkuList)){
            for (PmsSkuStock pmsSkuStock : updateSkuList) {
                skuStockMapper.updateByPrimaryKeySelective(pmsSkuStock);
            }
        }

    }

    @Override
    public List<AssetOrder> list(AssetOrderQueryParam assetRoomQueryParam, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        AssetOrderExample assetRoomExample = new AssetOrderExample();
        AssetOrderExample.Criteria criteria = assetRoomExample.createCriteria();
        if (StrUtil.isNotBlank(assetRoomQueryParam.getOrderNum()) ) {
            criteria.andOrderNumLike("%"+assetRoomQueryParam.getOrderNum()+"%"  );
        }
        if (StrUtil.isNotBlank(assetRoomQueryParam.getZlr()) ) {
            criteria.andZlrLike("%"+assetRoomQueryParam.getZlr()+"%"  );
        }
        assetRoomExample.setOrderByClause("order_num desc");
        return assetOrderMapper.selectByExample(assetRoomExample);
    }

    @Override
    public int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail) {
        AssetOrder assetRoom = new AssetOrder();
       // assetRoom.setVerifyStatus(verifyStatus);
        AssetOrderExample example = new AssetOrderExample();
        example.createCriteria().andIdIn(ids);
        List<PmsProductVertifyRecord> list = new ArrayList<>();
        int count = assetOrderMapper.updateByExampleSelective(assetRoom, example);
        //修改完审核状态后插入审核记录
        for (Long id : ids) {
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
        AssetOrder assetRoom = new AssetOrder();
        //assetRoom.setPublishStatus(publishStatus);
        AssetOrderExample example = new AssetOrderExample();
        example.createCriteria().andIdIn(ids);
        return assetOrderMapper.updateByExampleSelective(assetRoom, example);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        AssetOrder assetRoom = new AssetOrder();
        //assetRoom.setRecommandStatus(recommendStatus);
        AssetOrderExample example = new AssetOrderExample();
        example.createCriteria().andIdIn(ids);
        return assetOrderMapper.updateByExampleSelective(assetRoom, example);
    }

    @Override
    public int updateNewStatus(List<Long> ids, Integer newStatus) {
        AssetOrder assetRoom = new AssetOrder();
        //assetRoom.setNewStatus(newStatus);
        AssetOrderExample example = new AssetOrderExample();
        example.createCriteria().andIdIn(ids);
        return assetOrderMapper.updateByExampleSelective(assetRoom, example);
    }

    @Override
    public int updateDeleteStatus(List<Long> ids, Integer deleteStatus) {
        AssetOrder assetRoom = new AssetOrder();
        //assetRoom.setDeleteStatus(deleteStatus);
        AssetOrderExample example = new AssetOrderExample();
        example.createCriteria().andIdIn(ids);
        return assetOrderMapper.updateByExampleSelective(assetRoom, example);
    }

    @Override
    public List<AssetOrder> list(String keyword) {
        AssetOrderExample assetRoomExample = new AssetOrderExample();
        AssetOrderExample.Criteria criteria = assetRoomExample.createCriteria();
        //criteria.andDeleteStatusEqualTo(0);
      /*  if(!StrUtil.isEmpty(keyword)){
            criteria.andRoomNumLike("%" + keyword + "%");
          //  assetRoomExample.or().andDeleteStatusEqualTo(0).andProductSnLike("%" + keyword + "%");
        }*/
        return assetOrderMapper.selectByExample(assetRoomExample);
    }

    @Override
    public AssetOrder getBrand(Long id) {
        return assetOrderMapper.selectByPrimaryKey(id);
    }

    /**
     * 建立和插入关系表操作
     *
     * @param dao       可以操作的dao
     * @param dataList  要插入的数据
     * @param productId 建立关系的id
     */
    private void relateAndInsertList(Object dao, List dataList, Long productId) {
        try {
            if (CollectionUtils.isEmpty(dataList)) return;
            for (Object item : dataList) {
                Method setId = item.getClass().getMethod("setId", Long.class);
                setId.invoke(item, (Long) null);
                Method setProductId = item.getClass().getMethod("setProductId", Long.class);
                setProductId.invoke(item, productId);
            }
            Method insertList = dao.getClass().getMethod("insertList", List.class);
            insertList.invoke(dao, dataList);
        } catch (Exception e) {
            LOGGER.warn("创建产品出错:{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int updateFactoryStatus(List<Long> ids, String zszt) {
        AssetOrder assetRoom = new AssetOrder();
       // assetRoom.setZszt(zszt);
        AssetOrderExample assetRoomExample = new AssetOrderExample();
        assetRoomExample.createCriteria().andIdIn(ids);
        return assetOrderMapper.updateByExampleSelective(assetRoom, assetRoomExample);
    }

    @Override
    public int updateIsOccupancy(List<Long> ids, String isOccupancy) {
        AssetOrder assetRoom = new AssetOrder();
      //  assetRoom.setIsOccupancy(isOccupancy);
        AssetOrderExample assetRoomExample = new AssetOrderExample();
        assetRoomExample.createCriteria().andIdIn(ids);
        return assetOrderMapper.updateByExampleSelective(assetRoom, assetRoomExample);
    }
    @Override
    public int deleteBrand(Long id) {
        return assetOrderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Long getOrderNum() {
        return System.currentTimeMillis();
    }

    @Override
    public List<AssetOrderRoomDto> getOrderRoom(Long id) {
        return  assetOrderRoomMapper.selectByOrderId(id);
    }

    @Override
    public List<AssetOrderRoomDto> lqyj(AssetRoomQueryParam assetRoomQueryParam, Integer pageSize, Integer pageNum) {
       PageHelper.startPage(pageNum,pageSize);
        AssetOrderRoomExample assetRoomExample = new AssetOrderRoomExample();
        AssetOrderRoomExample.Criteria criteria = assetRoomExample.createCriteria();
        if(assetRoomQueryParam.getFloorId()!=null){
            criteria.andFloorIdEqualTo(assetRoomQueryParam.getFloorId());
        }
        DateTime dateTime = DateUtil.offsetDay(new Date(), 31);
        criteria.andEndTimeBetween(new Date(),dateTime);
        List<AssetOrderRoomDto> assetOrderRooms = assetOrderRoomMapper.selectByLqtx(assetRoomExample);
        return assetOrderRooms;
    }


    @Override
    public List<AssetOrderRoom> selectIsOccupancyList() {
        AssetOrderRoomExample assetRoomExample = new AssetOrderRoomExample();
        AssetOrderRoomExample.Criteria criteria = assetRoomExample.createCriteria();
        criteria.andEndTimeGreaterThan(new Date());
        List<AssetOrderRoom> assetOrderRooms = assetOrderRoomMapper.selectByExample(assetRoomExample);
        return assetOrderRooms;
    }

    @Override
    public int updateAll() {
        return assetOrderRoomMapper.updateAll();
    }
    @Override
    public int updateIsOccupancy(List<Long> ids) {
        AssetRoom assetRoom = new AssetRoom();
        assetRoom.setIsOccupancy("1");
        AssetRoomExample assetRoomExample = new AssetRoomExample();
        assetRoomExample.createCriteria().andIdIn(ids);
        return assetRoomMapper.updateByExampleSelective(assetRoom, assetRoomExample);

    }

    public List<AssetOrderRoom> orderRoomms(Long roomId){
        AssetOrderRoomExample room = new AssetOrderRoomExample();
        AssetOrderRoomExample.Criteria roomCriteria = room.createCriteria();
        roomCriteria.andRoomIdEqualTo(roomId);
        roomCriteria.andEndTimeGreaterThan(new Date());
        List<AssetOrderRoom> assetOrderRooms = assetOrderRoomMapper.selectByExample(room);
        return assetOrderRooms;
    }
}
