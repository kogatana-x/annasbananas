/* Name: Anna Andler
 * Class: SE 450
 * Date: 10/3/2023
 * File Name: CreatePatient.java
 * Description: Creates a new patient object based on user input
 */

package edu.depaul;

import java.util.ArrayList;

//Interfaces the CreatePatient Program Uses
interface NewDiseaseXPatient{ public Patient newDiseaseXPatient(); }
interface NewRegularPatient{ public Patient newRegularPatient(); }
interface NewCovid19Patient{ public Patient newCovid19Patient(); } 

public class CreatePatient implements NewDiseaseXPatient, NewRegularPatient, NewCovid19Patient{
    private int id, age;
    private String fName,lName;
    private String[] name;
    private Prompter prompter = new Prompter();

    public CreatePatient(ArrayList<Patient> patients) {      
        //Prompt for patient name. First and Last must be contained in the same string. 
        name=prompter.promptPatientName();
        fName=name[0];
        lName=name[name.length-1];

        //Prompt for patient ID
        id=prompter.promptId(patients);
        //Prompt for patient age
        age=prompter.promptAge();
 
    }

    /* Name: newDiseaseXPatient
     * Description: Creates a new DiseaseXPatient object with the required fields
     * Parameters: None
     * Returns: a new Patient
     */
    public Patient newDiseaseXPatient(){
        boolean isPregnant=prompter.promptPregnant(); //Prompt for pregnancy status. True==pregnant, False==not pregnant.
        Patient patient = new DiseaseXPatient(id, fName, lName, age, isPregnant); //Create a disease x patient with positive antigen test result
        return patient;
    }

    /* Name: newRegularPatient
     * Description: Creates a new Regular Patient object with the required fields
     * Parameters: None
     * Returns: a new Patient
     */
    public Patient newRegularPatient(){
        String mainSymptom=prompter.promptSymptom();
        return new RegularPatient(id, fName, lName, age, mainSymptom); //Create a regular patient with negative test results
    }

    /* Name: newCovid19Patient
     * Description: Creates a new Covid19Patient object with the required fields
     * Parameters: None
     * Returns: a new Patient
     */
    public Patient newCovid19Patient(){
        double temperature=prompter.promptTemperature(); //Prompt for temperature in celcius
        Patient patient =new Covid19Patient(id, fName, lName, age, temperature); //Create a covid19 patient with positive pcr test result
        return patient;
    }

}
