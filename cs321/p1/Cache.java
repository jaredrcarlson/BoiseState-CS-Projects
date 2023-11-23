/**
 * The Cache class is used to create cache memory storage for quick data retrieval.
 * This class supports both single level (1 cache) or double level (2 cache) operation.
 *
 * @author jcarlson
 * @param <T> the generic type
 */
public class Cache<T extends Comparable<T>> {
		
	/** Maximum number of items to store */
	private int capacity;
	
	/** Current number of items stored */
	private int numberOfCacheItems;
	
	/** Pointer to the Most Recently Used item */
	private DLLCacheNode<T> newestCacheNode;
	
	/** Pointer to the last item (Oldest item in storage) */
	private DLLCacheNode<T> oldestCacheNode;
	
	/** Pointer used for linked list functionality
	 *   Note: previousCacheNode points to the adjacent CacheNode of a "More Recently Used" item */
	private DLLCacheNode<T> previousCacheNode;
	
	/** Pointer used for linked list functionality
	 *   Note: nextCacheNode points to the adjacent CacheNode of an "Older" item */
	private DLLCacheNode<T> nextCacheNode;
	
	/**
	 * Instantiates a new cache object
	 *
	 * @param capacity The cache capacity
	 */
	public Cache(int capacity) {
		setCapacity(capacity);
		numberOfCacheItems = 0;
	}
	
