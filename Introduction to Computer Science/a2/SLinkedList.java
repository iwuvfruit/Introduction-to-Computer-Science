//package assignments2018.a2template;
import java.lang.Iterable;
import java.util.Iterator;

/**
 * A basic implementation of some methods in a singly linked list class.
 * @author Michael Langer 
 * @modified by Sayantan Datta
 * 
 * (Most of this code was adapted from textbooks 
 * e.g. by Frank Carrano,  Mark Allen Weiss, 
 *         Michael Goodrich and Roberto Tomassia)
 *         
 *  I put some Javadoc in this code, e.g.  @param, @return
 *  But I do not expect you to do so in this course 
 *  (and I generally don't do it when I am writing my own Java code)
 *  
 */

//  The "extends" (rather than "implements") in the generic type definition is a Java detail that you don't need to think about.

public class SLinkedList<E extends DeepClone<E>> implements Iterable<E>
{
	// Fields
	private SNode<E> head;			 
	private	SNode<E> tail;		
	private	int 	 size; 	

	// Constructor
	SLinkedList()
	{
    	head  = null;
	    tail  = null;
	    size  = 0;
	}
	
	/**
	 * Inserts the element to the specified position in this list
	 * where index is from 0 to size.   If the position is size, then 
	 * add element to the end of the list. 
	 * @param i  the position where the element should go
	 * @param element  the element to be added 
	 */
	public void add(int i, E element)
	{
		if ((i < 0) || (i > size))
		  throw new IndexOutOfBoundsException();
		
		if (i == 0)
			addFirst(element);
		else if (i == size)        //  Bot necessary to do this test.  Only do it because 
			addLast(element);       //  it is more efficient than what is below.
		else 
		{
			SNode<E> previousNode = getNode(i-1);     //  undefined if i==0
			SNode<E> newNode = new SNode<E>(element);
			newNode.next = previousNode.next;
			previousNode.next = newNode;
			size++;
		}			
	}
	
	/*
	 * add a new element to front of list 
	 * @param element the new element
	 */
	public void addFirst(E element)
	{	
		SNode<E> newNode = new SNode<E>(element); 
		size++;
		
		if (head == null)
		{
			head = newNode;
			tail = head; 
		}
		else
		{
			newNode.next = head;  //  Why not newNode.setNext(head)  ?  You could but you don't need to.                           
			head = newNode;       //  i.e. Don't need to use get and set methods inside the class.
		}
	}

	/*
	 * add a new element to the end of the list
	 * @param element  the new element 
	 */
	public void addLast(E element)
	{
		SNode<E> newNode = new SNode<E>(element); 
		size++;
		if (head == null)
		{
			head = newNode;
			tail = newNode;
		}
		else
		{
			tail.next = newNode;
			tail = newNode;
		}
	}
	      
	/*
	 * remove all elements from the list
	 */
	public void clear()
	{
		head  = null;
		tail  = null;
		size  = 0;
	}

	/*
	 * get the element at position i in the list (0,..., size -1 )
	 * @param i  the index of the element
	 * @return the element to get
	 */
	public E get(int i)
	{
		return getNode(i).element;
	}
	
	public boolean isEmpty()
	{ 
		return (size == 0); 
	}

	/*
	 *   Removes the element at index i  in  0 to size-1, and return it.
	 *   @return the element at index i.
	 */
	public E remove(int i)
	{ 
		if ((i < 0) || (i >= size))
			  throw new IndexOutOfBoundsException();
		else {
			
			//  first deal with special case that size == 1, i == 0
			if ((size == 1) && (i == 0)) //  only one node in list
			{  	
				size--;
				SNode<E> cur = head;
				head = null;  
				tail = null;
				return cur.element; 
			}
			
			//  Now we can assume that size > 1.   
			//  We first deal with case that i == 0
			SNode<E> cur = head;
			size--;
			if (i == 0){
				head = head.next;
				return cur.element;
			}
			else //  we can assume that i > 0;
			{  
				cur = getNode(i-1);
				SNode<E> nodeToRemove = cur.next;
				cur.next = nodeToRemove.next;
				if (nodeToRemove.next == null)   //  removing the tail
					tail = cur;
				else
					nodeToRemove.next = null;   //  be safe
				return nodeToRemove.element;
			}
		}
	}

	/**
	 * Remove element at front of the list.
	 * @return first element in list
	 */
	public	E removeFirst(){  
		return remove(0);
	}

	/**
	 * Remove element at back of list.
	 * @return  last element 
	 */
	
	public E removeLast(){		
		return remove(size-1);
	}
	

	/**
	 *   Sets the ith element in the list.   
	 *   @param i  the index of element to be set
	 *   @param e  the new element that replaces the old element   
	 */
	public void set(int i,  E  e)
	{
		if ((i < 0) || (i >= size))
			  throw new IndexOutOfBoundsException();
		else
			getNode(i).element = e;
	}
	    
	/**
	 *    Show all elements in list.    Alternatively I could have overriden the toString() method and then
	 *    just print the object,  since printing an object automatially calls the toString() method.
	 */
	public void show()
	{			 
		SNode<E> cur = head;
		while (cur != null)
		{
			System.out.print("  " + cur.element.toString());
			cur = cur.next;
		}
		System.out.println("  (size is " + size + " )");
	}
	
	public int size()
	{
		return size;
	}
	
	/*
 	 *  TODO: Makes a deep copy of the this linked list.
 	 *  Hint: Use E.deepCopy().
 	 */
 	public SLinkedList<E> deepClone()
 	{
 		/**** ADD CODE HERE ****/
 		SLinkedList<E> newList = new SLinkedList<E>();
 		for(E e: this) {
 			newList.addLast(e.deepClone()); //recursion. runtime is O(n)
 		}
 		return newList;
 		}
 	
 	@Override
    public String toString()
    {	
   		String ret = "";
  		if (head == null)
 			return "";
  		
  		// Hint: Use T.toString().
  		SNode<E> current = head;
  		while (current != null)
 		{
 			ret = ret + current.element.toString();
 			current = current.next;
 		}
  		
  		return ret;
     }
   	   	
   	@Override
	public SLLIterator iterator() 
   	{
		SLLIterator iter = new SLLIterator(this);
		return iter;
	}

	private class SLLIterator implements Iterator<E>
	{   // use a different generic type, since we're defining a new class here
		SNode<E>    cur;
		
		// constructor
		SLLIterator(SLinkedList<E>  list)
		{			
			cur = list.head;
		}
		
		@Override
		public boolean hasNext() 
		{
			return (cur != null);
		}

		@Override
		public E next() 
		{
			SNode<E> tmp = cur;
			cur = cur.next;
			return tmp.element;
		}
	}

	//   The next two methods are private.  The client has no access to the nodes of the linked list, 
	//   but rather the client can only access the elements that are stored in the list.
	
	
	/*
	 *     @param  index of node to get
	 *     @return ith SNode of the linked list
	 *     
	 */
	private SNode<E> getNode(int i)
	{
		if ((i < 0) || (i >= size))
			  throw new IndexOutOfBoundsException();
		else
		{		
			if (i == 0)  	//  only one node in list
				return head; 
			else
			{
				int index = 0;	
				SNode<E> cur = head;
				while (index < i){   
					cur = cur.next;
					index++;
				}
				return cur;
			}
		}
	}
	
	//  inner class
	private class SNode<T> 
	{    //  I have to use a different generic type since I am defining a class here. 

		private T	  		element;		
		private	SNode<T> 	next;		

		SNode(T element){
			this.element = element; 
			next = null;
		}
	}
}

