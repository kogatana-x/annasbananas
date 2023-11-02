/* Name: Anna Andler
 * Class: SE 450
 * Date: 10/3/2023
 * File Name: DiseaseXPatient.java
 * Description: Contains information specific to Disease X Patients
 */
package edu.depaul;

interface Pregnant { public void setPregnant(boolean isPregnant);}

public class DiseaseXPatient extends Patient implements Pregnant{
    private CreateObject IsPregnant=new CreateObject();
    public DiseaseXPatient(int id, String fName, String lName, int age, boolean isPregnant){
        super.ID.set(id);
        super.FName.set(fName);
        super.LName.set(lName);
        super.Age.set(age);
        this.IsPregnant.set(isPregnant);
    }

    /* Name: setPregnant
     * Description: updates the value signifiying if a patient is pregnant or not
     * Arguments: true or false as a boolean
     * Returns: None
     */
    public void setPregnant(boolean isPregnant){ this.IsPregnant.set(isPregnant); }
    
    /* Name: treat
    * Description: Displays appropriate treatment plan for the patient
    * Arguments: None
    * Returns: medication plan as a string
    */
   @Override
   public String treat(){ 
        String treatment="";
        treatment="REGEN-COV antibody"; //all patients will receive this treatment
        if(!(Boolean)IsPregnant.get()){treatment.concat(" and Herapin");} //if the patient is not pregnant, they will also receive Herapin
        return treatment;
     }

   /* Name: toString
    * Description: a String of currently set patient information
    * Arguments: None
    * Returns: a String 
    */
    @Override
    public String toString(){
        return ("Id: "+super.ID.get()+"\nFull Name: "+super.FName.get()+" "+super.LName.get()+"\nAge: "+super.Age.get()+"\nIs the patient pregnant?: "+this.IsPregnant.get()+"\nPCR test result: negative"+"\nAntigen test result: positive"+"\nTreatment: "+this.treat());
    }
}