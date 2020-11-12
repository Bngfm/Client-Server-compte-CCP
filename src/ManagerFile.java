

import java.io.*;

	/****************************************
	 *		CLASS FILE MANAGER 				*
	 ****************************************/
public class ManagerFile{

	/*====================================================================*/
		// consulter(numccp ) \\
	public static String getCompte(int input){
		String compte ="";
		try{
			FileReader fr = new FileReader("comptes_ccp.txt");
			BufferedReader reader = new BufferedReader(fr);

			String numccp = "";
			
			while ((numccp = reader.readLine()) != null){
				if(numccp.equals(String.valueOf(input))){
					compte += numccp + ",";
					compte += reader.readLine() + ",";
					compte += reader.readLine();
					
				}
			}
			if(compte.isEmpty()){
				compte = "0,Inexistant,0";
			}
			reader.close();
			fr.close();
		}catch(FileNotFoundException e){}
		catch(IOException ex){}

		return compte;
	}
/*====================================================================*/

/*====================================================================*/
		//crediter(numccp,solde)\\
	public static synchronized String crediterCompte(int n, float s){
		String nn = String.valueOf(n);

		File f1=null;	File f2=null;

		try{
			f1 = new File("comptes_ccp.txt");
			f2 = new File("newComptes.txt");

			BufferedReader reader = new BufferedReader(new FileReader (f1));
			BufferedWriter writer = new BufferedWriter(new FileWriter (f2));

			String line = "";
			while ((line = reader.readLine()) != null){
				writer.write(line); writer.flush();writer.newLine();
				if(line.equals(nn)){
					line = reader.readLine();
					writer.write(line); writer.flush();writer.newLine();

					line = reader.readLine();
					float solde = Float.parseFloat(line);
					solde += s;
					line = String.valueOf(solde);
					writer.write(line); writer.flush();writer.newLine();					
				}

			}
			reader.close();
			writer.close();
			
		}catch(FileNotFoundException e){}
		catch(IOException ex){}

			// renommer le nouveau comme le premier et supprimer le premier
		String name = f1.getName();
		f1.delete();
		File newFile = new File(name);
		f2.renameTo(newFile);

		return getCompte(n);
	}
/*====================================================================*/

/*====================================================================*/
			// debiter(num,solde) \\
	public static synchronized String debiter(int num, float s){
		String nn = String.valueOf(num);
		File f1=null;	File f2=null;
		boolean ok = true;
		try{
			f1 = new File("comptes_ccp.txt");
			f2 = new File("newComptes.txt");
			BufferedReader reader = new BufferedReader(new FileReader (f1));
			BufferedWriter writer = new BufferedWriter(new FileWriter (f2));

			String line ="";
			while ((line = reader.readLine()) != null){
				writer.write(line); writer.flush(); writer.newLine(); // copie de numccp
				if(line.equals(nn)){
					line = reader.readLine();
					writer.write(line); writer.flush();writer.newLine(); // copie de nomccp

					line = reader.readLine();
					float solde = Float.parseFloat(line);
					if(solde>=s){
						solde -= s;
						line = String.valueOf(solde);
						writer.write(line); writer.flush();writer.newLine(); // copie de solde
					}else{
						ok = false;
						writer.write(line); writer.flush();writer.newLine(); // copie de solde
					}
					 		
				}

			}
			reader.close();
			writer.close();
		}catch(FileNotFoundException e){}
		catch(IOException ex){}

			// renommer le nouveau comme le premier et supprimer le premier \\
		String name = f1.getName();
		f1.delete();
		File newFile = new File(name);
		f2.renameTo(newFile);

		if(ok){
			return getCompte(num);
		}else{
			return "0, Solde insufisant,0";
		}
		
	}

/*====================================================================*/
}