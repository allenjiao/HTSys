package mina2.jp_tcp;

import java.nio.charset.Charset;

import mina2.jp_tcp.vo.JpTcpVo;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * 
 * @author reny<br>
 *         创建时间：2015-3-24下午3:31:23
 */
public class MyCharsetDecoder implements ProtocolDecoder {

	private final static Logger log = Logger.getLogger(MyCharsetDecoder.class);

	private final static Charset charset = Charset.forName("GBK");
	private final static Charset charsetB = Charset.forName("iso-8859-1");
	// 残包
	private static String packageStrTemp = "";
	// 协议值
	private final String packagestart = "HD";

//	private final String packageend = "ED";

	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
//		log.info("#########decode#########");
//		log.info("============1读入本次数据，并与残包拼接...");
		IoBuffer bufferThis = IoBuffer.allocate(1).setAutoExpand(true);
		while (in.hasRemaining()) {// 如果有消息
			bufferThis.put(in.get());
		}
		bufferThis.flip();
		byte[] bytes = new byte[bufferThis.limit()];
		bufferThis.get(bytes);
		String packageStrThis = new String(bytes, charsetB);
		packageStrTemp += packageStrThis;
//		log.debug("peek Str[" + packageStrThis + "]");

//		log.info("============2查找包头...");
		byte[] bs = packageStrTemp.getBytes(charsetB);
		int startindex = -1;

		for (int i = 0; i < bs.length - 1; i++) {
			if (packagestart.substring(0, 1).equals(getStringValue(bs, i, 1))) {
				if (packagestart.substring(1, 2).equals(getStringValue(bs, i + 1, 1))) {
					startindex = i;
					break;
				}
			}
		}

		while (bs.length > 30 && startindex >= 0) {// 开始处理
//			log.info("============3判断完整包...");
			int packagelen = getIntValue(bs, (startindex + 23), 4);
//			log.debug("-总包长度-:" + Integer.toString(bs.length));
//			log.debug("单个包长度:" + Integer.toString(packagelen));
			if (bs.length < packagelen) {
//				log.info("============4-1不够完整return收录在残包中...");
				return;
			}

//			log.info("============4-2处理完整包...");
			String contextStr = packageStrTemp.substring(startindex, startindex + packagelen);
			// 返回对象&去处理单包内容
			out.write(decode(contextStr));
//			log.info("============5截取剩余部分...");
			// 截取剩余部分...准备下次循环
			packageStrTemp = packageStrTemp.substring(startindex + packagelen);
			bs = packageStrTemp.getBytes(charsetB);
			startindex = -1;
			for (int i = 0; i < bs.length - 1; i++) {
				if (packagestart.substring(0, 1).equals(getStringValue(bs, i, 1))) {
					if (packagestart.substring(1, 2).equals(getStringValue(bs, i + 1, 1))) {
						startindex = i;
						break;
					}
				}
			}

		}
	}

	/**
	 * 解析整包数据
	 * 
	 * @return
	 */
	private JpTcpVo decode(String byteStr) {
		JpTcpVo jpTcpVo = new JpTcpVo();
		byte[] bs = byteStr.getBytes(charsetB);
		jpTcpVo.setM_pHeader(new String(byteStr.substring(0, 2).getBytes(charsetB), charset));
		jpTcpVo.setM_pVersion(byteStr.substring(2, 3).getBytes(charsetB)[0]);
		jpTcpVo.setM_iDecSystemID(getIntValue(byteStr.substring(3, 7).getBytes(charsetB), 0, 4));
		jpTcpVo.setM_pBusinessID(getIntValue(byteStr.substring(7, 11).getBytes(charsetB), 0, 4));
		jpTcpVo.setM_iSourSystemID(getIntValue(byteStr.substring(11, 15).getBytes(charsetB), 0, 4));
		jpTcpVo.setM_pApplicationID(getIntValue(byteStr.substring(15, 19).getBytes(charsetB), 0, 4));
		jpTcpVo.setM_iSynchroID(getIntValue(byteStr.substring(19, 23).getBytes(charsetB), 0, 4));

		int xmlLen = getIntValue(byteStr.substring(23, 27).getBytes(charsetB), 0, 4) - 30;// xmlLen=总长-协议中的固定长度内容
		jpTcpVo.setM_pMessageDataPacketLength(xmlLen);

		jpTcpVo.setM_pMessageData(byteStr.substring(27, 27 + xmlLen).getBytes(charsetB));

		jpTcpVo.setM_pMessageAuthentication((char) byteStr.substring(bs.length - 3, bs.length - 2).getBytes(charsetB)[0]);
		jpTcpVo.setM_pMessageEnd(new String(byteStr.substring(bs.length - 2, bs.length).getBytes(charsetB), charset));
		// 验证
		int yh = bs[0];
		for (int i = 1; i < bs.length - 3; i++) {
//			if (bs[i] < 0) {
//				bs[i] += 256;
//			}
			yh ^= bs[i];
		}
		if ((char) yh != jpTcpVo.getM_pMessageAuthentication()) {
			log.warn("验证失败yh=[" + (char) yh + "]实际为[" + jpTcpVo.getM_pMessageAuthentication() + "]");
			log.info("packageStrTemp字节容器长度："+packageStrTemp.length());
			jpTcpVo = new JpTcpVo();
			return jpTcpVo;
		}

		return jpTcpVo;
	}

	/** 字节取String */
	private String getStringValue(byte codebuff[], int startnum, int intlen) {
		String strval = "";
		for (int i = startnum; i < startnum + intlen; i++)
			strval = (new StringBuilder(String.valueOf(strval))).append((char) (new Byte(codebuff[i])).intValue())
					.toString();

		return strval;
	}

	/** 字节取int */
	private int getIntValue(byte[] codebuff, int startnum, int intlen) {
		double intval = 0;
		for (int i = startnum; i < (startnum + intlen); i++) {
			int a = (new Byte(codebuff[i])).intValue();
			// ****注意这里*****//
			// 由于java的byte值的范围是从-128到127,而从客户端传来的数据byte值范围从0到256
//			if (DataTypeID == 1) {
			if (a < 0) {
				a = 256 + a;
			}
//			}
			intval = intval + (a * Math.pow(256, i - startnum));
		}
		return (new Double(intval)).intValue();
	}

	@Override
	public void dispose(IoSession session) throws Exception {
		log.info("#########dispose#########");
		log.info(session.getCurrentWriteMessage());
	}

	@Override
	public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
		log.info("#########完成解码#########");
	}
}