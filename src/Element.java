/* 
CS 1027B - Assignment 1
Name: Ethan Gregory
Student Number: 251433485
Email: egregor4@uwo.ca
Created: January 23rd, 2025
 */
public class Element {
    private int atomicNo; // INIT. GLOBAL VARS
    private float atomicWeight;
    private String symbol;
    private String name;
    private String state;
    private String type;

    /**
     * Stores data pertaining to an Element on the Periodic Table.
     * 
     * 
     * @param num    - The Element's Atomic Number. (i.e. 6)
     * @param wt     - The Element's Atomic Weight. (i.e. 12.011)
     * @param symbol - Element's Symbol. (i.e. C)
     * @param name   - The Element's Name. (i.e. Carbon)
     * @param state  - The Element's State at room temp. (i.e Solid)
     * @param type   - The Element's type. (i.e. Nonmetalic)
     * 
     * @author Ethan Gregory
     */
    public Element(int num, float wt, String sym, String nm, String st, String ty) {
        this.atomicNo = num;
        this.atomicWeight = wt; // DEFINE EACH GLOBAL VAR. TO THE INPUT
        this.symbol = sym;
        this.name = nm;
        this.state = st;
        this.type = ty;
    } // END OF CONSTRUCTOR

    /**
     * Returns an Element's Atomic Number. (i.e. 6).
     * 
     * 
     * @return The Element's Atomic Number in {@code int} format.
     * @author Ethan Gregory
     */
    public int getAtomicNo() {
        return this.atomicNo;
    } // END OF getAtomicNo()

    /**
     * Returns an Element's Atomic Weight. (i.e. 12.011)
     * 
     * 
     * @return The Element's Atomic Weight in {@code float} format.
     * @author Ethan Gregory
     */
    public float getAtomicWeight() {
        return this.atomicWeight;
    } // END OF getAtomicWeight()

    /**
     * Returns an Element's Symbol. (i.e. C)
     * 
     * 
     * @return The Element's Symbol in {@code String} format.
     * @author Ethan Gregory
     */
    public String getSymbol() {
        return this.symbol;
    } // END OF getSymbol()

    /**
     * Returns an Element's Name. (i.e. Carbon)
     * 
     * 
     * @return The Element's Name in {@code String} format.
     * @author Ethan Gregory
     */
    public String getName() {
        return this.name;
    } // END OF getName()

    /**
     * Returns an Element's State. (i.e. Solid)
     * 
     * 
     * @return The Element's State in {@code String} format.
     * @author Ethan Gregory
     */
    public String getState() {
        return this.state;
    } // END OF getState()

    /**
     * Returns an Element's Type. (i.e. Nonmetalic)
     * 
     * 
     * @return The Element's Type in {@code String} format.
     * @author Ethan Gregory
     */
    public String getType() {
        return this.type;
    } // END OF getType()

    /**
     * Sets the Name of the current element to the specified String
     * 
     * 
     * @param newName - New Name for the Element, in String format
     * @author Ethan Gregory
     */
    public void setName(String newName) {
        this.name = newName;
    } // END OF setName()

    /**
     * Sets the State of the current element to the specified String
     * 
     * 
     * @param newState - New Name for the Element, in String format
     * @author Ethan Gregory
     */
    public void setState(String newState) {
        this.state = newState;
    } // END OF setState()

    /**
     * Sets the Type of the current element to the specified String
     * 
     * 
     * @param newType - New Name for the Element, in String format
     * @author Ethan Gregory
     */
    public void setType(String newType) {
        this.type = newType;
    } // END OF setType()

    // CUSTOM toString IMPLEMENTATION
    public String toString() {
        return this.getSymbol() + " (" + this.getName() + ")";
    } // END OF toString()

    /**
     * Checks if 2 Elements' Atomic Numbers match, and returns True or False
     * depending on the result.
     * 
     * @param other - Another Element to compare to
     * @return True if an Element's Atomic Number matches, False if not.
     * @author Ethan Gregory
     */
    public boolean equals(Element other) {
        if (this.getAtomicNo() == other.getAtomicNo()) { // IF ATOMIC NUMBER MATCH, IT'S TRUE.
            return true;
        } // END OF IF STATEMENT

        return false; // OTHERWISE, FALSE
    } // END OF equals()

} // END OF CLASS
