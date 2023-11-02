/* Name: Anna Andler
 * Class: SE 450
 * Date: 10/3/2023
 * File Name: Triage.java
 * Description: Responsible for triaging a patient. Currently involves admitting, submitting tests, and discharging, printing, and doing rounds on patients.
 */


package edu.depaul;
import java.util.ArrayList;

//INTERFACE DEFINITIONS for triaging patients
interface Admit { public void admit(); }
interface Discharge { public void discharge(); }
interface Print { public void print(); }
interface Rounds { public void doRounds(); }
interface SubmitAntigen { public void submitAntigen(); }
interface SubmitPCR { public void submitPCR(); }

public class Triage implements Admit,Discharge,Print,Rounds,SubmitAntigen,SubmitPCR{
     //Initialize scanner object to parse system input
     private ArrayList<Patient> patients=new ArrayList<Patient>();
     private static Prompter prompter = new Prompter();

    /* Name: admit
     * Description: asks what the result of the patient PCR test is and determine patient type
     * Arguments: None
     * Returns: None
     */
    public void admit(){
        System.out.println("You have chosen the Admit Function");

        CreatePatient createPatient=new CreatePatient(patients); //Create a new patient object with user input
        
        //Aquire patient test results 
        boolean pcr=prompter.promptPcr();
        boolean antigen=prompter.promptAntigen();

        Patient patient;

        //Create a covid19 patient with positive test result    
        if(pcr){patient=createPatient.newCovid19Patient();} 
        //negative test result: 
        else {
            if(antigen){patient=createPatient.newDiseaseXPatient();} //Create a disease x patient with positive test result
            else {patient=createPatient.newRegularPatient();} //Create a regular patient with negative test result
        }
        //Add admited patient to the array list of patients
        patients.add(patient); 
        System.out.println("Patient Added Successfully");  
    }

    /* Name: print
     * Description: prints all patient information
     * Arguments: a single patient object
     * Returns: None - system output only
     */
    public void print(){
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
    public void submitPCR(){
        //Make sure there is at least one patient before submitting a pcr test
       if(patients.size()<1){System.out.println("Please admit a patient first."); return;}

       System.out.println("You have chosen the PCR Test Function");

       //Get base information from user regarding the patient to update
       int index=prompter.findId(patients);       //Prompt for ID of patient to submit PCR test for
       boolean result=prompter.promptPcr();         //Prompt for PCR test result of patient to submit


       //Handle patient based on PCR test result:
        if (result){ //positive PCR test result
            System.out.println("Now changing patient to Covid19 Patient");

            TransferPatient transferer = new TransferPatient(patients,index);
            Patient patient=transferer.transfer2Covid(patients);             //Change patient to covid19 patient 
            this.transfer(patient,index);                                   //Update Array List

        }else{ //Negative PCR test result:
            if(patients.get(index) instanceof Covid19Patient){ //If the patient was formerly a covid patient, discharge them. 
                System.out.println("Now discharging former Covid19 Patient");
                patients.remove(index);
            }
        }
       System.out.println("Successfully submitted the PCR test and updated patient status");
    }

    /* Name: submitAntigen
     * Description: prompts for patient id and will update the antigen test value for that patient
     * Arguments: None
     * Returns: None
    */
    public void submitAntigen(){
        //Make sure there is at least one patient before submitting a pcr test
       if(patients.size()<1){System.out.println("Please admit a patient first."); return;}

       System.out.println("You have chosen the Antigen Test Function");

       //Get base information from user regarding the patient to update
       int index=prompter.findId(patients);       //Prompt for ID of patient to submit PCR test for
       boolean result=prompter.promptAntigen();         //Prompt for Antigen test result of patient to submit


       //Handle patient based on antigen test result:
        if (result){ //positive antigen test result
            System.out.println("Now changing patient to a DiseaseX Patient");

            TransferPatient transferer = new TransferPatient(patients,index);
            //Change patient to covid19 patient 
            Patient patient=transferer.transfer2X(patients);
            this.transfer(patient,index);

        }else{ //negative antigen test result
            if(patients.get(index) instanceof DiseaseXPatient){ //If the patient was formerly a diseasex patient, discharge them. 
                System.out.println("Now discharging former DiseaseX Patient");
                patients.remove(index);
            }
        }
       System.out.println("Successfully submitted the Antigen test and updated patient status");
    }
    
    /* Name: doRounds
     * Description: perform patient rounds by taking covid19 patient temperatures and doing all patient treatment plans
     * Arguments: None
     * Returns: None
    */
    public void doRounds(){
       //Make sure there is at least one patient before doing patient rounds
       if(patients.size()<1){System.out.println("Please admit a patient first."); return;}

       System.out.println("You have chosen the Rounds Function");
       System.out.println("Treating all patients in the hospital...");

       //For each patient admitted to the hospital: 
        for(Patient patient : patients){
            if(patient instanceof Covid19Patient){ //update their temperature if the patient is a covid19 patient
                Covid19Patient covid=(Covid19Patient)patient;
                covid.setTemp(prompter.promptTemperature());
            }
            //"Treat" and Output the patient treatment plan.
            System.out.println("Treatment plan for patient with ID "+patient.ID.get()+" is " + patient.treat());
        }
        System.out.println("All "+patients.size()+" patients have been treated");
    }

    /* Name: transfer 
     * Description: update the patient list if a patient was transfered
     * Arguments: index of patient to discharge
     * Returns: None
    */
    private void transfer(Patient patient,int index){
        patients.remove(index);  
        patients.add(patient);
    }

    /* Name: discharge
     * Description: discharges a patient by removing them from the current patient list, only if their pcr AND antigen test is negative.
     * Arguments: None
     * Returns: None
    */
    public void discharge(){
        //Make sure there is at least one patient before deleting a patient
        if(patients.size()<1){System.out.println("Please admit a patient first."); return;}

        System.out.println("You have chosen the Discharge Function");

        int index=prompter.findId(patients);        //Prompt for patient ID

        //Discharge patient, only if they have a negative pcr test and negative antigen test
        if(patients.get(index) instanceof RegularPatient){patients.remove(index);}
        else { System.out.println("This patient needs a negative PCR and negative Antigen test in order to be discharged");}
    }

}
