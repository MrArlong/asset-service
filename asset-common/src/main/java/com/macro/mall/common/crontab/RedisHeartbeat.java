package com.macro.mall.common.crontab;

import com.macro.mall.common.service.RedisService;
import com.macro.mall.common.service.impl.RedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author leec
 * @version 1.0
 * @date 2022/7/18
 * @description lettuce redis心跳检测，解决连接超时问题
 */
@Component
@EnableScheduling
public class RedisHeartbeat {

    @Autowired
    private RedisService redisService;

    @Scheduled(cron = "0 0/2 * * * *")
    public void timer() {
        redisService.get("heartbeat");
    }

}
