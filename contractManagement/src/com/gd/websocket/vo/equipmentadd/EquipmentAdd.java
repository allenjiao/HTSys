package com.gd.websocket.vo.equipmentadd;

/**
 * 添加的设备信息
 * 
 * @author tyutNo4<br>
 *         创建时间：2015-1-20下午4:02:29
 */
public class EquipmentAdd {

	private String equipmentId;
	private String x2d;
	private String y2d;
	private String z2d;
	private String x2dSize;
	private String y2dSize;
	private String shortName2d;
	private String equipmentType;
	private String rowId;
	private String alarmStatus;

	public String getAlarmStatus() {
		return alarmStatus;
	}

	public void setAlarmStatus(String alarmStatus) {
		this.alarmStatus = alarmStatus;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getX2d() {
		return x2d;
	}

	public void setX2d(String x2d) {
		this.x2d = x2d;
	}

	public String getY2d() {
		return y2d;
	}

	public void setY2d(String y2d) {
		this.y2d = y2d;
	}

	public String getZ2d() {
		return z2d;
	}

	public void setZ2d(String z2d) {
		this.z2d = z2d;
	}

	public String getX2dSize() {
		return x2dSize;
	}

	public void setX2dSize(String x2dSize) {
		this.x2dSize = x2dSize;
	}

	public String getY2dSize() {
		return y2dSize;
	}

	public void setY2dSize(String y2dSize) {
		this.y2dSize = y2dSize;
	}

	public String getShortName2d() {
		return shortName2d;
	}

	public void setShortName2d(String shortName2d) {
		this.shortName2d = shortName2d;
	}

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

}
