package com.bean;

/**
 * 登录返回结果
 * 
 * @author 21829
 */
public class LoginRet {
	
	private int resultCode;// Int -1失败 0成功 -2：未授权（返回该参数则需要调用第一个接口）
	private String message;// String 返回消息
	private String name;// String 用户名
	private String nickName;// String 用户昵称
	private String photo;// String 头像地址
	private int sex;// Int 1:男 2：女 3：秘密
	private String birthday;// DateTime 生日
	private String mobile;// String 手机号
	private String skinId;// String 皮肤类型编号
	private String userId;// String 用户编号
	private String sensitiveId;// String 敏感类型编号

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSkinId() {
		return skinId;
	}

	public void setSkinId(String skinId) {
		this.skinId = skinId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSensitiveId() {
		return sensitiveId;
	}

	public void setSensitiveId(String sensitiveId) {
		this.sensitiveId = sensitiveId;
	}

}
