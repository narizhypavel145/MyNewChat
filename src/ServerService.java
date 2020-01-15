import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.UUID;

public class ServerService extends Thread{
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String clientId;

    ServerService(Socket socket){
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientId = String.valueOf(UUID.randomUUID());
        sendMessage(new Message("server", "your id", clientId));
        start();
    }

    public void run() {
        try {
            while (true) {
                Message message = (Message) in.readObject();
                if(message.getMessage().equals("stop")) {
                    sendMessage(new Message("server", "stop", message.getId()));
                    break;
                }
                else
                    ServerManager.sendMessageToAll(message);
            }
//            out.close();
//            in.close();
            ServerManager.removeServerService(clientId);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getClientId() {
        return clientId;
    }

    void sendMessage(Message message){
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
