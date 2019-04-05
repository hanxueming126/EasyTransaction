package com.yiqiniu.easytrans.rpc.impl.rest;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.ribbon.RibbonClientSpecification;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yiqiniu.easytrans.filter.EasyTransFilterChainFactory;
import com.yiqiniu.easytrans.monitor.MonitorConsumerFactory;
import com.yiqiniu.easytrans.rpc.EasyTransRpcConsumer;
import com.yiqiniu.easytrans.rpc.EasyTransRpcProvider;
import com.yiqiniu.easytrans.serialization.ObjectSerializer;

/** 
* @author xudeyou 
*/
@Configuration
@ConditionalOnProperty(name="easytrans.rpc.rest-ribbon.enabled",havingValue="true",matchIfMissing=true)
@EnableConfigurationProperties(RestRibbonEasyTransRpcProperties.class)
public class RestRibbonEasyTransRpcConfiguration {
	
	@Bean
	@ConditionalOnMissingBean(EasyTransRpcConsumer.class)
	public RestRibbonEasyTransRpcConsumerImpl restRibbonEasyTransRpcConsumerImpl(RestRibbonEasyTransRpcProperties properties, ObjectSerializer serializer, ApplicationContext ctx,  List<RibbonClientSpecification> configurations){
		return new RestRibbonEasyTransRpcConsumerImpl(properties, serializer, ctx, configurations);
	}
	
	@Bean
	@ConditionalOnMissingBean(EasyTransRpcProvider.class)
	public RestRibbonEasyTransRpcProviderImpl restRibbonEasyTransRpcProviderImpl(EasyTransFilterChainFactory filterChainFactory, ObjectSerializer serializer){
		return new RestRibbonEasyTransRpcProviderImpl(filterChainFactory, serializer);
	}
	
	@Bean
	@ConditionalOnProperty(name="easytrans.rpc.rest-ribbon.monitor.enabled",havingValue="true",matchIfMissing=true)
	public RestRibbonMonitorProvider restRibbonMonitorProvider() {
	    return new RestRibbonMonitorProvider();
	}
	
	@Bean
	@ConditionalOnMissingBean(MonitorConsumerFactory.class)
	public MonitorConsumerFactory monitorConsumerFactory(RestRibbonEasyTransRpcConsumerImpl consumer) {
	    return new RestRibbonMonitorConsumerFactory(consumer);
	}
	
}
