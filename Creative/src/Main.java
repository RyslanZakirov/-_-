import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        EventQueue.invokeLater( new Runnable(){
            public void run(){
                Window wnd = new Window();
                wnd.setDefaultCloseOperation(Window.EXIT_ON_CLOSE);
                wnd.setVisible(true);
            }

        });
    }
}
