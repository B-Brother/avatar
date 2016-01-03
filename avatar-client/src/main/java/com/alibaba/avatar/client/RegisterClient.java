package com.alibaba.avatar.client;

import com.alibaba.avatar.client.entity.ListData;
import com.alibaba.avatar.client.entity.MetaData;

/**
 * 
 * 向服务端注册。
 * 
 * @author beckham
 *
 */
public class RegisterClient {
    
	/**
	 * 注册
	 * 
	 * @param group
	 * @param dataId
	 */
	public void register(MetaData meta, ListData data){
		// 1. 校验
		
		// 2. 向服务端发送注册信息并处理返回数据
		String response = AvatarDataTransfer.sendRegister(meta, data);
//		if(response.equals("success")){			// 这里应该有个规范
//			
//		}
	}
	
	public static void main(String[] args) {
		MetaData meta = new MetaData();
		meta.setDataId("aaa");
		meta.setGroup("bbb");
		
		ListData data = new ListData();
		data.setContent("ccc");
		data.setSubDataId("eee");
		
		new RegisterClient().register(meta, data);
	}
}
