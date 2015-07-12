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
	
	private String sex;
	
	private String age;
	
	private String height;
	
	private String weight;
	
	private String menses;
	
	private String skinType;
	
	private int account;
	
	private String password;

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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
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

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	@Override
	public String toString() {
		return "User [nickName=" + nickName + ", sign=" + sign + ", level="
				+ level + ", grades=" + grades + ", sex=" + sex + ", age="
				+ age + ", height=" + height + ", weight=" + weight
				+ ", menses=" + menses + ", skinType=" + skinType
				+ ", account=" + account + ", password=" + password + "]";
	}
}
