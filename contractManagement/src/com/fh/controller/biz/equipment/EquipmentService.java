package com.fh.controller.biz.equipment;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

/**
 * 设备服务类
 * 
 * @author tyutNo4<br>
 *         创建时间：2015-1-30下午8:40:48
 */
@Service("equipmentService")
public class EquipmentService {

	private static final Logger log = Logger.getLogger(EquipmentService.class);

	@Resource(name = "daoSupport")
	private DaoSupport dao;

//	@Resource(name = "alarmLogService")
//	private AlarmLogService alarmLogService;

	// ======================================================================================

	public PageData findByEquipmentId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("EquipmentMapper.findByEquipmentId", pd);
	}

	public PageData findByPositionId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("EquipmentMapper.findByPositionId", pd);
	}

	public void editEquipment(PageData pd) throws Exception {
		dao.update("EquipmentMapper.editEquipment", pd);
	}

	public void editEquipmentExtAttr(PageData pd) throws Exception {
		dao.update("EquipmentMapper.editEquipmentExtAttr", pd);
	}

	public void editEquipmentPosition(PageData pd) throws Exception {
		dao.update("EquipmentMapper.editEquipmentPosition", pd);
	}

	public void saveEquipment(PageData pd) throws Exception {
		dao.update("EquipmentMapper.saveEquipment", pd);
	}

	/**
	 * 取最大设备id（数字手动自增）
	 * 
	 * @return
	 * @throws Exception
	 */
	public PageData findMaxId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("EquipmentMapper.findMaxId", pd);
	}

	public void saveEquipmentExtAttr(PageData pd) throws Exception {
		dao.update("EquipmentMapper.saveEquipmentExtAttr", pd);
	}

	public void saveEquipmentPosition(PageData pd) throws Exception {
		dao.update("EquipmentMapper.saveEquipmentPosition", pd);
	}

	public void deleteEquipment(PageData pd) throws Exception {
		dao.update("EquipmentMapper.deleteEquipment", pd);
	}

	public void deletePosition(PageData pd) throws Exception {
		dao.update("EquipmentMapper.deletePosition", pd);
	}

	/*
	 * 设备类型列表（分页）
	 */
	public List<PageData> listPdPageEquipment(Page page) throws Exception {
		return (List<PageData>) dao.findForList("EquipmentMapper.equipmentlistPage", page);
	}

	/*
	 * 根据条件查询设备类型列表(no分页)
	 */
	public List<PageData> listEquipment(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("EquipmentMapper.listEquipment", pd);
	}

	/*
	 * 设备类型属性模板
	 */
	public List<PageData> listEquipmentExtAttr(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("EquipmentMapper.listEquipmentExtAttr", pd);
	}

	/**
	 * 设备采集配置-查询
	 */
	public List<PageData> listEquipmentGatherConfig(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("EquipmentMapper.listEquipmentGatherConfig", pd);
	}

	/**
	 * 设备采集配置-编辑（同一设备的采集配置，无id要生成id并insert，旧的id要修改，原id提交后没有的要删除）
	 */
	public void saveBatchEquipmentGatherConfig(String EQUIPMENT_ID, List<PageData> pds) throws Exception {
		PageData condition = new PageData();
		condition.put("EQUIPMENT_ID", EQUIPMENT_ID);
		List<PageData> thisConfigs = listEquipmentGatherConfig(condition);
		Iterator<PageData> iterator = thisConfigs.iterator();
		if (pds != null) {
			while (iterator.hasNext()) {// 剩下的要删除
				PageData thisConfig = iterator.next();
				for (PageData pd : pds) {
					if (StringUtils.equals(pd.getString("CONFIG_ID"), thisConfig.getString("CONFIG_ID"))) {
						iterator.remove();
						break;
					}
				}
			}
		}
		for (PageData thisConfig : thisConfigs) {
			dao.delete("EquipmentMapper.deleteEquipmentGatherConfig", thisConfig.getString("CONFIG_ID"));
		}
		if (pds != null) {
			for (PageData pd : pds) {
				if (StringUtils.isBlank(pd.getString("CONFIG_ID"))) {
					pd.put("CONFIG_ID", UuidUtil.get32UUID());
					dao.save("EquipmentMapper.saveEquipmentGatherConfig", pd);
				} else {
					dao.update("EquipmentMapper.updateEquipmentGatherConfig", pd);
				}
			}
		}
	}

	/**
	 * 设备采集配置-删除
	 */
	public void deleteEquipmentGatherConfig(String EQUIPMENT_ID) throws Exception {
		dao.save("EquipmentMapper.deleteEquipmentGatherConfig", EQUIPMENT_ID);
	}

	/*
	 * 采集设备目标设备-查询
	 */
	public List<PageData> listEquipmentGatherTarget(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("EquipmentMapper.listEquipmentGatherTarget", pd);
	}

	/**
	 * 变更报警状态(可手动设报警以减轻查询压力)
	 * 
	 * @param equipmengId
	 *        变更报警状态的设备id
	 * @param ifAlarm
	 *        是否直接设为报警状态，为false则自检Alarm_Log来判定
	 * @return boolean 当前设备是否报警
	 */
//	public boolean setEquipmentAlarmStatus(String equipmentId, boolean autoAlarm) {
//		PageData pdCondition = new PageData();
//		pdCondition.put("EQUIPMENT_ID", equipmentId);
//		PageData equmipment = null;
//		try {
//			equmipment = this.findByEquipmentId(pdCondition);
//		} catch (Exception e) {
//			log.error(e);
//			return false;
//		}
//		if (autoAlarm) {// 直接报警
//			equmipment.put("ALARM_STATUS", "0");// 报警状态，标识该设备现在是否报警中0报警中1未报警
//			try {
//				this.editEquipment(equmipment);// 更新设备表
//			} catch (Exception e) {
//				log.error(e);
//			}
//			return true;
//		} else {// 根据alarmLog判断是否报警
//			PageData alarmLogCondition = new PageData();
//			alarmLogCondition.put("EQUIPMENT_ID", equipmentId);
//			alarmLogCondition.put("START_TIME",
//					new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.addHours(new Date(), -24)));
//			List<PageData> alarmLogs = new ArrayList<PageData>();
//			try {
//				alarmLogs = alarmLogService.listAlarmLog(alarmLogCondition);
//			} catch (Exception e) {
//				log.error(e);
//				return false;
//			}
//			Collections.reverse(alarmLogs);
//			Map<String, Object> map = new HashMap<String, Object>();
//			for (PageData alarmLog : alarmLogs) {
//				if (StringUtils.equals("0", alarmLog.getString("PROCESS_FLAG"))) {// 未处理
//					map.put(alarmLog.getString("ATTR_CODE"), "");
//				} else if (StringUtils.equals("1", alarmLog.getString("PROCESS_FLAG"))) {// 已处理
//					map.remove(alarmLog.getString("ATTR_CODE"));
//				}
//			}
//			if (map.isEmpty()) {
//				equmipment.put("ALARM_STATUS", "1");// 报警状态，标识该设备现在是否报警中0报警中1未报警
//				try {
//					this.editEquipment(equmipment);// 更新设备表
//				} catch (Exception e) {
//					log.error(e);
//				}
//				return false;
//			} else {
//				equmipment.put("ALARM_STATUS", "0");// 报警状态，标识该设备现在是否报警中0报警中1未报警
//				try {
//					this.editEquipment(equmipment);// 更新设备表
//				} catch (Exception e) {
//					log.error(e);
//				}
//				return true;
//			}
//		}
//	}
}
