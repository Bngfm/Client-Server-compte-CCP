

import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.io.*;
import java.util.Scanner;

		// SERVER MAIN CLASS \\

public class Server{
	public static void main(String[] args) throws IOException{
		
		ServerSocket ss;
		Socket socketClient;
		BufferedReader in;
		PrintWriter out;
		Scanner sc = new Scanner(System.in);

		System.out.println("WAIT FOR CONNECTION ...");

		try{
				// NEW SERVERSOCKET \\
			ss = new ServerSocket(15000);
			

			while (true){
					// ACCEPT CLIENT \\
				socketClient = ss.accept();

					// CRETAE IO \\
				in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
				out= new PrintWriter(new OutputStreamWriter(socketClient.getOutputStream()));

					// START SERVER INSTANCE IN THREAD \\
				Thread prog = new Thread(new ServerToClient(socketClient,in, out));
				prog.start();
			}

		}catch(IOException e){
			e.printStackTrace();
		}
	}
}