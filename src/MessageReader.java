import java.io.IOException;
import java.io.ObjectInputStream;

public class MessageReader extends Thread{
    private ObjectInputStream in;
    private ClientService parentClientService;

    MessageReader(ObjectInputStream objectInputStream, ClientService clientService){
        in = objectInputStream;
        this.parentClientService = clientService;
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message message = (Message) in.readObject();
                if (message.getMessage().equals("your id") && message.getName().equals("server")) {
                    parentClientService.setClientId(message.getId());
                }else
                if (message.getMessage().equals("stop") && message.getName().equals("server") && message.getId().equals(parentClientService.getClientId())) {
                    parentClientService.stopClient();
                    break;
                }else if (!message.getName().equals("server") && !message.getId().equals(parentClientService.getClientId()))
                    parentClientService.writeToHistory(message.getName() + "(" + message.getTime() + "): " + message.getMessage());

            }
        } catch (IOException | ClassNotFoundException e) {

        }
    }
}
