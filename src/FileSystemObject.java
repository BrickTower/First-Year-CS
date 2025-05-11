/* 
 * CS 1027B - Assignment 4
 * Name: Ethan Gregory
 * Student Number: 251433485
 * Email: egregor4@uwo.ca
 * Created: March 24th, 2025
 */

import java.util.Iterator; // IMPORT ITERATOR

public class FileSystemObject implements Comparable<FileSystemObject> {
    private String name;
    OrderedListADT<FileSystemObject> children;
    FileSystemObject parent;
    int id;

    public FileSystemObject(String name, int id) {
        this.name = name;
        this.id = id;
        if (!this.isFile()) { // INIT. CHILDREN IF NOT FILE
            this.children = new ArrayOrderedList<FileSystemObject>();
        }
    } // END OF CONSTRUCTOR

    private int getSize(FileSystemObject rtNode) { // GET THE SIZE 
        if (rtNode.isFile()) { // IF FILE, RETURN SIZE (BASE CASE)
            return ((ComputerFile) (rtNode)).size(); // RETURN SIZE OF COMPUTER FILE
        }

        Iterator<FileSystemObject> nodeChildren = rtNode.children.iterator();
        FileSystemObject curr; // INIT. VARS
        int result = 0;

        while (nodeChildren.hasNext()) {
            
            if (this != null) { // NULL GUARD
                curr = nodeChildren.next(); // GET NEXT

                result += getSize(curr); // DO IT FOR ALL SUBSEQUENT CHILDREN
            }
        }

        return result;
    }

    public int size() {

        if (this.isFile()) { // GUARD IN CASE COMPUTERFILE.JAVA'S SIZE FUNCTION DOESN'T WORK FOR SOME REASON.
            return ((ComputerFile) (this)).size(); // RETURN SIZE OF COMPUTER FILE
        } else {
            return getSize(this);
        }
    };

    private boolean childNameExists(FileSystemObject node) {
        Iterator<FileSystemObject> nodeChildren = this.children.iterator();

        while (nodeChildren.hasNext()) {
            FileSystemObject current = nodeChildren.next();

            if (current.getName().equals(node.getName())) { // IF NAME MATCHES
                return true;
            } // END OF IF STATEMENT
        } // END OF WHILE LOOP

        return false;
    }

    public void addChild(FileSystemObject node) {
        if (this.isFile()) { // GUARD AGAINST FILE
            throw new DirectoryTreeException("Can't add file to another file.");
        }

        if (childNameExists(node)) { // GUARD AGAINST COMMON NAME
            throw new DirectoryTreeException("File name already exists.");
        }

        // ADD TO CHILDREN IF IT PASSES THE GUARDS
        this.children.add(node);
        node.setParent(this);

    }

    public boolean isFile() { // CHECK IF FILE OR NOT
        if (this instanceof ComputerFile) { // IF INSTANCE OF ComputerFile.java, RETURN TRUE
            return true;
        }
        return false; // FALSE OTHERWISE
    } // END OF isFile()

    public String toString() { // RETURN NAME
        return this.name;
    }

    // GETTERS AND SETTERS
    public String getName() { // AVERAGE GETTER
        return this.name;
    }

    public void setName(String name) { // AVERAGE SETTER
        this.name = name;
    }

    public void setParent(FileSystemObject parent) { // AVERAGE SETTER
        this.parent = parent;
    }

    public FileSystemObject getParent() { // AVERAGE GETTER
        return this.parent;
    } // END OF

    public void setID(int id) { // AVERAGE SETTER
        this.id = id;
    }

    public int getID() { // AVERAGE GETTER
        return this.id;
    }

    public OrderedListADT<FileSystemObject> getChildren() { // AVERAGE GETTER
        return this.children;
    }

    public int compareTo(FileSystemObject o) {
        if (this.isFile() && !o.isFile()) { // FILE / FOLDER CHECK
            return 1;
        } else if (!this.isFile() && o.isFile()) {
            return -1;
        }

        String thisName =  this.getName().toLowerCase();
        String otherName = o.getName().toLowerCase(); // GET NAMES
        
        return thisName.compareTo(otherName); // RETURN INT VALUE USING STRING'S VERSION OF COMPARE TO
    }

    public static void main(String[] args) {
        FileSystemObject root = new FileSystemObject("C:", 0);
        FileSystemObject node1 = new FileSystemObject("Folder 1", 1);
        ComputerFile node2 = new ComputerFile("My File", 2, 10);
        ComputerFile node3 = new ComputerFile("My Other File", 3, 15);
        root.addChild(node1);
        node1.addChild(node2);
        node1.addChild(node3);

        System.out.println(node2.size());
        System.out.println(root.size());

    }

}
