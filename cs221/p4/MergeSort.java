import java.util.*;

/**
 * Class for sorting DoubleLinkedLists using either natural order
 * or a Comparator.
 *
 * @author spanter, mvail, jcarlson
 */
public class MergeSort
{
	/**
	 * Sorts a list that implements the DoubleLinkedListADT using the
	 * natural ordering of list elements.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The data type in the list must extend Comparable
	 * @param list
	 *            The list that will be sorted
	 * @see DoubleLinkedListADT 
	 */
	public static <T extends Comparable<T>> void sort(DoubleLinkedListADT<T> list) {
		//Get size of list
		int listSize = list.size();
				
		//Determine if base case has been reached (empty or single-element list)
		if(listSize < 2) {
			return;
		}
		else {
			//Set MidPoint
			int midPoint = listSize / 2;
			
			//Create and initialize first sublist
			WrappedDLL<T> firstHalf = new WrappedDLL<T>();
			ListIterator<T> itr = list.listIterator();
			//Copy elements from first to middle (inclusive) to first sublist 
			for(int i = 0; i < midPoint; i++) {
				firstHalf.addToRear(itr.next());
			}
			
			//Create and initialize second sublist
			WrappedDLL<T> secondHalf = new WrappedDLL<T>();
			//Copy the rest of the elements to second sublist
			for(int i = midPoint; i < listSize; i++) {
				secondHalf.addToRear(itr.next());
			}
			
			//Sort first sublist
			sort(firstHalf);
			
			//Sort second sublist
			sort(secondHalf);
			
			//Rebuild list
			int rebuildIndex = 0;
			while(!firstHalf.isEmpty() || !secondHalf.isEmpty()) {
				//If firstHalf sublist is empty
				if(firstHalf.isEmpty()) {
					//Move first (smallest) element of secondHalf sublist to list at current rebuildIndex
					list.set(rebuildIndex,secondHalf.removeFirst());
				}
				//Else if secondHalf sublist is empty
				else if(secondHalf.isEmpty()) {
					//Move first (smallest) element of firstHalf sublist to list at current rebuildIndex
					list.set(rebuildIndex,firstHalf.removeFirst());
				}
				//Else both sublists still contain elements
				else {
					//If first element of firstList is smaller or equal to first element of secondList
					if(firstHalf.first().compareTo(secondHalf.first()) < 1) {
						//Move first (smallest) element of firstHalf sublist to list at current rebuildIndex
						list.set(rebuildIndex,firstHalf.removeFirst());
					}
					//Else first element of secondHalf is smaller than first element of firstList
					else {
						//Move first (smallest) element of secondHalf sublist to list at current rebuildIndex
						list.set(rebuildIndex,secondHalf.removeFirst());
					}
				}
				rebuildIndex++; //Continue rebuild at next index of list
			}
		}
	}

	/**
	 * Sorts a DoubleLinkedListADT with the provided Comparator.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The type of list to sort
	 * @param list
	 *            The list to sort
	 * @param c
	 *            The Comparator to use
	 * @see DoubleLinkedListADT
	 */
	public static <T> void sort(DoubleLinkedListADT<T> list, Comparator<T> c) {
		//Get size of list
		int listSize = list.size();
				
		//Determine if base case has been reached (empty or single-element list)
		if(listSize < 2) {
			return;
		}
		else {
			//Set MidPoint
			int midPoint = listSize / 2;
			
			//Create and initialize first sublist
			WrappedDLL<T> firstHalf = new WrappedDLL<T>();
			ListIterator<T> itr = list.listIterator();
			//Copy elements from first to middle (inclusive) to first sublist 
			for(int i = 0; i < midPoint; i++) {
				firstHalf.addToRear(itr.next());
			}
			
			//Create and initialize second sublist
			WrappedDLL<T> secondHalf = new WrappedDLL<T>();
			//Copy the rest of the elements to second sublist
			for(int i = midPoint; i < listSize; i++) {
				secondHalf.addToRear(itr.next());
			}
			
			//Sort first sublist
			sort(firstHalf, c);
			
			//Sort second sublist
			sort(secondHalf, c);
			
			//Rebuild list
			int rebuildIndex = 0;
			while(!firstHalf.isEmpty() || !secondHalf.isEmpty()) {
				//If firstHalf sublist is empty
				if(firstHalf.isEmpty()) {
					//Move first (smallest) element of secondHalf sublist to list at current rebuildIndex
					list.set(rebuildIndex,secondHalf.removeFirst());
				}
				//Else if secondHalf sublist is empty
				else if(secondHalf.isEmpty()) {
					//Move first (smallest) element of firstHalf sublist to list at current rebuildIndex
					list.set(rebuildIndex,firstHalf.removeFirst());
				}
				//Else both sublists still contain elements
				else {
					//If first element of firstList is smaller or equal to first element of secondList
					if(c.compare(firstHalf.first(),secondHalf.first()) < 1) {
						//Move first (smallest) element of firstHalf sublist to list at current rebuildIndex
						list.set(rebuildIndex,firstHalf.removeFirst());
					}
					//Else first element of secondHalf is smaller than first element of firstList
					else {
						//Move first (smallest) element of secondHalf sublist to list at current rebuildIndex
						list.set(rebuildIndex,secondHalf.removeFirst());
					}
				}
				rebuildIndex++; //Continue rebuild at next index of list
			}
		}

	}

