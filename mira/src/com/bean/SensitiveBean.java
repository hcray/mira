package com.bean;

/**
 * 敏感类型小类
 * @author CYY
 *
 */
public class SensitiveBean {
	
	/**
	 * 敏感类型编号
	 */
	private String sensitiveId;
	
	/**
	 * 敏感类型名称
	 */
	private String name;

	public String getSensitiveId() {
		return sensitiveId;
	}

	public void setSensitiveId(String sensitiveId) {
		this.sensitiveId = sensitiveId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
