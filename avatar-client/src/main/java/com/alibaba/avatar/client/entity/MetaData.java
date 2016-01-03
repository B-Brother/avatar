package com.alibaba.avatar.client.entity;

/**
 * 最基本的元数据对象
 * 
 * @author beckham
 *
 */
public class MetaData {
	
	// dataId，数据唯一标识
	private String dataId;
	
	// group，分组
	private String group;

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	} 
}
