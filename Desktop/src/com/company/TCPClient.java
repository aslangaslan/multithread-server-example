package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
	private int dataPort = 8080;
	private   Socket socket;
	private BufferedReader socketReader;
	private PrintWriter socketWriter;

	public TCPClient() throws IOException {

	}

	public void connect(String host) throws IOException {
		this.socket = new Socket(InetAddress.getByName(host), dataPort);
		socketReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
		socketWriter=new PrintWriter(socket.getOutputStream(),true);


	}

	public void disconnect() throws IOException {
		socket.shutdownOutput();
		socket.shutdownInput();
		this.socket.close();
	}

	public boolean isConneted(){
		return this.socket!=null && this.socket.isConnected();
	}

	public String getMessage() throws IOException {
		return isConneted()?socketReader.readLine():null;
	}

	public void sendMessage(String message) throws IOException {
		this.socketWriter.println(message);
	}
}
