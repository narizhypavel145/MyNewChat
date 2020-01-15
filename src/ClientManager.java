import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientManager {
    private static class MainLayout extends JFrame {
        private String name;
        public MainLayout() {
            super();
            // выход при закрытии окна
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            // устанавливаем расположение компонентов
            setLayout(new GridBagLayout());

            GridBagHelper.getInstance().nextCell().gap(4);
            add(new JLabel("Имя:"), GridBagHelper.getInstance().get());
            GridBagHelper.getInstance().nextCell().span();
            JTextField name = new JTextField(20);
            add(name);
            JButton start = new JButton("Присоединиться");
            start.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!name.getText().isEmpty()){
                        new ClientService(name.getText());
                        name.setText("");
                    }
                }
            });
            GridBagHelper.getInstance().nextRow();
            add(start, GridBagHelper.getInstance().get());

            // выведем окно на экран
            setSize(400, 200);
            setVisible(true);
        }
        @Override
        public String getName() {
            return name;
        }

    }

    public static void main(String[] args) {
        new MainLayout();

    }
}
