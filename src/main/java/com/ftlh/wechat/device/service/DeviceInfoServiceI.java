package com.ftlh.wechat.device.service;


import com.ftlh.wechat.device.model.DeviceInfo;

public interface DeviceInfoServiceI {
    int deleteByPrimaryKey(Long id);
    int insert(DeviceInfo record);
    int insertSelective(DeviceInfo record);
    DeviceInfo selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(DeviceInfo record);
    int updateByPrimaryKey(DeviceInfo record);
}