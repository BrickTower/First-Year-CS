/* 
CS 1027B - Assignment 1
Name: Ethan Gregory
Student Number: 251433485
Email: egregor4@uwo.ca
Created: February 3rd, 2025
 */
public class Compound {
    private int[] elementCount;
    private Element[] elements; // INIT. GLOBAL VARS.

    /**
     * A basic recreation of an elemental compound.
     * 
     * @param table         - An initialized {@link PeriodicTable} variable.
     * @param compoundArray - A 2D Array of type {@link String} that contains a list
     *                      of valid Elements' Symbols, and potentially a count of
     *                      how many there are. If the latter cannot be found, it
     *                      will default to 1.
     * @return A Compound Object.
     * @author Ethan Gregory
     */
    public Compound(PeriodicTable table, String[][] compoundArray) {
        final int defaultValue = 1;

        int currentElement = 0;
        this.elementCount = new int[compoundArray.length]; // INIT. VARS
        this.elements = new Element[compoundArray.length];

        for (int i = 0; i < compoundArray.length; i++) {
            for (int j = 0; j < compoundArray[i].length; j++) {
                if (table.getElement(compoundArray[i][j]) != null) {
                    elements[currentElement] = table.getElement(compoundArray[i][j]);
                } else { // IF NOT, ATTEMPT TO CONVERT THE CORRESPONDING ELEMENT'S COUNT, IF POSSIBLE, TO
                         // AN INTEGER
                    try {
                        int atomCount = Integer.parseInt(compoundArray[i][j]);
                        if (atomCount > 0) { // GUARD AGAINST 0 AND NEGATIVE NUMBERS
                            elementCount[currentElement] = atomCount;
                        } else {
                            elementCount[currentElement] = defaultValue; // APPLY DEFAULT VALUE IF LESS THAN 1
                        } // END OF IF ELSE
                    } catch (Exception e) {
                        elementCount[currentElement] = defaultValue; // FORCE SET TO 1 IF NOT POSSIBLE
                    } // END OF TRY CATCH
                } // END OF IF ELSE
            } // END OF FOR LOOP
            currentElement++; // UP CURRENT VALUE FOR NEXT ELEMENT
        } // END OF FOR LOOP

    }

    /**
     * Checks a 2-element Compound for its metal types.
     * @return A {@link String} representation of its bond type. Null if Compound has more than 2 elements, or if both elements are metal.
     * @author Ethan Gregory
     */
    public String getBondType() {
        if (elements.length != 2) { // RETURN NULL IF NOT EXACTLY 2 ELEMENTS
            return null;
        } // END OF IF STATEMENT

        // MASSIVE IF ELSE CHAIN TO CHECK FOR RULES
        if (elements[0].getType() == "Metal" && elements[1].getType() == "Nonmetal") {
            return "ionic";
        } else if (elements[0].getType() == "Metal" && elements[1].getType() == "Metalloid") {
            return "covalent";
        } else if (elements[0].getType() != "Metal" && elements[1].getType() != "Metal") {
            return "covalent";
        } else if (elements[1].getType() == "Metal" && elements[0].getType() == "Nonmetal") {
            return "ionic";
        } else if (elements[1].getType() == "Metal" && elements[0].getType() == "Metalloid") {
            return "covalent";
        } else if (elements[1].getType() != "Metal" && elements[0].getType() != "Metal") {
            return "covalent";
        } else {
            return null;
        } // END OF IF ELSE CHAIN
    } // END OF getBondType()

    public String toString() {
        String outString = ""; // INIT. BLANK STRING

        for (int i = 0; i < elements.length; i++) {
            outString += elements[i].getName() + ": " + elementCount[i];
            if (i != (elements.length - 1)) { // NEW LINE FOR EACH ELEMENT
                outString += "\n";
            } // END OF IF STATEMENT
        } // END OF FOR LOOP

        return outString;
    } // END OF toString()
}
