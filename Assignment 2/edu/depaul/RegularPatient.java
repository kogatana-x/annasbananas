/* Name: Anna Andler
 * Class: SE 450
 * Date: 10/3/2023
 * File Name: RegularPatient.java
 * Description: Defines a regular patient
 */

package edu.depaul;

public class RegularPatient extends Patient {
    /* GLOBAL VARIABLE DECLARATION */
    private CreateObject MainSymptom=new CreateObject(""); //patient main symptom
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public RegularPatient(int id, String fName, String lName, int age, String mainSymptom){
        super.ID.set(id);
        super.FName.set(fName);
        super.LName.set(lName);
        super.Age.set(age);
        this.MainSymptom.set(mainSymptom);
    }
    /* Name: treat
     * Description: Displays appropriate treatment plan for the patient
     * Arguments: None
     * Returns: medication plan as a string
     */
    @Override
    public String treat(){ 
        String mainSymptom=(String)this.MainSymptom.get();
        if(mainSymptom.matches("(.*)hypertension(.*)")){ return "ACE inhibitors"; }
        else if(mainSymptom.matches("(.*)coughing(.*)")||mainSymptom.matches("(.*)runny nose(.*)")||mainSymptom.matches("(.*)stuffy nose(.*)")) { return "Amoxicillin"; }
        else { return "IV fluids"; }
    }

    /* Name: toString
     * Description: a String of currently set patient information
     * Arguments: None
     * Returns: a String 
     */
    @Override
    public String toString(){
        return ("Id: "+super.ID.get()+"\nFull Name: "+super.FName.get()+" "+super.LName.get()+"\nAge: "+super.Age.get()+"\nMain Symptom(s): "+this.MainSymptom.get()+"\nPCR test result: negative"+"\nAntigen test result: negative"+"\nTreatment: "+this.treat());
   }
    
}
