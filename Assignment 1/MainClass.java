/* Name: Anna Andler
 * Class: SE 450
 * Date: 9/21/2023
 * File Name: MainClass.java
 */
package edu.depaul;
import java.util.ArrayList;
import java.util.Scanner;

/* MainClass: 
 * Description: controls program flow and initializes patient list by admitting and discharging patients. 
 */
public class MainClass {
    /* GLOBAL VARIABLE DECLARATION */
     //Create a blank array list of patients
     private static ArrayList<Patient> patients = new ArrayList<Patient>();
     //Initialize scanner object to parse system input
     private static Scanner input=new Scanner(System.in);    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /* Name: admit
     * Description: asks what the result of the patient PCR test is and determine patient type
     * Arguments: None
     * Returns: None
     */
    public static void admit(){
        System.out.println("You have chosen the Admit Function");
        //Initialize local variables
        int id=-1, age=-1;
        String fName,lName;
        String[] name;
        Patient patient = new Patient();

        //Prompt for patient name. First and Last must be contained in the same string. 
        do{
            System.out.println("Please enter the Patient's First and Last Name delimited by a space. Example -  Anna Andler");
            name=input.nextLine().split("\\s+");
        }while(name.length<=1);
        fName=name[0];
        lName=name[name.length-1];

        //Prompt for patient ID. Must be a whole, unique number
        do{
            try{
                System.out.println("Please enter the Patient's ID Number as a positive whole number. This item must not already be in the system.");
                id=Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e){ id=-1; }
        } while (id<0||indexOfId(id)>=0);

        //Prompt for patient age. Must be between 0 and 130.
        do{
            try{
                System.out.println("Please enter the Patient's age as a positive whole number. Note age must be greater than 0 and less than 130.");
                age=Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e){ age=-1; }
        } while (age<0 || age>130);

        //Prompt for PCR result - takes 'negative' or 'positive' and admit patient according to the pcr test result
        boolean result=promptPcr();
        if (result==false){
            System.out.println("You have entered a negative test result. \nWhat is the main symptom the patient is experiencing? (e.g., coughing, hypertension, etc.)");
            String mainSymptom=input.nextLine(); 
            patient = new RegularPatient(id, fName, lName, age, mainSymptom); //Create a regular patient with negative test result
        } else if (result==true){
            double temperature=promptTemperature();
            patient=new Covid19Patient(id, fName, lName, age, temperature); //Create a covid 19 patient with positive test result
            patient.setPcr(true);
        } else { System.out.println("Unexpected error"); return;  } //Handle unexpected output (shouldnt reach this point)

        //Add admited patient to the array list of patients
        patients.add(patient); 
        System.out.println("Patient Added Successfully");  
    }

    /* Name: print
     * Description: prints all patient information
     * Arguments: a single patient object
     * Returns: None - system output only
     */
    public static void print(){
        //Make sure there is at least one patient before printing patient data
        if(patients.size()<1){System.out.println("Please admit a patient first."); return;} 

        //print patient data from each patient
        for(Patient patient : patients){
            System.out.println("****************************");
            System.out.println(patient.toString());
            System.out.println("****************************");
        }
    }
    /* Name: submitPCR 
     * Description: prompts for patient id and will update the pcr test value for that patient
     * Arguments: None
     * Returns: None
    */
    public static void submitPCR(){
        //Make sure there is at least one patient before submitting a pcr test
       if(patients.size()<1){System.out.println("Please admit a patient first."); return;}

       System.out.println("You have chosen the PCR Test Function");
       //Prompt for ID of patient to submit PCR test for
       int index=promptId();
    //Prompt for PCR test result of patient to submit
       boolean result=promptPcr();

       //Handle patient based on PCR test result:
       if (result==false){ 
            patients.get(index).setPcr(result); //Set the PCR test result in patient data
            if(patients.get(index) instanceof Covid19Patient){ //If the patient was formerly a covid patient, discharge them. 
                System.out.println("Now discharging former Covid19 Patient");
                discharge(index);
            }
        } else if (result==true){
            System.out.println("Now changing patient to Covid19 Patient");
            //Prompt for patient temperature
            double temperature=promptTemperature(); 
            //Create new Covid19Patient by copying previous data into a new object
            patients.add(new Covid19Patient(patients.get(index).getId(), patients.get(index).getFName(), patients.get(index).getLName(), patients.get(index).getAge(),temperature));
            //Set positive PCR test result in patient data
            patients.get(patients.size()-1).setPcr(result);  
            //Destroy previous patient data copied over
            patients.remove(index);
        } else { System.out.println("Unexpected Error");return; } //Handle unexpected output (shouldnt reach this point)

       System.out.println("Successfully submitted the PCR test and updated patient status");
    }

    /* Name: doRounds
     * Description: perform patient rounds by taking covid19 patient temperatures and doing all patient treatment plans
     * Arguments: None
     * Returns: None
    */
    public static void doRounds(){
       //Make sure there is at least one patient before doing patient rounds
       if(patients.size()<1){System.out.println("Please admit a patient first."); return;}

       System.out.println("You have chosen the Rounds Function");
       System.out.println("Treating all patients in the hospital...");
       //For each patient admitted to the hospital: 
        for(Patient patient : patients){
            if(patient instanceof Covid19Patient){ //update their temperature if the patient is a covid19 patient
                Covid19Patient covid=(Covid19Patient)patient;
                covid.setTemp(promptTemperature());
            }
            //"Treat" and Output the patient treatment plan.
            System.out.println("Treatment plan for patient with ID "+patient.getId()+" is " + patient.treat());
        }
        System.out.println("All "+patients.size()+" patients have been treated");
    }

