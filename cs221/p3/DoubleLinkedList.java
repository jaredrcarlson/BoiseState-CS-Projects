import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * DoubleLinkedList represents a LinearNode-based implementation of both an unordered and indexed list.
 *
 * @author Java Foundations, jcarlson
 * @version 4.0
 */
public class DoubleLinkedList<T> implements DoubleLinkedListADT<T> {
	protected DoubleLinkedLinearNode<T> head, tail, previousNode, nextNode;
	protected int count, modCount;
	protected enum TraversalDirection { Forward, Reverse };
		
	/**
	 * Creates an empty list.
	 */
	public DoubleLinkedList() {
		
		// Create Sentinel Nodes
		head = new DoubleLinkedLinearNode<T>();
		tail = new DoubleLinkedLinearNode<T>();
		
		// Link Head Sentinel to Tail Sentinel
		head.setNext(tail);
		
		// Link Tail Sentinel to Head Sentinel
		tail.setPrevious(head); 
		
		// previousNode and nextNode are initially undefined
		previousNode = nextNode = null;
		
		// A new list is empty and has yet to be modified
		count = modCount = 0;
	}
	
	/**
	 * Adds the specified element to the front of this list.
	 *
	 * @param element the element to be added to the list
	 */
	public void addToFront(T element) {
		nextNode = head.getNext();
		DoubleLinkedLinearNode<T> newNode = createNewNode(element,head,nextNode);
		head.setNext(newNode);
		nextNode.setPrevious(newNode);
	}

	/**
	 * Adds the specified element to the rear of this list.
	 *
	 * @param element the element to be added to the list
	 */
	public void addToRear(T element) {
		previousNode = tail.getPrevious();
		DoubleLinkedLinearNode<T> newNode = createNewNode(element,previousNode,tail);
		tail.setPrevious(newNode);
		previousNode.setNext(newNode);
	}	

	/**
	 * Adds the specified element to this list after the given target.
	 *
	 * @param  element the element to be added to this list
	 * @param  target the target element to be added after
	 * @throws ElementNotFoundException if the target is not found
	 */
	@SuppressWarnings("unchecked")
	public void addAfter(T element, T target) {
		boolean targetElementFound = false;
		DoubleLinkedLinearNode<T> currentNodeAtIndex = null;
		ListIterator<T> itr = listIterator();
		
		// Attempt to locate and establish pointer to target node containing element
		while(!targetElementFound && itr.hasNext()) {
			currentNodeAtIndex = (DoubleLinkedLinearNode<T>) itr.next();
			if(currentNodeAtIndex.getElement() == target) {
				targetElementFound = true;
			}
		}
		
		// If target is not found - Throw exception
		if(!targetElementFound) {
			throw new ElementNotFoundException("");
		}
		else {
			
			// Add new element after element at index
			nextNode = currentNodeAtIndex.getNext();
			DoubleLinkedLinearNode<T> newNode = createNewNode(element,currentNodeAtIndex,nextNode);
    		currentNodeAtIndex.setNext(newNode);
    		nextNode.setPrevious(newNode);
		}
	}

	/**  
     * Inserts the specified element at the specified index. 
     * 
     * @param index the index into the array to which the element is to be inserted.
     * @param element the element to be inserted into the array
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size())
     */
	@SuppressWarnings("unchecked")
	public void add(int index, T element) {
    	
		// Validate Index
		if(index < 0 || index >= size()) {
    		throw new IndexOutOfBoundsException("");
    	}
    	else {
    		
    		// Iterate to index and add element
    		ListIterator<T> itr = listIterator(index);
    		DoubleLinkedLinearNode<T> currentNodeAtIndex = (DoubleLinkedLinearNode<T>) itr.next();
    		previousNode = currentNodeAtIndex.getPrevious();
    		nextNode = currentNodeAtIndex.getNext();
    		DoubleLinkedLinearNode<T> newNode = createNewNode(element,previousNode,currentNodeAtIndex);
    		previousNode.setNext(newNode);
    		currentNodeAtIndex.setPrevious(newNode);
    	}
    }

