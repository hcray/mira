package com.bean;


/**
 * 创建token结果
 * @author 21829
 */
public class VersionResultBean {
	
	/**
	 * int 返回状态代码：0-成功 -1-失败
	 */
	private int ResultCode;
	
	/**
	 * 返回消息，成功则返回“成功”，失败则返回错误信息
	 */
	private String Message;
	
	private int Id;	//Int	版本编号
	
	private String Name;//	String	版本名称
	
	private String Code;//	String	版本代号
	
	private String Description;//	String	更新说明
	
	private String Url;//	String	更新地址

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

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	@Override
	public String toString() {
		return "VersionResultBean [ResultCode=" + ResultCode + ", Message="
				+ Message + ", Id=" + Id + ", Name=" + Name + ", Code=" + Code
				+ ", Description=" + Description + ", Url=" + Url + "]";
	}
	
	
}
