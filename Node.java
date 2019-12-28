/****************************************************************************
 * Name : Tom Wallenstein
 * 
 *  
 * Description:
 * The program creates linked list containing node objects 
 *****************************************************************************/
public class Node {
    private Node next;
    private int number;
    
    // constructor
    public Node(Node nextNode, int candidate) {
        next = nextNode;
        number = candidate;
    }
    
    // get value in node 
    public int getNumber() {
        int value = this.number;
        return value;
    }
    
    // get next node
    public Node getNext() {
        Node nextNode = this.next;
        return nextNode;
    }
    
    // insert new node after previous
    public void insertAfter(int number) {
        Node newNode = new Node(null, number);
        newNode.next = next;
        next = newNode;
    }
    
    // insert 
    public int changeNumber(int number) {
        this.number = number;
        return getNumber();
    }
    
    // create a string representation of the linked list
    // starting at this node                                                      
    // How does function get called? 
    // -> default java setting that System.out.println(ll) calls toString() 
    public String toString() {
        String str = number + "";
        if (next != null)
            str += " -> " + next;
        return str;
    }
}
