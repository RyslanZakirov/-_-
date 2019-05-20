import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class Window extends JFrame {

    private static int MAX_WIDTH;
    private static int MAX_HEIGHT;
    private JMenuBar bar;
    private JPanel panel;
    private JFileChooser file_chooser;
    private Path path = null;

    public Window(){

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();

        MAX_WIDTH = dim.width / 2;
        MAX_HEIGHT = dim.height / 2;
        setSize(MAX_WIDTH,MAX_HEIGHT);
        setResizable(false);

        bar = new JMenuBar();
        setJMenuBar(bar);
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem save_as = new JMenuItem("Save as...");
        JMenuItem close = new JMenuItem("Close");
        file.add(open);
        file.add(save);
        file.add(save_as);
        file.addSeparator();
        file.add(close);
        bar.add(file);

        panel = new JPanel();
        JTextArea text = new JTextArea(this.getHeight()-409, this.getWidth()-694);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        panel.add(text);
        add(panel);

        file_chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt","txt");
        file_chooser.setFileFilter(filter);
        file_chooser.setAcceptAllFileFilterUsed(true);

        open.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){

                file_chooser.showOpenDialog(Window.this);
                try{

                    byte[] str_byte = Files.readAllBytes(file_chooser.getSelectedFile().toPath());
                    path = file_chooser.getSelectedFile().toPath();
                    text.setText(new String(str_byte, Charset.defaultCharset()));

                }catch(IOException exception){
                    System.out.println(exception.getMessage());
                }
            }
        });

        save.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                try{
                    if(path != null){
                        PrintWriter writer = new PrintWriter(new FileWriter(path.toFile(),false));
                        writer.append(text.getText());
                        writer.close();
                    }else {
                        file_chooser.showSaveDialog(Window.this);
                        PrintWriter writer1 = new PrintWriter(new FileWriter(file_chooser.getSelectedFile(), false));
                        writer1.append(text.getText());
                        path = file_chooser.getSelectedFile().toPath();
                        writer1.close();
                    }
                }catch(IOException exception){
                    System.out.println(exception.getMessage());
                }
            }
        });

        save_as.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                file_chooser.showSaveDialog(Window.this);
                try{
                    PrintWriter writer = new PrintWriter(new FileWriter(file_chooser.getSelectedFile(),false));
                    writer.append(text.getText());
                    path = file_chooser.getSelectedFile().toPath();
                    writer.close();
                }catch(IOException exception){
                    System.out.println(exception.getMessage());
                }
            }
        });

        close.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                System.exit(0);
            }
        });

    }

}
