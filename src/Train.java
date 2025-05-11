/* 
CS 1027B - Assignment 2
Name: Ethan Gregory
Student Number: 251433485
Email: egregor4@uwo.ca
Created: Feburary 12th, 2025
 */
public class Train {
    private DoubleNode<TrainCar> caboose;
    private DoubleNode<TrainCar> locomotive; // INIT. PRIVATE VAR

    public Train() {
        this.caboose = null;
        this.locomotive = null;
    }

    /**
     * Gets the current Caboose of the Train.
     * 
     * @return the Caboose.
     * @author Ethan Gregory
     */
    public DoubleNode<TrainCar> getCaboose() {
        return this.caboose;
    }

    /**
     * Gets the current Locomotive of the Train.
     * 
     * @return the Locomotive.
     * @author Ethan Gregory
     */
    public DoubleNode<TrainCar> getLocomotive() {
        return this.locomotive;
    }

    /**
     * Adds a TrainCar to the Train if possible.
     * 
     * @param car - A valid {@link TrainCar} or {@link Reefer} Object
     * @throws TrainException - If it is not possible to connect the car to the
     *                        train. (See canConnect for more info.)
     * @author Ethan Gregory
     */
    public void addCar(TrainCar car) {
        boolean additionSuccess = false; // VAR. TO TRACK SUCCESSFUL ADDITION
        if (this.getCaboose() == null && this.getLocomotive() == null) { // IF TRAIN IS EMPTY, ADD CAR AND SET BOTH
                                                                         // VARIABLES TO
            // IT
            this.locomotive = new DoubleNode<TrainCar>(car); // SET LOCAMOTIVE
            this.getLocomotive().setNext(caboose);
            this.caboose = this.getLocomotive(); // SET CABOOSE

            additionSuccess = true;

        } else {
            DoubleNode<TrainCar> newAddition = new DoubleNode<TrainCar>(car); // CREATE NEW NODE
            DoubleNode<TrainCar> currCar = this.getCaboose();

            if (currCar.getElement().canConnect(car)) { // IF CAN CONNECT AT THE END

                currCar.setNext(newAddition); // GIVE NODE TO CABOOSE
                newAddition.setPrevious(currCar); // MAKE NODE CABOOSE
                this.caboose = newAddition;

                additionSuccess = true;

            } else {

                while (currCar != null) { // CODE FOR INTERIOR ADDING
                    TrainCar currElement = currCar.getElement();
                    if (currElement.canConnect(car)) {
                        DoubleNode<TrainCar> nextNode = currCar.getNext(); // TEMPORAILY STORE NEXT NODE

                        if (nextNode.getElement().canConnect(car)) {

                            newAddition.setNext(nextNode);
                            newAddition.setPrevious(currCar);

                            currCar.setNext(newAddition); // DOUBLE LINK IT
                            nextNode.setPrevious(newAddition);

                            additionSuccess = true;
                            break; // END LOOP IF SUCCESSFUL
                        } // END OF IF STATEMENT

                    } // END OF IF STATEMENT

                    currCar = currCar.getPrevious();
                } // END OF WHILE LOOP

            } // END OF IF / ELSE
        } // END OF IF / ELSE

        // IF EVERYTHING FAILS, THROW AN EXCEPTION
        if (!additionSuccess) {
            throw new TrainException("Could not add car.");
        } // END OF IF STATEMENT
    } // END OF addCar()

    /**
     * Boolean way of utilizing the addCar function.
     * 
     * @param other
     * @return True if operation was successful, False if not.
     * @author Ethan Gregory
     */
    public boolean tryAddCar(TrainCar other) {
        try {
            addCar(other);
            return true; // TRUE IF WORKS
        } catch (TrainException e) {
            return false; // FALSE IF NOT
        } // END OF TRY CATCH
    }

    /**
     * Removes a TrainCar from the Train if possible.
     * 
     * @param car - A valid {@link TrainCar} or {@link Reefer} Object
     * @throws TrainException - If it is not possible to remove the car to the
     *                        train. (See canConnect for more info.)
     * @author Ethan Gregory
     */
    public void removeCar(TrainCar car) {
        boolean removalSuccess = false;
        if (this.getCaboose() == null) { // IF TRAIN NULL
            throw new TrainException("Could not remove car.");
        }
        
        DoubleNode<TrainCar> currentNode = this.getCaboose(); // GET END OF CAR

        while (currentNode != null) { // LOOP THROUGH TRAIN
            if (currentNode.getElement().equals(car)) { // IF MATCHES INPUT
                // ONLY CAR
                if (this.getLocomotive() == this.getCaboose()) {
                    this.locomotive = null;
                    this.caboose = null;
                }

                if (currentNode != this.getLocomotive() && currentNode != this.getCaboose()) {
                    DoubleNode<TrainCar> nextNode = currentNode.getNext(); // TEMPORAILY STORE PREV & NEXT NODES
                    DoubleNode<TrainCar> prevNode = currentNode.getPrevious(); // TEMPORAILY STORE PREV & NEXT NODES

                    if (!prevNode.getElement().canConnect(nextNode.getElement())) {
                        throw new TrainException("Could not remove car.");
                    }
                }

                if (currentNode == this.getLocomotive()) {
                    this.locomotive = currentNode.getNext();
                    if (this.locomotive != null) {
                        this.getLocomotive().setPrevious(null);
                    }

                } else if (currentNode == this.getCaboose()) {
                    this.caboose = currentNode.getPrevious();
                    if (this.caboose != null) {
                        this.getCaboose().setNext(null);
                    }

                } else {
                    currentNode.getNext().setPrevious(currentNode.getPrevious());
                    currentNode.getPrevious().setNext(currentNode.getNext()); // RELINK THEM
                } // END OF IF ELSE
                currentNode = null; // SET CURRENT NODE TO NULL, TO SAVE ON MEMORY
                removalSuccess = true;
                break;

            }
            currentNode = currentNode.getPrevious();
        }

        // IF IT WASN'T POSSIBLE FOR WHATEVER REASON, THROW EXCEPTION
        if (!removalSuccess) {
            throw new TrainException("Could not remove car.");
        } // END OF IF STATEMENT
    }

    /**
     * Boolean way of utilizing the removeCar function.
     * 
     * @param other
     * @return True if operation was successful, False if not.
     * @author Ethan Gregory
     */
    public boolean tryRemoveCar(TrainCar other) {
        try {
            removeCar(other);
            return true;
        } catch (TrainException e) {
            return false;
        }
    }

    public String toString() {
        if (this.getCaboose() == null && this.getLocomotive() == null) { // IF TRAIN IS EMPTY, RETURN EMPTY STRING
            return "The train is empty.";
        } else {
            DoubleNode<TrainCar> currCar = this.getLocomotive(); // GET LOCOMOTIVE
            String trainString = ""; // INIT. STRING
            while (currCar != null) { // LOOP THROUGH
                trainString += currCar.getElement().toString();
                if (currCar.getNext() != null) {
                    trainString += ", ";
                }
                currCar = currCar.getNext();
            }

            return trainString;
        }
    } // END OF toString

    public static void main(String[] args) {
        Train t = new Train();
        t.removeCar(new TrainCar(1, "Test"));
    }
}