    /**  
     * Sets the element at the specified index. 
     *
     * @param index   the index into the array to which the element is to be set
     * @param element the element to be set into the list
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size())
     */
    @SuppressWarnings("unchecked")
	public void set(int index, T element) {
    	
    	// Validate index
    	if(index < 0 || index >= size()) {
    		throw new IndexOutOfBoundsException("");
    	}
    	else {
    		
    		// Set element at index
    		ListIterator<T> itr = listIterator(index);
    		DoubleLinkedLinearNode<T> currentNodeAtIndex = (DoubleLinkedLinearNode<T>) itr.next();
    		currentNodeAtIndex.setElement(element);
    		
    		modCount++;
    	}
    }

    /**  
     * Adds the specified element to the rear of this list. 
     *
     * @param element  the element to be added to the rear of the list    
     */
    public void add(T element) {
    	
    	// Add new element to the end of the list using addToRear method
    	addToRear(element);
    }

    /**  
     * Returns a reference to the element at the specified index. 
     *
     * @param index  the index to which the reference is to be retrieved from
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size())
     */
    @SuppressWarnings("unchecked")
	public T get(int index) {
    	
    	// Validate index
    	if(index < 0 || index >= size()) {
    		throw new IndexOutOfBoundsException("");
    	}
    	else {
    		
    		// Return element at index
    		ListIterator<T> itr = listIterator(index);
        	DoubleLinkedLinearNode<T> currentNodeAtIndex = (DoubleLinkedLinearNode<T>) itr.next();
    		return currentNodeAtIndex.getElement();
    	}
    }

    /**  
     * Returns the index of the first occurrence of the specified element. 
     *
     * @param element the element for the index is to be retrieved
     * @return the integer index for this element or -1 if element is not in the list    
     */
	@SuppressWarnings("unchecked")
	public int indexOf(T element) {
		int targetIndex = -1;
		boolean elementFound = false;
		DoubleLinkedLinearNode<T> currentNodeAtIndex = null;
		ListIterator<T> itr = listIterator();
		
		// Attempt to locate and establish pointer to node containing element
		while(!elementFound && itr.hasNext()) {
			currentNodeAtIndex = (DoubleLinkedLinearNode<T>) itr.next();
			if(currentNodeAtIndex.getElement() == element) {
				targetIndex = itr.previousIndex();
				elementFound = true;
			}
		}
		
		return targetIndex;
    }

    /**  
     * Removes and returns the element at the specified index. 
     *
     * @param index the index of the element to be removed and retrieved
     * @return the element at the given index
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size())
     */
    @SuppressWarnings("unchecked")
	public T remove(int index) {
    	
    	// Validate index
    	if(index < 0 || index >= size()) {
    		throw new IndexOutOfBoundsException("");
    	}
    	else {
    		
    		// Establish pointer to node at index and remove node
    		ListIterator<T> itr = listIterator(index);
    		DoubleLinkedLinearNode<T> currentNodeAtIndex = (DoubleLinkedLinearNode<T>) itr.next();
    		previousNode = currentNodeAtIndex.getPrevious();
    		nextNode = currentNodeAtIndex.getNext();
    		previousNode.setNext(nextNode);
    		nextNode.setPrevious(previousNode);
    		currentNodeAtIndex.setPrevious(null);
    		currentNodeAtIndex.setNext(null);
    		
    		count--;
    		modCount++;
    		
    		return currentNodeAtIndex.getElement(); 
    	}
    }

	/**
	 * Removes the first element in this list and returns a reference
	 * to it. Throws an EmptyCollectionException if the list is empty.
	 *
	 * @return a reference to the first element of this list
	 * @throws EmptyCollectionException if the list is empty
	 */
	public T removeFirst() {
		if(isEmpty()) {
			throw new EmptyCollectionException("");
		}
		else {
			
			// Remove head's next node
			DoubleLinkedLinearNode<T> firstNode = head.getNext();
			nextNode = firstNode.getNext();
			head.setNext(nextNode);
			nextNode.setPrevious(head);
			firstNode.setPrevious(null);
			firstNode.setNext(null);
			
			count--;
			modCount++;
			
			return firstNode.getElement();
		}
	}

