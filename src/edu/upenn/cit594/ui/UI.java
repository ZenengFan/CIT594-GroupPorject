package edu.upenn.cit594.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
//import java.util.HashMap;

import java.util.regex.Pattern;

import edu.upenn.cit594.processor.CsvProcessor;
import edu.upenn.cit594.processor.DataProcessor;
import edu.upenn.cit594.processor.JsonProcessor;




public class UI {
	


	//input from users
	private  String fileFormat;
	private  String parkingPath;
	private  String propertyPath;
	private  String populationPath;
	private int inputNumber;
	
	
	//static variable for internal use
	private static String pattern1 = ".*.json";
	private static String pattern2 = ".*.csv";
	private static String pattern3 = ".*.txt";
	
	
	//output variable for processor use
	//-1: illegal input format
	//0: JSON parking format
	//1: csv parking format
	//2: reading finished
	private int indicator = -1;
	//create the processor calculate engine
	private DataProcessor processor;
	
	
	
	//construct interface
	public UI(String[] args) {
		
		if (args.length != 5) {
			System.out.println("Please input right number of arguments");
			System.exit(0);
		}
		
		//set up the variables
		fileFormat= args[0];
		parkingPath = args[1];
		propertyPath = args[2];
		populationPath = args[3];
		
		
	}
	
	
	
	
	//handle the input and verify if the input format is correct.
	public void inputHandler() {
		
		//see if parking file match with json format
		if (Pattern.matches(pattern1,parkingPath) && fileFormat.contentEquals("json")){
			
			//check parking file existence and permissions for read
			if(InterfaceUtility.fileCheck(parkingPath)) {
				indicator = 0;
			}else {
				System.out.println("The parking json file provided can not be read or does not exist.");
				System.exit(0);
			}
	    //see if parking file match with csv format	
		}else if(Pattern.matches(pattern2,parkingPath) && fileFormat.contentEquals("csv")) {
			
			//check parking file existence and permissions for read
			if(InterfaceUtility.fileCheck(parkingPath)) {
				indicator = 1;
			}else {
				System.out.println("The parking csv file provided can not be read or does not exist.");
				System.exit(0);
			}
		
		}else {
			
			indicator = -1;
			System.out.println("Please input right format of parking");
			System.exit(0);
		}
		
		//check property file existence and permissions for read
		if(Pattern.matches(pattern2, propertyPath) && InterfaceUtility.fileCheck(propertyPath)) {}
			 else {
				indicator = -1;
				System.out.println("The property file provided can not be read or does not exist.");
				System.exit(0);
			}
		
		//check population file existence and permissions for read	
		if(Pattern.matches(pattern3, populationPath) && InterfaceUtility.fileCheck(populationPath)) {}
		 else {
			indicator = -1;
			System.out.println("The population file provided can not be read or does not exist.");
			System.exit(0);
		}
		
		
	}
	
	//call the processor which will call reading
	public void callRead() throws ParseException, FileNotFoundException, IOException, org.json.simple.parser.ParseException {
		
		//test
		/*PropertyReader propertyRd = new PropertyReader();
		propertyRd.read(propertyPath);
		
		PopulationReader populationRd = new PopulationReader();
		populationRd.read(populationPath);*/
		
		//check if the indicator is valid
		if (indicator == 0) {
			
			processor = new JsonProcessor(propertyPath,populationPath);
			processor.readParking(parkingPath);
			
			indicator = 2;
			
			//ParkingJReader being used inside
			/*jsonProcessor js = new jsonProcessor(propertyPath, populationPath);
			js.process(parkingPath);
			indicator = 2;*/
			
			
		}else if(indicator == 1){
			
			processor = new CsvProcessor(propertyPath,populationPath);
			processor.readParking(parkingPath);
			
			indicator = 2;
			//ParkingCReader being used inside
			/*csvProcessor csv = new csvProcessor(propertyPath, populationPath);
			csv.process(parkingPath);
			indicator = 2;*/
			
		}else {
			
			System.out.println("The indicator is set to be negative");
			System.exit(0);
			
		}
		
		
	};
	
	//get indicator
	public int getIndicator() {
		
		return indicator;
		
	}
	
	//print final output to the screen
	public void present() {
		
		System.out.println("The program finish reading");
		
		
	}



    //ask user to provide number
	public void getNumber() {
		
		System.out.println("Please input a number");
		Scanner sc = new Scanner(System.in);
		
		try {
			inputNumber = sc.nextInt();
			if(inputNumber == 0) {
				System.out.println("The program is terminated by user");
				System.exit(0);
			}else if(inputNumber < 0 || inputNumber > 6) {
				System.out.println("Error: the input is not a valid number");
				System.exit(0);	
			}
			
		} catch (InputMismatchException e) {
		    System.out.println("Error: the input is not a valid number");
		    System.exit(0);
		}
		  
		
		// TODO Auto-generated method stub
		
	}




	public void calculate() {
		
		processor.process(inputNumber);
		
		
	}




	
	
	

}
