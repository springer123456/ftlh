package com.ftlh.wechat.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenService implements Runnable {

	@Autowired
	private AccessTokenUtil util;

	private static AccessToken accesstoken = new AccessToken();
	
	private static JsApiTicket ticket =new JsApiTicket();

	public static JsApiTicket getTicket() {
		return ticket;
	}




	public static void setTicket(JsApiTicket ticket) {
		AccessTokenService.ticket = ticket;
	}




	public static AccessToken getAccesstoken() {
		return accesstoken;
	}
	
	
	

	public static void setAccesstoken(AccessToken accesstoken) {
		AccessTokenService.accesstoken = accesstoken;
	}

	private static final Logger logger = LoggerFactory.getLogger(AccessTokenService.class);

	@Override
	public void run() {

		while (true) {
			  try{
				accesstoken = (AccessToken) util.getAccessToken();
			
				if (null != accesstoken) {    
                    ticket = util.geTicket(accesstoken.getAccess_token());

                    logger.info("获取access_token成功，有效时长{}秒 token:{}",accesstoken.getExpires_in()+"", accesstoken.getAccess_token());   
                    if(ticket!=null){
                    logger.info("获取jsapiticket成功，有效时长{}秒 ticket:{}",ticket.getExpires_in()+"", ticket.getTicket());   
                    
                    }
                    // 休眠7000秒    
                    Thread.sleep((accesstoken.getExpires_in() - 200) * 1000);    
                    
                } else {    
                    // 如果access_token为null，60秒后再获取    
                    Thread.sleep(60 * 1000);    
                }    
            } catch (InterruptedException e) {    
                try {    
                    Thread.sleep(60 * 1000);    
                } catch (InterruptedException e1) {    
                    logger.error("{}", e1);    
                }    
                logger.error("{}", e);    
            }    
			
			
			
		}

	}

	

}
