/**
 * LinearNode represents a node in a linked list.
 *
 * @author Java Foundations, jcarlson
 * @version 4.0
 */
public class DoubleLinkedLinearNode<T>
{
	private DoubleLinkedLinearNode<T> previous, next;
    private T element;
    
    /**
     * Creates an empty node.
     */
    public DoubleLinkedLinearNode()
    {
        this(null);
    }
    
    /**
     * Creates a node storing the specified element.
     *
     * @param elem the element to be stored within the new node
     */
    public DoubleLinkedLinearNode(T element)
    {
    	previous = null;
    	next = null;
        this.element = element;
    }
    
    /**
     * Returns the previous node.
     *
     * @return the node previous to the current one
     */
    public DoubleLinkedLinearNode<T> getPrevious()
    {
        return previous;
    }
    
    /**
     * Returns the node that follows this one.
     *
     * @return the node that follows the current one
     */
    public DoubleLinkedLinearNode<T> getNext()
    {
        return next;
    }
    
    /**
     * Returns the element stored in this node.
     *
     * @return the element stored in this node
     */
    public T getElement()
    {
        return element;
    }
    
    /**
     * Sets the previous node.
     *
     * @param node the node to be set as previous to the current one
     */
    public void setPrevious(DoubleLinkedLinearNode<T> node)
    {
        previous = node;
    }
    
    /**
     * Sets the node that follows this one.
     *
     * @param node the node to be set to follow the current one
     */
    public void setNext(DoubleLinkedLinearNode<T> node)
    {
        next = node;
    }
    
    /**
     * Sets the element stored in this node.
     *
     * @param element the element to be stored in this node
     */
    public void setElement(T element)
    {
        this.element = element;
    }
}
