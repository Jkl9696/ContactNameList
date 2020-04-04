package binaryclassmarch31;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * description: a class that adds names to a .dat file and lets the data be static 
 * and not disappear after closing the program
 * 
 * @author Jared Sullin
 *
 */

public class ContactList {
	
	/**
	 * 
	 * description: prompts what action user wants to use 
	 * 
	 * @param args
	 */
	

	public static void main(String[] args) {

		int choice;
		boolean validation = false;
		Scanner keyboard = new Scanner(System.in);

		
		// input validation for if a user types in a word or letter 
		do {
			
			System.out.println("1) add a name, 2) show all names, 3) exit");
			while(!keyboard.hasNextInt()) {
				System.out.println("That's not a number!");
				System.out.println("please input 1 or 2 or 3");
				System.out.println("1) add a name, 2) show all names, 3) exit");
		        keyboard.next(); 	
			}
			choice = keyboard.nextInt();
		
		}while(choice <= 0);
		
		// for input validation if they put in any other numbers 
		if (choice != 1 && choice != 2 && choice !=3) {
		
			while(!validation) {
				
				System.out.println("Please input 1 or 2 or 3");
				System.out.println("1) add a name, 2) show all names, 3) exit");

				choice = keyboard.nextInt();
			
				if (choice == 1|| choice == 2|| choice ==3) { 
					validation = true ;
				}
			}
		}

		if (choice == 1) {
			try {
				add();
			}
			catch(IllegalArgumentException e){
					System.out.println("Please input valid name. \n "
							+ "No numbers in the name.  ");		
			}			
		}
		if(choice == 2) {			
			show(load());
		}
		if(choice == 3) {
			System.exit(0);
		}
		
		keyboard.close();

	}
	
	/**
	 * description: adds names to a array list and saves them after you have input all of the names 
	 * user wanted
	 * @throws IllegalArgumentException
	 */

	public static void add() throws IllegalArgumentException{

		ArrayList<String> names = new ArrayList<String>();

		String addAnother = "y";

		Scanner keyboard = new Scanner(System.in);

		String name;


		while(addAnother.equalsIgnoreCase("y")){

			System.out.println("Input name");
			
			// gets user input 
			name = keyboard.nextLine();
			
			if(name.matches(".*\\d.*")) {
				System.out.println("input valid name without numbers");
			}
			else {	
				names.add(name);		
			}

			// adds name to array list 

			System.out.println("Press Y to add another name. \nAny other key to stop.");

			addAnother = keyboard.nextLine();

		}
		save(names);
		
		keyboard.close();
	}

	/**
	 * description: this prints out everying that was in the serialized array list
	 * @param array
	 */

	public static void show(ArrayList<String> array){

		for (int i = 0; i < array.size(); i++) {
			// print 
			System.out.print((i + 1) + ")");
			System.out.println(array.get(i));			
		}

	}

	/**
	 * description: this deserializes all the data in the .dat file and puts iy in a array list
	 * 
	 * @return names //  array list
	 * @throws IOException
	 */

	public static ArrayList<String> load() {

		//
		String fileName = "src/contactlist.dat";

		// creates new array list to store the deserialized data
		ArrayList<String> array = new ArrayList<String>();


		FileInputStream fos;
		try {
			fos = new FileInputStream(fileName);
			ObjectInputStream oos = new ObjectInputStream(fos);
			
			try {

				// casts the object read in to a string array list so it is easier to return
				array = (ArrayList<String>) oos.readObject();
				
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
				
			System.out.println("Error No such file or directory recompile code and try again. ");
					
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		

		

		return array;

	}

	/**
	 * description: this method that serializes data from a string array list
	 * 
	 * @param array
	 */

	public static void save(ArrayList<String> array) {
		// a try catch to prevent anyone from breaking the code
		try {
			FileOutputStream fos = new FileOutputStream("src/contactlist.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(array);
			oos.close();
			fos.close();

		}
		catch(FileNotFoundException fnfe) {
			System.out.println("ERROR cannot find file");
		}

		catch(IOException ioe) {
			System.out.println("ERROR cannot write to file");
		}
	}


}



//is this one way  of serialization ?	


//	RandomAccessFile randomFile = new RandomAccessFile("src/contactlist.dat", "rw");


//	System.out.println("writing to file........");

//	for (int i = 0; i < array.size(); i++) {

//		randomFile.writeChars(array.get(i));
//		randomFile.writeChars(" ");

//	}

//	randomFile.close();

//	System.out.println("done.");

//}
