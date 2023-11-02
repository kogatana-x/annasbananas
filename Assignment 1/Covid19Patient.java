/* Name: Anna Andler
 * Class: SE 450
 * Date: 9/21/2023
 * File Name: Covid19Patient.java
 */
package edu.depaul;
public class Covid19Patient extends Patient {
    /* GLOBAL VARIABLE DECLARATION */
    private double temperature=0; //patient temperature in degrees celcius
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    //Constructor for Covid19Patient. Includes temperature
    public Covid19Patient(int id, String fName, String lName, int age, double temperature){
        super.id=id;
        super.fName=fName;
        super.lName=lName;
        super.age=age;
        this.temperature=temperature;
    }

    /* Name: getTemp
     * Description:gets the patient's set temperature
     * Arguments: None
     * Returns: temperature as a double
     */
    public double getTemp(){ return this.temperature;  }

    /* Name: setTemp
     * Description:assigns a temperature associated with the current patient
     * Arguments: temperature as a double
     * Returns: None
     */
    public void setTemp(double temperature){ this.temperature=temperature; }

    /* Name: treat
     * Description: Displays appropriate treatment plan for the patient
     * Arguments: None
     * Returns: medication plan as a string
     */
    public String treat(){ 
        if(super.age>59&&this.temperature>36.5){ return "Paxlovid"; } // return Paxlovid for patients over 59 who have a fever 
        if(this.temperature>40){ return "Dexamethasone"; }  // Any patient who has a temperature over 40, regardless of age, should be receiving Dexamethasone
        else { return "Fluids and Acetaminophen"; } //otherwise, it will be fluids and Acetaminophen
    }

    /* Name: toString
     * Description: a String of currently set patient information
     * Arguments: None
     * Returns: a String 
     */
    public String toString(){
        return ("Id: "+super.id+"\nFull Name: "+super.fName+" "+super.lName+"\nAge: "+super.age+"\nTemperature: "+this.temperature+"\nPCR test result: "+super.pcrToString()+"Treatment: "+this.treat());
   }

}
