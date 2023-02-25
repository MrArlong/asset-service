package com.macro.mall.controller;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.AssetFloor;
import com.macro.mall.model.AssetRoom;
import com.macro.mall.model.CmsPrefrenceArea;
import com.macro.mall.service.CmsPrefrenceAreaService;
import com.macro.mall.service.asset.AssetFloorService;
import com.macro.mall.service.asset.AssetRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/applet")
@CrossOrigin
public class AppletController {
    @Autowired
    private AssetFloorService assetFloorService;
    @Autowired
    private AssetRoomService assetRoomService;

    @ApiOperation("小程序热门出租")
    @GetMapping(value = "/wxHostFloorList")
    public CommonResult<List<AssetFloor>> wxHostFloorList() {
        List<AssetFloor> prefrenceAreaList = assetFloorService.wxHostFloorList();
        return CommonResult.success(prefrenceAreaList);
    }

    @ApiOperation("资产列表")
    @GetMapping(value = "/floorListAll")
    public CommonResult<List<AssetFloor>> floorListAll(String syzt) {
        List<AssetFloor> prefrenceAreaList = assetFloorService.floorListAll(syzt);
        return CommonResult.success(prefrenceAreaList);
    }

    @ApiOperation("小程序点击详情查询房间")
    @GetMapping(value = "/floorDetailsById")
    public CommonResult<List<AssetRoom>> floorDetailsById(@RequestParam("floorId") Long floorId) {
        List<AssetRoom> prefrenceAreaList = assetRoomService.findByFloorId(floorId);
        return CommonResult.success(prefrenceAreaList);
    }

    @ApiOperation(value = "根据编号查询资产信息")
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<AssetFloor> getItem(@RequestParam("id") Long id) {
        return CommonResult.success(assetFloorService.getBrand(id));
    }

    @ApiOperation(value = "查询首页轮播图和广告")
    @RequestMapping(value = "/getHometpAndGgtp", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Map<String,Object>> getHometpAndGgtp() {
        return CommonResult.success(assetFloorService.getHometpAndGgtp());
    }
}