	/**
	 * Finds the smallest element in a list according to the natural ordering of 
	 * list elements. Does not alter the order of list elements.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The type of object we are comparing
	 * @param list
	 *            The list we are passed
	 * @return The smallest element or null if list is empty
	 * @see DoubleLinkedListADT
	 */
	public static <T extends Comparable<T>> T findSmallest(DoubleLinkedListADT<T> list) {
		//Get size of list
		int listSize = list.size();
		
		//Check for initial empty list
		if(listSize == 0) {
			return null;
		}
		
		//Determine if base case has been reached (two-element list)
		if(listSize == 2) {
			if(list.first().compareTo(list.last()) < 0) {
				return list.first();
			}
			else {
				return list.last();
			}	
		}
		else if(listSize == 1) {
			return list.first();
		}
		else {
			
			//Create a copy of list to avoid modifications to original
			WrappedDLL<T> listCopy = new WrappedDLL<T>();
			for(int i=0; i < listSize; i++) {
				listCopy.addToRear(list.get(i));
			}
			
			//Remove last element and compare it recursively
			T lastElement = listCopy.removeLast();
			T smallestElement = findSmallest(listCopy);
			if(lastElement.compareTo(smallestElement) < 1) {
				return lastElement;
			}
			else {
				return smallestElement;
			}
			
		}
	}

	/**
	 * Finds the smallest element in a list with a Custom comparator. Does not
	 * alter the order of list elements.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The type of object we are comparing
	 * @param list
	 *            The list we are passed
	 * @param c
	 *            The comparator to use
	 * @return The smallest element or null if list is empty
	 * @see DoubleLinkedListADT
	 */
	public static <T> T findSmallest(DoubleLinkedListADT<T> list, Comparator<T> c) {
		//Get size of list
		int listSize = list.size();
		
		//Check for initial empty list
		if(listSize == 0) {
			return null;
		}
		
		//Determine if base case has been reached (two-element list)
		if(listSize == 2) {
			if(c.compare(list.first(),list.last()) < 0) {
				return list.first();
			}
			else {
				return list.last();
			}	
		}
		else if(listSize == 1) {
			return list.first();
		}
		else {
			
			//Create a copy of list to avoid modifications to original
			WrappedDLL<T> listCopy = new WrappedDLL<T>();
			for(int i=0; i < listSize; i++) {
				listCopy.addToRear(list.get(i));
			}
			
			//Remove last element and compare it recursively
			T lastElement = listCopy.removeLast();
			T smallestElement = findSmallest(listCopy, c);
			if(c.compare(lastElement,smallestElement) < 0) {
				return lastElement;
			}
			else {
				return smallestElement;
			}
			
		}
	}

	/**
	 * Finds the largest element in a list according to the natural ordering of
	 * list elements. Does not alter the order of list elements.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The type of object we are comparing
	 * @param list
	 *            The list we are passed
	 * @return The largest element or null if list is empty
	 * @see DoubleLinkedListADT
	 */
	public static <T extends Comparable<T>> T findLargest(DoubleLinkedListADT<T> list) {
		//Get size of list
		int listSize = list.size();
		
		//Check for initial empty list
		if(listSize == 0) {
			return null;
		}
		
		//Determine if base case has been reached (two-element list)
		if(listSize == 2) {
			if(list.first().compareTo(list.last()) > 0) {
				return list.first();
			}
			else {
				return list.last();
			}	
		}
		else if(listSize == 1) {
			return list.first();
		}
		else {
			
			//Create a copy of list to avoid modifications to original
			WrappedDLL<T> listCopy = new WrappedDLL<T>();
			for(int i=0; i < listSize; i++) {
				listCopy.addToRear(list.get(i));
			}
			
			//Remove last element and compare it recursively
			T lastElement = listCopy.removeLast();
			T largestElement = findLargest(listCopy);
			if(lastElement.compareTo(largestElement) > 0) {
				return lastElement;
			}
			else {
				return largestElement;
			}
			
		}
	}

	/**
	 * Finds the largest element in a list with a Custom comparator. Does not
	 * alter the order of list elements.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The type of object we are comparing
	 * @param list
	 *            The list we are passed
	 * @param c
	 *            The comparator to use
	 * @return The largest element or null if list is empty
	 * @see DoubleLinkedListADT
	 */
	public static <T> T findLargest(DoubleLinkedListADT<T> list, Comparator<T> c) {
		//Get size of list
		int listSize = list.size();
		
		//Check for initial empty list
		if(listSize == 0) {
			return null;
		}
		
		//Determine if base case has been reached (two-element list)
		if(listSize == 2) {
			if(c.compare(list.first(),list.last()) > 0) {
				return list.first();
			}
			else {
				return list.last();
			}	
		}
		else if(listSize == 1) {
			return list.first();
		}
		else {
			
			//Create a copy of list to avoid modifications to original
			WrappedDLL<T> listCopy = new WrappedDLL<T>();
			for(int i=0; i < listSize; i++) {
				listCopy.addToRear(list.get(i));
			}
			
			//Remove last element and compare it recursively, returning the smaller element
			T lastElement = listCopy.removeLast();
			T largestElement = findLargest(listCopy, c);
			if(c.compare(lastElement,largestElement) > 0) {
				return lastElement;
			}
			else {
				return largestElement;
			}
			
		}
	}
}
