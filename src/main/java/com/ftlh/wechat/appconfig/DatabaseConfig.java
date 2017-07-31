package com.ftlh.wechat.appconfig;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.LocalCacheScope;
import org.aspectj.lang.annotation.Pointcut;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;

@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages = { "com.ftlh.wechat.device.dao", "com.ftlh.wechat.device.service",
		"com.ftlh.wechat.device.model", "com.ftlh.device.sevice.impl" })
@EnableTransactionManagement
public class DatabaseConfig {

	@Bean
	DataSource getDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setInitialSize(1);
		dataSource.setMinIdle(1);
		dataSource.setMaxActive(20);
		dataSource.setMaxWait(20000);
		dataSource.setTimeBetweenConnectErrorMillis(60000);
		dataSource.setMinEvictableIdleTimeMillis(30000);
		dataSource.setValidationQuery("SELECT 'x'");
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
		dataSource.setPoolPreparedStatements(true);
		dataSource.setMaxOpenPreparedStatements(20);
		dataSource.setUrl(
				"jdbc:mysql://caohaiwang.mysql.rds.aliyuncs.com:3306/ftlh?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		// validationQuery=SELECT 1
		// #jdbc_url=
		// #jdbc_username=root
		// #jdbc_password=iiiiii
		// jdbc_url=jdbc:mysql://caohaiwang.mysql.rds.aliyuncs.com:3306/ftlh?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull
		// jdbc_user=ftlh
		// jdbc_password=FTLHftlh1234!");
		dataSource.setUsername("ftlh");
		dataSource.setPassword("FTLHftlh1234!");
		// dataSource.set
		try {
			dataSource.setFilters("stat,log4j");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataSource;
	}

	/*
	 * 
	 * <!-- 慢SQL记录 --> <bean id="stat-filter"
	 * class="com.alibaba.druid.filter.stat.StatFilter"> <property
	 * name="slowSqlMillis" value="10000" /> <property name="logSlowSql"
	 * value="true" /> </bean> <!-- myBatis文件 --> <bean id="sqlSessionFactory"
	 * class="org.mybatis.spring.SqlSessionFactoryBean"> <property
	 * name="configLocation"
	 * value="classpath:com/ftlh/wechat/device/config/mybatis-config.xml" />
	 * <property name="dataSource" ref="dataSource" /> <!-- 自动扫描entity目录,
	 * 省掉Configuration.xml里的手工配置 --> <property name="mapperLocations"
	 * value="classpath:com/ftlh/wechat/device/mapping/*.xml" /> </bean>
	 * 
	 * <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
	 * <property name="basePackage" value="com.ftlh.wechat.device.dao" />
	 * <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
	 * </bean>
	 * 
	 * <!-- 配置事务管理器 -->
	 * 
	 * <bean id="transactionManager"
	 * class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
	 * <property name="dataSource" ref="dataSource" />
	 * 
	 * </bean>
	 * 
	 * <aop:config> <aop:advisor advice-ref="druid-stat-interceptor"
	 * pointcut-ref="druid-stat-pointcut" /> </aop:config> <bean
	 * id="druid-stat-pointcut"
	 * class="org.springframework.aop.support.JdkRegexpMethodPointcut"
	 * scope="prototype"> <property name="patterns"> <list>
	 * <value>com.ftlh.wechat.device.service.impl.*</value>
	 * <value>com.ftlh.wechat.device.dao.*</value> </list> </property> </bean>
	 * <bean id="druid-stat-interceptor"
	 * class="com.alibaba.druid.support.spring.stat.DruidStatInterceptor">
	 * </bean>
	 */
	// @Bean
	// JdkRegexpMethodPointcut getJdkRegexpMethodPointcut(){
	// JdkRegexpMethodPointcut pointcut =new JdkRegexpMethodPointcut();
	// pointcut.setPattern("com.ftlh.wechat.device.dao.*");
	// pointcut.setPattern("com.ftlh.wechat.device.service.impl.*");
	// return pointcut;
	// }

	@Bean("defaultDataSource")
	DruidStatInterceptor getDruidStatInterceptor() {
		return new DruidStatInterceptor();
	}

	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(getDataSource());
	}

	@Bean
	StatFilter getStatFilter() {
		StatFilter filter = new StatFilter();
		filter.setSlowSqlMillis(5000);
		filter.setLogSlowSql(true);
		return filter;// .set
	}

	@Bean
	Configuration getConfiguration() {
		Configuration configuration = new Configuration();
		configuration.setAggressiveLazyLoading(true);
		// configuration.setLogImpl((Class<? extends Log>) Log4j2Impl.class);
		configuration.setCacheEnabled(true);
		configuration.setLocalCacheScope(LocalCacheScope.SESSION);
		configuration.setLogImpl(Log4jImpl.class);
		return configuration;

	}

	@Bean("SqlSessionFactoryBean")
	SqlSessionFactoryBean getsqlSessionFactory() {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(getDataSource());
		// sqlSessionFactory.setConfigLocation(new Resource(""));
		try {
			sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
					.getResources("classpath:com/ftlh/wechat/device/mapping/*.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sqlSessionFactory.setConfiguration(getConfiguration());
		// sqlSessionFactory.setCache(cache);
		// sqlSessionFactory.setMapperLocations(mapperLocations);
		return sqlSessionFactory;
	}

	@Bean
	MapperScannerConfigurer getMapperScannerConfigurer() {
		MapperScannerConfigurer mapperscenconfig = new MapperScannerConfigurer();
		mapperscenconfig.setBasePackage("com.ftlh.wechat.device.dao");
		mapperscenconfig.setSqlSessionFactoryBeanName("SqlSessionFactoryBean");
		// mapperscenconfig.setSqlSessionFactoryBeanName(sqlSessionFactoryName););
		return mapperscenconfig;
	}
}
