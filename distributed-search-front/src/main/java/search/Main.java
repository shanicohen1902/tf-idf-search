package search;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event;
import org.apache.zookeeper.data.Stat;

import network.WebServer;
import service.SerachService;

public class Main  {
	
    
    public static void main(String[]args) throws KeeperException, InterruptedException, IOException {

		Properties prop = loadProperties();
		SerachService service = new SerachService(prop.getProperty("zookeeper.connection"));
		WebServer webServer = new WebServer(service,prop.getProperty("server.port"));
     	webServer.startServer();
    }

	private static Properties loadProperties() throws IOException {
		
        InputStream input = new FileInputStream("src/main/resources/application.properties");
    	Properties prop = new Properties();
    	prop.load(input);
		return prop;
	}
    



	


}
