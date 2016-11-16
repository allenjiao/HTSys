package com.gd.websocket.vo.equipmentdelete;

import java.util.ArrayList;
import java.util.List;

import com.gd.websocket.vo.WebSocketVo;

/**
 * 设备删除
 * @author tyutNo4<br>
 * 创建时间：2015-1-20下午5:05:16
 */
public class EquipmentDeleteVo extends WebSocketVo{

	private List<EquipmentDelete> equipmentDeletes = new ArrayList<EquipmentDelete>();

	public List<EquipmentDelete> getEquipmentDeletes() {
		return equipmentDeletes;
	}

	public void setEquipmentDeletes(List<EquipmentDelete> equipmentDeletes) {
		this.equipmentDeletes = equipmentDeletes;
	}
	
}
