package com.macro.mall.service.impl.asset;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.macro.mall.dto.*;
import com.macro.mall.mapper.AssetFloorMapper;
import com.macro.mall.mapper.AssetOrderMapper;
import com.macro.mall.mapper.AssetOrderRoomMapper;
import com.macro.mall.mapper.AssetRoomMapper;
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
import java.util.*;

/**
 * 订单管理Service实现类
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
            List<AssetOrderRoomDto> assetOrderRoomDtos = assetOrderRoomMapper.selectByOrderId(item.getId());
            if(CollUtil.isNotEmpty(assetOrderRoomDtos)) {
                assetOrderRoomDtos.stream().forEach(a -> {
                    map.put("id", item.getId());
                    map.put("订单编号（必填）", item.getOrderNum());
                    map.put("操作人", item.getCzr());
                    map.put("租赁人", item.getZlr());
                    map.put("租赁人联系电话", item.getZlrlxdh());
                    map.put("支付时间", DateUtil.formatDate(item.getZfsj()));
                    map.put("支付方式", item.getZffs());
                    map.put("总金额（必填）", item.getZje());


                    map.put("订单从表id", a.getId());
                    map.put("订单id", a.getOrderId());
                    map.put("资产id（必填）", a.getFloorId());
                    map.put("资产名称", a.getFloorName());
                    map.put("楼层", a.getFloorNum());
                    map.put("房间id（必填）", a.getRoomId());
                    map.put("房间号", a.getRoomNum());
                    map.put("租赁开始时间", DateUtil.formatDate(a.getBeginTime()));
                    map.put("租赁结束时间", DateUtil.formatDate(a.getEndTime()));
                    map.put("租赁单价", a.getUnitprice());
                    map.put("租赁总价（必填）", a.getPrice());
                    responseList.add(map);
                });
            }

        });
        List<Map<String, Object>> sheet2 = new ArrayList<>();
        List<AssetFloor> assetFloors = assetFloorMapper.selectByExample(new AssetFloorExample());
        assetFloors.forEach(item -> {
            AssetRoomExample assetRoomExample = new AssetRoomExample();
            AssetRoomExample.Criteria criteria1 = assetRoomExample.createCriteria();
            criteria1.andFloorIdEqualTo(item.getId());
            List<AssetRoom> rooms = assetRoomMapper.selectByExample(assetRoomExample);
            rooms.stream().forEach(a -> {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("资产id", item.getId());
                map.put("资产名称", item.getName());
                map.put("房间id", a.getId());
                map.put("楼层", a.getFloorNum());
                map.put("房间号", a.getRoomNum());
                sheet2.add(map);
            });
        });

        try {
            String content1="注：若有新增订单先查看“资产与房间”工作表，把对应的资产id与房间id复制到本表中，订单从表id与订单id可为空。更新数据时订单从表id与订单id不可为空。";
            String content2="注：若有新增订单请对照资产名称、房间名称，把本表中的”资产id、房间id“对应填写在要新增订单中的”资产id、房间id“字段中，“id”、“订单从表id”、“订单id可为空”。";
            FileUtil.downloadExcelSheels(responseList, sheet2, "资产和房间",content1,content2, response);
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
        Row titleRow = sheet.getRow(1);
        //总列数
        int colNum = titleRow.getPhysicalNumberOfCells();

        //校验是否填写内容
        if(rowNumber <= 2) {
            throw new Exception("文件无内容");
        }
        Long newId = 0L;
        //循环读取每一行数据并校验
        for(int i = 2; i < rowNumber; i++) {
            try {
                //读取行
                Row row = sheet.getRow(i);
                //
                AssetOrder assetOrder = new AssetOrder();
                //校验
                for(int m = 0; m < colNum; m++) {
                    String bt = titleRow.getCell(m).getStringCellValue();
                    if(titleRow.getCell(m) == null || "".equals(bt)) {
                        throw new Exception("列表头不能为空");
                    }
                    if("订单编号（必填）".equals(bt)) {
                        if(row.getCell(m) == null || row.getCell(m).getCellType() == CellType.BLANK) {
                            throw new Exception("订单编号不能有空数据");
                        }
                    }if("总金额（必填）".equals(bt)) {
                        if(row.getCell(m) == null || row.getCell(m).getCellType() == CellType.BLANK) {
                            throw new Exception("总金额不能有空数据");
                        }
                    }
                    if("资产id（必填）".equals(bt)) {
                        if(row.getCell(m) == null || row.getCell(m).getCellType() == CellType.BLANK) {
                            throw new Exception("资产id不能有空数据");
                        }
                    }
                    if("房间id（必填）".equals(bt)) {
                        if(row.getCell(m) == null || row.getCell(m).getCellType() == CellType.BLANK) {
                            throw new Exception("房间id不能有空数据");
                        }
                    }
                    if("租赁总价（必填）".equals(bt)) {
                        if(row.getCell(m) == null || row.getCell(m).getCellType() == CellType.BLANK) {
                            throw new Exception("租赁总价不能有空数据");
                        }
                    }
                }

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
                        AssetOrderExample assetOrderExample = new AssetOrderExample();
                        AssetOrderExample.Criteria criteria = assetOrderExample.createCriteria();
                        criteria.andOrderNumEqualTo(assetOrder.getOrderNum());
                        List<AssetOrder> assetOrders = assetOrderMapper.selectByExample(assetOrderExample);
                        if(CollUtil.isNotEmpty(assetOrders)){
                            assetOrder.setId(assetOrders.get(0).getId());
                            assetOrderMapper.updateByPrimaryKeySelective(assetOrder);
                        }else{
                            assetOrderMapper.insertSelective(assetOrder);
                        }
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
                }else if("订单编号不能有空数据".equals(e.getMessage())) {
                    throw new Exception("第" + (i + 1) + "行数据有错误,订单编号不能有空数据");
                }else if("总金额不能有空数据".equals(e.getMessage())) {
                    throw new Exception("第" + (i + 1) + "行数据有错误,总金额不能有空数据");
                }else if("资产id不能有空数据".equals(e.getMessage())) {
                    throw new Exception("第" + (i + 1) + "行数据有错误,资产id不能有空数据");
                }else if("房间id不能有空数据".equals(e.getMessage())) {
                    throw new Exception("第" + (i + 1) + "行数据有错误,房间id不能有空数据");
                }else if("租赁总价不能有空数据".equals(e.getMessage())) {
                    throw new Exception("第" + (i + 1) + "行数据有错误,租赁总价不能有空数据");
                }else {
                    throw new Exception("第" + (i + 1) + "行数据有错误");
                }
            }
        }
        workBook.close();
    }
}