	/**
	 * Removes the last element in this list and returns a reference
	 * to it. Throws an EmptyCollectionException if the list is empty.
	 *
	 * @return the last element in this list
	 * @throws EmptyCollectionException if the list is empty    
	 */
	public T removeLast() {
		if(isEmpty()) {
			throw new EmptyCollectionException("");
		}
		else {
			
			// Remove tail's previous node
			DoubleLinkedLinearNode<T> lastNode = tail.getPrevious();
			previousNode = lastNode.getPrevious();
			tail.setPrevious(previousNode);
			previousNode.setNext(tail);
			lastNode.setPrevious(null);
			lastNode.setNext(null);
			
			count--;
			modCount++;
			
			return lastNode.getElement();
		}
	}

	/**
	 * Removes the first instance of the specified element from this
	 * list and returns a reference to it
	 *
	 * @param  element the element to be removed from the list
	 * @return a reference to the removed element
	 * @throws ElementNotFoundException - if the target element is not found
	 */
	@SuppressWarnings("unchecked")
	public T remove(T element) {
		boolean elementFound = false;
		DoubleLinkedLinearNode<T> currentNodeAtIndex = null;
		ListIterator<T> itr = listIterator();
		
		// Attempt to locate and establish pointer to node containing element
		while(!elementFound && itr.hasNext()) {
			currentNodeAtIndex = (DoubleLinkedLinearNode<T>) itr.next();
			if(currentNodeAtIndex.getElement() == element) {
				elementFound = true;
			}
		}
		
		// If element is not found, throw exception
		if(!elementFound) {
			throw new ElementNotFoundException("");
		}
		else {
			
			// Remove node containing element
			previousNode = currentNodeAtIndex.getPrevious();
    		nextNode = currentNodeAtIndex.getNext();
    		previousNode.setNext(nextNode);
    		nextNode.setPrevious(previousNode);
    		currentNodeAtIndex.setPrevious(null);
    		currentNodeAtIndex.setNext(null);
    		
    		count--;
    		modCount++;
    		
    		return currentNodeAtIndex.getElement();
		}
	}

	/**
	 * Returns the first element in this list without removing it. 
	 *
	 * @return the first element in this list
	 * @throws EmptyCollectionException if the list is empty
	 */
	public T first() {
		if(isEmpty()) {
			throw new EmptyCollectionException("");
		}
		else {
			
			// Return pointer to head's next node
			DoubleLinkedLinearNode<T> firstNode = head.getNext();
			return firstNode.getElement();
		}
	}

	/**
	 * Returns the last element in this list without removing it. 
	 *
	 * @return the last element in this list  
	 * @throws EmptyCollectionException if the list is empty
	 */
	public T last() {
		if(isEmpty()) {
			throw new EmptyCollectionException("");
		}
		else {
			
			// Return pointer to tail's previous node
			DoubleLinkedLinearNode<T> lastNode = tail.getPrevious();
			return lastNode.getElement();
		}
	}

	/**
	 * Returns true if the specified element is found in this list and 
	 * false otherwise.
	 *
	 * @param  element the element that is sought in the list
	 * @return true if the element is found in this list
	 */
	@SuppressWarnings("unchecked")
	public boolean contains(T element) {
		boolean elementFound = false;
		DoubleLinkedLinearNode<T> currentNodeAtIndex = null;
		ListIterator<T> itr = listIterator();
		
		// Attempt to locate element, updating elementFound if element is located
		while(!elementFound && itr.hasNext()) {
			currentNodeAtIndex = (DoubleLinkedLinearNode<T>) itr.next();
			if(currentNodeAtIndex.getElement() == element) {
				elementFound = true;
			}
		}
		
		return elementFound;
	}

	/**
	 * Returns true if this list is empty and false otherwise.
	 *
	 * @return true if the list is empty, false otherwise
	 */
	public boolean isEmpty() {
		return count == 0;
	}

	/**
	 * Returns the number of elements in this list.
	 *
	 * @return the number of elements in the list
	 */
	public int size() {
		return count;
	}

