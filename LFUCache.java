import java.util.HashMap;
import java.lang.Exception;

public class LFUCache {
	private class InvalidCapacityException extends Exception {
		private static final long serialVersionUID = 1L;}; // define only exception -> want positive capacity
	private int capacity; // capacity of cache
	private int size; // number of elements in cache
	private HashMap<Integer, Integer> key_to_freq; //map of keys to its frequency
	private HashMap<Integer, Queue<Integer>> frequency_to_keys; // map of frequency to keys of that frequency
	private HashMap<Integer, DoubleLinkedList<Integer>> key_to_node; // map key to its node
	private HashMap<Integer, Integer> kv_map; // map key to its value
	private int min_frequency; // minimum frequency of the cache
	public LFUCache(int capacity) throws InvalidCapacityException {
		this.capacity = capacity;
		if (this.capacity <= 0) 
			throw new InvalidCapacityException();
		// initialize maps and size and frequency below
		this.size = 0;
		this.min_frequency = 0;
		this.key_to_freq = new HashMap<Integer, Integer>();
		this.frequency_to_keys = new HashMap<Integer, Queue<Integer>>();
		this.key_to_node = new HashMap<Integer, DoubleLinkedList<Integer>>();
		this.kv_map = new HashMap<Integer, Integer>();
	}
	
	// increments the frequency of a given key in the map
	private boolean incrementFrequency(int key) {
		if (this.kv_map.containsKey(key)) {
			DoubleLinkedList<Integer> node;
			Queue<Integer> q;
			int freq;
			// reset frequency map
			freq = this.key_to_freq.get(key);
			this.key_to_freq.put(key, freq + 1);
			// take out element from old frequency
			q = this.frequency_to_keys.get(freq);
			node = this.key_to_node.get(key);
			q.remove(node);
			// if q has size 0-> remove everything
			if (q.getSize() == 0) {
				this.frequency_to_keys.remove(freq);
				// if was old frequency, new frequency is 1 greater
				if (this.min_frequency == freq) {
					this.min_frequency ++;
				}
			}
			// new_freq, put element into new frequency map
			freq ++;
			if (this.frequency_to_keys.containsKey(freq)) {
				node = this.frequency_to_keys.get(freq).enqueue(key);
				this.key_to_node.put(key, node);
			}
			else {
				q = new Queue<Integer> ();
				node = q.enqueue(key);
				this.key_to_node.put(key, node);
				this.frequency_to_keys.put(freq, q);
			}
			
			return true;
		}
		return false;
	}
	
	public void set(int key, int val) {
		// need positive values if return -1 when key does not exist
		if (val < 0) {
			System.out.println("Need positive values");
			return;
		}
		DoubleLinkedList<Integer> node;
		Queue<Integer> q;
		int freq;
		// doesn't contain key
		if (!this.incrementFrequency(key)){
			// reset frequency map
			freq = 1;
			this.key_to_freq.put(key, freq);
			// reset frequency to keys map
			if (this.frequency_to_keys.containsKey(freq)) {
				q = this.frequency_to_keys.get(freq);
				node = q.enqueue(key);
				this.key_to_node.put(key, node);
			}
			else {
				q = new Queue<Integer>();
				node = q.enqueue(key);
				this.key_to_node.put(key, node);
				this.frequency_to_keys.put(freq, q);
			}
			// delete an element from the cache if the size is the capacity
			if (this.size == this.capacity) {
				q = this.frequency_to_keys.get(this.min_frequency);
				node = q.dequeue();
				if (q.getSize() == 0) {
					this.frequency_to_keys.remove(this.min_frequency);
				}
				int min_key = node.getVal();
				this.kv_map.remove(min_key);
				this.key_to_node.remove(min_key);
				this.key_to_freq.remove(min_key);
			}
			else {
				this.size ++;
			}
			this.min_frequency = freq;
		}
		// reset kv map
		this.kv_map.put(key, val);		
	}
	
	public int get(int key) {
		// increment key if it is in cache, return value
		if (this.incrementFrequency(key)) {
			return this.kv_map.get(key);
		}
		return -1;
	}
	
	public String toString() {
		String output = "{\n";
		for (Integer key: this.kv_map.keySet()) {
			output += "\t" + key + "," + this.kv_map.get(key) + "," + this.key_to_freq.get(key) + "\n";
		}
		return output + "}";
	}
	
	public String freqToKeysString() {
		String output = "";
		for (Integer key: this.frequency_to_keys.keySet()) {
			Queue <Integer> q = this.frequency_to_keys.get(key);
			output += "Key: " + key + "\n" + q.toString() + "\n"; 
		}
		return output;
	}
	
	public static void main(String [] args) {
		LFUCache c;
		try {
			c = new LFUCache(4);
		}
		catch (InvalidCapacityException e) {
			return;
		}
		c.set(4, 5);
		c.set(4, 3);
		c.set(3, 10);
		c.set(2, 9);
		c.get(2);
		c.get(4);
		c.get(2);
		c.get(3);
		c.set(2,24);
		System.out.println(c);
		c.set(7, 15);
		c.set(7, 12);
		c.get(7);
		c.get(7);
		System.out.println(c);
		c.set(9, 1);
		c.set(9, 2);
		c.get(9);
		c.set(12, 4);
		c.get(2);
		c.get(2);
		System.out.println(c);
	}
}
