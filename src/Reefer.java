/* 
CS 1027B - Assignment 2
Name: Ethan Gregory
Student Number: 251433485
Email: egregor4@uwo.ca
Created: Feburary 10th, 2025
 */
public class Reefer extends TrainCar {
    private int temp; // PRIVATE VAR INIT.

    /**
     * A special type of {@link TrainCar} that has refrigeration on board, meant to
     * keep goods cold.
     * 
     * @param weight  - An int variable representing total weight of the goods
     * @param freight - An String variable representing the name of the goods in
     *                question
     * @param temp    - An int variable representing the current temperature in the
     *                car.
     * @author Ethan Gregory
     */
    public Reefer(int weight, int temp, String freight) {
        super(weight, freight);
        this.temp = temp;
    } // END OF CONSTRUCTOR

    /**
     * Gets the current Temperature of the Reefer Car.
     * 
     * @return the Temp, represented as an int variable
     * @author Ethan Gregory
     */
    public int getTemp() {
        return this.temp;
    } // END OF getTemp()

    /**
     * Sets the Temp of the Reefer.
     * 
     * @param newTemp - The new Temp of the TrainCar in type int.
     * @author Ethan Gregory
     */
    public void setTemp(int newTemp) {
        this.temp = newTemp;
    } // END OF setTemp()

    public String toString() { // STRING REP
        return String.format("<%s, %s, %sC>", this.getFreight(), this.getWeight(), this.getTemp());
    } // END OF toString()

    public boolean canConnect(TrainCar other) { // REEFER VERSION OF CANCONNECT
        if (super.canConnect(other)) { // IF CONNECTION ITSELF IS POSSIBLE, RETURN TRUE
            return true;
        } else if (other instanceof Reefer) { // IF NOT, CHECK TEMPEARTURE
            int thisTemp = this.getTemp();
            int otherTemp = ((Reefer) other).getTemp();
            int difference = thisTemp - otherTemp;

            if (difference <= 5 && difference >= -5) { // IF DIFFERENCE IS 5 OR LESS, LET THEM CONNECT
                return true;
            } // END OF IF STATEMENT

        } // END OF IF STATEMENT

        return false;
    } // END OF canConnect()

    public boolean equals(TrainCar other) { // REEFER VERSION OF EQUALS
        if (super.equals(other)) { // RUN TRAINCAR EQUALS FIRST
            return true;
        } else if (other instanceof Reefer) {
            if (this.getTemp() == ((Reefer) other).getTemp()) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Reefer reef = new Reefer(10, 5, "Meat");
        TrainCar tc = new TrainCar(10, "Meat");

        System.out.println(reef.equals(tc));
    }

}
