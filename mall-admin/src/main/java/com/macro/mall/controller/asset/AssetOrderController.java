package com.macro.mall.controller.asset;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.*;
import com.macro.mall.model.AssetOrder;
import com.macro.mall.model.AssetOrderRoom;
import com.macro.mall.model.AssetRoom;
import com.macro.mall.service.asset.AssetOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 订单管理Controller
 * Created by macro on 2018/4/26.
 */
@Controller
@Api(tags = "AssetOrderController")
@Tag(name = "AssetOrderController", description = "订单管理")
@RequestMapping("/assetorder")
public class AssetOrderController {
    @Autowired
    private AssetOrderService assetOrderService;

    @ApiOperation("查询最新订单号")
    @RequestMapping(value = "/getOrderNum", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getOrderNum() {
        Long orderNum = assetOrderService.getOrderNum();
        return CommonResult.success(orderNum);
    }



    @ApiOperation("创建订单")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody AssetOrderParam assetOrderParam) {
        int count = assetOrderService.create(assetOrderParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("更新订单")
    @RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateOrder(@RequestBody AssetOrderParam assetOrderParam) {
        int count = assetOrderService.updateOrder(assetOrderParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("删除订单")
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult deleteOrder(@RequestParam("orderId") Long orderId ) {
        int count = assetOrderService.deleteOrder(orderId);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("根据订单id获取订单和房间")
    @RequestMapping(value = "/updateInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<AssetOrderParam> getUpdateInfo(@PathVariable Long id) {
        AssetOrderParam updateInfo = assetOrderService.getUpdateInfo(id);
        return CommonResult.success(updateInfo);
    }

    @ApiOperation("更新商品")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody AssetOrderParam assetOrderParam) {
        int count = assetOrderService.update(id, assetOrderParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("查询商品")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<AssetOrder>> getList(AssetOrderQueryParam assetRoomQueryParam,
                                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<AssetOrder> assetRooms = assetOrderService.list(assetRoomQueryParam, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(assetRooms));
    }

    @ApiOperation("根据商品名称或货号模糊查询")
    @RequestMapping(value = "/simpleList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<AssetOrder>> getList(String keyword) {
        List<AssetOrder> list = assetOrderService.list(keyword);
        return CommonResult.success(list);
    }

    @ApiOperation("批量上下架商品")
    @RequestMapping(value = "/update/publishStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updatePublishStatus(@RequestParam("ids") List<Long> ids,
                                            @RequestParam("publishStatus") Integer publishStatus) {
        int count = assetOrderService.updatePublishStatus(ids, publishStatus);
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
        int count = assetOrderService.updateRecommendStatus(ids, recommendStatus);
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
        int count = assetOrderService.updateNewStatus(ids, newStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "根据编号查询房间信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<AssetOrder> getItem(@PathVariable("id") Long id) {
        return CommonResult.success(assetOrderService.getBrand(id));
    }

    @ApiOperation(value = "根据订单id查询房间信息")
    @RequestMapping(value = "/room/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<AssetOrderRoomDto>> getOrderRoom(@PathVariable("id") Long id) {
        return CommonResult.success(assetOrderService.getOrderRoom(id));
    }

    @ApiOperation("批量修改删除状态")
    @RequestMapping(value = "/update/deleteStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateDeleteStatus(@RequestParam("ids") List<Long> ids,
                                           @RequestParam("deleteStatus") Integer deleteStatus) {
        int count = assetOrderService.updateDeleteStatus(ids, deleteStatus);
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
        int count = assetOrderService.updateFactoryStatus(ids, zszt);
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
        int count = assetOrderService.updateIsOccupancy(ids, isOccupancy);
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
        int count = assetOrderService.deleteBrand(id);
        if (count == 1) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }


    @ApiOperation("临期预警")
    @RequestMapping(value = "/lqyj", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<AssetOrderRoomDto>> lqyj(AssetRoomQueryParam assetRoomQueryParam,
                                                         @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<AssetOrderRoomDto> assetRooms = assetOrderService.lqyj(assetRoomQueryParam, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(assetRooms));
    }
    @ApiOperation("导出订单")
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel(AssetOrderQueryParam param,HttpServletResponse response) {
        assetOrderService.downloadExcel(param,response);

    }

}
