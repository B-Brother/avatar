package com.alibaba.avatar.core.component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.alibaba.avatar.core.entity.ListData;
import com.alibaba.avatar.core.entity.MetaData; 

@Component
public class ConfigCore {

	// 注册者
	private ConcurrentHashMap<MetaData, List<ListData>> registerMap = new ConcurrentHashMap<>();
	
	// 消费者
	private ConcurrentHashMap<MetaData, List<ListData>> consumerMap = new ConcurrentHashMap<>();

	public ConfigCore(){
		init();
	}
	
	private void init(){
		
	}
	
	/**
	 * 注册者添加
	 * 
	 * @param meta
	 * @param data
	 */
	public void addRegister(MetaData meta, ListData data){
		safeAdd(registerMap, meta, data);
	}
	
	/**
	 * 消费者添加
	 * 
	 * @param meta
	 * @param data
	 */
	public void addConsumer(MetaData meta, ListData data){
		safeAdd(consumerMap, meta, data);
	} 
	
	// ------------ private --------------
	
	private void safeAdd(ConcurrentHashMap<MetaData, List<ListData>> map, MetaData meta, ListData data){
		List<ListData> dataList = map.get(meta);
		if(dataList == null){
			dataList = new ArrayList<>();
		}
		
		dataList.add(data);
		map.putIfAbsent(meta, dataList);
	}
}







