import java.util.Iterator;
import java.util.LinkedList;

/**
 * Represents a cache storage in memory to hold generic data objects. 
 * Uses a linked list data structure to perform search, insert, and 
 * remove functions on the collection.
 * 
 */
public class Cache<T extends Comparable<T>> implements Iterable<T>{
    public LinkedList<T> list;
    public int sizeMax;
    
    public Cache(int sizeMax){
        list = new LinkedList<T>();
        this.sizeMax = sizeMax;
    }
    
    public boolean contains(int address){
        BTreeNode node = new BTreeNode(address);
        Iterator<T> iter = list.listIterator();
        T temp;
        while(iter.hasNext()){
            temp = iter.next();
            if(((Comparable<T>) node).compareTo(temp) == 0){
                return true;
            }
        }
        return false;
    }
    
    
    
    
    public T get(int address){
        BTreeNode node = new BTreeNode(address); //dummy node to accommodate address
        Iterator<T> iter = list.listIterator();
        T temp, copy;
        while(iter.hasNext()){
            temp = iter.next();
            if(((Comparable<T>) node).compareTo(temp) == 0){
                copy = temp; //copy & temp point to target now
                iter.remove(); //
                addObject(copy); //inserts removed node back at head of LL
                return temp;
            }
        }
        return null;
    }
    
    public LinkedList<T> dumpCacheList(){
    	return list;
    }
    
    /**
     * Inserts new data object at the top of the cache; removes 
     * and returns last element in cache if cache reaches capacity
     * @param element data object
     * @return the element that was removed due to overflow, or null
     */
    public T addObject(T element){
        T ret = null;
        if (list.size() >= sizeMax){
            ret = list.removeLast();
            list.addFirst(element);
        }
        else{
            list.addFirst(element);
        }
        return ret;
    }
    
    /**
     * Attempts to remove and return the specified element from the 
     * underlying list. If the element is not found in the list, null
     * is returned.
     * @param element object to remove from cache
     * @return the element that was successfully removed, or null
     */
    public T removeObject(T element){
        T temp = null;
        Iterator<T> iter = list.listIterator();
        while(iter.hasNext()){
            temp = iter.next();
            if(element.compareTo(temp) == 0){
                iter.remove();
                return temp;
            }
        }
        return null;
    }
    
    /**
     * Returns the index of the specified data object in the cache, 
     * otherwise returns -1 if not found
     * @param element data object
     * @return index of data in cache or -1 if not found
     */
    public int getIndexOf(T element){
        T temp = null;
        Iterator<T> iter = list.listIterator();
        int index = -1;
        while(iter.hasNext()){
            temp = iter.next();
            index++;
            if(element.compareTo(temp) == 0){
                return index;
            }
        }
        return -1;
    }
    
    public boolean contains(T element){
        return list.contains(element);
    }
    
    public int currentSize(){
        return list.size();
    }
    
    /**
     * Removes and returns the data object at the specified index of 
     * the cache, or throws exception if invalid index
     * @param index the index of the object to be removed
     * @return the data object previously at the specified index
     */
    public T removeAtIndex(int index){
        return list.remove(index);
    }
    
    /**
     * Removes all data objects from the cache
     */
    public void clearCache(){
        list.clear();
    }

    public Iterator<T> iterator() {
        return list.listIterator();
    }
}