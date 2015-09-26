package com.bean;

import java.util.List;

public class DetectionRet {
	/**
	 * -1失败 0成功
	 */
	private int ResultCode;
	
	/**
	 * 返回消息
	 */
	private String Message;

	/**
	 * 返回数据
	 */
	private List<DetectionBean> DetectionList;

	public int getResultCode() {
		return ResultCode;
	}

	public void setResultCode(int resultCode) {
		ResultCode = resultCode;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public List<DetectionBean> getDetectionList() {
		return DetectionList;
	}

	public void setDetectionList(List<DetectionBean> detectionList) {
		DetectionList = detectionList;
	}


}
