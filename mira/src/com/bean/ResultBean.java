package com.bean;

import java.util.List;

/**
 * 创建token结果
 * @author 21829
 */
public class ResultBean {
	
	/**
	 * int 返回状态代码：0-成功 -1-失败
	 */
	private int ResultCode;
	
	/**
	 * 返回消息，成功则返回“成功”，失败则返回错误信息
	 */
	private String Message;
	
	/**
	 * 皮肤类型小类
	 */
	private List<SkinBean> skinList;
	
	/**
	 * 敏感类型小类
	 */
	private List<SensitiveBean> SensitiveList;

	public int getResultCode() {
		return ResultCode;
	}

	public void setResultCode(int resultCode) {
		this.ResultCode = resultCode;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public List<SkinBean> getSkinList() {
		return skinList;
	}

	public void setSkinList(List<SkinBean> skinList) {
		this.skinList = skinList;
	}

	public List<SensitiveBean> getSensitiveList() {
		return SensitiveList;
	}

	public void setSensitiveList(List<SensitiveBean> sensitiveList) {
		SensitiveList = sensitiveList;
	}
}
