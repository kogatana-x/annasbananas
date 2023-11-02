/* Name: Anna Andler
 * Class: SE 450
 * Date: 10/3/2023
 * File Name: Prompter.java
 * Description: Handles User Input
 */

package edu.depaul;

import java.util.ArrayList;
import java.util.Scanner;

//Interfaces the Prompter Program Uses
interface InputName{ public String[] promptPatientName(); }
interface InputID{ public int promptId(ArrayList<Patient> patients); public int findId(ArrayList<Patient> patients); }
interface InputAge{ public int promptAge(); }
interface InputTemperature{ public double promptTemperature(); }
interface InputSymptom{ public String promptSymptom(); }
interface InputPCR{ public boolean promptPcr(); }
interface InputAntigen{ public boolean promptAntigen(); }
interface InputPregnant{ public boolean promptPregnant(); }

public class Prompter implements InputName, InputID, InputAge, InputTemperature, InputSymptom, InputPCR, InputAntigen, InputPregnant{
     private static Scanner input=new Scanner(System.in);   

    /* Name: promptPatientName
     * Description: obtain a valid patient name from the user, delimited by a space
     * Arguments: None
     * Returns: patient name as a string.
    */ 
     public String[] promptPatientName(){
        //Prompt for patient name. First and Last must be contained in the same string.
        String name[]; 
        do{
            System.out.println("Please enter the Patient's First and Last Name delimited by a space. Example -  Anna Andler");
            name=input.nextLine().split("\\s+");
        }while(name.length<=1);

        return name;
     }
    /* Name: promptId
     * Description: obtain a valid NEW patient id from the user
     * Arguments: None
     * Returns: patient id as an integer. this must be a unique id.
    */   
     public int promptId(ArrayList<Patient> patients){
        int id=-1;
        do{
            try{
                System.out.println("Please enter the Patient's ID Number as a positive whole number. This item must not already be in the system.");
                id=Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e){ id=-1; }
        } while (id<0||indexOfId(patients,id)>=0);
        return id;
     }
    /* Name: findId
     * Description: obtain a valid patient id from the user that is already in the system
     * Arguments: None
     * Returns: index of the patient id as an integer
    */  
     public int findId(ArrayList<Patient> patients){
        int id=-1,index=-1;
        do{
            try{
                System.out.println("Please enter the Patient's ID Number as a positive whole number.");
                id=Integer.parseInt(input.nextLine());
                index=indexOfId(patients,id);
            } catch (NumberFormatException e){ id=-1;}
        }while(id<0||index<0);
        return index; 
     }
    /* Name: promptAge
     * Description: obtain a valid patient age from the user
     * Arguments: None
     * Returns: patient age as an integer (between 0 and 130)
    */   
     public int promptAge(){
        int age=-1;
        do{
            try{
                System.out.println("Please enter the Patient's age as a positive whole number. Note age must be greater than 0 and less than 130.");
                age=Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e){ age=-1; }
        } while (age<0 || age>130);
        return age;
     }

    /* Name: promptTemperature
     * Description: obtain a valid patient temperature from the user
     * Arguments: None
     * Returns: patient temperature as a double
    */   
    public double promptTemperature(){
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
    /* Name: promptSymptom
     * Description: aquire a symptom or list of symptoms the patient is experiencing
     * Arguments: None
     * Returns: symptom(s) as a string
     */
    public String promptSymptom(){
        System.out.println("What is the main symptom the patient is experiencing? (e.g., coughing, hypertension, etc.)");
        String mainSymptom=input.nextLine(); 
        return mainSymptom;
    }
    /* Name: promptPcr
     * Description: obtain a valid pcr test result from the user
     * Arguments: None
     * Returns: patient pcr test result as a boolean. Negative==false, Positive==true.
    */  
    public boolean promptPcr(){
            System.out.println("Please enter the PCR test result as either 'negative' or 'positive'");
            return isNegPos();         
    }
    /* Name: promptAntigen
     * Description: obtain a valid patient antigen test result from the user
     * Arguments: None
     * Returns: antigen test result as boolean
    */ 
     public boolean promptAntigen(){
        System.out.println("Please enter the Antigen test result as either 'negative' or 'positive'");
        return isNegPos();
     }

    /* Name: promptPregnant
     * Description: aquire a pregnancy test result from the user
     * Arguments: None
     * Returns: a valid test result as a boolean. Negative==false, Positive==true.
     */
    public boolean promptPregnant(){
            System.out.println("Please enter the patients pregancy results as either 'negative' or 'positive'");   
            return isNegPos();              
    }

    /* Name: isNegPos
     * Description: obtain a valid test result from the user as either negative or positive
     * Arguments: None
     * Returns: a valid test result as a boolean. Negative==false, Positive==true.
     */
    private boolean isNegPos(){
       String result="N/A";
       do{
           //Prompt user for input
            result=input.nextLine();
            if (result.matches("(.*)negative(.*)")){ return false; } //Translate 'negative' to false
            else if (result.matches("(.*)positive(.*)")){ return true;} //Translate 'positive' to true
            System.out.println( "Please enter a valid test result (negative/positive)");
       } while(!result.equals("positive") && !result.equals("negative")); //The result can only be 'negative' or 'positive'
       return false;
    }

    /* Name: indexOfId
     * Description: Returns the current index of a patient identified by its id in the patients array list
     * Arguments: None
     * Returns: The index that the patient with the corresponding id is located at in the patient array list
    */   
    private int indexOfId(ArrayList<Patient> patients, int id){
        for(int x=0;x<patients.size();x++){ //Check each patient in the array list, patients
            if((int)patients.get(x).ID.get()==id){return x;}  //return the index if found
        }
        return -1; //else, return -1 for not found
    }
}
