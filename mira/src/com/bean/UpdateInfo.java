package com.bean;

public class UpdateInfo {
	private String version;
	private String name;
	private String url;
	
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("version: ").append(version)
		  .append(" name: ").append(name)
		  .append(" url: ").append(url);
		return sb.toString();
	}
	
}
