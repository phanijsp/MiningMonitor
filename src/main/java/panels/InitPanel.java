package panels;

import designs.borders.CircularButtonBorder;
import models.AppModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InitPanel extends JPanel {
    public InitPanel(AppModel appModel){
        JLabel jLabel = new JLabel("Finding configured rig ips...");
        add(jLabel);
        List<String> ips = loadSavedIps();
        if(ips!=null) {
            appModel.setIpList(ips);
            return;
        }
        jLabel.setText("<html><body><center>Enter ip addresses of mining rigs on your network below<br>(each in new line)</center></body></html>:");
        Border jLabelBorder = new EmptyBorder(8,8,8,8);
        jLabel.setBorder(jLabelBorder);
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel.setAlignmentX(CENTER_ALIGNMENT);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JTextArea ipTextArea = new JTextArea(10,1);
        add(ipTextArea);
        JButton jButton = new JButton("Proceed");
        jButton.setBorderPainted(true);
        jButton.setBorder(new CircularButtonBorder(9));
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(jButton);
        add(Box.createRigidArea(new Dimension(0, 5)));

    }
    private List<String> loadSavedIps(){
        List<String> ips = new ArrayList<>();
        String tmp = null;
        try {
            FileReader fileReader = new FileReader("ips.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((tmp = bufferedReader.readLine())!=null){
                ips.add(tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(ips.size()>0) return ips;
        return null;
    }
}
