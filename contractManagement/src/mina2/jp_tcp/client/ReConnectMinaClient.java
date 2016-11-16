package mina2.jp_tcp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReConnectMinaClient implements Runnable {

	private final static Logger log = LoggerFactory.getLogger(ReConnectMinaClient.class);
	private MinaClient minaClient;

	public ReConnectMinaClient(MinaClient minaClient) {
		this.minaClient = minaClient;
	}
	@Override
	public void run() {
		while (true) {
			try {
				minaClient.connect();
				Thread.sleep(5000);
			} catch (Exception ex) {
				log.error("休眠线程失败" + ex.getMessage());
			}
		}
	}
}
