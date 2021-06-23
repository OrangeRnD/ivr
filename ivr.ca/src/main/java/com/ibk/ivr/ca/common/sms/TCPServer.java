package com.ibk.ivr.ca.common.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(5555)) {
            System.out.println("5555 대기 중");
        	try (
        			Socket clientSocket = serverSocket.accept(); 
        			//PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),"euc-kr"));
        		) {

                System.out.println("접속");
                // 클라이언트로 부터 데이터를 받는다.

                String inputLine = null; 
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("read:" + inputLine);
                    //out.println(inputLine);
                }
        	} catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
