import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class ClientService {
    private static Socket clientSocket; //сокет для общения
    private static ObjectInputStream in; // поток чтения из сокета
    private static ObjectOutputStream out; // поток записи в сокет
    private String clientName;
    private String clientId;
    private JFrame clientFrame;
    private JTextArea history;

    ClientService(String name){
        clientName = name;
        try {
            startService();
            startGUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startService() throws IOException {
        clientSocket = new Socket(InetAddress.getByName("localhost"), 8081); // этой строкой мы запрашиваем
        in = new ObjectInputStream(clientSocket.getInputStream());
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        new MessageReader(in, this);
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void stopClient(){
//        try {
//            clientSocket.close();
//            in.close();
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        clientFrame.setVisible(false);
        clientFrame.dispose();
    }

    private void writeToServer(String message){
        try {
            out.writeObject(new Message(clientName, message, clientId));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startGUI(){
        clientFrame = new JFrame("Chat - " + clientName);
        clientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        clientFrame.setLayout(new GridBagLayout());
        GridBagHelper.getInstance().resetSettings();
        GridBagHelper.getInstance().gap(4);
        GridBagHelper.getInstance().setWeights(3, 4);
        history = new JTextArea("Чат начат");
        history.setEditable(false);
        clientFrame.add(new JScrollPane(history), GridBagHelper.getInstance().span().get());
        GridBagHelper.getInstance().resetSettings();
        GridBagHelper.getInstance().nextRow().nextRow();
        JTextField message = new JTextField(30);
        GridBagHelper.getInstance().setWeights(2, 1);
        clientFrame.add(message, GridBagHelper.getInstance().nextRow().get());
        JButton send = new JButton("Отправить");
        send.addActionListener(e -> {
            if (!message.getText().isEmpty()) {
                writeToServer(message.getText());
                writeToHistory("You (" + new Message("", "", "").getTime() + "): " + message.getText());
                message.setText("");
            }
        });
        GridBagHelper.getInstance().setWeights(1, 1);
        clientFrame.add(send, GridBagHelper.getInstance().nextCell().nextCell().get());

        clientFrame.setVisible(true);
        clientFrame.setSize(400, 250);
    }

    public void writeToHistory(String message){
        history.setText(history.getText() + "\n" + message);
    }

    public String getClientId() {
        return clientId;
    }
}
