package com.jeesite.modules.utils;

import com.jeesite.modules.zhinenggui.entity.ZngDevice;

import java.util.List;

public class CommonUtils {

    public static String getDevicesIds(List<ZngDevice> list) {
        String ids = "";
        for (ZngDevice device : list) {
            String id = device.getDeviceId();
            if (!"".equalsIgnoreCase(ids)) {
                ids += ",";
            }
            ids += id;
        }
        return ids;
    }
}
