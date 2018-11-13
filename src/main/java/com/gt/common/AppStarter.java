package com.gt.common;

import java.net.ServerSocket;
import java.net.Socket;

import com.ca.ui.Main;
import com.gt.common.utils.Logger;

/**
 * Used in code <br/>
 * com.gt.common-AppStarter.java<br/>
 *
 * @author IVONEE ONIETH </a>
 */
public class AppStarter {
    public boolean notFindExisting = true;
    private String HOST = "localhost";
    private final int PORT = 45433;

    public AppStarter() {

        if (notFindExisting()) {
            // if not found existing ... will be killed
            // start detecting server thread
            new Thread(new DetectForNew()).start();
        }

    }

    private synchronized boolean notFindExisting() {
        // try to connect to server
        Socket client;
        try {
            client = new Socket(HOST, PORT);
            Logger.L("conn", "Connection accepted by already running app");
            notFindExisting = false;
        } catch (Exception e) {
            notFindExisting = true;

        }
        return notFindExisting;
    }

    class DetectForNew implements Runnable {
        ServerSocket serverSocket;

        public final void run() {
            try {
                serverSocket = new ServerSocket(PORT);
                while (true) {
                    serverSocket.accept();
                    Main.showMaximized();
                }
            } catch (Exception e) {
                System.out.println("detect thread terminated " + e.getMessage());

            }
        }
    }

}
