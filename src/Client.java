import java.net.Socket;
import java.io.IOException;
import java.util.Scanner;
import java.io.*;

			// CLIENT CLASS \\

public class Client{
		// MATCH CLIENT INPUT TO SEND TO SERVER \\
	public static String matching(String req){
			req = req.replaceAll("\\s", ""); // supprimer les espaces
			req = req.replaceAll("\\(", ",");// remplacer ( par ,
			req = req.replaceAll("\\)", ""); // supprimer le dernier )
			return req;
		}

		// MAIN METHOD \\
	public static void main(String[] args) throws IOException{
		Socket client = new Socket("127.0.0.1",15000);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		PrintWriter out = new PrintWriter(client.getOutputStream(), true);
		Scanner sc = new Scanner(System.in);

			// POSSIBLE COMMANDS \\
		System.out.println("Vos commandes possibles sont : \t");
		System.out.println("*******************************************************************************");
		System.out.print("[consulter(numccp) , ");
		System.out.print("crediter(numccp, solde) , ");
		System.out.println("consulter(numccp, solde) , exit ]");
		System.out.println("*******************************************************************************");
		
		try{
				// CREATE THREAD TO SEND \\
			Thread s = new Thread(new Runnable(){
				String req;
				public void run(){
					while (true){
						req = (sc.nextLine()).toUpperCase();
						String command = matching(req); // MATCH CLIENT INPUT

						if(command.equals("EXIT")){ // TO EXIT CLIENT PROGRAM
							System.out.println("Au revoir ");
							System.exit(0);
						}else{
							out.println(command); out.flush(); // SEND COMMAND TO SERVER
						}
					}
				}
			}); s.start();

				// CREATE THREAD TO RECEIVE \\
			Thread r = new Thread(new Runnable(){
				String msg;
				public void run(){
					try{
						msg = in.readLine(); // TRY TO READ SERVER RESPONSE \\
						while (msg != null){
							System.out.println(msg); // PRINT SERVER RESPONSE \\
							msg = in.readLine(); // READ MORE IF AVAILABLE \\
						}
						in.close();
					}catch(IOException e){
						e.printStackTrace();
					}
				}
			}); r.start();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}