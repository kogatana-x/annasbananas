/* Name: Anna Andler
 * Class: SE 450
 * Date: 10/3/2023
 * File Name: MainClass.java
 */
package edu.depaul;
import java.util.Scanner;

/* MainClass: 
 * Description: controls program flow and initializes patient list by admitting and discharging patients. 
 */
public class MainClass {
    /* GLOBAL VARIABLE DECLARATION */
     //Initialize scanner object to parse system input
     private static Scanner input=new Scanner(System.in);    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public static void main (String[] args){
        //Initialize local string variable to evaluate user selection input
        String inputString="";
        Triage Menu=new Triage();
        //Send user menu options to system I/O
        while(!inputString.equals("q")){ //type quit to exit the program
            //Send menu output to user -- 
            System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
            System.out.println("Welcome to the Hospital Program\n Please select one of the following menu options by entering their corresponding number, or enter 'quit' to exit the program");
            System.out.println("\t1. Admit a patient\n\t2. Print patient information\n\t3. Submit a PCR test result\n\t4. Submit an Antigen Test \n\t5. Do rounds\n\t6. Discharge a patient\n\t7. Exit");

            //Grab user input and only take the first character sent
            inputString=input.nextLine().substring(0,1);

            //Pull menu option based on user input: 
            switch(inputString){
                case "1": //1. == Admit a patient
                    Menu.admit();
                    break;
                case "2": //2. == Print all patient data
                    Menu.print();
                    break;
                case "3": //3. == Submit a PCR test result for a patient
                    Menu.submitPCR();
                    break;
                case "4": //4. == Submit an Antigen test result for a patient
                    Menu.submitAntigen();
                    break;
                case "5": // 4. == Do Patient Rounds
                    Menu.doRounds();
                    break;
                case "6": //5. == Discharge a Patient
                    Menu.discharge(); 
                    break;
                case "7": //6. Quit option
                    System.out.println("Thank you for using the Hospital Program. Goodbye");
                    return;
                case "q": //"quit" or "q" == Quit option
                    System.out.println("Thank you for using the Hospital Program. Goodbye");
                    break;
                default:
                    System.out.println("Please select a valid option");
            }
        }
    }
}
