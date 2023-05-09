package com.xms.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangjs
 */
@Configuration
public class SeqUtil {

    /**
     * 数据中心
     */
    private static long dataCenterId;

    /**
     * 机器标识
     */
    private static long machineId;

    @Value(value = "${snow.dataCenterId}")
    public void setDataCenterId(long dataCenterId) {
        SeqUtil.dataCenterId = dataCenterId;
    }

    @Value(value = "${snow.machineId}")
    public void setMachineId(long machineId) {
        SeqUtil.machineId = machineId;
    }

    public static long getSnowId() {
        System.out.println(dataCenterId + "----" + machineId);
        SnowFlakeShortUrl snowFlake = new SnowFlakeShortUrl(dataCenterId, machineId);
        return snowFlake.nextId();
    }
}
