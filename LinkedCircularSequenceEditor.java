///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:	 MessageSequenceEditor.java
// File:             LinkedCircularSequenceIterator.java
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
//////////////////////////// 80 columns wide //////////////////////////////////import java.util.*;
import java.util.*;

/**
 * Creates an Iterator that is able to traverse a LinkedCircularSequence.
 *
 * @author Kristin Cox and Will Kraus
 */
public class LinkedCircularSequenceIterator<E> implements Iterator<E>{
	//current node
	private DblListnode<E> curr;
	//node at the beginning of list
	private DblListnode<E> head;
	// check for if head equals curr first time around
	private boolean first = false;
	
	/**
	  * Constructs an iterator for the given sequence starting with the
	  * DblListnode given.
	  *
	  * @param: DblListnode<E> first: the first node in the list
	  */
	LinkedCircularSequenceIterator(DblListnode<E> first) {
		curr = first;
		head = first;
	}
	
	/**
	  * Returns true if the iteration has more elements.
	  *
	  * @return boolean: true if the list has more elements to iterate through
	  */
	public boolean hasNext() {
		if (curr == head && !first){
			first = true;
			return first;
		}
		return curr != head;
	}
	
	/**
	  * Returns the next element in the iteration. 
	  *
	  * @return E: data held by the next node in the iteration.
	  */
	public E next() {
		DblListnode<E> temp = curr;
		curr = curr.getNext();
		return temp.getData();
	}
	
	/**
	  * Not implemented.  Throws an UnsupportedOperationException when called.
	  */
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
