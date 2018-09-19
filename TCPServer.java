package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {
    private static final int DataPort = 8080;
    private static ServerSocket serverSocket;

    public static List<Socket> clients = new ArrayList<Socket>();

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(DataPort);

                listen();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void listen() throws IOException, InterruptedException {
        System.out.println("Server has been started at " + DataPort);
        ExecutorService executorService=Executors.newFixedThreadPool(10);
        do {
            Socket socket = serverSocket.accept();
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clients.add(socket);
            executorService.execute(()->{
                try {
                    //f

                    while (true) {
                        if(socket.isConnected())
                        {
                        String incomingMessage = socketReader.readLine();
                        final String requestMessage="Client:" + socket.getRemoteSocketAddress() + "=" + incomingMessage;
                        System.out.println(requestMessage);
                        clients.parallelStream().filter(sz->sz.isConnected()==true).forEach(s->{
                            PrintWriter socketWriter = null;
                            try {
                                socketWriter = new PrintWriter(s.getOutputStream(), true);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            socketWriter.println(requestMessage);
                                }
                        );
                    }}
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    try {//r c
                        socketReader.close();
                        socket.close();
                        clients.remove(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


        }
     while (true) ;


    }


}