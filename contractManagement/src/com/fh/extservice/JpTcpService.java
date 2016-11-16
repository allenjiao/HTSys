package com.fh.extservice;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

import javax.annotation.Resource;

import mina2.jp_tcp.proto.DeviceDatasReqOuterClass;
import mina2.jp_tcp.proto.LoginRspOuterClass;
import mina2.jp_tcp.vo.JpTcpVo;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fh.servlet.TimerServlet;
import com.fh.util.PageData;
import com.google.protobuf.ByteString;

@Service("jpTcpService")
public class JpTcpService {
	private static final Logger log = Logger.getLogger(JpTcpService.class);
	@Resource(name = "DHCMassageService")
	private DHCMassageService dhcMassageService;

	// -------------------------------被动业务start--------------------------------
	/**
	 * 收到消息
	 * 
	 * @param inVo
	 * @return
	 */
	public JpTcpVo getTcpMessage(JpTcpVo inVo) {
//		log.info("收到tcp包id：" + inVo.getM_pBusinessID());
		switch (inVo.getM_pBusinessID()) {
			case 2:
				receivedLoginRes(inVo.getM_pMessageData());
				break;
			case 4098:
				dataReFresh(inVo.getM_pMessageData());// 数据推送刷新
				break;
			case 10004:
				warning(inVo.getM_pMessageData());// 报警
				break;

			default:
				break;
		}
		return null;
	}

	/**
	 * 获得登录响应
	 */
	private void receivedLoginRes(byte[] bs) {

		LoginRspOuterClass.LoginRsp loginRsp = null;
		try {
			loginRsp = LoginRspOuterClass.LoginRsp.parseFrom(bs);
		} catch (Exception e) {
			log.error(e);
			return;
		}
		log.info("loginRes code：" + loginRsp.getCode());
	}

	/**
	 * 获得设备实时数据
	 */
	private void dataReFresh(byte[] bs) {
//		ReadRealdataItemRsp.ReadRealDataList dataList = null;
//		try {
//			dataList = ReadRealdataItemRsp.ReadRealDataList.parseFrom(bs);
//		} catch (InvalidProtocolBufferException e1) {
//			log.error(e1);
//			return;
//		}

	}

	/**
	 * 收到报警
	 * 
	 * @param bs
	 */
	private void warning(byte[] bs) {
	}

	// -------------------------------被动业务end--------------------------------

	// -------------------------------主动业务start--------------------------------
	/**
	 * 发送消息
	 * 
	 * @param m_iDecSystemID
	 *        目的系统号
	 * @param m_pBusinessID
	 *        业务编号
	 * @param m_iSourSystemID
	 *        源系统号
	 * @param m_pApplicationID
	 *        应用编号
	 * @param m_iSynchroID
	 *        消息同步号
	 * @param bs
	 *        包数据
	 */
	private void SendTcpMessage(int m_iDecSystemID, int m_pBusinessID, int m_iSourSystemID, int m_pApplicationID,
			int m_iSynchroID, byte[] bs) {
		JpTcpVo jpTcpVo = new JpTcpVo();
		jpTcpVo.setM_iDecSystemID(m_iDecSystemID);
		jpTcpVo.setM_iSourSystemID(m_iSourSystemID);
		jpTcpVo.setM_iSynchroID(m_iSynchroID);
		jpTcpVo.setM_pApplicationID(m_pApplicationID);
		jpTcpVo.setM_pBusinessID(m_pBusinessID);
		jpTcpVo.setM_pMessageData(bs);
		try {
			TimerServlet.minaClient.session.write(jpTcpVo);
		} catch (Exception e) {
			log.warn("检查TCP连接状态...", e);
		}
	}

	/**
	 * 心跳...timer调用
	 */
	public void sentHeart() {
		SendTcpMessage(0, 4096, 0, 0, 0, "h".getBytes(Charset.forName("GBK")));
	}

//	/**
//	 * 登录
//	 */
//	public void sentLogin() {
//		LoginReqOuterClass.LoginReq.Builder builder = LoginReqOuterClass.LoginReq.newBuilder();
//		builder.setUsername(Config.getStr("tcp_username"));
//		builder.setPassword(Config.getStr("tcp_password"));
//		builder.setType(Config.getStr("tcp_type"));
//		builder.setMac("");
//		builder.setRemark("");
//		SendTcpMessage(0, 1, 0, 0, 0, builder.build().toByteArray());
//	}

	/**
	 * 发送设备数据推送请求
	 * 
	 * @param EQUIPMENT_ID
	 *        设备id
	 * @param attrCodes
	 *        List<PageData>采集配置集合,为null则获取全部设备采集属性
	 */
	public void sentDeviceReq(String EQUIPMENT_ID, List<PageData> attrCodes) {
//		if (StringUtils.isBlank(EQUIPMENT_ID) || attrCodes == null || attrCodes.isEmpty()) {
//			return;
//		}
		if (StringUtils.isBlank(EQUIPMENT_ID)) {
			return;
		}
		DeviceDatasReqOuterClass.DeviceDatasReq.Builder builder = DeviceDatasReqOuterClass.DeviceDatasReq.newBuilder();
		DeviceDatasReqOuterClass.DeviceWithAttrs.Builder dbuilder = DeviceDatasReqOuterClass.DeviceWithAttrs
				.newBuilder();
		dbuilder.setDeviceId(Integer.parseInt(EQUIPMENT_ID));
		if (attrCodes != null) {
			for (PageData attrCode : attrCodes) {
				if (StringUtils.isBlank(attrCode.getString("ATTR_CODE"))) {
					continue;
				}
				try {
					dbuilder.addAttrNames(ByteString.copyFrom(attrCode.getString("ATTR_CODE"), "GBK"));
				} catch (UnsupportedEncodingException e) {
					log.error(e);
				}
			}
		}
		builder.addDevices(dbuilder);
		SendTcpMessage(0, 4097, 0, 0, 0, builder.build().toByteArray());
	}
	// -------------------------------主动业务end--------------------------------
}
