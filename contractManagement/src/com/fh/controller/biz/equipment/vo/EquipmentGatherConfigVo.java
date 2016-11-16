package com.fh.controller.biz.equipment.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备采集配置，jsp提交
 * 
 * @author 毅
 * 
 */
public class EquipmentGatherConfigVo {
	private String CONFIG_ID;
	private String EQUIPMENT_ID;
	private String EQUIPMENT_NAME;
	private String ATTR_NAME;
	private String GATHER_SYSTEM_ID;
	private String GATHER_ATTR_CODE;
	private String ATTR_UNIT;
	private String CHANNEL_NO;
	private String X_3D;
	private String Y_3D;
	private String Z_3D;
	private String ATTR_VALUE;
	private String ATTR_CODE;
	private String GATHER_TYPE;
	private String RELATED_CONFIG_ID;

	public String getGATHER_TYPE() {
		return GATHER_TYPE;
	}

	public void setGATHER_TYPE(String gATHER_TYPE) {
		GATHER_TYPE = gATHER_TYPE;
	}

	public String getRELATED_CONFIG_ID() {
		return RELATED_CONFIG_ID;
	}

	public void setRELATED_CONFIG_ID(String rELATED_CONFIG_ID) {
		RELATED_CONFIG_ID = rELATED_CONFIG_ID;
	}

	private List<EquipmentGatherConfigVo> vos = new ArrayList<EquipmentGatherConfigVo>();

	public String getCONFIG_ID() {
		return CONFIG_ID;
	}

	public void setCONFIG_ID(String cONFIG_ID) {
		CONFIG_ID = cONFIG_ID;
	}

	public String getEQUIPMENT_ID() {
		return EQUIPMENT_ID;
	}

	public void setEQUIPMENT_ID(String eQUIPMENT_ID) {
		EQUIPMENT_ID = eQUIPMENT_ID;
	}

	public String getEQUIPMENT_NAME() {
		return EQUIPMENT_NAME;
	}

	public void setEQUIPMENT_NAME(String eQUIPMENT_NAME) {
		EQUIPMENT_NAME = eQUIPMENT_NAME;
	}

	public String getATTR_NAME() {
		return ATTR_NAME;
	}

	public void setATTR_NAME(String aTTR_NAME) {
		ATTR_NAME = aTTR_NAME;
	}

	public String getGATHER_SYSTEM_ID() {
		return GATHER_SYSTEM_ID;
	}

	public void setGATHER_SYSTEM_ID(String gATHER_SYSTEM_ID) {
		GATHER_SYSTEM_ID = gATHER_SYSTEM_ID;
	}

	public String getGATHER_ATTR_CODE() {
		return GATHER_ATTR_CODE;
	}

	public void setGATHER_ATTR_CODE(String gATHER_ATTR_CODE) {
		GATHER_ATTR_CODE = gATHER_ATTR_CODE;
	}

	public String getATTR_UNIT() {
		return ATTR_UNIT;
	}

	public void setATTR_UNIT(String aTTR_UNIT) {
		ATTR_UNIT = aTTR_UNIT;
	}

	public String getCHANNEL_NO() {
		return CHANNEL_NO;
	}

	public void setCHANNEL_NO(String cHANNEL_NO) {
		CHANNEL_NO = cHANNEL_NO;
	}

	public String getX_3D() {
		return X_3D;
	}

	public void setX_3D(String x_3d) {
		X_3D = x_3d;
	}

	public String getY_3D() {
		return Y_3D;
	}

	public void setY_3D(String y_3d) {
		Y_3D = y_3d;
	}

	public String getZ_3D() {
		return Z_3D;
	}

	public void setZ_3D(String z_3d) {
		Z_3D = z_3d;
	}

	public String getATTR_VALUE() {
		return ATTR_VALUE;
	}

	public void setATTR_VALUE(String aTTR_VALUE) {
		ATTR_VALUE = aTTR_VALUE;
	}

	public String getATTR_CODE() {
		return ATTR_CODE;
	}

	public void setATTR_CODE(String aTTR_CODE) {
		ATTR_CODE = aTTR_CODE;
	}

	public List<EquipmentGatherConfigVo> getVos() {
		return vos;
	}

	public void setVos(List<EquipmentGatherConfigVo> vos) {
		this.vos = vos;
	}

}
