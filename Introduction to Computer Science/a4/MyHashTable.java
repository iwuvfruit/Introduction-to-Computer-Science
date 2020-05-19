//package ;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class MyHashTable<K,V> implements Iterable<HashPair<K,V>>{
    // num of entries to the table
    private int numEntries;
    // num of buckets 
    private int numBuckets;
    // load factor needed to check for rehashing 
    private static final double MAX_LOAD_FACTOR = 0.75;
    // ArrayList of buckets. Each bucket is a LinkedList of HashPair
    private ArrayList<LinkedList<HashPair<K,V>>> buckets; 
    
    // constructor
    public MyHashTable(int initialCapacity) {
        // ADD YOUR CODE BELOW THIS
        this.numBuckets = initialCapacity; 
        this.numEntries = 0;
        this.buckets = new ArrayList<LinkedList<HashPair<K,V>>>();
        
        for(int i = 0; i < initialCapacity; i++) {
        	this.buckets.add(new LinkedList<HashPair<K,V>>());
        }
        
        //ADD YOUR CODE ABOVE THIS
    }
    
    public int size() {
        return this.numEntries;
    }
    
    public int numBuckets() {
        return this.numBuckets;
    }
    
    /**
     * Returns the buckets vairable. Usefull for testing  purposes.
     */
    public ArrayList<LinkedList< HashPair<K,V> > > getBuckets(){
        return this.buckets;
    }
    /**
     * Given a key, return the bucket position for the key. 
     */
    public int hashFunction(K key) {
        int hashValue = Math.abs(key.hashCode())%this.numBuckets;
        return hashValue;
    }
    /**
     * Takes a key and a value as input and adds the corresponding HashPair
     * to this HashTable. Expected average run time  O(1)
     */
    public V put(K key, V value) {
        //  ADD YOUR CODE BELOW HERE
        
        int index = hashFunction(key);
        for(HashPair<K,V> e: this.buckets.get(index) ) {
        	if (e.getKey().equals(key)) {
        		V oldValue = e.getValue();
        		e.setValue(value);
        		return oldValue;
        	}
        }
        this.buckets.get(index).addLast(new HashPair<K,V>(key, value));
        this.numEntries++;
    	if(currentLoadFactor(numEntries) > MAX_LOAD_FACTOR) {
    		this.rehash();
    	}
    	return null;
        //  ADD YOUR CODE ABOVE HERE
    }
    private double currentLoadFactor(int numEntries) {
    	double loadFactor = ((double)numEntries) / ((double)this.numBuckets);
    	return loadFactor;
    }
    
    /**
     * Get the value corresponding to key. Expected average runtime = O(1)
     */
    
    public V get(K key) {
        //ADD YOUR CODE BELOW HERE
    	int index = hashFunction(key);
    	for(HashPair<K, V> e: this.buckets.get(index)) {
    		if (e.getKey().equals(key)) {
    			return e.getValue();
    		}
    	}
        return null;//remove
        //ADD YOUR CODE ABOVE HERE
    }
    
    /**
     * Remove the HashPair correspoinding to key . Expected average runtime O(1) 
     */
    public V remove(K key) {
        //ADD YOUR CODE BELOW HERE
    	int index = hashFunction(key);
    	for(HashPair<K, V> e : this.buckets.get(index)) {
    		if(e.getKey().equals(key)) {
    			V value = e.getValue();
    			this.buckets.get(index).remove(e);
    			numEntries --;
    			return value;
    		}
    	}
        return null;//remove
        //ADD YOUR CODE ABOVE HERE
    }
    
    // Method to double the size of the hashtable if load factor increases
    // beyond MAX_LOAD_FACTOR.
    // Made public for ease of testing.
    
    public void rehash() {
        //ADD YOUR CODE BELOW HERE
   	ArrayList<LinkedList<HashPair<K,V>>> tempBucket = this.buckets;
   	tempBucket = new ArrayList<LinkedList<HashPair<K,V>>>(tempBucket.size() * 2);
   	
   	this.numBuckets *= 2;
   	this.numEntries = 0;
   	for(int i = 0; i < this.numBuckets; i++) {
   		this.buckets.add(new LinkedList<HashPair<K,V>>());
   	}
   	
   	for(LinkedList<HashPair<K,V>> bucket: tempBucket) {
   		for(HashPair<K,V> pair: bucket) {
   			this.put(pair.getKey(), pair.getValue());
   		}
   	}
    	
        //ADD YOUR CODE ABOVE HERE
    }
    
    
    /**
     * Return a list of all the keys present in this hashtable.
     */
    
    public ArrayList<K> keys() {
        //ADD YOUR CODE BELOW HERE
    	ArrayList<K> keys = new ArrayList<K>();
    	for(LinkedList<HashPair<K,V>> bucket: this.buckets ) {
    		for(HashPair<K,V> pair: bucket) {
    			keys.add(pair.getKey());
    		}
    		
    	}
        return keys;
        //ADD YOUR CODE ABOVE HERE
    }
    
    /**
     * Returns an ArrayList of unique values present in this hashtable.
     * Expected average runtime is O(n)
     */
    public ArrayList<V> values() {
        //ADD CODE BELOW HERE
//    	ArrayList<V> values = new ArrayList<V>();
//    	for(LinkedList<HashPair<K,V>> bucket: this.buckets) {
//    		for(HashPair<K,V> pair: bucket) {
//    			if(!(values.contains(pair.getValue()))) {
//    				values.add(pair.getValue());
//    			}
//    		}
//    	}
//        return values;//remove
    	
    	//we want unique values in the Table 
    	MyHashTable<K,V> HashedValues = new MyHashTable<K,V>(this.numBuckets);
    	ArrayList<V> value = new ArrayList<V>();
    	
    	for(LinkedList<HashPair<K,V>> bucket: this.buckets) {
    		for(HashPair<K,V> pair: bucket) {
    			//using put since put can eliminate the exisiting key, we swap
    			HashedValues.put((K)pair.getValue(), (V)pair.getKey()); 
    		}
    	}
    	for(LinkedList<HashPair<K,V>> bucket: HashedValues.getBuckets()) {
    		for(HashPair<K,V> pair: bucket) {
    			value.add((V)pair.getKey());
    		}
    		
    	}
    	return value;
    	
        //ADD CODE ABOVE HERE
    }
    
    
    @Override
    public MyHashIterator iterator() {
        return new MyHashIterator();
    }
    
    
    private class MyHashIterator implements Iterator<HashPair<K,V>> {
        private LinkedList<HashPair<K,V>> entries;
        
        private MyHashIterator() {
            //ADD YOUR CODE BELOW HERE
            this.entries = new LinkedList<HashPair<K,V>>();
            for(LinkedList<HashPair<K,V>> bucket: buckets) {
        		for(HashPair<K,V> pair: bucket) {
        			entries.add(pair);
        		}
            }
        			//ADD YOUR CODE ABOVE HERE
        }
        
        @Override
        public boolean hasNext() {
            //ADD YOUR CODE BELOW HERE
        	return (!(this.entries.isEmpty()));

            //ADD YOUR CODE ABOVE HERE
        }
        
        @Override
        public HashPair<K,V> next() {
            //ADD YOUR CODE BELOW HERE
        	return this.entries.removeFirst();
            //ADD YOUR CODE ABOVE HERE
        }
        
    }
}
