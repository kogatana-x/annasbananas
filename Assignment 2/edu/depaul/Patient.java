/* Name: Anna Andler
 * Class: SE 450
 * Date: 10/3/2023
 * File Name: Patient.java
 * Description: Defines a patient
 */
package edu.depaul;


public abstract class Patient implements Treatable, Printable {
    /* GLOBAL VARIABLE DECLARATION */
    public CreateObject ID=new CreateObject(0); //patient ID number. must be unique.
    public CreateObject FName=new CreateObject(""); //patient first name
    public CreateObject LName=new CreateObject(""); //patient last name
    public CreateObject Age=new CreateObject(0); //patient age (must be a whole number between 0-130)
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    //Default blank constructor for a new patient
    public Patient(){}

    //Initialize constructor with pre-set fields
    public Patient(int id, String fName, String lName, int age){
        this.ID.set(id);
        this.FName.set(fName);
        this.LName.set(lName);
        this.Age.set(age);
    }

    /* Name: treat
     * Description: Displays appropriate treatment plan for the patient
     * Arguments: None
     * Returns: medication as a string
     */
    abstract public String treat();


    /* Name: toString
     * Description: a String of currently set patient information
     * Arguments: None
     * Returns: a String 
     */
    abstract public String toString();


}
