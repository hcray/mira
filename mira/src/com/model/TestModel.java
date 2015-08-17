package com.model;

public class TestModel {
	public int id;
	public short wenDu;
	public short shiDu;
	public short shuiFen;
	public short ziWaiXian;
	/**
	 * 评分
	 */
	public int score;
	public int type;
	public int status;
	public long time;
	public int weatherpm;
	public String weatherziwaixian;
	public String weatherwendu;
	public String weathercity;
	
	@Override
	public String toString() {
		return "TestModel [id=" + id + ", wenDu=" + wenDu + ", shiDu=" + shiDu
				+ ", shuiFen=" + shuiFen + ", ziWaiXian=" + ziWaiXian + ", score=" + score
				+ ", type=" + type + ", status=" + status + ", time=" + time
				+ ", weatherpm=" + weatherpm + ", weatherziwaixian="
				+ weatherziwaixian + ", weatherwendu=" + weatherwendu
				+ ", weathercity=" + weathercity + "]";
	}
}
