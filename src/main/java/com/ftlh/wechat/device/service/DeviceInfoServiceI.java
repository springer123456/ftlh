package com.ftlh.wechat.device.service;


import org.springframework.transaction.annotation.Transactional;

import com.ftlh.wechat.device.model.DeviceInfo;

public interface DeviceInfoServiceI {
    int deleteByPrimaryKey(Long id);
	@Transactional
    int insert(DeviceInfo record);
	@Transactional
	int insertSelective(DeviceInfo record);
	@Transactional
    DeviceInfo selectByPrimaryKey(Long id);
	@Transactional
    int updateByPrimaryKeySelective(DeviceInfo record);
	@Transactional
	int updateByPrimaryKey(DeviceInfo record);
}