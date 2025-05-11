/* 
 * CS 1027B - Assignment 3
 * Name: Ethan Gregory
 * Student Number: 251433485
 * Email: egregor4@uwo.ca
 * Created: March 6th, 2025
 */
public class ArrayStack<T> implements StackADT<T> {
    private T[] array;
    private int top;

    public ArrayStack() { // DEFAULT ARRAY SIZE
        this.array = (T[]) (new Object[10]); // INIT WITH DEFAULT SIZE
        this.top = this.array.length - 1;
    }

    public ArrayStack(int initCapacity) { // CUSTOM ARRAY SIZE
        this.array = (T[]) (new Object[initCapacity]);
        this.top = this.array.length - 1;
    }

    public void push(T dataItem) { // ADD TO STACK
        if (top == -1) { // EXPAND STACK IF NECESSARY
            expandCapacity();
        }

        array[top] = dataItem; // ADD TO STACK AND DECREASE TOP
        top--;
    }

    public T pop() throws CollectionException {
        if (top == this.array.length - 1) { // GUARD AGAINST EMPTY STACK
            throw new CollectionException("Stack is empty");
        }

        top++; // INC. STACK
        T element = array[top]; // PULL TOP OF STACK
        array[top] = null; // SET TOP TO NULL

        return element; // GIVE ELEMENT
    }

    public T peek() throws CollectionException { // PULL TOP OF
        if (top == this.array.length - 1) {
            throw new CollectionException("String is empty");
        }

        return array[top + 1];
    }

    public boolean isEmpty() {

        if (top == this.array.length - 1) { // IF TOP IS ACTUAL MAX ARRAY, TRUE
            return true;
        } else { // ELSE, FALSE
            return false;
        } // END OF IF STATEMENT
    }

    public int size() { // LOOP THROUGH ARRAY FOR VALID NON-NULL ITEMS, ADD TO SIZE
        int maxSize = 0;
        for (int i = (this.array.length - 1); i >= 0; i--) {
            if (array[i] != null) {
                maxSize++;
            }
        }

        return maxSize;
    }

    public int getTop() { // RETURN THE TOP INDEX OF THE ARRAY
        return top;
    }

    public int getCapacity() { // RETURN MAX SIZE
        return this.array.length;
    }

    private void expandCapacity() {
        T[] larger; // INIT. LARGER

        if (this.size() <= 15) { // ADJUST SIZE BASED ON CURRENT CAPACITY
            larger = (T[]) (new Object[this.size() * 2]);
        } else {
            larger = (T[]) (new Object[this.size() + 10]);
        }

        int largerTop = top + (larger.length - this.array.length); // GET TOP OF LARGER ARRAY, ADJUSTED WITH PREVIOUS
                                                                   // TOP

        for (int i = this.array.length - 1; i >= top + 1; i--) { // ADD TO LARGER ARRAY
            larger[largerTop + (i - top)] = this.array[i];
        }

        top = largerTop;
        this.array = larger; // UPDATE ARRAY & TOP

    }

    public String toString() { // STRING IMPLEMENTATION
        if (top == this.array.length - 1) { // GUARD AGAINST EMPTY STACK
            return "Empty stack.";
        }

        String outString = "";
        for (int i = top + 1; i < array.length; i++) { // LOOP THROUGH TO ADD TO STRING
            outString += array[i];
            if (i == array.length - 1) { // PERIOD AT THE VERY END
                outString += ".";
            } else {
                outString += ", ";
            }
        }

        return outString;
    }

    public static void main(String[] args) {
        ArrayStack<Integer> test = new ArrayStack<>(5);

        for (int i = 0; i < 10; i++) {
            test.push(1);
            test.push(2);
        }

        System.out.println(test.toString());
    }

}
