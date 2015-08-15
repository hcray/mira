package com.bean;

import java.util.List;

/**
 * 用户登录返回的结果
 * 
 * @author 21829
 */
public class RetUserBean {

	/**
	 * int 返回状态代码：0-成功 -1-失败
	 */
	private int ResultCode;

	/**
	 * 返回消息，成功则返回“成功”，失败则返回错误信息
	 */
	private String Message;

	private String Name;// String 用户名
	
	private String NickName;// String 用户昵称
	
	private String Photo;// String 头像地址
	
	private int Sex;// Int 1:男 2：女 3：秘密
	
	private String Birthday;// DateTime 生日
	
	private String Mobile;// String 手机号
	
	private String SkinId;// String 皮肤类型编号
	
	private String UserId;// String 用户编号
	
	private String SensitiveId;// String 敏感类型编号

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

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getNickName() {
		return NickName;
	}

	public void setNickName(String nickName) {
		NickName = nickName;
	}

	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String photo) {
		Photo = photo;
	}

	public int getSex() {
		return Sex;
	}

	public void setSex(int sex) {
		Sex = sex;
	}

	public String getBirthday() {
		return Birthday;
	}

	public void setBirthday(String birthday) {
		Birthday = birthday;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getSkinId() {
		return SkinId;
	}

	public void setSkinId(String skinId) {
		SkinId = skinId;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getSensitiveId() {
		return SensitiveId;
	}

	public void setSensitiveId(String sensitiveId) {
		SensitiveId = sensitiveId;
	}

	@Override
	public String toString() {
		return "RetUserBean [ResultCode=" + ResultCode + ", Message=" + Message
				+ ", Name=" + Name + ", NickName=" + NickName + ", Photo="
				+ Photo + ", Sex=" + Sex + ", Birthday=" + Birthday
				+ ", Mobile=" + Mobile + ", SkinId=" + SkinId + ", UserId="
				+ UserId + ", SensitiveId=" + SensitiveId + "]";
	}

}