    /* Name: discharge 
     * Description: quicker version of discharging patients (i.e., removing them from the patient list) if the index of the patient in the array list patients is known, and if their pcr test is negative
     * Arguments: index of patient to discharge
     * Returns: None
    */
    public static void discharge(int index){
        if(patients.get(index).getPcr()==false){patients.remove(index);}
        else { System.out.println("This patient needs a negative PCR test in order to be discharged");}
    }

    /* Name: discharge
     * Description: discharges a patient by removing them from the current patient list, only if their pcr test is negative.
     * Arguments: None
     * Returns: None
    */
    public static void discharge(){
        //Make sure there is at least one patient before deleting a patient
        if(patients.size()<1){System.out.println("Please admit a patient first."); return;}

        System.out.println("You have chosen the Discharge Function");
        //Prompt for patient ID
        int index=promptId();
        //Discharge patient, only if they have a negative pcr test
        if(patients.get(index).getPcr()==false){patients.remove(index);}
        else { System.out.println("This patient needs a negative PCR test in order to be discharged");}
    }
    /* Name: promptId
     * Description: Handles prompting the user for a valid patient ID for submitting a pcr test
     * Arguments: None
     * Returns: The index that the patient with the corresponding id is located at in the patient array list
    */    
    public static int promptId(){
        int id=-1;
        int index=-1;
        do {
            try{
                //Ask user for patient ID
                System.out.println("Please enter the patient id as a whole number to submit the pcr test for. This patient must already be admitted.");
                id=Integer.parseInt(input.nextLine());
                index=indexOfId(id);
            } catch (NumberFormatException e){ index=-1; }  //The patient ID must be a valid integer
        } while(index<0); //the patient id must exist in the admitted patients list
        return index;
    }

    /* Name: indexOfId
     * Description: Returns the current index of a patient identified by its id in the patients array list
     * Arguments: None
     * Returns: The index that the patient with the corresponding id is located at in the patient array list
    */   
    public static int indexOfId(int id){
        for(int x=0;x<patients.size();x++){ //Check each patient in the array list, patients
            if(patients.get(x).getId()==id){return x;}  //return the index if found
        }
        return -1; //else, return -1 for not found
    }

    /* Name: promptTemperature
     * Description: obtain a valid patient temperature from the user
     * Arguments: None
     * Returns: patient temperature as a double
    */   
    public static double promptTemperature(){
        double temperature=0.0;
        do {
            try{
                //Prompt User for Input
                System.out.println("Please enter the patient's temperature. This value must be between 30 and 50 degrees celcius");
                temperature=Double.parseDouble(input.nextLine());
            } catch (NumberFormatException e){ temperature=0.0; } //The patient temperature must be a double
        } while(temperature<30||temperature>50);  //The patient temperature must be a temperature that is physically possible
        return temperature;
    }

    /* Name: promptPcr
     * Description: obtain a valid pcr test result from the user
     * Arguments: None
     * Returns: patient pcr test result as a boolean. Negative==false, Positive==true.
    */  
    public static boolean promptPcr(){
       String result="N/A";
       do{
           //Prompt user for input
            System.out.println("Please enter the PCR test result as either 'negative' or 'positive'");
            result=input.nextLine();
            if (result.matches("(.*)negative(.*)")){ return false; } //Translate 'negative' to false
            else if (result.matches("(.*)positive(.*)")){ return true;} //Translate 'positive' to true
            System.out.println( "Please enter a valid test result");
       } while(!result.equals("positive") && !result.equals("negative")); //The PCR result can only be 'negative' or 'positive'
       return false;
    }

    public static void main (String[] args){

        //Initialize local string variable to evaluate user selection input
        String inputString="";

        //Send user menu options to system I/O
        while(!inputString.equals("q")){ //type quit to exit the program
            //Send menu output to user -- 
            System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
            System.out.println("Welcome to the Hospital Program\n Please select one of the following menu options by entering their corresponding number, or enter 'quit' to exit the program");
            System.out.println("\t1. Admit a patient\n\t2. Print patient information\n\t3. Submit a PCR test result\n\t4. Do rounds\n\t5. Discharge a patient\n\t6. Exit");

            //Grab user input and only take the first character sent
            inputString=input.nextLine().substring(0,1);

            //Pull menu option based on user input: 
            switch(inputString){
                case "1": //1. == Admit a patient
                    admit();
                    break;
                case "2": //2. == Print all patient data
                    print();
                    break;
                case "3": //3. == Submit a PCR test result for a patient
                    submitPCR();
                    break;
                case "4": // 4. == Do Patient Rounds
                    doRounds();
                    break;
                case "5": //5. == Discharge a Patient
                    discharge(); 
                    break;
                case "6": //6. Quit option
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
