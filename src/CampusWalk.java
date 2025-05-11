/* 
 * CS 1027B - Assignment 3
 * Name: Ethan Gregory
 * Student Number: 251433485
 * Email: egregor4@uwo.ca
 * Created: March 6th, 2025
 */
public class CampusWalk {
    private Map map;

    public CampusWalk(String filename, boolean showMap) { // CONSTRUCTOR
        try {
            map = new Map(filename);
        } catch (Exception e) {
            System.out.println("Error occurred");
        }

        if (showMap == true) {
            map.showGUI();
        }

    }

    public void exit() { // EXIT THE MAP
        map.exit();
    }

    public int neighbourGooseCount(Hexagon cell) { // GET GOOSE COUNT
        int gooseCount = 0; // INIT VAR

        for (int i = 0; i < 6; i++) { // FOR LOOP FOR ALL 6
            if (cell.getNeighbour(i) != null) { // GUARD AGAINST NULL
                if (cell.getNeighbour(i).isGooseCell()) { // CHECK IF GOOSE
                    gooseCount++; // UP COUNTER
                }
            }
        }

        return gooseCount;
    }

    private boolean isNull(Hexagon cell) { // HELPER METHOD TO DETERMINE IF CELL IS NULL
        if (cell == null) {
            return true;
        } else {
            return false;
        }
    }

    public Hexagon findBest(Hexagon cell) {

        for (int i = 0; i < 6; i++) { // LOOP THROUGH NEIGHBOURS FOR END
            Hexagon currNeighbour = cell.getNeighbour(i);
            boolean nullCell = isNull(currNeighbour);

            if (!nullCell && currNeighbour.isEnd()) {
                return currNeighbour;
            }
        } // END OF FOR LOOP

        for (int i = 0; i < 6; i++) { // LOOP THROUGH NEIGHBOURS FOR BOOK
            Hexagon currNeighbour = cell.getNeighbour(i);
            boolean nullCell = isNull(currNeighbour); // INIT. BASIC VAR

            if (!nullCell) {
                boolean followsRules = !currNeighbour.isMarked() && neighbourGooseCount(currNeighbour) < 3; // SEE IF IT
                                                                                                            // FOLLOWS
                                                                                                            // RULES

                if (followsRules && currNeighbour.isBookCell()) {
                    return currNeighbour;
                }
            }

        } // END OF FOR LOOP

        for (int i = 0; i < 6; i++) { // LOOP THROUGH NEIGHBOURS FOR GRASS
            Hexagon currNeighbour = cell.getNeighbour(i);
            boolean nullCell = isNull(currNeighbour);

            if (!nullCell) {
                boolean followsRules = !currNeighbour.isMarked() && neighbourGooseCount(currNeighbour) < 3;

                if (followsRules && currNeighbour.isBookCell()) {
                    return currNeighbour;
                }
            }
        } // END OF FOR LOOP

        // INIT. GRASS CELL VARS
        Hexagon bestGrass = null;
        int currGooseCount = 6; // MAX POSSIBLE VALUE

        for (int i = 0; i < 6; i++) {
            Hexagon currNeighbour = cell.getNeighbour(i);
            boolean nullCell = isNull(currNeighbour);

            if (!nullCell) {
                boolean followsRules = !currNeighbour.isMarked() && neighbourGooseCount(currNeighbour) < 3;

                if (followsRules && currNeighbour.isGrassCell()
                        && neighbourGooseCount(currNeighbour) < currGooseCount) { // SAME GUARD, BUT COMPARE WITH OTHER
                                                                                  // GRASS CELL'S GEESE
                    bestGrass = currNeighbour; // IF SUCCESSFUL, MAKE CURR NEIGHT
                    currGooseCount = neighbourGooseCount(bestGrass);
                }
            }
        }

        if (bestGrass != null) { // RETURN GRASS CELL IF POSSIBLE
            return bestGrass;
        }

        // CHECK SNOW CELLS IF ALL ELSE FAILS
        for (int i = 0; i < 6; i++) {
            Hexagon currNeighbour = cell.getNeighbour(i);
            boolean nullCell = isNull(currNeighbour);

            if (!nullCell) {
                boolean followsRules = !currNeighbour.isMarked() && neighbourGooseCount(currNeighbour) < 3;

                if (followsRules && currNeighbour.isSnowCell()) {
                    return currNeighbour;
                }
            }
        }

        return null; // RETURN NULL IF CELL NOT POSSIBLE

        
    }

    public String findPath() {
        ArrayStack<Hexagon> s = new ArrayStack<Hexagon>();
        s.push(map.getStart()); // INIT. STACK AND SET STARTING CELL

        boolean isRunning = true;
        String pathString = "";

        s.peek().markInStack(); // MARK THE START CELL AS TRUE

        while (s.isEmpty() == false && isRunning == true) {
            Hexagon curr = s.peek();
            Hexagon next;

            pathString += curr.getID() + " ";

            if (curr.isEnd()) { // IF CELL IS EXIT, BREAK LOOP IMMEDIATELY
                isRunning = false;
                break;
            }

            next = findBest(curr);
            if (next == null) {
                s.pop();
                curr.markOutStack();
            } else {
                s.push(next);
                next.markInStack();
            }

        }

        if (isRunning == false) {
            return pathString;
        } else {
            return "No path found";
        }

    }

    public static void main(String[] args) {
        Hexagon.TIME_DELAY = 500; // Change speed of animation.
        String file = "map5.txt"; // Change when trying other maps.
        CampusWalk walk = new CampusWalk(file, true);

        try {
            System.out.println(walk.findPath());
        } catch (Exception e) {
            System.out.println("Error.");
        } finally {
            walk.exit();
        }

    }
}
