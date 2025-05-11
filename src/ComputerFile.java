/* 
 * CS 1027B - Assignment 4
 * Name: Ethan Gregory
 * Student Number: 251433485
 * Email: egregor4@uwo.ca
 * Created: March 24th, 2025
 */
public class ComputerFile extends FileSystemObject {
    private int size;

    public ComputerFile(String name, int id, int size) { // CONSTRUCTOR
        super(name, id);
        this.size = size;
    }

    public int size() { // CUSTOM SIZE GETTER
        return this.size;
    }
    
}
