package com.gd.websocket.vo.dataget;

/**
 * 机柜信息
 * @author tyutNo4<br>
 * 创建时间：2015-1-20下午3:53:51
 */
public class Cabinet {

	private String equipmentId;
	private String name;
	private String position;
	private String temperature;
	private String voltage;
	private String electricity;
	private String totalFrequency;
	private String totalElectricity;
	
	public String getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getVoltage() {
		return voltage;
	}
	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}
	public String getElectricity() {
		return electricity;
	}
	public void setElectricity(String electricity) {
		this.electricity = electricity;
	}
	public String getTotalFrequency() {
		return totalFrequency;
	}
	public void setTotalFrequency(String totalFrequency) {
		this.totalFrequency = totalFrequency;
	}
	public String getTotalElectricity() {
		return totalElectricity;
	}
	public void setTotalElectricity(String totalElectricity) {
		this.totalElectricity = totalElectricity;
	}
	
}
