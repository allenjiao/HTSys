package com.fh.entity.system;

import java.util.List;

/**
 * 组织机构实体
 * 
 * @author reny<br>
 *         创建时间：2014-12-2下午1:08:17
 */
public class Organ {
	private String ORGAN_ID;
	private String ORGAN_NAME;
	private String ORGAN_TYPE;
	private String PARENT_ID;
	private String ORGAN_LEVEL;
	private String ORGAN_DESC;
	private String ORGAN_ORDER;
	private String doubleName;

	private Organ parentOrgan;
	private List<Organ> subOrgans;

	public String getDoubleName() {
		return doubleName;
	}

	public void setDoubleName(String doubleName) {
		this.doubleName = doubleName;
	}

	public String getORGAN_ID() {
		return ORGAN_ID;
	}

	public void setORGAN_ID(String oRGAN_ID) {
		ORGAN_ID = oRGAN_ID;
	}

	public String getORGAN_NAME() {
		return ORGAN_NAME;
	}

	public void setORGAN_NAME(String oRGAN_NAME) {
		ORGAN_NAME = oRGAN_NAME;
	}

	public String getORGAN_TYPE() {
		return ORGAN_TYPE;
	}

	public void setORGAN_TYPE(String oRGAN_TYPE) {
		ORGAN_TYPE = oRGAN_TYPE;
	}

	public String getPARENT_ID() {
		return PARENT_ID;
	}

	public void setPARENT_ID(String pARENT_ID) {
		PARENT_ID = pARENT_ID;
	}

	public String getORGAN_LEVEL() {
		return ORGAN_LEVEL;
	}

	public void setORGAN_LEVEL(String oRGAN_LEVEL) {
		ORGAN_LEVEL = oRGAN_LEVEL;
	}

	public String getORGAN_DESC() {
		return ORGAN_DESC;
	}

	public void setORGAN_DESC(String oRGAN_DESC) {
		ORGAN_DESC = oRGAN_DESC;
	}

	public String getORGAN_ORDER() {
		return ORGAN_ORDER;
	}

	public void setORGAN_ORDER(String oRGAN_ORDER) {
		ORGAN_ORDER = oRGAN_ORDER;
	}

	public Organ getParentOrgan() {
		return parentOrgan;
	}

	public void setParentOrgan(Organ parentOrgan) {
		this.parentOrgan = parentOrgan;
	}

	public List<Organ> getSubOrgans() {
		return subOrgans;
	}

	public void setSubOrgans(List<Organ> subOrgans) {
		this.subOrgans = subOrgans;
	}

}
