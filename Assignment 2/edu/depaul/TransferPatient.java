/* Name: Anna Andler
 * Class: SE 450
 * Date: 10/3/2023
 * File Name: TransferPatient.java
 * Description: "Transfers" patients to different wings based by changing their type
 */

package edu.depaul;

import java.util.ArrayList;

interface Transfer2Covid{ public Patient transfer2Covid(ArrayList<Patient> patients); }
interface Transfer2X{ public Patient transfer2X(ArrayList<Patient> patients);}

public class TransferPatient implements Transfer2Covid, Transfer2X{
    private int index;
    private Prompter prompter = new Prompter();

    public TransferPatient(ArrayList<Patient> patients, int index){ this.index=index; }

    public Patient transfer2Covid(ArrayList<Patient> patients){
        //Create new Covid19Patient by copying previous data into a new object
        double temperature=prompter.promptTemperature();
        Patient patient=patients.get(index);
        Patient NewPatient = new Covid19Patient((int)patient.ID.get(), (String)patient.FName.get(), (String)patient.LName.get(), (int)patient.Age.get(),temperature);
        return NewPatient;
    }
    public Patient transfer2X(ArrayList<Patient> patients){
        //Create new Covid19Patient by copying previous data into a new object
        boolean isPregnant=prompter.promptPregnant();
        Patient patient=patients.get(index);
        Patient NewPatient = new DiseaseXPatient((int)patient.ID.get(), (String)patient.FName.get(), (String)patient.LName.get(), (int)patient.Age.get(),isPregnant);
        return NewPatient;
    }
    
}
