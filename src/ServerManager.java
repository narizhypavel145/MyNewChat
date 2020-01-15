import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class ServerManager {
    public static final int PORT = 8081;
    public static HashMap<String, ServerService> serverList = new HashMap(); // список всех нитей

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        try {
            while (true) {
                Socket socket = server.accept();
                ServerService serverService = new ServerService(socket);
                serverList.put(serverService.getClientId(), serverService);
            }
        } finally {
            server.close();
        }
    }

    public static void removeServerService(String clientId){
        serverList.remove(clientId);
    }

    public static void sendMessageToAll(Message message) {
        System.out.println(message.getName() + "(" + message.getTime() + "): " + message.getMessage());
        for(Map.Entry<String, ServerService> entry : ServerManager.serverList.entrySet()) {
            ServerService thisServer = entry.getValue();
            if (!message.getId().equals(thisServer.getClientId()))
                thisServer.sendMessage(message);
        }
    }
}
