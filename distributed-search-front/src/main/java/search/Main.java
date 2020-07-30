package search;

import network.WebServer;
import org.apache.zookeeper.KeeperException;
import service.SerachService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main  {
	
    
    public static void main(String[]args) throws IOException {
		String zookeeperConnectionString;
		String serverPort;
		System.out.println("args: " + args.length);

		if (args.length > 0){
			 zookeeperConnectionString = args[0];
			 serverPort = args[1];
		}else{
			Properties prop = loadProperties();
			zookeeperConnectionString = prop.getProperty("zookeeper.connection");
			serverPort = prop.getProperty("server.port");
		}
		SerachService service = new SerachService(zookeeperConnectionString);
		WebServer webServer = new WebServer(service,serverPort);
     	webServer.startServer();
    }

	private static Properties loadProperties() throws IOException {
		
        InputStream input = new FileInputStream("src/main/resources/application.properties");
    	Properties prop = new Properties();
    	prop.load(input);
		return prop;
	}
    



	


}