	/**
	 * Gets an element from this cache
	 *
	 * @param elem The element to get from cache
	 * @return Returns the element if found or returns otherwise
	 */
	public T getObject(T elem) {
		DLLCacheNode<T> targetCacheNode = locateItem(elem);
		if(targetCacheNode != null) {
			T retrievedItem = targetCacheNode.getElem();
			moveToNewestSlot(retrievedItem);
			return retrievedItem;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Adds an element to this cache
	 *
	 * @param elem The element to be added
	 */
	public void addObject(T elem) {
		if(isEmpty()) {
			initializeCache(elem);
		}
		else if(isFull()) {
			removeOldestObject();
			addObject(elem);
		}
		else {
			nextCacheNode = new DLLCacheNode<T>(elem);
			nextCacheNode.setNext(newestCacheNode);
			newestCacheNode.setPrevious(nextCacheNode);
			newestCacheNode = nextCacheNode;
			numberOfCacheItems++;
		}
	}
	
	/**
	 * Removes an element from this cache
	 *
	 * @param elem The element to be removed
	 */
	public void removeObject(T elem) {
		DLLCacheNode<T> targetCacheNode = locateItem(elem);
		if(targetCacheNode != null) {
			if(targetCacheNode == oldestCacheNode) {
				removeOldestObject();
			}
			else if(targetCacheNode == newestCacheNode) {
				nextCacheNode = targetCacheNode.getNext();
				nextCacheNode.setPrevious(null);
				newestCacheNode = nextCacheNode;
				targetCacheNode.erase();
				numberOfCacheItems--;
			}
			else {
				previousCacheNode = targetCacheNode.getPrevious();
				nextCacheNode = targetCacheNode.getNext();
				previousCacheNode.setNext(nextCacheNode);
				nextCacheNode.setPrevious(previousCacheNode);
				targetCacheNode.erase();
				numberOfCacheItems--;
			}
		}	
	}
	
	/**
	 * Clears the contents of this cache
	 */
	public void clearCache() {
		if(!isEmpty()) {
			DLLCacheNode<T> currentCacheNode = newestCacheNode;
			while(currentCacheNode.hasNextSlot()) {
				nextCacheNode = currentCacheNode.getNext();
				currentCacheNode.erase();
				currentCacheNode = nextCacheNode;
			}
            currentCacheNode.erase();
            numberOfCacheItems = 0;
		}
	}
	
	/**
	 * Locates an element in this cache
	 *
	 * @param elem The element to locate
	 * @return Returns a pointer to the CacheNode containing the element
	 */
	private DLLCacheNode<T> locateItem(T elem) {
		if(!isEmpty()) {
			DLLCacheNode<T> currentCacheNode = newestCacheNode;
			if(currentCacheNode.getElem().compareTo(elem) == 0) {
				return currentCacheNode;
			}
			else {
				while(currentCacheNode.hasNextSlot()) {
					currentCacheNode = currentCacheNode.getNext();
					if(currentCacheNode.getElem().compareTo(elem) == 0) {
						return currentCacheNode;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Initializes this cache
	 *
	 * @param elem The first item to store in this cache
	 */
	private void initializeCache(T elem) {
		newestCacheNode = oldestCacheNode = new DLLCacheNode<T>(elem);
		numberOfCacheItems++;
	}
	
	/**
	 * Sets the capacity of this cache
	 *
	 * @param capacity The maximum size of this cache
	 */
	private void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	/**
	 * Checks if this cache is full
	 *
	 * @return true, if capacity has been reached
	 */
	private boolean isFull() {
		return numberOfCacheItems == capacity;
	}
	
	/**
	 * Checks if this cache is empty
	 *
	 * @return true, if no elements exist in this cache
	 */
	public boolean isEmpty() {
		return numberOfCacheItems == 0;
	}
	
	/**
	 * Removes the last (Oldest) element from this cache
	 */
	private void removeOldestObject() {
		previousCacheNode = oldestCacheNode.getPrevious();
		previousCacheNode.setNext(null);
		oldestCacheNode.erase();
		numberOfCacheItems--;
		oldestCacheNode = previousCacheNode;
	}
	
	/**
	 * Moves an element to the front of this cache (Most Recently Used)
	 * 
	 * @param elem The element to be moved
	 */
	private void moveToNewestSlot(T elem) {
		removeObject(elem);
		addObject(elem);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * 
	 * Displays the contents of this cache
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		if(isEmpty()) {
			str.append("EMPTY");
		}
		else {
			DLLCacheNode<T> currentCacheNode = newestCacheNode;
			str.append("[" + currentCacheNode.getElem() + "]");
			while(currentCacheNode.hasNextSlot()) {
				currentCacheNode = currentCacheNode.getNext();
				str.append("  [" + currentCacheNode.getElem() + "]");
			}
		}
		return str.toString();
	}
	
	/**
	 * The Class DLLCacheNode<S> is used to store linked generic type elements in a cache object
	 *
	 * @param <S> the generic type
	 */
	private class DLLCacheNode<S> {
		
		/** Stores the generic type data */
		private S elem;
		
		/** Pointer (Link) to the previous DLLCacheNode<S> */
		private DLLCacheNode<S> previous;
		
		/** Pointer (Link) to the next DLLCacheNode<S> */
		private DLLCacheNode<S> next;
		
		/**
		 * Instantiates a new DLLCacheNode<S>
		 *
		 * @param elem The generic type element to store in this node
		 */
		public DLLCacheNode(S elem) {
			setElem(elem);
			setPrevious(null);
			setNext(null);
		}
		
		/**
		 * Sets the element for this node 
		 *
		 * @param elem The new element
		 */
		public void setElem(S elem) {
			this.elem = elem;
		}
		
		/**
		 * Gets the element of this node
		 *
		 * @return The element of this node
		 */
		public S getElem() {
			return elem;
		}
		
		/**
		 * Sets the previous node of this node
		 *
		 * @param previous The new previous node
		 */
		public void setPrevious(DLLCacheNode<S> previous) {
			this.previous = previous;
		}
		
		/**
		 * Gets the previous node of this node
		 *
		 * @return The previous node
		 */
		public DLLCacheNode<S> getPrevious() {
			return previous;
		}
		
		/**
		 * Sets the next node of this node
		 *
		 * @param next The next node
		 */
		public void setNext(DLLCacheNode<S> next) {
			this.next = next;
		}
		
		/**
		 * Gets the next node of this node
		 *
		 * @return The next node
		 */
		public DLLCacheNode<S> getNext() {
			return next;
		}
		
		/**
		 * Checks to see if this node has a next node (i.e. Checks for last node)
		 *
		 * @return true, if this node has a next node
		 */
		public boolean hasNextSlot() {
			return next != null;
		}
		
		/**
		 * Erases (Removes) this node
		 */
		public void erase() {
			setElem(null);
			setPrevious(null);
			setNext(null);
		}
	}
}
