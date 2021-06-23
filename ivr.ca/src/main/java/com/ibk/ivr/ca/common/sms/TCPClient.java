package com.ibk.ivr.ca.common.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClient {
	public static void main(String[] args) {
		new Thread(() -> {
			try (Socket socket = new Socket("127.0.0.1", 5555)) {
				try (PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "euc-kr"), true)) {
					//BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
					try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
						System.out.println("입력 : ");
						String data = br.readLine();
						out.println(data);
					} catch(Exception e) {
						e.printStackTrace();
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
}
