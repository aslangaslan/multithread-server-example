package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class MyForm extends JFrame {
	public static boolean socketOpened = false;

	public MyForm() throws HeadlessException, IOException {
		TCPClient client = new TCPClient();


		this.setSize(600, 400);
		this.setTitle("YKB Chat");

		JMenuBar menuBar = new JMenuBar();
		JMenu connectionMenu = new JMenu("Connection");
		JMenu helpMenu = new JMenu("Help");

		JMenuItem connectMenuItem = new JMenuItem("Connect");
		connectMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					client.connect(JOptionPane.showInputDialog("Enter host").toString());
					JOptionPane.showMessageDialog(null, "Connected.");
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		JMenuItem disconnectMenuItem = new JMenuItem("Disconnect");
		disconnectMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					client.disconnect();
					JOptionPane.showMessageDialog(null, "Dısconnected.");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		connectionMenu.add(connectMenuItem);
		connectionMenu.add(disconnectMenuItem);

		menuBar.add(connectionMenu);
		menuBar.add(helpMenu);

		JPanel panel = new JPanel();

		JLabel enterMessageLabel = new JLabel("Enter your message:");
		JTextField messageTextField = new JTextField(15);


		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					client.sendMessage(messageTextField.getText());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				messageTextField.setText("");
			}
		});

		panel.add(enterMessageLabel);
		panel.add(messageTextField);
		panel.add(sendButton);
		panel.add(resetButton);

		JTextArea summary = new JTextArea();

		java.util.Timer messageReaderTimer = new Timer();

		messageReaderTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					if (client.isConneted()) {
						String result = client.getMessage();
						if (result != null)
							summary.append(result);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, 0, 100);


		this.getContentPane().add(BorderLayout.PAGE_START, menuBar);
		this.getContentPane().add(BorderLayout.CENTER, summary);
		this.getContentPane().add(BorderLayout.PAGE_END, panel);
		this.setLocation(200, 200);

		this.setVisible(true);
	}

}
