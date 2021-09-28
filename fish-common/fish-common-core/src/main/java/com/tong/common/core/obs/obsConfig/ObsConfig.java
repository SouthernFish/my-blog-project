package com.tong.common.core.obs.obsConfig;

import com.obs.services.ObsClient;

public class ObsConfig {
    public static final String endPoint = "obs.cn-south-1.myhuaweicloud.com";
    public static final String ak = "WQ2OXVCOYGFY7D6DVIZQ";
    public static final String sk = "38t423rfXpxCGxwmxY3nQyvYTUPVzMEZaqkVjn1G";

    public ObsConfig() {
    }

    public static ObsClient getObsClient() {
        return new ObsClient("WQ2OXVCOYGFY7D6DVIZQ", "38t423rfXpxCGxwmxY3nQyvYTUPVzMEZaqkVjn1G", "obs.cn-south-1.myhuaweicloud.com");
    }
}