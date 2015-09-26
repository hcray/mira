package com.bean;

public class DetectionBean {
	
	private String CheckinTime;
	
	private String Comment;
	
	private String Temperature;
	
	private String Humidity;
	
	private int Position;
	
	private String UserId;
	
	private String Score;
	
	private String Water;

	public String getCheckinTime() {
		return CheckinTime;
	}

	public void setCheckinTime(String checkinTime) {
		CheckinTime = checkinTime;
	}

	public String getComment() {
		return Comment;
	}

	public void setComment(String comment) {
		Comment = comment;
	}

	public String getTemperature() {
		return Temperature;
	}

	public void setTemperature(String temperature) {
		Temperature = temperature;
	}

	public String getHumidity() {
		return Humidity;
	}

	public void setHumidity(String humidity) {
		Humidity = humidity;
	}

	public int getPosition() {
		return Position;
	}

	public void setPosition(int position) {
		Position = position;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getScore() {
		return Score;
	}

	public void setScore(String score) {
		Score = score;
	}

	public String getWater() {
		return Water;
	}

	public void setWater(String water) {
		Water = water;
	}

	@Override
	public String toString() {
		return "DetectionBean [CheckinTime=" + CheckinTime + ", Comment="
				+ Comment + ", Temperature=" + Temperature + ", Humidity="
				+ Humidity + ", Position=" + Position + ", UserId=" + UserId
				+ ", Score=" + Score + ", Water=" + Water + "]";
	}
}
