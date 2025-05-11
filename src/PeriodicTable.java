/* 
CS 1027B - Assignment 1
Name: Ethan Gregory
Student Number: 251433485
Email: egregor4@uwo.ca
Created: January 26th, 2025
 */
public class PeriodicTable {
    private Element[][] mainTable;
    private Element[] lanthanides;
    private Element[] actinides; // INIT. GLOBAL VARS

    /**
     * A recreation of the Periodic Table. Requires a text file with data to
     * populate it.
     * 
     * @param filename - A {@link String} variable with the path to a text file with
     *                 the data to load in.
     * @return A replica of the Periodic Table.
     * @author Ethan Gregory
     */
    public PeriodicTable(String filename) {
        this.mainTable = new Element[7][18];
        this.lanthanides = new Element[15];
        this.actinides = new Element[15]; // INITALIZE THE VARIABLES

        loadData(filename);
    }

    /**
     * Loads data from the {@link String} {@code filename}, and populates the main
     * table, the lanthanides and actinides subcategories.
     * 
     * @param filename - A {@link String} variable with the path to a text file with
     *                 the data to load in.
     * @author Ethan Gregory
     */
    public void loadData(String filename) {
        TextFileReader tf = new TextFileReader(filename); // CREATE READER
        String line = ""; // CREATE LINE VARIABLE TO STORE DATA

        int maxLath = 0;
        int maxAnti = 0; // INIT. VAR TO TRACK CURRENT ARRAY SIZE

        int currentRow = 0; // USED FOR MAIN TABLE
        int currentColumn = 0;

        while (!tf.endOfFile()) { // READ THROUGH FILE
            line = tf.readString(); // GET LINE
            String[] lineData = line.split(","); // SPLIT TO REGEX

            int atomicNo; // INIT VARS. THAT WE TRY CATCH ON
            float atomicWt;
            int elementPeriod;

            try { // ATTEMPT TO GET ATOMIC NUMBER (CONTINUE TO NEXT LINE IF FAILED)
                atomicNo = Integer.parseInt(lineData[0].strip());
            } catch (Exception e) {
                continue;
            } // END OF TRY CATCH

            String elementName = lineData[1].strip(); // GET DATA
            String elementSymbol = lineData[2].strip();

            try { // REPEAT OUR TRY CATCH GUARD FOR WEIGHT
                atomicWt = Float.parseFloat(lineData[3].strip());
            } catch (Exception e) {
                continue;
            } // END OF TRY CATCH

            // STATE CHECK
            String elementState = lineData[9].strip();

            // METAL TYPE CHECK
            String metalType = ""; // INITIALIZE STRING
            if (lineData[12].contains("yes")) {
                metalType = "Metal";
            } else if (lineData[13].contains("yes")) {
                metalType = "Nonmetal";
            } else if (lineData[14].contains("yes")) {
                metalType = "Metalloid";
            } // END OF IF ELSE STATEMENT

            // CREATE ELEMENT AND ADD TO NECESSARY ARRAYS, IF POSSIBLE
            Element currentElement = new Element(atomicNo, atomicWt, elementSymbol, elementName, elementState,
                    metalType);

            try {
                elementPeriod = Integer.parseInt(lineData[7]); // GET GROUP NUMBER
            } catch (Exception e) {
                continue; // RESET ON NEXT LINE IF BAD INPUT
            } // END OF TRY CATCH

            String elementMetal = lineData[15].strip(); // GUARD AGAINST FIRST 2 ELEMENTS IN LANTHANIDES & ACTINIDES.

            if (elementPeriod == 6 && elementMetal.equals("Lanthanide")) { // PUT IN NECESSARY ARRAYS DEPENDING ON GROUP
                this.lanthanides[maxLath] = currentElement;
                maxLath++; // ADD TO ARRAY AND INCREASE CURRENT SPOT

            } else if (elementPeriod == 7 && elementMetal.equals("Actinide")) {
                this.actinides[maxAnti] = currentElement;
                maxAnti++; // ADD TO ARRAY AND INCREASE CURRENT SPOT

            } else { // IF NEITHER, ADD TO MAIN TABLE
                this.mainTable[currentRow][currentColumn] = currentElement;
                currentColumn++;
            } // END OF IF/ELSE STATEMENT

            // IF-ELSE CHAIN TO INSERT NULL (FORMATTING)
            if (currentElement.getAtomicNo() == 1) { // FIRST LINE (H & He)
                for (int i = 0; i < (this.mainTable[currentRow].length - 2); i++) {
                    this.mainTable[currentRow][currentColumn] = null;
                    currentColumn++;
                } // END OF FOR LOOP
            } else if (currentElement.getAtomicNo() == 4 || currentElement.getAtomicNo() == 12) { // LINES 2 & 3 (Be &
                                                                                                  // Mg)
                for (int i = 0; i < (this.mainTable[currentRow].length - 8); i++) {
                    this.mainTable[currentRow][currentColumn] = null;
                    currentColumn++;
                } // END OF FOR LOOP
            } else if (currentElement.getAtomicNo() == 56 || currentElement.getAtomicNo() == 103) { // LINES 6 & 7 (Ba &
                                                                                                    // Rf)
                this.mainTable[currentRow][currentColumn] = null;
                currentColumn++;
            }

            if (currentColumn == mainTable[currentRow].length) { // CHECK IF MAIN TABLE IF FULL, MOVE TO NEXT LINE IF SO
                currentColumn = 0;
                currentRow++;
            }

        } // END OF WHILE LOOP

        tf.close(); // CLOSE READER

    } // END OF LOAD DATA

