/* 
 * CS 1027B - Assignment 4
 * Name: Ethan Gregory
 * Student Number: 251433485
 * Email: egregor4@uwo.ca
 * Created: March 25th, 2025
 */

import java.util.Iterator;

public class DirectoryTree {
    FileSystemObject root;

    public DirectoryTree(FileSystemObject rt) {
        this.root = rt;
    }

    public FileSystemObject getRoot() {
        return this.root;
    }

    private FileSystemObject getAncestor(int targetLevel, FileSystemObject node) {
        FileSystemObject curr = node;

        if (curr != null) {
            if (curr.getParent() != null) {
                if (level(curr) == targetLevel) {
                    return curr;
                } else {
                    return getAncestor(targetLevel, curr.getParent());
                }
            }
        }

        return curr;

    }

    public FileSystemObject lca(FileSystemObject a, FileSystemObject b) {
        // INIT. VARS
        int aLevel = level(a);
        int bLevel = level(b);

        // CALC. LCA
        if (a == b) { // IF A & B ARE THE SAME
            return a;
        }
        
        if (aLevel > bLevel) { // GO UP A'S SIDE IF A IS LOWER
            return lca(a.getParent(), b);
        } else if (bLevel > aLevel) { // VICE VERSA FOR B
            return lca(a, b.getParent());
        }

        return lca(a.getParent(), b.getParent()); // IF EQUAL, GO UP UNTIL WE FIND ITS PARENT.
    }

    public int level(FileSystemObject node) {
        if (node == getRoot()) {
            return 0;
        } else {
            int level = 0; // INIT. VAR
            boolean hasParent = true;
            FileSystemObject curr = node;

            while (hasParent) { // COUNT UP WHILE PARENT IS NOT NULL
                if (curr != null) { // NULL CHECK
                    curr = curr.getParent();
                    level++;
                } else {
                    hasParent = false;
                }
            }

            return level - 1; // LEVELS START AT 0

        }
    }

    public String toString() {
        String output = root.toString() + '\n'; // ADD ROOT

        output += outputChildren(root); // CALL HELPER
        return output;

    }

    private String outputChildren(FileSystemObject node) {
        String output = ""; // INIT. STRING

        Iterator<FileSystemObject> iter = node.getChildren().iterator(); // CREATE ITERATOR

        while (iter.hasNext()) {
            FileSystemObject curr = iter.next();

            for (int i = 0; i < level(curr) - 1; i++) { // ADD INDENTATION PER THE CURRENT LEVEL.
                output += "  ";
            }
            output += " - " + curr.toString() + "\n"; // ADD LINE

            if (curr.isFile()) { // RETURN OUTPUT IF FILE
                continue;
            } else { // CALL FUNCTION AGAIN IF NOT

                output += outputChildren(curr);
            }
        }

        return output;
    }

    public void cutPaste(FileSystemObject f, FileSystemObject dest) {
        if (dest == null || dest.isFile() == true) { // GUARDS FOR MOVING INSIDE FILES OR MOVING ROOT
            throw new DirectoryTreeException("Cannot store folders inside file.");
        }
        
        if (f == null || f == getRoot()) {
            throw new DirectoryTreeException("Cannot move the entire directory tree inside a folder.");
        }

        FileSystemObject fParent = f.getParent(); // GET PARENT AND AN ITERATOR FOR ITS CHILDREN
        Iterator<FileSystemObject> iter = fParent.getChildren().iterator();

        while (iter.hasNext()) {
            FileSystemObject curr = iter.next();

            if (curr == f) {
                dest.addChild(curr); // ADD F TO DEST
                fParent.getChildren().remove(curr); // REMOVE F FROM ITS PARENT'S CHILDREN
                break; // END CODE EARLY IF MATCH
            } // END OF IF STATEMENT
        } // END OF WHILE LOOP
    }

    public void copyPaste(FileSystemObject f, FileSystemObject dest) {
        if (dest.isFile()) { // GUARDS FOR MOVING INSIDE FILES OR MOVING ROOT
            throw new DirectoryTreeException("Cannot store folders inside file.");
        }

        FileSystemObject fCopy = makeCopy(f);
        dest.addChild(fCopy);
    }

    private FileSystemObject makeCopy(FileSystemObject f) {
        if (f.isFile()) {
            // CREATE COPY OF FILE, SAVING ITS PARENT AS WELL
            ComputerFile newFile = new ComputerFile(f.getName(), f.getID() + 100, ((ComputerFile) (f)).size());
            newFile.setParent(f.getParent());
            return newFile;
        } else { // FOLDER CODE
            FileSystemObject newFolder = new FileSystemObject(f.getName(), f.getID() + 100);

            // ADD CHILDREN
            Iterator<FileSystemObject> iter = f.getChildren().iterator();
            while (iter.hasNext()) {
                FileSystemObject curr = iter.next();
                if (curr != null) {
                    newFolder.addChild(makeCopy(curr)); // IF FILE HAS CHILDREN, CALL AGAIN TO ADD
                }
            }

            return newFolder;
        }
    }

    public String buildPath(FileSystemObject a, FileSystemObject b) {
        if (a == b || level(a) == level(b)) {
            return b.toString();
        }

        String path = "";

        FileSystemObject top = lca(a, b); // GET LOWEST COMMON ANCESTOR
        FileSystemObject curr = a;

        while (curr != top) { // CLIMB UP LEVELS
            curr = curr.getParent();
            path += "../";
        }

        path += dowString(top, b);

        return path;
    }

    private String dowString(FileSystemObject top, FileSystemObject lowerNode) {
        String lowerPart = "";
        lowerPart += lowerNode.toString();

        lowerNode = lowerNode.getParent();
        while (lowerNode != top) {
            lowerPart = lowerNode.toString() + "/" + lowerPart;
            lowerNode = lowerNode.getParent();
        }

        // REMOVE LOWER PART
        return lowerPart;
    }
}
