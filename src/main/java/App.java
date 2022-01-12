import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import models.AppModel;
import panels.InitPanel;

import javax.swing.*;
import java.io.ByteArrayOutputStream;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame jFrame = new JFrame("Mining Monitor");
        jFrame.setUndecorated(true);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.pack();
        AppModel appModel = new AppModel();
        InitPanel initPanel = new InitPanel(appModel);

        jFrame.getContentPane().add(initPanel);
        jFrame.pack();

        listFolderStructure("user", "1", "192.168.0.127", 22,"curl http://localhost:22333/api/v1/status | python -m json.tool");
    }
    public static void listFolderStructure(String username, String password,
                                           String host, int port, String command) throws Exception {

        Session session = null;
        ChannelExec channel = null;

        try {
            session = new JSch().getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.connect();

            while (channel.isConnected()) {
                Thread.sleep(100);
            }

            String responseString = new String(responseStream.toByteArray());
            System.out.println(responseString);
        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
    }
}
