package com.ftlh.wechat.device.service;

import com.ftlh.wechat.device.model.UserDevice;

public interface UserDeviceServiceI {
	int deleteByPrimaryKey(String openid);

	int insert(UserDevice record);

	int insertSelective(UserDevice record);

	UserDevice selectByPrimaryKey(String openid);

	int updateByPrimaryKeySelective(UserDevice record);

	int updateByPrimaryKey(UserDevice record);
}
