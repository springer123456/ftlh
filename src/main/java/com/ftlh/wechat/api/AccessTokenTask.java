package com.ftlh.wechat.api;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AccessTokenTask implements InitializingBean  {
	@Autowired
	private AccessTokenService Service;
	
	private ExecutorService executorService= Executors.newSingleThreadExecutor();

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		executorService.execute(Service);
		
	}

}
