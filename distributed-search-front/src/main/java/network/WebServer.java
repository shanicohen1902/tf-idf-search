

package network;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import service.SerachService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class WebServer {
    private static final String SEARCH_ENDPOINT = "/search";

    private final int port;
    private HttpServer server;
    SerachService service;

    public WebServer(SerachService service, String port) {
        this.port = Integer.valueOf(port);
        this.service = service;
    }

    public void startServer() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Server listening on port: " + port);

        HttpContext searchContext = server.createContext(SEARCH_ENDPOINT);

        searchContext.setHandler(this::handleSearchRequest);
        
        server.setExecutor(Executors.newFixedThreadPool(8));
        
        server.start();
    }


    private void handleSearchRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {
            exchange.close();
            return;
        }

        String query = exchange.getRequestURI().getQuery();
        System.out.println("query: " + query);

        if(query == null){
            exchange.close();
            return;
        }

        sendResponse(service.sendSearchTask(query).getBytes(), exchange);
    }

    private void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, responseBytes.length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(responseBytes);
        outputStream.flush();
        outputStream.close();
    }

    public void stop() {
        server.stop(10);
    }

}
