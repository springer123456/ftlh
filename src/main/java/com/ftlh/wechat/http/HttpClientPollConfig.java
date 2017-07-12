package com.ftlh.wechat.http;

import org.apache.http.impl.client.HttpClientBuilder;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientPollConfig {

	private static final Integer HTTPCLIENT_CONNECTION_COUNT = 200;
	private static final Integer HTTPCLIENT_MAXPERROUTE_COUNT = 200;

	/**
	 * 
	 * SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null, new
	 * TrustSelfSignedStrategy()) .build(); HostnameVerifier hostnameVerifier =
	 * SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
	 * SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	 * sslcontext,hostnameVerifier); Registry<ConnectionSocketFactory>
	 * socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
	 * .register("http", PlainConnectionSocketFactory.getSocketFactory())
	 * .register("https", sslsf) .build();
	 * 
	 */

	@Bean
	SSLContext getSSlContext() {
		SSLContext sslcontext = null;
		try {
			sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sslcontext;
	}

	@Bean

	HostnameVerifier getHostnameVerifier() {
		HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.getDefaultHostnameVerifier();
		return hostnameVerifier;
	}

	@Bean
	SSLConnectionSocketFactory getsslf() {
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(getSSlContext(), getHostnameVerifier());
		return sslsf;
	}

	@Bean
	Registry<ConnectionSocketFactory> getSoketFactoryRegistry() {
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", getsslf()).build();
		return socketFactoryRegistry;
	}

	@Bean
	HttpClientConnectionManager gethttpClientConnectionManager() {
		HttpClientConnectionManager clientConnectionManager = new PoolingHttpClientConnectionManager(getSoketFactoryRegistry());
		((PoolingHttpClientConnectionManager) clientConnectionManager).setMaxTotal(HTTPCLIENT_CONNECTION_COUNT);
		// 设置单个路由最大连接数量
		((PoolingHttpClientConnectionManager) clientConnectionManager)
				.setDefaultMaxPerRoute(HTTPCLIENT_MAXPERROUTE_COUNT);
		((PoolingHttpClientConnectionManager) clientConnectionManager).setValidateAfterInactivity(10);
		return clientConnectionManager;
	}

	@Bean
	public HttpClientBuilder gethttpClientBuilder() {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		httpClientBuilder.setConnectionManager(gethttpClientConnectionManager());
		return httpClientBuilder;
	}

	@Bean
	CloseableHttpClient getHttpClient() {
		HttpClientBuilder myHttpClientBuilder = gethttpClientBuilder();
		// myHttpClientBuilder.setConnectionManager(clientConnectionManager);
		CloseableHttpClient client = myHttpClientBuilder.build();
		return client;
	}

	/**
	 * 
	 * 
	 * <bean id="requestConfigBuilder" class=
	 * "org.apache.http.client.config.RequestConfig.Builder"> <!-- 创建连接的最长时间 -->
	 * <property name="connectTimeout" value="${http.connectTimeout}"/> <!--
	 * 从连接池中获取到连接的最长时间 --> <property name="connectionRequestTimeout" value=
	 * "${http.connectionRequestTimeout}"/> <!-- 数据传输的最长时间 -->
	 * <property name="socketTimeout" value="${http.socketTimeout}"/> <!--
	 * 提交请求前测试连接是否可用 --> <property name="staleConnectionCheckEnabled" value=
	 * "${http.staleConnectionCheckEnabled}"/> </bean> <!-- 定义请求参数 -->
	 * <bean id="requestConfig" class=
	 * "org.apache.http.client.config.RequestConfig" factory-bean=
	 * "requestConfigBuilder" factory-method="build"> </bean>
	 */

	@Bean
	RequestConfig getRequestConfig() {
		RequestConfig myRequstconfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000)
				// .setStaleConnectionCheckEnabled(true)
				.setConnectionRequestTimeout(10000).setCookieSpec(CookieSpecs.DEFAULT).setExpectContinueEnabled(true)
				// .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM,
				// AuthSchemes.DIGEST))
				// .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
				.build();
		return myRequstconfig;
	}
}