    public String toString() {
        String outString = "";

        for (int i = 0; i < mainTable.length; i++) {
            for (int j = 0; j < mainTable[i].length; j++) {
                if (mainTable[i][j] != null) {
                    outString += String.format("%4s", mainTable[i][j].getSymbol());
                } else {
                    outString += String.format("%4s", "");
                }

            }
            outString += "\n"; // DOUBLE LINE BREAK
        }

        outString += "\n"; // DOUBLE BLANK LINE

        outString += String.format("%12s", ""); // INDENT ON ARRAY LINE
        for (int index = 0; index < lanthanides.length; index++) { // LATHINIDES
            outString += String.format("%4s", lanthanides[index].getSymbol());
        }

        outString += "\n"; // LINE BREAK

        outString += String.format("%12s", ""); // INDENT ON ARRAY LINE
        for (int index = 0; index < actinides.length; index++) {
            outString += String.format("%4s", actinides[index].getSymbol());
        }
        return outString; // RETURN OUTPUT
    }

    /**
     * Returns an Object of type {@link Element} corresponding to its Symbol.
     * @param sym - A String representation of an Element's Symbol.
     * @return an {@link Element} object if found, null if not.
     * @author Ethan Gregory
     */
    public Element getElement(String sym) {
        for (int i = 0; i < mainTable.length; i++) { // CHECK MAIN TABLE FIRST
            for (int j = 0; j < mainTable[i].length; j++) {
                if (mainTable[i][j] != null) { // GUARD AGAINST NULL
                    if (mainTable[i][j].getSymbol().equals(sym)) { // COMPARE TO PROPER
                        return mainTable[i][j];
                    } // END OF IF STATEMENT
                } // END OF IF STATEMENT
            } // END OF FOR LOOP
        } // END OF FOR LOOP

        // FAILING THAT, CHECK THE OTHER TABLES
        for (int i = 0; i < actinides.length; i++) {
            if (actinides[i] != null) { // GUARD
                if (actinides[i].getSymbol().equals(sym)) {
                    return actinides[i];
                } // END OF IF STATEMENT
            } // END OF IF STATEMENT
        } // END OF FOR LOOP

        for (int i = 0; i < lanthanides.length; i++) { // ANOTHER TABLE CHECK
            if (lanthanides[i] != null) { // GUARD
                if (lanthanides[i].getSymbol().equals(sym)) {
                    return lanthanides[i];
                } // END OF IF STATEMENT
            } // END OF IF STATEMENT

        } // END OF FOR LOOP

        return null; // RETURN NULL IF FALSE
    } // END OF getElement()