	/**
	 * Returns an iterator for the elements in this list. 
	 *
	 * @return an iterator over the elements of the list
	 */
	public ListIterator<T> iterator() {
		return listIterator();
	}
	
	/**
	 * Returns a list iterator over the elements in this list (in proper sequence).
	 * 
	 * @return a list iterator over the elements in this list (in proper sequence)
	 */
	public ListIterator<T> listIterator() {
		return new DoubleLinkedListIterator();
	}

	/**
	 * Returns a list iterator over the elements in this list (in proper sequence),
	 * starting at the specified position in the list. The specified index indicates
	 * the first element that would be returned by an initial call to next.
	 * An initial call to previous would return the element with the specified index minus one.
	 * 
	 * @param startingIndex index of the first element to be returned from the list iterator (by a call to next)
	 * 
	 * @return a list iterator over the elements in this list (in proper sequence), starting at the specified position in the list
	 * 
	 * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index > size())
	 */
	public ListIterator<T> listIterator(int startingIndex) {
		return new DoubleLinkedListIterator(startingIndex);
	}
	
	/**  
     * Returns a string representation of this list. 
     *
     * @return a string representation of this list
     */
    @SuppressWarnings("unchecked")
	public String toString() {
    	
    	// Build a string representation of the list
    	StringBuilder output = new StringBuilder("[");
		DoubleLinkedLinearNode<T> currentNodeAtIndex = null;
    	Integer element;
    	Iterator<T> itr = iterator();
    	
    	// Iterate through list elements and add them to the string
		while(itr.hasNext()) {
			currentNodeAtIndex = (DoubleLinkedLinearNode<T>) itr.next();
			element = (Integer) currentNodeAtIndex.getElement();
			output.append(integerToChar(element));
			
			// Add a comma to all elements except for the last element
			if(itr.hasNext()) {
				output.append(",");
			}
		}
		
		// Complete the string and return it
		output.append("]\n");
		return output.toString();
    }
    
    /** Creates a new Node from supplied element, previousNode, and nextNode arguments
     * 
     * @param element The new element being added to the list
     * @param previousNode The node before the new node in the list
     * @param nextNode The node after the new node in the list
     * @return returns a new Node containing the new element
     */
    private DoubleLinkedLinearNode<T> createNewNode(T element, DoubleLinkedLinearNode<T> previousNode, DoubleLinkedLinearNode<T> nextNode) {
    	DoubleLinkedLinearNode<T> newNode = new DoubleLinkedLinearNode<T>(element);
    	
    	// Only update previous link if not null
    	if(previousNode != null) {
    		newNode.setPrevious(previousNode);
    	}
    	
    	// Only update next link if not null
    	if(newNode != null) {
    		newNode.setNext(nextNode);
    	}
    	
    	// Update list size and modification count
    	count++;
    	modCount++;
    	
    	return newNode;
    }
    
    /**
     * Converts Integer element to it's corresponding character value.
     * This private method is used to display A-E for Integers 1-5.
     * 
     * @param element the Integer element to convert to character
     * @return returns corresponding character (A-E) for intValue
     *         returns empty char ' ' for intValue out of range
     */
    private char integerToChar(Integer intValue) {
    	
    	// Convert Integer values to their corresponding character values
    	// Supported Integer values are: (1-5) == (A-E)
    	switch (intValue) {
			case 1:
				return 'A';
			case 2:
				return 'B';
			case 3:
				return 'C';
			case 4:
				return 'D';
			case 5:
				return 'E';
			default:
				return ' ';
    	}
    }
	
	/**
	 * LinkedListIterator iterator over the elements of a DoubleLinkedList.
	 */	
	private class DoubleLinkedListIterator implements ListIterator<T> {
		int iteratorModCount;	// The "version" of the list at the time the Iterator was created
		int previousIndex, nextIndex; // Used to store indexes for adjacent nodes 
		DoubleLinkedLinearNode<T> previousNode, nextNode; // Used to store pointers to adjacent nodes
		TraversalDirection currentTraversalDirection; // Used to keep track of current cursor position
		
