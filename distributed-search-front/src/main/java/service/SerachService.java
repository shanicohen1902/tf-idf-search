package service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
    
    public SerachService(String connectionString) throws IOException {
    	zooKeeperAddress = connectionString;
   	 	zooKeeper = new ZooKeeper(zooKeeperAddress, SESSION_TIMEOUT, this);
    }
	
	public String getLeaders() throws KeeperException, InterruptedException{
		
	        
	        Stat stat = zooKeeper.exists(LEDER_PATH, false);
	        
            if (stat == null) {
                return null;
            }
	        
            byte[] addressBytes = zooKeeper.getData(LEDER_PATH, false, stat);
	        
	        System.out.println("The leader address is: " + new String(addressBytes, StandardCharsets.UTF_8));
	        
	        return new String(addressBytes, StandardCharsets.UTF_8);
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
