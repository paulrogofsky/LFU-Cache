
// Defines a double Linked List node
public class DoubleLinkedList <E> {
	// has attributes next, previous, and the value
	private DoubleLinkedList <E> next;
	private DoubleLinkedList <E> prev;
	private E val;
	
	// initialized to None
	public DoubleLinkedList (E val) {
		this.val = val;
		this.next = null;
		this.prev = null;
	}
	
	// assume user takes care of all next and previous stuff
	// this class just permits us to set and get pointers pretty easily; how DLL is used is up to user
	public E getVal() {
		return this.val;
	}
	
	public DoubleLinkedList <E> getPrev() {
		return this.prev;
	}
	
	public DoubleLinkedList <E> getNext() {
		return this.next;
	}
	
	public void setPrev(DoubleLinkedList <E> prev) {
		this.prev = prev;
	}
	
	public void setNext(DoubleLinkedList <E> next) {
		this.next = next;
	}
	
	// just my way to represent a DLL-> want to know LL behind node and LL in front of node
	public String backwardsToString() {
		if (this.prev == null) {
			return "" + this.val;
		}
		return "(" + this.prev.backwardsToString() + ", " + this.val + ")";
	}
	
	public String forwardsToString() {
		if (this.next == null) {
			return "" + this.val;
		}
		return "(" + this.val + ", " + this.next.forwardsToString() + ")";		
	}
	
	public String toString() {
		return "Behind: " + this.backwardsToString() + "\nForward: " + this.forwardsToString();
	}
}
