package com.macro.mall.service.impl.asset;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.macro.mall.dao.*;
import com.macro.mall.dto.*;
import com.macro.mall.mapper.*;
import com.macro.mall.model.*;
import com.macro.mall.service.asset.AssetOrderService;
import com.macro.mall.service.asset.AssetRoomService;
import com.macro.mall.utils.FileUtil;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.math.BigDecimal;
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
    private AssetOrderMapper assetOrderMapper;
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
            // 如果数据库状态是已出租，那就按照数据库的已出租来，如果数据库状态是未出租，就按照订单来
            if(a.getIsOccupancy().equals("0")){
                List<AssetOrderRoom> assetOrderRooms = assetOrderService.orderRoomms(a.getId());
                if(CollUtil.isEmpty(assetOrderRooms)) {
                    a.setIsOccupancy("0");
                }else {
                    a.setIsOccupancy("1");
                }
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
                if(a.getIsOccupancy().equals("0")){
                List<AssetOrderRoom> assetOrderRooms = assetOrderService.orderRoomms(a.getId());
                if(CollUtil.isEmpty(assetOrderRooms)) {
                    a.setIsOccupancy("0");
                }else {
                    a.setIsOccupancy("1");
                }
            }
        });

        return assetRooms;
    }

    @Override
    public Map<String, Object> homeRoomSum() {
        AssetRoomExample assetRoomExample = new AssetRoomExample();
        AssetRoomExample.Criteria criteria = assetRoomExample.createCriteria();
        criteria.andZsztEqualTo("1");

        List<AssetRoom> assetRooms = assetRoomMapper.selectByExample(assetRoomExample);

        Map<String,Object>map=new HashMap<>();
        map.put("sum", assetRooms.size());
        // 下架
        long below = assetRooms.stream().filter(a -> a.getZszt().equals("0")).count();
        map.put("below",below);
        // 上架
        long up = assetRooms.stream().filter(a -> a.getZszt().equals("1")).count();
        map.put("up",up);

        List<Long> collect1 = assetRooms.stream().filter(a -> a.getIsOccupancy().equals("1")).map(AssetRoom::getId).collect(Collectors.toList());
        List<Long> collect = assetRooms.stream().map(AssetRoom::getId).collect(Collectors.toList());
        List<AssetOrderRoom> assetOrderRooms1 = assetOrderService.orderRoommsList(collect);

        List<Long> collect2 = assetOrderRooms1.stream().map(AssetOrderRoom::getRoomId).collect(Collectors.toList());
        Set<Long> longs = CollUtil.unionDistinct(collect1, collect2);
        map.put("cz",longs.size());


        // 今日新增
        AssetOrderExample assetOrderExample = new AssetOrderExample();
        AssetOrderExample.Criteria assetOrderExampleCriteria = assetOrderExample.createCriteria();
        DateTime dateTime = DateUtil.parseDate(DateUtil.today());

        assetOrderExampleCriteria.andCreatetimeGreaterThanOrEqualTo(dateTime);
        assetOrderExampleCriteria.andCreatetimeLessThan(DateUtil.offsetDay(dateTime,1));
        List<AssetOrder> assetOrderRooms = assetOrderMapper.selectByExample(assetOrderExample);
        int size = assetOrderRooms.size();
        map.put("jrxz",size);

        // 昨日新增
        AssetOrderExample a2 = new AssetOrderExample();
        AssetOrderExample.Criteria aa2 = a2.createCriteria();
        aa2.andCreatetimeGreaterThanOrEqualTo(DateUtil.offsetDay(dateTime,-1));
        aa2.andCreatetimeLessThan(dateTime);
        List<AssetOrder> zrxzs = assetOrderMapper.selectByExample(a2);
        map.put("zrxz",zrxzs.size());

        // 本月新增
        AssetOrderExample a3 = new AssetOrderExample();
        AssetOrderExample.Criteria aa3 = a3.createCriteria();
        String format = DateUtil.format(new DateTime(), "yyyy-MM");
        String bengin=format+"-01";
        String end=format+"-31";
        aa3.andCreatetimeGreaterThanOrEqualTo(DateUtil.parse(bengin));
        aa3.andCreatetimeLessThan(DateUtil.parse(end));
        List<AssetOrder> byxzs = assetOrderMapper.selectByExample(a3);
        map.put("byxz",byxzs.size());
        BigDecimal bydde=new BigDecimal(0.00);
        for(AssetOrder a:byxzs) {
            bydde=bydde.add(a.getZje());
        }
        map.put("bydde",bydde);

        // 总数
        List<AssetOrder> zss = assetOrderMapper.selectByExample(new AssetOrderExample());
        map.put("zs",zss.size());


        // 本周新增
        AssetOrderExample a4 = new AssetOrderExample();
        AssetOrderExample.Criteria aa4 = a4.createCriteria();
        Date weekStart = DateUtil.beginOfWeek(new DateTime());
        DateTime weekEnd = DateUtil.offsetDay(weekStart, 7);
        aa4.andCreatetimeGreaterThanOrEqualTo(weekStart);
        aa4.andCreatetimeLessThan(weekEnd);
        List<AssetOrder> bzxz = assetOrderMapper.selectByExample(a4);
        map.put("bzxz",bzxz.size());
        BigDecimal bzdde=new BigDecimal(0.00);
        for(AssetOrder a:bzxz) {
            bzdde=bzdde.add(a.getZje());
        }
        map.put("bzdde",bzdde);
        return map;
    }

    @Override
    public List<Map<String, Object>> orderTj(Date beginTime, Date endTime) {

        AssetOrderExample assetOrderExample = new AssetOrderExample();
        AssetOrderExample.Criteria assetOrderExampleCriteria = assetOrderExample.createCriteria();
        assetOrderExampleCriteria.andCreatetimeGreaterThanOrEqualTo(beginTime);
        assetOrderExampleCriteria.andCreatetimeLessThan(DateUtil.offsetDay(endTime,1));
        List<Map<String,Object>> assetOrderRooms = assetOrderMapper.selectOrderTj(assetOrderExample);
        return assetOrderRooms;
    }

