/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.zhinenggui.entity.ZngDevice;
import com.jeesite.modules.zhinenggui.search.FileImageSearch;
import com.jeesite.modules.file.entity.FileUpload;

/**
 * 设备表DAO接口
 * @author lzy
 * @version 2018-08-28
 */
@MyBatisDao
public interface ZngDeviceDao extends CrudDao<ZngDevice> {

    ZngDevice getOneByDeviceId(@Param("deviceId")String deviceId);
    
    List<FileImageSearch> getFileImageList(@Param("deviceId")String deviceId);
    
    List<FileUpload> getFileDeviceImageList(@Param("deviceId")String deviceId);

}