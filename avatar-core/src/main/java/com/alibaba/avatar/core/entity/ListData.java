package com.alibaba.avatar.core.entity;

/**
 * MetaData的下一级
 * 
 * @author beckham
 *
 */
public class ListData {
	
	// 下层的dataId，一般情况下这里指代的是ip。
	private String subDataId;
	
	// 内容
	private String content;

	public String getSubDataId() {
		return subDataId;
	}

	public void setSubDataId(String subDataId) {
		this.subDataId = subDataId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
