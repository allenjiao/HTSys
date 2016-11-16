package mina2.jp_tcp;

import java.nio.charset.Charset;

import mina2.jp_tcp.vo.JpTcpVo;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * 
 * @author reny<br>
 *         创建时间：2015-3-24下午3:32:07
 */
public class MyCharsetEncoder implements ProtocolEncoder {
	private final static Logger log = Logger.getLogger(MyCharsetEncoder.class);
	private final static Charset charset = Charset.forName("GBK");

	@Override
	public void dispose(IoSession session) throws Exception {
		log.info("#############dispose############");
	}

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
//		log.info("#############字符编码############");
		IoBuffer ioBuffer = IoBuffer.allocate(30).setAutoExpand(true);
//        ioBuffer.putString((String)message, charset.newEncoder());
//        ioBuffer.flip();
//        out.write(ioBuffer);
		JpTcpVo jpTcpVo = (JpTcpVo) message;

		byte[] dataB = jpTcpVo.getM_pMessageData();
		int dataLen = dataB.length;
		int packageLen = dataLen + 30;
		byte[] netPackage = new byte[packageLen];

		netPackage[0] = jpTcpVo.getM_pHeader().getBytes(charset)[0];
		netPackage[1] = jpTcpVo.getM_pHeader().getBytes(charset)[1];

		netPackage[2] = (byte) jpTcpVo.getM_pVersion();

		netPackage[3] = (byte) (jpTcpVo.getM_iDecSystemID() >> 0 & 0xff);
		netPackage[4] = (byte) (jpTcpVo.getM_iDecSystemID() >> 8 & 0xff);
		netPackage[5] = (byte) (jpTcpVo.getM_iDecSystemID() >> 16 & 0xff);
		netPackage[6] = (byte) (jpTcpVo.getM_iDecSystemID() >> 24 & 0xff);

		netPackage[7] = (byte) (jpTcpVo.getM_pBusinessID() >> 0 & 0xff);
		netPackage[8] = (byte) (jpTcpVo.getM_pBusinessID() >> 8 & 0xff);
		netPackage[9] = (byte) (jpTcpVo.getM_pBusinessID() >> 16 & 0xff);
		netPackage[10] = (byte) (jpTcpVo.getM_pBusinessID() >> 24 & 0xff);

		netPackage[11] = (byte) (jpTcpVo.getM_iSourSystemID() >> 0 & 0xff);
		netPackage[12] = (byte) (jpTcpVo.getM_iSourSystemID() >> 8 & 0xff);
		netPackage[13] = (byte) (jpTcpVo.getM_iSourSystemID() >> 16 & 0xff);
		netPackage[14] = (byte) (jpTcpVo.getM_iSourSystemID() >> 24 & 0xff);

		netPackage[15] = (byte) (jpTcpVo.getM_pApplicationID() >> 0 & 0xff);
		netPackage[16] = (byte) (jpTcpVo.getM_pApplicationID() >> 8 & 0xff);
		netPackage[17] = (byte) (jpTcpVo.getM_pApplicationID() >> 16 & 0xff);
		netPackage[18] = (byte) (jpTcpVo.getM_pApplicationID() >> 24 & 0xff);

		netPackage[19] = (byte) (jpTcpVo.getM_iSynchroID() >> 0 & 0xff);
		netPackage[20] = (byte) (jpTcpVo.getM_iSynchroID() >> 8 & 0xff);
		netPackage[21] = (byte) (jpTcpVo.getM_iSynchroID() >> 16 & 0xff);
		netPackage[22] = (byte) (jpTcpVo.getM_iSynchroID() >> 24 & 0xff);

		netPackage[23] = (byte) (packageLen >> 0 & 0xff);
		netPackage[24] = (byte) (packageLen >> 8 & 0xff);
		netPackage[25] = (byte) (packageLen >> 16 & 0xff);
		netPackage[26] = (byte) (packageLen >> 24 & 0xff);

		for (int i = 0; i < dataLen; i++)
			netPackage[27 + i] = dataB[i];

		int yh = netPackage[0];
		for (int i = 1; i < packageLen - 3; i++) {
			yh ^= netPackage[i];
		}

		netPackage[packageLen - 3] = (byte) yh;
		netPackage[packageLen - 2] = (byte) jpTcpVo.getM_pMessageEnd().getBytes(charset)[0];
		netPackage[packageLen - 1] = (byte) jpTcpVo.getM_pMessageEnd().getBytes(charset)[1];

		ioBuffer.put(netPackage);
		ioBuffer.flip();
		out.write(ioBuffer);
	}
}