		// The following fields specify which methods are supported
		boolean validRemove, addMethodSupported, setMethodSupported, removeMethodSupported;
				
		/**
		 * Sets up this iterator by calling the overloaded constructor with default index 0
		 *
		 */
		public DoubleLinkedListIterator() {
			
			// Establish the iterator at index 0 (Start of the list)
			this(0);
		}
		
		/**
		 * Sets up this iterator using the specified modCount and starting index parameter
		 * 
		 * @param startingIndex the index of the node that will be returned
		 * 						by the first call to next() 
		 */
		public DoubleLinkedListIterator(int startingIndex) {
			
			// Set current modification value - Integrity check
			// Used to detect modifications that are made outside of this iterator
			iteratorModCount = modCount;
			
			// Initialize fields to their starting values
			nextNode = head.getNext();
			nextIndex = 0;
			previousNode = head;
			previousIndex = -1;
			currentTraversalDirection = null;
			addMethodSupported = true;
			setMethodSupported = true;
			removeMethodSupported = true;
			validRemove = false; // used to check for IllegalStateException
			
			// Move iterator to startingIndex parameter
			// Default constructor sets nextIndex to 0 (First Node after Sentinel)
			// The first call to next() will return the first node in this list
			while(nextIndex < startingIndex) {
				next();
			}
		}

		/**
		 * Returns true if this list iterator has more elements
		 * when traversing the list in the forward direction
		 *
		 * @return  true if this iterator has at least one more element to deliver
		 *          in the iteration
		 */
		public boolean hasNext() {
			
			// Integrity Check (Detect modifications to the list outside this iterator)
			modCheck();
			
			// *** Using head and tail sentinel Nodes
			// - Must check for null element rather than null Node
			return nextNode.getElement() != null;
		}
		
		/**
		 * Returns the next Node in the iteration.
		 *
		 * @return  the next element in the iteration
		 * @throws  NoSuchElementException if an element not found exception occurs 
		 */
		@SuppressWarnings("unchecked")
		public T next() throws NoSuchElementException {
			
			// Integrity Check (Detect modifications to the list outside this iterator)
			modCheck();
			
			if(!hasNext()) {
				throw new NoSuchElementException("");
			}
			else {
				
				// Return next node
				previousNode = nextNode;
				previousIndex++;
				nextNode = nextNode.getNext();
				nextIndex++;
				validRemove = true; // Updates valid removal status
				
				// Track current cursor position
				currentTraversalDirection = TraversalDirection.Forward;
				
				return (T) previousNode;
			}
		}
		
		/**
		 * Returns true if this list iterator has more elements when
		 * traversing the list in the reverse direction.
		 * 
		 * @return  true if this iterator has at least one more element to deliver
		 *          in the iteration
		 */
		public boolean hasPrevious() {
			
			// Integrity Check (Detect modifications to the list outside this iterator)
			modCheck();
			
			// *** Using head and tail sentinel Nodes
			// - Must check for null element rather than null Node
			return previousNode.getElement() != null;
		}
		
		/**
		 * Returns the previous element in the list and
		 * moves the cursor position backwards. 
		 * 
		 * @return  the previous element in the iteration
		 * @throws  ElementNotFoundException if an element not found exception occurs
		 */
		@SuppressWarnings("unchecked")
		public T previous() throws ElementNotFoundException {
			
			// Integrity Check (Detect modifications to the list outside this iterator)
			modCheck();
			
			if(!hasPrevious()) {
				throw new ElementNotFoundException("");
			}
			else {
				
				// Return previous node
				nextNode = previousNode;
				nextIndex--;
				previousNode = previousNode.getPrevious();
				previousIndex--;
				validRemove = true;
				
				// Track current cursor position
				currentTraversalDirection = TraversalDirection.Reverse;
								
				return (T) nextNode;
			}
		}
		
		/**
		 * Returns the index of the element that would
		 * be returned by a subsequent call to next()
		 * 
		 * @return the next index in the iteration
		 */
		public int nextIndex() {
			
			// Integrity Check (Detect modifications to the list outside this iterator)
			modCheck();
			
			return nextIndex;
		}

