/* Name: Anna Andler
 * Class: SE 450
 * Date: 10/3/2023
 * File Name: CreateObject.java
 * Description: Generic object creator
 */
package edu.depaul;

public class CreateObject {
    private Object object;

    //Default blank constructor 
    public CreateObject(){}
    //Initialize constructor with pre-set fields
    public CreateObject(Object object){ this.object=object; }
    
    /* Name: set
     * Description: generic setter for object
     * Arguments: object to set
     * Returns: none
     */
    public void set(Object object){this.object=object;}

    /* Name: get
     * Description: generic getter for object
     * Arguments: none
     * Returns: generic object
     */
    public Object get(){return this.object;}
}
