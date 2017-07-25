package com.ftlh.wechat.device.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftlh.wechat.device.dao.UserDeviceMapper;
import com.ftlh.wechat.device.model.UserDevice;
import com.ftlh.wechat.device.service.UserDeviceServiceI;

@Service
public class UserDeviceServiceImpl implements UserDeviceServiceI {
	@Autowired
	private UserDeviceMapper mapper;

	@Override
	public int deleteByPrimaryKey(String openid) {
 
		return mapper.deleteByPrimaryKey(openid);
	}

	@Override
	public int insert(UserDevice record) {
 
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(UserDevice record) {
 
		return mapper.insertSelective(record);
	}

	@Override
	public UserDevice selectByPrimaryKey(String openid) {
 
		return mapper.selectByPrimaryKey(openid);
	}

	@Override
	public int updateByPrimaryKeySelective(UserDevice record) {
 
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(UserDevice record) {

		return mapper.updateByPrimaryKeySelective(record);
	}

}