		/**
		 * Returns the index of the element that would
		 * be returned by a subsequent call to previous()
		 * 
		 * @return the previous index in the iteration
		 */
		public int previousIndex() {
			
			// Integrity Check (Detect modifications to the list outside this iterator)
			modCheck();
			
			return previousIndex;
		}

		/**
		 * Removes from the list the last element that was returned by next() or previous()
		 * 
		 * @throws UnsupportedOperationException - if the add method is not supported by this list iterator
		 * @throws IllegalStateException if next() has not been called prior to remove
		 */
		public void remove() throws UnsupportedOperationException, IllegalStateException {
			
			// Supported method check
			if(!removeMethodSupported) {
				throw new UnsupportedOperationException("");
			}
			
			// Removal state check
			else if(!validRemove) {
				throw new IllegalStateException("");
			}
			else {
				
				// Integrity Check (Detect modifications to the list outside this iterator)
				modCheck();
				
				// Remove current node based on current cursor position
				switch (currentTraversalDirection) {
					case Forward:
						DoubleLinkedLinearNode<T> newPreviousNode = previousNode.getPrevious();
						previousNode.setPrevious(null);
						previousNode.setNext(null);
						previousNode = newPreviousNode;
						previousIndex--;
						previousNode.setNext(nextNode);
						nextNode.setPrevious(previousNode);
						nextIndex--;
						break;
					case Reverse:
						DoubleLinkedLinearNode<T> newNextNode = nextNode.getNext();
						nextNode.setNext(null);
						nextNode.setPrevious(null);
						nextNode = newNextNode;
						previousNode.setNext(nextNode);
						break;
					default:
						break;
				}
				
				count--;
				modCount++;
				iteratorModCount++;
				validRemove = false; // Updates valid removal status
			}	
		}
		
		/**
		 * Replaces the last element returned by next() or previous() with the specified element
		 * 
		 * @param element element to set into the list
		 * @throws UnsupportedOperationException - if the add method is not supported by this list iterator
		 * @throws IllegalStateException - if neither next nor previous have been called,
    	 *          or remove or add have been called after the last call to next or previous
		 */
		public void set(T element) throws UnsupportedOperationException, IllegalStateException {
			
			// Supported method check
			if(!setMethodSupported) {
				throw new UnsupportedOperationException("");
			}
			
			// Removal state check
			else if(!validRemove) {
				throw new IllegalStateException("");
			}
			else {
				
				// Integrity Check (Detect modifications to the list outside this iterator)
				modCheck();
				
				switch (currentTraversalDirection) {
					case Forward:
						previousNode.setElement(element);
						break;
					case Reverse:
						nextNode.setElement(element);
						break;
					default:
						break;
				}
				
				iteratorModCount++;
				modCount++;
			}
		}

		/**
		 * Inserts the specified element into the list
		 * 
		 * The element is inserted immediately before the element that
		 * would be returned by next(), if any, and after the element
		 * that would be returned by previous(), if any.
		 * 
		 * @param element element to be added to the list
		 * @throws UnsupportedOperationException - if the add method is
		 *          not supported by this list iterator
		 */
		public void add(T element) throws UnsupportedOperationException {
			
			// Supported method check
			if(!addMethodSupported) {
				throw new UnsupportedOperationException("");
			}
			
			// Integrity Check (Detect modifications to the list outside this iterator)
			modCheck();
			
			DoubleLinkedLinearNode<T> newNode = createNewNode(element,previousNode,nextNode);
			previousNode.setNext(newNode);
			nextNode.setPrevious(newNode);
			
			iteratorModCount++;
			validRemove = false; // Updates valid removal status			
		}
		
		/**
		 * Checks to make sure the list has not been modified outside of this iterator
		 * 
		 * @throws ConcurrentModificationException if the collection has changed
		 */
		private void modCheck() throws ConcurrentModificationException {
			if(iteratorModCount != modCount) {
				throw new ConcurrentModificationException();
			}
		}
	}
}



