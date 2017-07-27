package com.ftlh.wechat.device.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ftlh.wechat.device.dao.DeviceInfoMapper;
import com.ftlh.wechat.device.model.DeviceInfo;
import com.ftlh.wechat.device.service.DeviceInfoServiceI;

@Service
public class DeviceInfoServiceImpl implements DeviceInfoServiceI {
	@Autowired
	private DeviceInfoMapper mapper;

	public int deleteByPrimaryKey(Long id) {
		return mapper.deleteByPrimaryKey(id);
	};

	public int insert(DeviceInfo record) {
		return mapper.insert(record);
	};

	public int insertSelective(DeviceInfo record) {
		return mapper.insertSelective(record);
	};

	@Transactional
	@Cacheable(value = "cacheDevice", key = "#id")
	public DeviceInfo selectByPrimaryKey(Long id) {
		return mapper.selectByPrimaryKey(id);
	};

	public int updateByPrimaryKeySelective(DeviceInfo record) {
		return mapper.updateByPrimaryKeySelective(record);
	};

	public int updateByPrimaryKey(DeviceInfo record) {
		return mapper.updateByPrimaryKey(record);
	};
}