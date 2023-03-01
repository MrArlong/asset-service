package com.macro.mall.service.impl.asset;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.github.pagehelper.PageHelper;
import com.macro.mall.dto.AssetFloorParam;
import com.macro.mall.dto.AssetRoomQueryParam;
import com.macro.mall.mapper.AssetGgimgMapper;
import com.macro.mall.mapper.AssetFloorMapper;
import com.macro.mall.model.*;
import com.macro.mall.service.asset.AssetFloorService;
import com.macro.mall.service.asset.AssetRoomService;
import com.macro.mall.utils.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 资产管理Service实现类
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
        if(StrUtil.isBlank(assetFloorParam.getSftj())) {
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
        if(!StrUtil.isEmpty(keyword)) {
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

        PageHelper.startPage(1, 100);
        List<AssetFloor> assetFloors = assetFloorMapper.selectByExample(assetFloorExample);
        assetFloors.stream().forEach(a -> {
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
        if(StringUtils.isNotBlank(syzt)) {
            criteria.andSyztEqualTo(syzt);
        }
        List<AssetFloor> assetFloors = assetFloorMapper.selectByExample(assetFloorExample);
        assetFloors.stream().forEach(a -> {
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
        Map<String, Object> map = new HashMap<>();
        String hometp = "";
        String ggtp = "";
        for(AssetGgimg a : assetGgimgs) {
            if(StrUtil.isNotBlank(a.getHometp())) {
                if(StrUtil.isNotBlank(hometp)) {
                    hometp = hometp + ",";
                }
                hometp = hometp + a.getHometp();
            }
            if(StrUtil.isNotBlank(a.getLbtp())) {
                if(StrUtil.isNotBlank(ggtp)) {
                    ggtp = ggtp + ",";
                }
                ggtp = ggtp + a.getLbtp();
            }
        }
        if(CollUtil.isNotEmpty(assetGgimgs)){
            map.put("remark", assetGgimgs.get(0).getRemark());
        }else{
            map.put("remark", "");
        }
        map.put("hometp", hometp);
        map.put("ggtp", ggtp);
        return map;
    }

    @Override
    public int downloadExcel(HttpServletResponse response) {
        List<Map<String, Object>> responseList = new ArrayList<>();

        AssetFloorExample assetRoomExample = new AssetFloorExample();
        List<AssetFloor> assetRooms = assetFloorMapper.selectByExample(assetRoomExample);

        assetRooms.forEach(item -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", item.getId());
            map.put("资产名称(必填)", item.getName());
            map.put("资产地址(必填)", item.getAddress());
            map.put("所属区域", item.getRegion());
            map.put("联系人", item.getLxr());
            map.put("联系电话", item.getLxdh());
            map.put("使用状态（2出租，1建设中，0筹划中）(必填)", item.getSyzt());
            map.put("展示状态（1上架0下架）(必填)", item.getZszt());
            map.put("是否完工（1是0否）(必填)", item.getIsfinish());
            map.put("排序", item.getSort());
            map.put("经度", item.getLongitude());
            map.put("纬度", item.getLatitude());
            responseList.add(map);
        });

        try {
            FileUtil.downloadExcel(responseList, response);
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

        //循环读取每一行数据并校验
        for(int i = 1; i < rowNumber; i++) {
            try {
                //读取行
                Row row = sheet.getRow(i);
                //
                AssetFloor floor = new AssetFloor();
                //校验
                for(int m = 0; m < colNum; m++) {
                    String bt = titleRow.getCell(m).getStringCellValue();
                    if(titleRow.getCell(m) == null || "".equals(bt)) {
                        throw new Exception("列表头不能为空");
                    }
                    if("资产名称(必填)".equals(bt)) {
                        if(row.getCell(m) == null || row.getCell(m).getCellType() == CellType.BLANK) {
                            throw new Exception("资产名称不能有空数据");
                        }
                    }
                    if("资产地址(必填)".equals(bt)) {
                        if(row.getCell(m) == null || row.getCell(m).getCellType() == CellType.BLANK) {
                            throw new Exception("资产地址不能有空数据");
                        }
                    }
                    if("使用状态（2出租，1建设中，0筹划中）(必填)".equals(bt)) {
                        if(row.getCell(m) == null || row.getCell(m).getCellType() == CellType.BLANK) {
                            throw new Exception("使用状态不能有空数据");
                        }
                    }
                    if("展示状态（1上架0下架）(必填)".equals(bt)) {
                        if(row.getCell(m) == null || row.getCell(m).getCellType() == CellType.BLANK) {
                            throw new Exception("展示状态不能有空数据");
                        }
                    }
                    if("是否完工（1是0否）(必填)".equals(bt)) {
                        if(row.getCell(m) == null || row.getCell(m).getCellType() == CellType.BLANK) {
                            throw new Exception("是否完工不能有空数据");
                        }
                    }
                }
                for(int m = 0; m < colNum; m++) {
                    String bt = titleRow.getCell(m).getStringCellValue();
                    if(row.getCell(m) != null && row.getCell(m).getCellType() != CellType.BLANK) {
                        DataFormatter dataFormatter = new DataFormatter();
                        String value = dataFormatter.formatCellValue(row.getCell(m));
                        if("id".equals(bt)) {
                            floor.setId(Long.valueOf(value));
                        }
                        if("资产名称(必填)".equals(bt)) {
                            floor.setName(value);
                        }
                        if("资产地址(必填)".equals(bt)) {
                            floor.setAddress(value);
                        }
                        if("所属区域".equals(bt)) {
                            floor.setRegion(value);
                        }
                        if("联系人".equals(bt)) {
                            floor.setLxr(value);
                        }
                        if("联系电话".equals(bt)) {
                            floor.setLxdh(value);
                        }
                        if("使用状态（2出租，1建设中，0筹划中）(必填)".equals(bt)) {
                            floor.setSyzt(value);
                        }
                        if("展示状态（1上架0下架）(必填)".equals(bt)) {
                            floor.setZszt(value);
                        }
                        if("是否完工（1是0否）(必填)".equals(bt)) {
                            floor.setIsfinish(value);
                        }
                        if("排序".equals(bt)) {
                            floor.setSort(Integer.valueOf(value));
                        }
                        if("经度".equals(bt)) {
                            floor.setLongitude(value);
                        }
                        if("纬度".equals(bt)) {
                            floor.setLatitude(value);
                        }
                    }
                }
                //保存
                if(floor.getId() != null) {
                    assetFloorMapper.updateByPrimaryKeySelective(floor);
                }else {
                    floor.setSftj("0");
                    assetFloorMapper.insertSelective(floor);
                }
            }catch(Exception e) {
                e.printStackTrace();
                if("资产名称不能有空数据".equals(e.getMessage())) {
                    throw new Exception("第" + (i + 1) + "行数据有错误,资产名称不能有空数据");
                }else if("资产地址不能有空数据".equals(e.getMessage())) {
                    throw new Exception("第" + (i + 1) + "行数据有错误,资产地址不能有空数据");
                }else if("使用状态不能有空数据".equals(e.getMessage())) {
                    throw new Exception("第" + (i + 1) + "行数据有错误,使用状态不能有空数据");
                }else if("展示状态不能有空数据".equals(e.getMessage())) {
                    throw new Exception("第" + (i + 1) + "行数据有错误,展示状态不能有空数据");
                }else if("是否完工不能有空数据".equals(e.getMessage())) {
                    throw new Exception("第" + (i + 1) + "行数据有错误,是否完工不能有空数据");
                }else if("列表头不能为空".equals(e.getMessage())) {
                    throw new Exception("第" + (i + 1) + "行数据有错误,列表头不能为空");
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
