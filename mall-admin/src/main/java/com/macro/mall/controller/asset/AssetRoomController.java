package com.macro.mall.controller.asset;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.*;
import com.macro.mall.model.AssetFloor;
import com.macro.mall.model.AssetRoom;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.service.asset.AssetRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 房间管理Controller
 * Created by macro on 2018/4/26.
 */
@RestController
@Api(tags = "AssetRoomController")
@Tag(name = "AssetRoomController", description = "房间管理")
@RequestMapping("/room")
public class AssetRoomController {
    @Autowired
    private AssetRoomService assetRoomService;

    @ApiOperation("根据资产id查询楼层")
    @RequestMapping(value = "/getLc", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getLc(Long floorId) {
        List<String> room = assetRoomService.getLc(floorId);
            return CommonResult.success(room);
    }
    @ApiOperation("根据资产id和楼层查询房间")
    @RequestMapping(value = "/getFj", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getFj(Long floorId,String floor) {
        List<AssetRoom> room = assetRoomService.getFj(floorId,floor);
            return CommonResult.success(room);
    }

    @ApiOperation("创建房间")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody AssetRoomParam assetRoomParam) {
        int count = assetRoomService.create(assetRoomParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }
    @ApiOperation("更新商品")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody AssetRoomParam productParam) {
        int count = assetRoomService.update(id, productParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("查询商品")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<AssetRoom>> getList(AssetRoomQueryParam assetRoomQueryParam,
                                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<AssetRoom> assetRooms = assetRoomService.list(assetRoomQueryParam, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(assetRooms));
    }

    @ApiOperation("根据商品名称或货号模糊查询")
    @RequestMapping(value = "/simpleList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<AssetRoom>> getList(String keyword) {
        List<AssetRoom> list = assetRoomService.list(keyword);
        return CommonResult.success(list);
    }

    @ApiOperation("批量修改审核状态")
    @RequestMapping(value = "/update/verifyStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateVerifyStatus(@RequestParam("ids") List<Long> ids,
                                           @RequestParam("verifyStatus") Integer verifyStatus,
                                           @RequestParam("detail") String detail) {
        int count = assetRoomService.updateVerifyStatus(ids, verifyStatus, detail);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("批量上下架商品")
    @RequestMapping(value = "/update/publishStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updatePublishStatus(@RequestParam("ids") List<Long> ids,
                                            @RequestParam("publishStatus") Integer publishStatus) {
        int count = assetRoomService.updatePublishStatus(ids, publishStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("批量推荐商品")
    @RequestMapping(value = "/update/recommendStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateRecommendStatus(@RequestParam("ids") List<Long> ids,
                                              @RequestParam("recommendStatus") Integer recommendStatus) {
        int count = assetRoomService.updateRecommendStatus(ids, recommendStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("批量设为新品")
    @RequestMapping(value = "/update/newStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateNewStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("newStatus") Integer newStatus) {
        int count = assetRoomService.updateNewStatus(ids, newStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "根据编号查询房间信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<AssetRoom> getItem(@PathVariable("id") Long id) {
        return CommonResult.success(assetRoomService.getBrand(id));
    }

    @ApiOperation("批量修改删除状态")
    @RequestMapping(value = "/update/deleteStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateDeleteStatus(@RequestParam("ids") List<Long> ids,
                                           @RequestParam("deleteStatus") Integer deleteStatus) {
        int count = assetRoomService.updateDeleteStatus(ids, deleteStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }
    @ApiOperation(value = "批量更新厂家制造商状态")
    @RequestMapping(value = "/update/factoryStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateFactoryStatus(@RequestParam("ids") List<Long> ids,
                                            @RequestParam("zszt") String zszt) {
        int count = assetRoomService.updateFactoryStatus(ids, zszt);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "批量更新厂家制造商状态")
    @RequestMapping(value = "/update/updateIsOccupancy", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateIsOccupancy(@RequestParam("ids") List<Long> ids,
                                            @RequestParam("isOccupancy") String isOccupancy) {
        int count = assetRoomService.updateIsOccupancy(ids, isOccupancy);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }
    @ApiOperation(value = "删除房间")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult delete(@PathVariable("id") Long id) {
        int count = assetRoomService.deleteBrand(id);
        if (count == 1) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("首页房间总览")
    @RequestMapping(value = "/homeRoomSum", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Map<String,Object>> homeRoomSum() {
        Map<String,Object>assetRooms = assetRoomService.homeRoomSum();
        return CommonResult.success(assetRooms);
    }

    @ApiOperation("订单统计")
    @RequestMapping(value = "/orderTj", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Map<String,Object>>> orderTj(Date beginTime, Date endTime) {
        List<Map<String,Object>>assetRooms = assetRoomService.orderTj(beginTime,endTime);
        return CommonResult.success(assetRooms);
    }

    @ApiOperation("导出房间")
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel( AssetRoomQueryParam assetRoomQueryParam, HttpServletResponse response) {
         assetRoomService.downloadExcel(assetRoomQueryParam,response);

    }
    @ApiOperation("导入资产")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public CommonResult importExcel( MultipartFile file) {
        try {
            assetRoomService.importExcel(file);
            return CommonResult.success("导入成功");
        }catch(Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
    }
}