    /**
     * Returns an array of Elements in the corresponding Period
     * @param per - Int Variable of what Period to look within
     * @return An array of type {@link Element} if per is valid, null if not
     * @author Ethan Gregory
     */
    public Element[] getPeriod(int per) {
        if (per <= 0 || per >= 8) { // GUARD AGAINST INVALID NUMBERS
            return null;
        } // END OF IF STATEMENT

        int maxLength = 0; // INT TO DETERMINE LENGTH OF RETURNED ARRAY
        int currLength = 0; // INT TO DETERMINE CURRENT AMOUNT OF ELEMENTS IN ARRAY

        for (int i = 0; i < mainTable[per-1].length; i++) { // GET LENGTH OF PERIOD (WITHOUT NULL)
            if (mainTable[per - 1][i] instanceof Element) { 
                maxLength++;
            } // END OF IF STATEMENT
        } // END OF FOR LOOP

        Element[] elementsInPeriod = new Element[maxLength]; // INIT. ARRAY

        for (int i = 0; i < mainTable[per - 1].length; i++) { // LOOP THROUGH PERIOD
            if (mainTable[per - 1][i] instanceof Element) {
                elementsInPeriod[currLength] = mainTable[per - 1][i]; // ADD IF TRUE, UP CURRENT LENGTH
                currLength++;
            } // END OF IF STATEMENT
        } // END OF FOR LOOP

        return elementsInPeriod;
    } // END OF getPeriod()

    /**
     * Returns an array of Elements in the corresponding Group
     * @param grp - Int Variable of what Group to look within
     * @return An array of type {@link Element} if grp is valid, null if not
     * @author Ethan Gregory
     */
    public Element[] getGroup(int grp) {
        if (grp <= 0 || grp >= 19) { // GUARD AGAINST INVALID NUMBERS
            return null;
        } // END OF IF STATEMENT

        int maxLength = 0; // INT TO DETERMINE LENGTH OF RETURNED ARRAY
        int currLength = 0; // INT TO DETERMINE CURRENT AMOUNT OF ELEMENTS IN ARRAY

        for (int i = 0; i < mainTable.length; i++) { // GET LENGTH OF PERIOD (WITHOUT NULL)
            if (mainTable[i][grp-1] instanceof Element) { 
                maxLength++;
            } // END OF IF STATEMENT
        } // END OF FOR LOOP

        Element[] elementsInPeriod = new Element[maxLength]; // INIT. ARRAY

        for (int i = 0; i < mainTable.length; i++) { // LOOP THROUGH PERIOD
            if (mainTable[i][grp - 1] instanceof Element) {
                elementsInPeriod[currLength] = mainTable[i][grp - 1]; // ADD IF TRUE, UP CURRENT LENGTH
                currLength++;
            } // END OF IF STATEMENT
        } // END OF FOR LOOP

        return elementsInPeriod;
    } // END OF getPeriod()

    /**
     * Returns an Array of all Lanthanides in an Periodic Table
     * 
     * @return An array of all Lanthanides of type {@link Element}
     * @author Ethan Gregory
     */
    public Element[] getLanthanides() {
        return this.lanthanides;
    } // END OF getLanthanides()

    /**
     * Returns an Array of all Actinides in an Periodic Table
     * 
     * @return An array of all Actinides of type {@link Element}
     * @author Ethan Gregory
     */
    public Element[] getActinides() {
        return this.actinides;
    } // END OF getActinides()

}
