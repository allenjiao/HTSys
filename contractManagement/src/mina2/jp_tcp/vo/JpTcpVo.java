package mina2.jp_tcp.vo;

/**
 * 
 * @author reny<br>
 *         创建时间：2015-3-25下午3:04:45
 */
public class JpTcpVo {
	/** 协议头 2字节 值17480 */
	private String m_pHeader = "HD"; // 协议头 2字节 值17480
	/** 版本号 1字节 */
	private int m_pVersion = 1; // 版本号 1字节

	/** 目的系统号 4字节 */
	private int m_iDecSystemID; // 目的系统号 4字节
	/** 业务编号 = 目的标识 4字节 */
	private int m_pBusinessID = -1;// 业务编号 = 目的标识 4字节
	/** 源系统号 4字节 */
	private int m_iSourSystemID; // 源系统号 4字节
	/** 应用编号 = 源标识 4字节 */
	private int m_pApplicationID;// 应用编号 = 源标识 4字节
	/** 消息同步号 4字节 */
	private int m_iSynchroID; // 消息同步号 4字节

	/** 包长度 4字节 */
	private long m_pMessageDataPacketLength; // 包长度 4字节

	/** 包数据 */
	private byte[] m_pMessageData; // 包数据

	/** 协议校验位 1字节（校验位字符前所以字段进行^ 异或） */
	private char m_pMessageAuthentication; // 协议校验位 1字节（校验位字符前所以字段进行^ 异或）
	/** 协议尾 2字节 值17477 */
	private String m_pMessageEnd = "ED"; // 协议尾 2字节 值17477

	public String getM_pHeader() {
		return m_pHeader;
	}

	public void setM_pHeader(String m_pHeader) {
		this.m_pHeader = m_pHeader;
	}

	public int getM_pVersion() {
		return m_pVersion;
	}

	public void setM_pVersion(int m_pVersion) {
		this.m_pVersion = m_pVersion;
	}

	public int getM_iDecSystemID() {
		return m_iDecSystemID;
	}

	public void setM_iDecSystemID(int m_iDecSystemID) {
		this.m_iDecSystemID = m_iDecSystemID;
	}

	/**
	 * 业务id：见jp提供说明；
	 * 
	 * @return
	 */
	public int getM_pBusinessID() {
		return m_pBusinessID;
	}

	/**
	 * 业务id：见jp提供说明；
	 * 
	 * @return
	 */
	public void setM_pBusinessID(int m_pBusinessID) {
		this.m_pBusinessID = m_pBusinessID;
	}

	public int getM_iSourSystemID() {
		return m_iSourSystemID;
	}

	public void setM_iSourSystemID(int m_iSourSystemID) {
		this.m_iSourSystemID = m_iSourSystemID;
	}

	public int getM_pApplicationID() {
		return m_pApplicationID;
	}

	public void setM_pApplicationID(int m_pApplicationID) {
		this.m_pApplicationID = m_pApplicationID;
	}

	public int getM_iSynchroID() {
		return m_iSynchroID;
	}

	public void setM_iSynchroID(int m_iSynchroID) {
		this.m_iSynchroID = m_iSynchroID;
	}

	public long getM_pMessageDataPacketLength() {
		return m_pMessageDataPacketLength;
	}

	public void setM_pMessageDataPacketLength(long m_pMessageDataPacketLength) {
		this.m_pMessageDataPacketLength = m_pMessageDataPacketLength;
	}

	public byte[] getM_pMessageData() {
		return m_pMessageData;
	}

	public void setM_pMessageData(byte[] m_pMessageData) {
		this.m_pMessageData = m_pMessageData;
	}

	public char getM_pMessageAuthentication() {
		return m_pMessageAuthentication;
	}

	public void setM_pMessageAuthentication(char m_pMessageAuthentication) {
		this.m_pMessageAuthentication = m_pMessageAuthentication;
	}

	public String getM_pMessageEnd() {
		return m_pMessageEnd;
	}

	public void setM_pMessageEnd(String m_pMessageEnd) {
		this.m_pMessageEnd = m_pMessageEnd;
	}

}
