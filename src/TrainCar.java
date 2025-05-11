/* 
CS 1027B - Assignment 2
Name: Ethan Gregory
Student Number: 251433485
Email: egregor4@uwo.ca
Created: Feburary 10th, 2025
 */
public class TrainCar {
    private int weight; // INIT. PRIVATE VARS
    private String freight;

    /**
     * A TrainCar object represents a Car on a Train, each with its own freight type
     * and corresponding weight of goods.
     * 
     * @param weight  - An int variable representing total weight of the goods
     * @param freight - An String variable representing the name of the goods in
     *                question
     * @author Ethan Gregory
     */
    public TrainCar(int weight, String freight) {
        this.weight = weight; // ASSIGN TO CONSTRUCTOR
        this.freight = freight;
    }

    /**
     * Gets the current Weight of the TrainCar
     * 
     * @return the Weight of the TrainCar in type int.
     * @author Ethan Gregory
     */
    public int getWeight() {
        return this.weight;
    }

    /**
     * Gets the current Freight of the TrainCar
     * 
     * @return the Freight of the TrainCar in type String.
     * @author Ethan Gregory
     */
    public String getFreight() {
        return this.freight;
    }

    /**
     * Sets the Weight of the TrainCar.
     * 
     * @param newWeight - The new Weight of the TrainCar in type int.
     * @author Ethan Gregory
     */
    public void setWeight(int newWeight) {
        this.weight = newWeight;
    }

    /**
     * Sets the Freight of the TrainCar.
     * 
     * @param newWeight - The new Freight of the TrainCar in type String.
     * @author Ethan Gregory
     */
    public void setFreight(String newFreight) {
        this.freight = newFreight;
    }

    public String toString() { // RETURN STRING VERSION
        return String.format("<%s, %s>", this.getFreight(), this.getWeight());
    }

    /**
     * Checks to see if two TrainCars can safely connect. They can connect if either
     * their Weight or Freight are the same.
     * 
     * @param other - The TrainCar to compare it to.
     * @return True if connection is possible, False if not.
     * @author Ethan Gregory
     */
    public boolean canConnect(TrainCar other) {
        if (this.getWeight() == other.getWeight()
                || this.getFreight().toLowerCase().equals(other.getFreight().toLowerCase())) {
            return true;
        } // END OF IF STATEMENT
        return false;
    }

    /**
     * Sees if 2 TrainCar objects are equal to each other.
     * 
     * @param other - A TrainCar object that preferably should have the same weight,
     *              and freight type.
     * @return True if possible, False if not.
     * @author Ethan Gregory
     */
    public boolean equals(TrainCar other) {
        if (this instanceof TrainCar && other instanceof TrainCar) { // GUARD AGAINST REEFERS
            if (this instanceof Reefer == false && other instanceof Reefer == false) {
                if (this.getWeight() == other.getWeight()
                        && this.getFreight().toLowerCase().equals(other.getFreight().toLowerCase())) {
                    return true;
                } // END OF IF STATEMENT
            }

        } // END OF IF STATEMENT
        return false;
    }

}
