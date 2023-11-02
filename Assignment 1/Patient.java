/* Name: Anna Andler
 * Class: SE 450
 * Date: 9/21/2023
 * File Name: Patient.java
 */

package edu.depaul;
public class Patient {
    /* GLOBAL VARIABLE DECLARATION */
    public int id=0; //patient ID number. must be unique.
    public String fName=""; //patient first name
    public String lName=""; //patient last name
    public int age=0; //patient age (must be a whole number between 0-130)
    public boolean pcr; //status of pcr test (false==negative, true==positive)
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    //Default blank constructor for a new patient
    public Patient(){}

    //Initialize constructor with pre-set fields
    public Patient(int id, String fName, String lName, int age){
        this.id=id;
        this.fName=fName;
        this.lName=lName;
        this.age=age;
    }

    /* Name: getPcr
     * Description: return if the PCR test has been done or not
     * Arguments: None
     * Returns: pcr value as a boolean
     */
    public Boolean getPcr(){ return this.pcr; }

    /* Name: setPcr
     * Description: set the PCR test value to done or not done
     * Arguments: new pcr value to set 
     * Returns: None
     */
    public void setPcr(Boolean pcr){ this.pcr=pcr; }

    /* Name: getId
     * Description: return the currently set patient id
     * Arguments: None
     * Returns: patient's id as an integer
     */
    public int getId(){ return this.id; }

    /* Name: setId
     * Description: set patients id
     * Arguments: patient id as an integer
     * Returns: none
     */
    public void setId(int id){ this.id=id; }

    /* Name: getFName
     * Description: return patient's firstname
     * Arguments: None
     * Returns: first name as a string
     */
    public String getFName(){ return this.fName; }

    /* Name: setFName
     * Description: assigns patient's first name
     * Arguments: first name as a string
     * Returns:  None
     */
    public void setFName(String fName){ this.fName=fName; }

    /* Name: getLName
     * Description: returns the currently set last name of the patient
     * Arguments: None
     * Returns: last name as a string
     */
    public String getLName(){ return this.lName; }

    /* Name: setLName
     * Description: assigns patient's last name
     * Arguments: last name as a string
     * Returns: None
     */
    public void setLName(String lName){ this.lName=lName; }

    /* Name: getAge
     * Description: returns patient's age
     * Arguments: None
     * Returns: returns age as an integer
     */
    public int getAge(){ return this.age;}

    /* Name: setAge
     * Description:sets the patient's age
     * Arguments: age as an integer
     * Returns: None
     */
    public void setAge(int age){ this.age=age; }

    /* Name: treat
     * Description: Displays appropriate treatment plan for the patient
     * Arguments: None
     * Returns: medication as a string
     */
    public String treat(){ return "No treatment plan assigned to the patient"; }

    /* Name: pcrToString
     * Description: convert pcr test result boolean to a readable string
     * Arguments: None
     * Returns: pcr test result as positive or negative;
     */
    public String pcrToString(){
      //Convert PCR Test to String based on result
        if(this.pcr==false){ return "Negative"; }
        else if(this.pcr==true) { return "Positive"; }
        else { return "Not done"; }
    }
    /* Name: toString
     * Description: a String of currently set patient information
     * Arguments: None
     * Returns: a String 
     */
    public String toString(){
         return ("Id: "+this.id+"\nFull Name: "+this.fName+" "+this.lName+"\nAge: "+this.age+"\nPCR test result: "+pcrToString());
    }
}
