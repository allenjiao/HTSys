package com.gd.websocket;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;


/**
 *  WebSocket 帮助类，主要用来保存客户端session，并进行广播
 * @author tyutNo4<br>
 * 创建时间：2015-1-12下午10:53:53
 */
public class WebSocketHelper {
	
	private static final Logger log = Logger.getLogger(WebSocketHelper.class);
	
	private static final ConcurrentHashMap<Integer, Client> clients = new ConcurrentHashMap<Integer, Client>();
	
	protected static synchronized void addClient(Client client) {
		clients.put(Integer.valueOf(client.getId()), client);
	}
	
	protected static Collection<Client> getClients() {
		return Collections.unmodifiableCollection(clients.values());
	}

	protected static synchronized void removeClient(Client client) {
		clients.remove(Integer.valueOf(client.getId()));
	}

	public static synchronized void broadcast(String message) {
//		log.info("========== broadcast start ===========");
        for (Client client : getClients()) {
            try {
                client.sendMessage(message);
            } catch (IllegalStateException ise) {
                // An ISE can occur if an attempt is made to write to a
                // WebSocket connection after it has been closed. The
                // alternative to catching this exception is to synchronise
                // the writes to the clients along with the addSnake() and
                // removeSnake() methods that are already synchronised.
            }
        }
    }

}
