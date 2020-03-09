/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.zhinenggui.entity.ZngOrder;
import com.jeesite.modules.zhinenggui.search.CabinetsAndOrder;
import com.jeesite.modules.zhinenggui.search.CourierOrderDetail;
import com.jeesite.modules.zhinenggui.search.OrderDetail;
import com.jeesite.modules.zhinenggui.search.OrderSearch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 订单表DAO接口
 * @author lzy
 * @version 2018-08-28
 */
@MyBatisDao
public interface ZngOrderDao extends CrudDao<ZngOrder> {
	
    List<OrderSearch> findList(OrderSearch zhinengguiOrder);

    ZngOrder getOrderByExpressNumber(@Param("expressNumber")String expressNumber);

    CabinetsAndOrder getDoorByDeviceIdAndCode(@Param("validCode")String validCode, @Param("deviceId")String deviceId);

    CabinetsAndOrder getDoorByDeviceIdAndSize(@Param("deviceId")String deviceId, @Param("size")String size);

    CabinetsAndOrder getDoorByOrderIdAndPhone(@Param("sendDeviceId")String sendDeviceId, @Param("phone")String phone);

    CabinetsAndOrder getDoorByDeviceId(@Param("deviceId")String deviceId);

    List<OrderDetail> getNotGetOrder(@Param("userId")String userId);

    List<ZngOrder> getToSendOrderByCourier(@Param("deviceId")String deviceId);

    List<OrderDetail> getOrderByUserIdAndDeviceId(@Param("deviceId")String deviceId, @Param("id")String id);

    List<CourierOrderDetail> getCourierOrder(@Param("deviceId")String deviceId, @Param("userId")String userId);

    List<CourierOrderDetail> getNotDeviceOrderList(@Param("deviceId")String deviceId);

    List<OrderSearch> getOrderListByPhone(@Param("deviceId")String deviceId, @Param("phone")String phone);

    List<OrderSearch> getOrderListByPhoneNoState(@Param("deviceId")String deviceId, @Param("phone")String phone);
    
    List<OrderSearch> getOrderListByPhoneNoOver(@Param("deviceId")String deviceId, @Param("phone")String phone);
    
    List<OrderSearch> getOverOrderListByPhone(@Param("phone") String phone);
    

    void saveCopyOrder(@Param("expressNumber")String expressNumber);

    List<ZngOrder> getOrderByDoorIdAndDeviceId(@Param("deviceId")String deviceId, @Param("doorId")String doorId);
    
    List<ZngOrder> getOverOrderByDoorIdAndDeviceId(@Param("deviceId")String deviceId , @Param("doorId")String doorId);

    CabinetsAndOrder getDoorById(@Param("oid")String oid);

}