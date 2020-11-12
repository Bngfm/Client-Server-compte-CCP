import java.net.Socket;
import java.io.IOException;
import java.io.*;
import java.util.Scanner;


public class ServerToClient implements Runnable{
		// ATTRIBUTES \\
	Socket toClient ;
	BufferedReader in;
	PrintWriter out;
	Scanner sc = new Scanner(System.in);

		// CONSTRUCTOR \\
	public ServerToClient(Socket toClient,BufferedReader in,PrintWriter out) throws IOException{
		this.toClient = toClient;
		this.in = in;
		this.out = out;
		System.out.println("Server start..."+toClient);
	}

            // RUN THREAD METHOD \\
	public void run(){
		String msg ; // MSG COMMING FROM CLIENTS \\
		String response; // RESPONSE TO SEND TO CLIENTS \\

		try{
			while (true){

				msg = in.readLine(); // READ MSG COMMING FROM CLIENT \\
				if(msg != null){
					
					if(msg.startsWith("CONSULTER")){ // if consulter(num) REQUEST \\

						String command = msg.replaceFirst("\\,", "\\(");
						command += ")";
						System.out.println("Client : "+command);

						String[] t1 = msg.split(",");
						int numccp = Integer.parseInt(t1[1]);

							// CALL ManagerFile STATIC METHOD \\
						response = ManagerFile.getCompte(numccp);

						response = "("+response+")";
						
						out.println(response); out.flush();

					}else if(msg.startsWith("CREDITER")){ // if crediter(num,solde) REQUEST \\

						String command = msg.replaceFirst("\\,", "\\(");
						command += ")";
						System.out.println("Client : "+command);

						String[] t1 = msg.split(",");
						int numccp = Integer.parseInt(t1[1]);
						float solde = Float.parseFloat(t1[2]);

							// CALL ManagerFile STATIC METHOD \\
						response = ManagerFile.crediterCompte(numccp,solde);

						response = "("+response+")";

						out.println(response); out.flush();

					}else if(msg.startsWith("DEBITER")){ // if debiter(num,solde) REQUEST \\
						
						String command = msg.replaceFirst("\\,", "\\(");
						command += ")";
						System.out.println("Client : "+command);

						String[] t1 = msg.split(",");
						int numccp = Integer.parseInt(t1[1]);
						float solde = Float.parseFloat(t1[2]);

							// CALL ManagerFile STATIC METHOD \\
						response = ManagerFile.debiter(numccp,solde);

						response = "("+response+")";

						out.println(response); out.flush();

					}else{ // IF AN ERROR REQUEST \\
						out.println("Invalid command"); out.flush();						
					}
					
				}else{ // if MSG IS NULL \\
					out.println("invalid"); out.flush();
				}

			}
		}catch(Exception e){}
	}

}