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
import com.macro.mall.mapper.*;
import com.macro.mall.model.*;
import com.macro.mall.service.asset.AssetOrderService;
import com.macro.mall.utils.FileUtil;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
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
    private AssetFloorMapper assetFloorMapper;

    @Override
    public int create(AssetOrderParam assetOrderParam) {
        AssetOrder assetOrder = new AssetOrder();
        BeanUtils.copyProperties(assetOrderParam, assetOrder);
        List<AssetOrderRoomParam> orderRoom = assetOrderParam.getOrderRoom();
        assetOrder.setCreatetime(new Date());
        int i = assetOrderMapper.insertSelective(assetOrder);
        for(AssetOrderRoomParam room : orderRoom) {
            AssetOrderRoom assetOrderRoom = new AssetOrderRoom();
            BeanUtils.copyProperties(room, assetOrderRoom);
            assetOrderRoom.setOrderId(assetOrder.getId());
            assetOrderRoomMapper.insertSelective(assetOrderRoom);
        }
        return 1;
    }

    @Override
    public int updateOrder(AssetOrderParam assetOrderParam) {

        AssetOrder assetOrder = new AssetOrder();
        BeanUtils.copyProperties(assetOrderParam, assetOrder);
        assetOrder.setId(assetOrderParam.getId());
        assetOrderMapper.updateByPrimaryKeySelective(assetOrder);

        AssetOrderRoomExample assetRoomExample = new AssetOrderRoomExample();
        AssetOrderRoomExample.Criteria criteria = assetRoomExample.createCriteria();
        criteria.andOrderIdEqualTo(assetOrderParam.getId());
        assetOrderRoomMapper.deleteByExample(assetRoomExample);

        List<AssetOrderRoomParam> orderRoom = assetOrderParam.getOrderRoom();
        for(AssetOrderRoomParam room : orderRoom) {
            AssetOrderRoom assetOrderRoom = new AssetOrderRoom();
            BeanUtils.copyProperties(room, assetOrderRoom);
            assetOrderRoom.setOrderId(assetOrder.getId());
            assetOrderRoomMapper.insertSelective(assetOrderRoom);
        }
        return 1;
    }


    @Override
    public int deleteOrder(Long orderId) {
        AssetOrderExample assetExample = new AssetOrderExample();
        AssetOrderExample.Criteria criteria = assetExample.createCriteria();
        criteria.andIdEqualTo(orderId);
        assetOrderMapper.deleteByExample(assetExample);

        AssetOrderRoomExample assetRoomExample = new AssetOrderRoomExample();
        AssetOrderRoomExample.Criteria criteria1 = assetRoomExample.createCriteria();
        criteria1.andOrderIdEqualTo(orderId);
        assetOrderRoomMapper.deleteByExample(assetRoomExample);

        return 1;
    }

    private void handleSkuStockCode(List<PmsSkuStock> skuStockList, Long productId) {
        if(CollectionUtils.isEmpty(skuStockList)) return;
        for(int i = 0; i < skuStockList.size(); i++) {
            PmsSkuStock skuStock = skuStockList.get(i);
            if(StrUtil.isEmpty(skuStock.getSkuCode())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                StringBuilder sb = new StringBuilder();
                //日期
                sb.append(sdf.format(new Date()));
                //四位商品id
                sb.append(String.format("%04d", productId));
                //三位索引id
                sb.append(String.format("%03d", i + 1));
                skuStock.setSkuCode(sb.toString());
            }
        }
    }

    @Override
    public AssetOrderParam getUpdateInfo(Long id) {
        return null;
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
        if(CollUtil.isEmpty(currSkuList)) {
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
        List<PmsSkuStock> insertSkuList = currSkuList.stream().filter(item -> item.getId() == null).collect(Collectors.toList());
        //获取需要更新的sku信息
        List<PmsSkuStock> updateSkuList = currSkuList.stream().filter(item -> item.getId() != null).collect(Collectors.toList());
        List<Long> updateSkuIds = updateSkuList.stream().map(PmsSkuStock::getId).collect(Collectors.toList());
        //获取需要删除的sku信息
        List<PmsSkuStock> removeSkuList = oriStuList.stream().filter(item -> !updateSkuIds.contains(item.getId())).collect(Collectors.toList());
        handleSkuStockCode(insertSkuList, id);
        handleSkuStockCode(updateSkuList, id);
        //新增sku
        if(CollUtil.isNotEmpty(insertSkuList)) {
            relateAndInsertList(skuStockDao, insertSkuList, id);
        }
        //删除sku
        if(CollUtil.isNotEmpty(removeSkuList)) {
            List<Long> removeSkuIds = removeSkuList.stream().map(PmsSkuStock::getId).collect(Collectors.toList());
            PmsSkuStockExample removeExample = new PmsSkuStockExample();
            removeExample.createCriteria().andIdIn(removeSkuIds);
            skuStockMapper.deleteByExample(removeExample);
        }
        //修改sku
        if(CollUtil.isNotEmpty(updateSkuList)) {
            for(PmsSkuStock pmsSkuStock : updateSkuList) {
                skuStockMapper.updateByPrimaryKeySelective(pmsSkuStock);
            }
        }

    }

    @Override
    public List<AssetOrder> list(AssetOrderQueryParam assetRoomQueryParam, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        AssetOrderExample assetRoomExample = new AssetOrderExample();
        AssetOrderExample.Criteria criteria = assetRoomExample.createCriteria();
        if(StrUtil.isNotBlank(assetRoomQueryParam.getOrderNum())) {
            criteria.andOrderNumLike("%" + assetRoomQueryParam.getOrderNum() + "%");
        }
        if(StrUtil.isNotBlank(assetRoomQueryParam.getZlr())) {
            criteria.andZlrLike("%" + assetRoomQueryParam.getZlr() + "%");
        }
        assetRoomExample.setOrderByClause("order_num desc");
        return assetOrderMapper.selectByExample(assetRoomExample);
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
        return assetOrderRoomMapper.selectByOrderId(id);
    }

    @Override
    public List<AssetOrderRoomDto> lqyj(AssetRoomQueryParam assetRoomQueryParam, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        AssetOrderRoomExample assetRoomExample = new AssetOrderRoomExample();
        AssetOrderRoomExample.Criteria criteria = assetRoomExample.createCriteria();
        if(assetRoomQueryParam.getFloorId() != null) {
            criteria.andFloorIdEqualTo(assetRoomQueryParam.getFloorId());
        }
        DateTime dateTime = DateUtil.offsetDay(new Date(), 31);
        criteria.andEndTimeBetween(new Date(), dateTime);
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

    @Override
    public List<AssetOrderRoom> orderRoomms(Long roomId) {
        AssetOrderRoomExample room = new AssetOrderRoomExample();
        AssetOrderRoomExample.Criteria roomCriteria = room.createCriteria();
        roomCriteria.andRoomIdEqualTo(roomId);
        roomCriteria.andEndTimeGreaterThan(new Date());
        List<AssetOrderRoom> assetOrderRooms = assetOrderRoomMapper.selectByExample(room);
        return assetOrderRooms;
    }

    @Override
    public List<AssetOrderRoom> orderRoommsList(List<Long> roomId) {
        AssetOrderRoomExample room = new AssetOrderRoomExample();
        AssetOrderRoomExample.Criteria roomCriteria = room.createCriteria();
        roomCriteria.andRoomIdIn(roomId);
        roomCriteria.andEndTimeGreaterThan(new Date());
        List<AssetOrderRoom> assetOrderRooms = assetOrderRoomMapper.selectByExample(room);
        return assetOrderRooms;
    }

    @Override
    public int downloadExcel(AssetOrderQueryParam param, HttpServletResponse response) {
        List<Map<String, Object>> responseList = new ArrayList<>();

        AssetOrderExample assetOrderExample = new AssetOrderExample();
        AssetOrderExample.Criteria criteria = assetOrderExample.createCriteria();
        if(StrUtil.isNotBlank(param.getZlr())) {
            criteria.andZlrLike("%" + param.getZlr() + "%");
        }
        List<AssetOrder> assetRooms = assetOrderMapper.selectByExample(assetOrderExample);
        assetRooms.forEach(item -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", item.getId());
            map.put("订单编号", item.getOrderNum());
            map.put("操作人", item.getCzr());
            map.put("租赁人", item.getZlr());
            map.put("租赁人联系电话", item.getZlrlxdh());
            map.put("支付时间", DateUtil.formatDate(item.getZfsj()));
            map.put("支付方式", item.getZffs());
            map.put("总金额", item.getZje());
            map.put("订单从表id", "");
            map.put("订单id", "");
            map.put("资产id", "");
            map.put("资产名称", "");
            map.put("楼层", "");
            map.put("房间id", "");
            map.put("房间号", "");
            map.put("租赁开始时间", "");
            map.put("租赁结束时间", "");
            map.put("租赁单价", "");
            map.put("租赁总价", "");
            responseList.add(map);
            List<AssetOrderRoomDto> assetOrderRoomDtos = assetOrderRoomMapper.selectByOrderId(item.getId());
            if(CollUtil.isNotEmpty(assetOrderRoomDtos)) {
                assetOrderRoomDtos.stream().forEach(a -> {
                    Map<String, Object> map1 = new LinkedHashMap<>();
                    map1.put("订单房间id", a.getId());
                    map1.put("订单id", a.getOrderId());
                    map1.put("资产id", a.getFloorId());
                    map1.put("资产名称", a.getFloorName());
                    map1.put("楼层", a.getFloorNum());
                    map1.put("房间id", a.getRoomId());
                    map1.put("房间号", a.getRoomNum());
                    map1.put("租赁开始时间", DateUtil.formatDate(a.getBeginTime()));
                    map1.put("租赁结束时间", DateUtil.formatDate(a.getEndTime()));
                    map1.put("租赁单价", a.getUnitprice());
                    map1.put("租赁总价", a.getPrice());
                    responseList.add(map1);
                });
            }
        });
        List<Map<String, Object>> sheet2 = new ArrayList<>();
        List<AssetFloor> assetFloors = assetFloorMapper.selectByExample(new AssetFloorExample());
        assetFloors.forEach(item -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("资产id", item.getId());
            map.put("资产名称", item.getName());
            map.put("房间id", "");
            map.put("楼层", "");
            map.put("房间号", "");
            sheet2.add(map);
            AssetRoomExample assetRoomExample = new AssetRoomExample();
            AssetRoomExample.Criteria criteria1 = assetRoomExample.createCriteria();
            criteria1.andFloorIdEqualTo(item.getId());
            List<AssetRoom> rooms = assetRoomMapper.selectByExample(assetRoomExample);
            rooms.stream().forEach(a -> {
                Map<String, Object> map1 = new LinkedHashMap<>();
                map1.put("房间id", a.getId());
                map1.put("楼层", a.getFloorNum());
                map1.put("房间号", a.getRoomNum());
                sheet2.add(map1);
            });
        });

        try {
            FileUtil.downloadExcelSheels(responseList, sheet2, "资产和房间", response);
        }catch(IOException e) {
            e.printStackTrace();
        }
        return 1;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importExcel(MultipartFile file) throws Exception {
        //读取工作簿
        Workbook workBook = WorkbookFactory.create(file.getInputStream());
        //读取工作表
        Sheet sheet = workBook.getSheetAt(0);
        //总行数
        int rowNumber = sheet.getPhysicalNumberOfRows();

        //标题行
        Row titleRow = sheet.getRow(0);
        //总列数
        int colNum = titleRow.getPhysicalNumberOfCells();

        //校验是否填写内容
        if(rowNumber <= 1) {
            throw new Exception("文件无内容");
        }
        Long newId = 0L;
        //循环读取每一行数据并校验
        for(int i = 1; i < rowNumber; i++) {
            try {
                //读取行
                Row row = sheet.getRow(i);
                //
                AssetOrder assetOrder = new AssetOrder();

                DataFormatter dataFormatter = new DataFormatter();
                //id
                String id = dataFormatter.formatCellValue(row.getCell(0));
                if(StrUtil.isNotBlank(id)) {
                    assetOrder.setId(Long.valueOf(id));
                }
                //订单编号
                String ddbh = dataFormatter.formatCellValue(row.getCell(1));
                if(StrUtil.isNotBlank(ddbh)) {
                    assetOrder.setOrderNum(ddbh);
                }
                //操作人
                String czr = dataFormatter.formatCellValue(row.getCell(2));
                if(StrUtil.isNotBlank(czr)) {
                    assetOrder.setCzr(czr);
                }
                //租赁人
                String zlr = dataFormatter.formatCellValue(row.getCell(3));
                if(StrUtil.isNotBlank(zlr)) {
                    assetOrder.setZlr(zlr);
                }
                //租赁人联系电话
                String zlrlxdh = dataFormatter.formatCellValue(row.getCell(4));
                if(StrUtil.isNotBlank(zlrlxdh)) {
                    assetOrder.setZlrlxdh(zlrlxdh);
                }
                //支付时间
                String zfsj = dataFormatter.formatCellValue(row.getCell(5));
                if(StrUtil.isNotBlank(zfsj)) {
                    assetOrder.setZfsj(DateUtil.parseDate(zfsj));
                }
                //支付方式
                String zffs = dataFormatter.formatCellValue(row.getCell(6));
                if(StrUtil.isNotBlank(zffs)) {
                    assetOrder.setZffs(zffs);
                }
                //总金额
                String zje = dataFormatter.formatCellValue(row.getCell(7));
                if(StrUtil.isNotBlank(zje)) {
                    BigDecimal je = new BigDecimal(zje);
                    assetOrder.setZje(je);
                }
                //保存
                if(StrUtil.isNotBlank(assetOrder.getCzr()) || StrUtil.isNotBlank(assetOrder.getZlr()) || assetOrder.getZfsj() != null) {
                    if(assetOrder.getId() != null) {
                        newId = assetOrder.getId();
                        assetOrderMapper.updateByPrimaryKeySelective(assetOrder);
                    }else {
                        if(StrUtil.isBlank(assetOrder.getOrderNum())) {
                            assetOrder.setOrderNum(getOrderNum().toString());
                        }
                        assetOrderMapper.insertSelective(assetOrder);
                        newId = assetOrder.getId();
                    }
                }

                AssetOrderRoom assetOrderRoom = new AssetOrderRoom();
                //id
                String id1 = dataFormatter.formatCellValue(row.getCell(8));
                if(StrUtil.isNotBlank(id1)) {
                    assetOrderRoom.setId(Long.valueOf(id1));
                }
                //订单id
                String orderId = dataFormatter.formatCellValue(row.getCell(9));
                if(StrUtil.isNotBlank(orderId)) {
                    assetOrderRoom.setOrderId(Long.valueOf(orderId));
                }
                //资产id
                String zcId = dataFormatter.formatCellValue(row.getCell(10));
                if(StrUtil.isNotBlank(zcId)) {
                    assetOrderRoom.setFloorId(Long.valueOf(zcId));
                }
                //资产名称
                String zcmc = dataFormatter.formatCellValue(row.getCell(11));
                if(StrUtil.isNotBlank(zcmc)) {

                }
                //楼层
                String lc = dataFormatter.formatCellValue(row.getCell(12));
                if(StrUtil.isNotBlank(lc)) {

                }
                //房间id
                String roomId = dataFormatter.formatCellValue(row.getCell(13));
                if(StrUtil.isNotBlank(roomId)) {
                    assetOrderRoom.setRoomId(Long.valueOf(roomId));
                }
                //房间号
                String fjh = dataFormatter.formatCellValue(row.getCell(14));
                if(StrUtil.isNotBlank(fjh)) {

                }
                //租赁开始时间
                String zlkssj = dataFormatter.formatCellValue(row.getCell(15));
                if(StrUtil.isNotBlank(zlkssj)) {
                    assetOrderRoom.setBeginTime(DateUtil.parseDate(zlkssj));
                }
                //租赁结束时间
                String zljssj = dataFormatter.formatCellValue(row.getCell(16));
                if(StrUtil.isNotBlank(zljssj)) {
                    assetOrderRoom.setEndTime(DateUtil.parseDate(zljssj));
                }
                //租赁单价
                String zldj = dataFormatter.formatCellValue(row.getCell(17));
                if(StrUtil.isNotBlank(zldj)) {
                    assetOrderRoom.setUnitprice(zldj);
                }
                //租赁总价
                String zlzj = dataFormatter.formatCellValue(row.getCell(18));
                if(StrUtil.isNotBlank(zlzj)) {
                    BigDecimal zj = new BigDecimal(zlzj);
                    assetOrderRoom.setPrice(zj);
                }
                if(assetOrderRoom.getFloorId() != null && assetOrderRoom.getRoomId() != null) {
                    if(assetOrderRoom.getId() != null) {
                        assetOrderRoom.setOrderId(newId);
                        assetOrderRoomMapper.updateByPrimaryKeySelective(assetOrderRoom);
                    }else {
                        assetOrderRoom.setOrderId(newId);
                        assetOrderRoomMapper.insertSelective(assetOrderRoom);
                    }
                }
            }catch(Exception e) {
                e.printStackTrace();
                if("日期格式错误".equals(e.getMessage())) {
                    throw new Exception("第" + (i + 1) + "行数据有错误,日期格式错误");
                }else if("文件无内容".equals(e.getMessage())) {
                    throw new Exception("文件无内容");
                }else {
                    throw new Exception("第" + (i + 1) + "行数据有错误");
                }
            }
        }
        workBook.close();
    }
}