@Override
    public void downloadExcel(AssetRoomQueryParam assetRoomParam, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> responseList = new ArrayList<>();

        AssetRoomExample assetRoomExample = new AssetRoomExample();
        AssetRoomExample.Criteria criteria = assetRoomExample.createCriteria();
        if(assetRoomParam.getFloorId()!=null){
            criteria.andFloorIdEqualTo(assetRoomParam.getFloorId());
        }
        List<AssetRoom> assetRooms = assetRoomMapper.selectByExample(assetRoomExample);

        assetRooms.forEach(item -> {
                Map<String, Object> map = new HashMap<>();
                AssetFloor assetFloor = assetFloorMapper.selectByPrimaryKey(item.getFloorId());
                map.put("资产名称", assetFloor.getName());
                map.put("资产id", item.getFloorId());
                map.put("楼层号", item.getFloorNum());
                map.put("房间号", item.getRoomNum());
                map.put("面积", item.getAcreage());
                map.put("装修", item.getDecorationType());
                map.put("展示状态（0下架1上架）", item.getZszt());
                responseList.add(map);
            });

        FileUtil.downloadExcel(responseList, response);
    }


    public ResponseEntity<Object> importExecl(MultipartFile file) throws Exception {

        //读取工作簿
        Workbook workBook = WorkbookFactory.create(file.getInputStream());
        //读取工作表
        Sheet sheet = workBook.getSheetAt(0);
        //总行数
        int rowNumber = sheet.getPhysicalNumberOfRows();
        //总列数
        Row titleRow = sheet.getRow(0);
        int colNum = titleRow.getPhysicalNumberOfCells();

        //校验是否填写内容
        if (rowNumber <= 1) {
            return new ResponseEntity("文件无内容", HttpStatus.BAD_REQUEST);
        }
        //循环读取每一行数据并校验
        for (int i = 1; i < rowNumber; i++) {
            try {
                //读取行
                Row row = sheet.getRow(i);
                //读取单元格
                AssetRoom salary = new AssetRoom();
                JSONObject resultJson = new JSONObject(true);

                for (int m = 0; m < colNum; m++) {
                    if(titleRow.getCell(m) != null) {
                        titleRow.getCell(m).setCellType(CellType.STRING);
                    }else {
                        throw new Exception("第" + (i + 1) + "列表头不能为空");
                    }

                    if(row.getCell(m) != null) {
                        row.getCell(m).setCellType(CellType.STRING);
                    }else {
                        resultJson.put(titleRow.getCell(m).getStringCellValue(), row.getCell(m));
                        continue;
                    }
                    if(!"{}".equals(resultJson.toJSONString())) {
                        salary.setFloorName(resultJson.toJSONString());
                        assetRoomMapper.insert(salary);
                    }
                }
            } catch (Exception e) {
                throw new Exception("第" + (i + 1) + "行数据有错误," + e.getMessage());
            }
        }
        workBook.close();
        return null;
    }


}
