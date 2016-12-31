// implements a Queue with double linked list nodes
public class Queue <E> {
	private DoubleLinkedList <E> first;
	private DoubleLinkedList <E> last;
	private int size; // keep the size here
	
	public Queue () {
		this.first = null;
		this.last = null;
		this.size = 0;
	}
	
	// add an element to the end of the queue, return its node; handle all pointers
	public DoubleLinkedList<E> enqueue(E elem) {
		DoubleLinkedList<E> newNode = new DoubleLinkedList <E>(elem);
		if (this.last == null) {
			this.first = newNode;
		}
		else {
			this.last.setNext(newNode);
		}
		newNode.setPrev(this.last);
		this.last = newNode;
		this.size += 1;
		return this.last;
	}
	
	// pop an element off the front of the queue, handle all pointers
	public DoubleLinkedList<E> dequeue() {
		if (this.first == null) {
			return null;
		}
		DoubleLinkedList<E> next = this.first.getNext();
		DoubleLinkedList<E> top = this.first;
		if (next == null) {
			this.last = null;
		}
		else {
			next.setPrev(null);
			top.setNext(null);
		}
		this.first = next;
		this.size -= 1;
		return top;
	}
	
	// delete the last element in the queue, handle all pointers
	public DoubleLinkedList<E> deleteLast() {
		if (this.last == null) {
			return null;
		}
		DoubleLinkedList<E> penult = this.last.getPrev();
		DoubleLinkedList<E> bottom = this.last;
		if (penult == null) {
			this.first = null;
		}
		else {
			penult.setNext(null);
			bottom.setPrev(null);
		}
		this.last = penult;
		this.size -= 1;
		return bottom;
	}
	
	// remove a given node from the queue; assume it is in queue
	public void remove(DoubleLinkedList<E> dll) {
		if (dll == this.first) {
			this.dequeue();
		}
		else if (dll == this.last) {
			this.deleteLast();
		}
		else if (dll != null) {
			System.out.println(this.first);
			System.out.println(dll);
			DoubleLinkedList<E> next = dll.getNext();
			DoubleLinkedList<E> prev = dll.getPrev();
			next.setPrev(prev);
			prev.setNext(next);
			this.size -= 1;
		}
	}
	
	// print the first DLL for simplicity
	public String toString() {
		if (this.first == null) {
			return "null";
		}
		return this.first.toString();
	}
	
	// return the size
	public int getSize() {
		return this.size;
	}
}
