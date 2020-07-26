package service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import network.WebClient;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class SerachService implements Watcher{
	
    public static final String LEDER_PATH = "/leader";
    private static final int SESSION_TIMEOUT = 3000;
    
	ZooKeeper zooKeeper ;
	String zooKeeperAddress;
	WebClient webClient;
    
    public SerachService(String connectionString) throws IOException {
    	zooKeeperAddress = connectionString;
   	 	zooKeeper = new ZooKeeper(zooKeeperAddress, SESSION_TIMEOUT, this);
		webClient = new WebClient();
    }
	
	public String getLeaders() {

		Stat stat = getStat();
		if (stat == null) {
			return null;
		}

		byte[] addressBytes = getLeaderAddressData(stat);

		System.out.println("The leader address is: " + new String(addressBytes, StandardCharsets.UTF_8));
	        
		return new String(addressBytes, StandardCharsets.UTF_8);

    }

	private byte[] getLeaderAddressData(Stat stat) {
		byte[] addressBytes = null;
		try {
			addressBytes = zooKeeper.getData(LEDER_PATH, false, stat);
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return addressBytes;
	}

	private Stat getStat() {
		Stat stat = null;
		try {
			stat = zooKeeper.exists(LEDER_PATH, false);
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return stat;
	}

	public String sendSearchTask(String query){

    	String leaderAddress = this.getLeaders();
		String result = null;
		try {
			result = webClient.get(leaderAddress + "/result?" + query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }
	

		public void process(WatchedEvent event) {
		    switch (event.getType()) {
		        case None:
		            if (event.getState() == Event.KeeperState.SyncConnected) {
		                System.out.println("Successfully connected to Zookeeper");
		            }
			    }
			
		}
}
