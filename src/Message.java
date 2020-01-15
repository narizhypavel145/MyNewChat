import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable {
    private String name;
    private String message;
    private String id;
    private String time;

    public Message(String name, String message, String id) {
        this.name = name;
        this.message = message;
        this.id = id;
        setTime();
    }

    public Message(Message message) {
        this.name = message.name;
        this.message = message.message;
        this.id = message.id;
        this.time = message.time;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private void setTime(){
        time = new SimpleDateFormat("HH:mm:ss").format(new Date()); // время
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
