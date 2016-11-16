package com.gd.websocket.vo.alarm;

import java.util.ArrayList;
import java.util.List;

import com.gd.websocket.vo.WebSocketVo;

/**
 * 报警
 * @author tyutNo4<br>
 * 创建时间：2015-1-20下午3:55:50
 */
public class AlarmVo extends WebSocketVo{

	private List<Alarm> alarms = new ArrayList<Alarm>();

	public List<Alarm> getAlarms() {
		return alarms;
	}

	public void setAlarms(List<Alarm> alarms) {
		this.alarms = alarms;
	}
	
}
