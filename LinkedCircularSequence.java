///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:	 MessageSequenceEditor.java
// File:             LinkedCircularSequence.java
// Semester:         Spring 2012
//
// Author:           Kristin Cox
// CS Login:         kcox
// Lecturer's Name:  Beck Hasti
// Lab Section:      001
//
//                   PAIR PROGRAMMERS COMPLETE THIS SECTION
// Pair Partner:     Will Kraus
// CS Login:         kraus
// Lecturer's Name:  Beck Hasti
// Lab Section:      002
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.*;

/**
 * Creates and manipulates a doubly linked list of DblListnodes.
 *
 * @author Kristin Cox and Will Kraus
 */
public class LinkedCircularSequence<E> implements CircularSequenceADT<E> {
	//**data members**//
	//points to beginning of list
	private DblListnode<E> head;
	//points to current node
	private DblListnode<E> current;
	//holds number of items in list
	private int size;
	
	/**
	  * This constructor creates a doubly linked list.  It creates one
	  * DblListnode named head and initializes the next fields and size.
	  */
	public LinkedCircularSequence() {
		head = new DblListnode(null);
		//current starts at the beginning
		current = head;
		//makes head point backwards and forwards to itself
		head.setNext(head);
		head.setPrev(head);
		size = 0;
	}
	
	/**
	  * Returns the current item. If the CircularSequence is empty, an 
	  * EmptySequenceException is thrown. 
	  *
	  * @return E: the data held in the current node.
	  */
	public E getCurrent() {
		if (isEmpty()) {
			throw new EmptySequenceException();
		}
		return current.getData();
	}
	
	/**
	  * Adds the given item immediately before the current item. After the new 
	  * item has been added, the new item becomes the current item. 
	  * 
	  * @param E: data to be inserted into the list
	  */
	public void insert (E item) {
		//check if it is the first item added
		if (size == 0) {
			//then set head to hold that data
			head.setData(item);
		}
		//if not the first item added
		else {
			//create a node holding the data
			DblListnode<E> newNode = new DblListnode<E>(item);
			//the new nodes pointers
			newNode.setPrev(current.getPrev());
			newNode.setNext(current);
			//set the previous node to point to it
			current.getPrev().setNext(newNode);
			//set the current node to point backwards to it
			current.setPrev(newNode);
			//make current point to the new node
			current = current.getPrev();
		}
		size++;
	}
	
	/**
	  * Returns true if this CircularSequence is empty and false otherwise. 
	  *
	  * @return boolean: true if sequence is empty
	  */
	public boolean isEmpty() {
		return size==0;
	}
	
	/**
	  * Returns an iterator for this CircularSequence.  
	  *
	  * @return Iterator<E>: iterator to traverse this CircularSequence
	  */
	public Iterator<E> iterator() {
		return new LinkedCircularSequenceIterator<E>(current);
	}
	
	/**
	  * Advances the current item forward one item resulting in the item that is 
	  * immediately after the current item becoming the current item. If the 
	  * CircularSequence is empty initially, an EmptySequenceException is thrown. 
	  */
	public void next() {
		if (size == 0) {
			throw new EmptySequenceException();
		}
		current = current.getNext();
	}
	
	/**
	  * Moves the current item backwards one item resulting in the item that is 
	  * immediately before the current item becoming the current item. If the 
	  * CircularSequence is empty initially, an EmptySequenceException is thrown. 
	  */
	public void previous() {
		if (size == 0) {
			throw new EmptySequenceException();
		}
		current = current.getPrev();
	}

	/**
	  * Removes and returns the current item. The item immediately after the 
	  * removed item then becomes the current item. If the CircularSequence is 
	  * empty initially, an EmptySequenceException is thrown. 
	  *
	  * @return E: the data held in the node that was removed.
	  */
	public E removeCurrent() {
		if(size == 0){
			throw new EmptySequenceException();
		}
		//if you are deleting the head node, make head the next node
		if (current == head) {
			head = head.getNext();
		}
		//create a node to hold onto removed data
		DblListnode<E> temp = current;
		//change the previous and next nodes to point around the removed node
		current.getPrev().setNext(current.getNext());
		current.getNext().setPrev(current.getPrev());
		//update current and size
		current = current.getNext();
		size--;
		return temp.getData();
	}
	
	/**
	  * Returns the number of items in this CircularSequence. 
	  */
	public int size() {
		return size;
	}
	
	
}
