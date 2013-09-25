///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:	 MessageSequenceEditor.java
// File:             EmptySequenceException.java
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

/**
 * An unchecked exception indicating that an inappropriate action has been
 * attempted on an empty list.
 *
 * @author Kristin Cox and Will Kraus
 */
public class EmptySequenceException extends RuntimeException {
	
	public EmptySequenceException() {
		super();
	}
	
	public EmptySequenceException(String msg) {
		super (msg);
	}

}
