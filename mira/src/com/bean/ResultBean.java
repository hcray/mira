package com.bean;

/**
 * 创建token结果
 * @author 21829
 */
public class ResultBean {
	
	/**
	 * int 返回状态代码：0-成功 -1-失败
	 */
	private int resultCode;
	
	/**
	 * 返回消息，成功则返回“成功”，失败则返回错误信息
	 */
	private String Message;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}
}
