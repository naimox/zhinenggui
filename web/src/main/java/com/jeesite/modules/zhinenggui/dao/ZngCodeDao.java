/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.zhinenggui.entity.ZngCode;
import com.jeesite.modules.zhinenggui.search.LostValidCode;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 验证码通用表DAO接口
 * @author lzy
 * @version 2018-08-28
 */
@MyBatisDao
public interface ZngCodeDao extends CrudDao<ZngCode> {
	//根据userID获取验证码信息
	ZngCode getInfo(@Param("phone")String phone, @Param("type")String type);

	List<ZngCode> getCodeListByPhoneAndType(@Param("phone")String phone, @Param("type")String type);

	void updateCodeByPhoneAndType(@Param("phone")String phone, @Param("type")String type);
	
	void updateStatusByTelAndCode(@Param("tel")String tel, @Param("code")String code);

	void updateStatusById(@Param("id")String id);

	List<LostValidCode> getLostValidCode(@Param("deviceId")String deviceId, @Param("getCode")String getCode);

	void addStatusById(@Param("id")String id);

    List<ZngCode> getValidCodeList(@Param("phone")String phone, @Param("orderId")String orderId);

    ZngCode getByPhoneAndCode(ZngCode zhinengguiCode);

    List<ZngCode> findByUserId(ZngCode zhinengguiCode);
    
    void updateOrderCode6(@Param("codeId")String codeId);
}