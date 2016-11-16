package com.gd.websocket.vo.equipmentadd;

import java.util.ArrayList;
import java.util.List;

import com.gd.websocket.vo.WebSocketVo;

/**
 * 设备添加
 * @author tyutNo4<br>
 * 创建时间：2015-1-20下午4:02:08
 */
public class EquipmentAddVo extends WebSocketVo{

	private List<EquipmentAdd> equipmentAdds = new ArrayList<EquipmentAdd>();

	public List<EquipmentAdd> getEquipmentAdds() {
		return equipmentAdds;
	}

	public void setEquipmentAdds(List<EquipmentAdd> equipmentAdds) {
		this.equipmentAdds = equipmentAdds;
	}
	
}
