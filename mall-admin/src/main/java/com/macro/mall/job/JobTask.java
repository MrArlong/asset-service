package com.macro.mall.job;

import com.macro.mall.common.service.RedisService;
import com.macro.mall.model.AssetOrderRoom;
import com.macro.mall.service.asset.AssetOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author leec
 * @version 1.0
 * @date 2022/7/18
 * @description lettuce redis心跳检测，解决连接超时问题
 */
@Component
@EnableScheduling
@Slf4j
public class JobTask {

    @Autowired
    private RedisService redisService;

    @Autowired
    private AssetOrderService assetOrderService;

    //@Scheduled(cron = "0/5 * * * * ?")
    public void run() {
        // 查询已租
        List<AssetOrderRoom> assetOrderRooms = assetOrderService.selectIsOccupancyList();

        // 全部更新为未租
      //  assetOrderService.updateAll();
        List<Long> collect = assetOrderRooms.stream().map(AssetOrderRoom::getRoomId).collect(Collectors.toList());
      //  assetOrderService.updateIsOccupancy(collect);
    }

}
