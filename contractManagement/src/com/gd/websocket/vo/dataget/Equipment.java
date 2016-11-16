package com.gd.websocket.vo.dataget;

/**
 * 设备信息
 * 
 * @author tyutNo4<br>
 *         创建时间：2015-1-20下午3:54:18
 */
public class Equipment {

	private String equipmentId;
	private String equipmentName;
	private String equipmentType;// TODO 需要完善，一个9个基本属性，三个动态属性
	private String equipmentLabel;
	private String equipmentPosition;
	private String equipmentDepartment;
	private String equipmentPutawayTime;
	private String equipmentIp;
	private String equipmentSystem;
	private String equipmentSORT_NO;

	public String getEquipmentSORT_NO() {
		return equipmentSORT_NO;
	}

	public void setEquipmentSORT_NO(String equipmentSORT_NO) {
		this.equipmentSORT_NO = equipmentSORT_NO;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getEquipmentLabel() {
		return equipmentLabel;
	}

	public void setEquipmentLabel(String equipmentLabel) {
		this.equipmentLabel = equipmentLabel;
	}

	public String getEquipmentPosition() {
		return equipmentPosition;
	}

	public void setEquipmentPosition(String equipmentPosition) {
		this.equipmentPosition = equipmentPosition;
	}

	public String getEquipmentDepartment() {
		return equipmentDepartment;
	}

	public void setEquipmentDepartment(String equipmentDepartment) {
		this.equipmentDepartment = equipmentDepartment;
	}

	public String getEquipmentPutawayTime() {
		return equipmentPutawayTime;
	}

	public void setEquipmentPutawayTime(String equipmentPutawayTime) {
		this.equipmentPutawayTime = equipmentPutawayTime;
	}

	public String getEquipmentIp() {
		return equipmentIp;
	}

	public void setEquipmentIp(String equipmentIp) {
		this.equipmentIp = equipmentIp;
	}

	public String getEquipmentSystem() {
		return equipmentSystem;
	}

	public void setEquipmentSystem(String equipmentSystem) {
		this.equipmentSystem = equipmentSystem;
	}

}
