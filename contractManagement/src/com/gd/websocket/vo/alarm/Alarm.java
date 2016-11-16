package com.gd.websocket.vo.alarm;

/**
 * 报警信息
 * 
 * @author tyutNo4<br>
 *         创建时间：2015-1-20下午3:56:11
 */
public class Alarm {

	private String equipmentId;
	private String logId;
	private String alarmName;
	private String alarmLevel;
	private String alarmType;
	private String alarmstate;// 报警1、消除0
	private String alarmSource;
	private String alarmDesc;
	private String alarmTime;// 在实时报警这块需要以时间进行排序，具体倒叙or顺序实际开发中进行验证 TODO
	private String alarmDetail;

	public String getAlarmstate() {
		return alarmstate;
	}

	public void setAlarmstate(String alarmstate) {
		this.alarmstate = alarmstate;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public String getAlarmSource() {
		return alarmSource;
	}

	public void setAlarmSource(String alarmSource) {
		this.alarmSource = alarmSource;
	}

	public String getAlarmDesc() {
		return alarmDesc;
	}

	public void setAlarmDesc(String alarmDesc) {
		this.alarmDesc = alarmDesc;
	}

	public String getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}

	public String getAlarmDetail() {
		return alarmDetail;
	}

	public void setAlarmDetail(String alarmDetail) {
		this.alarmDetail = alarmDetail;
	}

	public String getAlarmName() {
		return alarmName;
	}

	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}

}
