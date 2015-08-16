package com.model;

public class User {
	/**
	 * 头像的图片文件名
	 */
	private String face;
	
	private String nickName;
	
	private String sign;
	
	private String level;
	
	private String grades;
	
	/**
	 * 用户性别 1：男 2：女 3：保密
	 */
	private int sex;
	
	private String birthday;
	
	private String height;
	
	private String weight;
	
	private String menses;
	
	private String skinType;
	
	private String account;
	
	private String userId;

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getGrades() {
		return grades;
	}

	public void setGrades(String grades) {
		this.grades = grades;
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

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getMenses() {
		return menses;
	}

	public void setMenses(String menses) {
		this.menses = menses;
	}

	public String getSkinType() {
		return skinType;
	}

	public void setSkinType(String skinType) {
		this.skinType = skinType;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "User [face=" + face + ", nickName=" + nickName + ", sign="
				+ sign + ", level=" + level + ", grades=" + grades + ", sex="
				+ sex + ", birthday=" + birthday + ", height=" + height
				+ ", weight=" + weight + ", menses=" + menses + ", skinType="
				+ skinType + ", account=" + account + ", userId=" + userId
				+ "]";
	}
	
}
