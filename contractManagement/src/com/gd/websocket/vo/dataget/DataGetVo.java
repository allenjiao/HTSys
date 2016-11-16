package com.gd.websocket.vo.dataget;

import com.gd.websocket.vo.WebSocketVo;
import com.gd.websocket.vo.alarm.Alarm;

/**
 * 点击设备获取相关数据信息
 * @author tyutNo4<br>
 * 创建时间：2015-1-20下午3:55:06
 */
public class DataGetVo extends WebSocketVo{

	private Cabinet cabinet;
	
	private Equipment equipment;
	
	private Alarm alarm;

	public Cabinet getCabinet() {
		return cabinet;
	}

	public void setCabinet(Cabinet cabinet) {
		this.cabinet = cabinet;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public Alarm getAlarm() {
		return alarm;
	}

	public void setAlarm(Alarm alarm) {
		this.alarm = alarm;
	}
	
}
