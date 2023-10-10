/* Name: Anna Andler
 * Class: SE 450
 * Date: 10/3/2023
 * File Name: Covid19Patient.java
 * Description: Contains information specific to COVID19Patients
 */

package edu.depaul;

interface Temperature { public void setTemp(double temperature);}

public class Covid19Patient extends Patient implements Temperature{
    /* GLOBAL VARIABLE DECLARATION */
    private CreateObject Temperature=new CreateObject(0.0); //patient temperature in degrees celcius
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    //Constructor for Covid19Patient. Includes temperature
    public Covid19Patient(int id, String fName, String lName, int age, double temperature){
        super.ID.set(id);
        super.FName.set(fName);
        super.LName.set(lName);
        super.Age.set(age);
        this.Temperature.set(temperature);
    }

    /* Name: setTemp
     * Description:assigns a temperature associated with the current patient
     * Arguments: temperature as a double
     * Returns: None
     */
    public void setTemp(double temperature){ this.Temperature.set(temperature); }

    /* Name: treat
     * Description: Displays appropriate treatment plan for the patient
     * Arguments: None
     * Returns: medication plan as a string
     */
    @Override
    public String treat(){ 
        if((Integer)super.Age.get()>59&&(Double)this.Temperature.get()>36.5){ return "Paxlovid"; } // return Paxlovid for patients over 59 who have a fever 
        if((Double)this.Temperature.get()>40){ return "Dexamethasone"; }  // Any patient who has a temperature over 40, regardless of age, should be receiving Dexamethasone
        else { return "Fluids and Acetaminophen"; } //otherwise, it will be fluids and Acetaminophen
    }

    /* Name: toString
     * Description: a String of currently set patient information
     * Arguments: None
     * Returns: a String 
     */
    @Override
    public String toString(){
        return ("Id: "+super.ID.get()+"\nFull Name: "+super.FName.get()+" "+super.LName.get()+"\nAge: "+super.Age.get()+"\nTemperature: "+this.Temperature.get()+"\nPCR test result: positive"+"\nTreatment: "+this.treat());
    }

}
