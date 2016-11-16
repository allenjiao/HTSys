package com.gd.websocket.vo.datarefresh;

import java.util.ArrayList;
import java.util.List;

import com.gd.websocket.vo.WebSocketVo;

/**
 * 数据更新
 * 
 * @author tyutNo4<br>
 *         创建时间：2015-1-20下午3:59:50
 */
public class DataReFreshVo extends WebSocketVo {
	/** 特殊页面控件需要匹配具体设备 */
	private String equmipmentId;

	public String getEqumipmentId() {
		return equmipmentId;
	}

	public void setEqumipmentId(String equmipmentId) {
		this.equmipmentId = equmipmentId;
	}

	private List<DataReFresh> dataReFresh = new ArrayList<DataReFresh>();

	public List<DataReFresh> getDataReFresh() {
		return dataReFresh;
	}

	public void setDataReFresh(List<DataReFresh> dataReFresh) {
		this.dataReFresh = dataReFresh;
	}

}
