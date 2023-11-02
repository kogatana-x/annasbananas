/* Name: Anna Andler
 * Class: SE 450
 * Date: 9/21/2023
 * File Name: RegularPatient.java
 */
package edu.depaul;
public class RegularPatient extends Patient {
    /* GLOBAL VARIABLE DECLARATION */
    private String mainSymptom=""; //patient main symptom
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


    public RegularPatient(int id, String fName, String lName, int age, String mainSymptom){
        super.id=id;
        super.fName=fName;
        super.lName=lName;
        super.age=age;
        this.mainSymptom=mainSymptom;
    }
    /* Name: treat
     * Description: Displays appropriate treatment plan for the patient
     * Arguments: None
     * Returns: medication plan as a string
     */
    public String treat(){ 
        if(mainSymptom.matches("(.*)hypertension(.*)")){ return "ACE inhibitors"; }
        else if(mainSymptom.matches("(.*)coughing(.*)")||mainSymptom.matches("(.*)runny nose(.*)")||mainSymptom.matches("(.*)stuffy nose(.*)")) { return "Amoxicillin"; }
        else { return "IV fluids"; }
    }

    /* Name: toString
     * Description: a String of currently set patient information
     * Arguments: None
     * Returns: a String 
     */
    public String toString(){
        return ("Id: "+super.id+"\nFull Name: "+super.fName+" "+super.lName+"\nAge: "+super.age+"\nMain Symptom: "+this.mainSymptom+"\nPCR test result: "+super.pcrToString()+"Treatment: "+this.treat());
   }
    
}
