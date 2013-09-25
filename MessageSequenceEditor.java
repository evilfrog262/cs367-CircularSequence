///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            MessageSequenceEditor
// Files:            LinkedCircularSequence.java,LinkedCircularSequenceIterator.java
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
import java.io.*;

/**
 * Creates an empty CircularSequence of messages and then prompts the user to
 * enter commands, which it will process until the user types q for quit.
 *
 * @author Kristin Cox and Will Kraus
 */
public class MessageSequenceEditor {
    
    // messages is a class data member; this allows all helper methods in this
    // class to be able to access it directly.
    private static CircularSequenceADT<String> messages = 
                                        new LinkedCircularSequence<String>();
    // NOTE: change the above line to:   new LinkedCircularSequence<String>();
    // after you have created and tested your LinkedCircularSequence class
    
    /**
     * Prompts the user to enter commands, which it will process until the user 
     * types q for quit.
     */
    public static void main(String[] args) { 
    	Scanner in = new Scanner(System.in);
    	boolean again = true;
        //holds the last message that was either copied or cut
        String buffer = null;
        
        //if filename is provided, open a scanner to read that file
        if (args.length > 1) {
        	System.out.println("invalid command-line arguments");
        	System.exit(0);
        }
        else if (args.length == 1) {
        	File inFile = new File(args[0]);
        	if (!inFile.exists() || !inFile.canRead()) {
        	    System.err.println("Problem with input file!");
        	    System.exit(1);
        	 }
        	try { 
        		in = new Scanner(inFile);
        	} catch (FileNotFoundException ex) {
        		System.out.println("problem with input file");
        	}
        }
        while (again) {
            System.out.print("enter command (? for help): ");
            String input = in.nextLine();

            // only do something if the user enters at least one character
            if (input.length() > 0) {
                char choice = input.charAt(0);  // strip off option character
                String remainder = "";  // used to hold the remainder of input
                if (input.length() > 1) {
                    // trim off any leading or trailing spaces
                    remainder = input.substring(1).trim(); 
                }

                switch (choice) {
                case '?':
                    System.out.println("s (save)  l (load)      d (display)");
                    System.out.println("n (next)  p (previous)  j (jump)");
                    System.out.println("a (add after)    i (insert before)");
                    System.out.println("c (copy)  v (paste)     x (cut)");
                    System.out.println("f (find)  r (replace)   q (quit)");            
                    break;

                case 's':
                    if (remainder.length() > 0)
                        save(remainder);
                    else
                        System.out.println("invalid command");
                    break;

                case 'l':
                    if (remainder.length() > 0)
                        load(remainder);
                    else
                        System.out.println("invalid command");
                    break;

                case 'd': 
                    display();
                    break;

                case 'n':
                    //go to next message and display context
                	contextNext();
                    break;

                case 'p':
                	//go to previous message and display context
                	contextPrev();
                    break;
                    
                case 'j':
                	if (remainder.equals("")) {
                		System.out.println("invalid command");
                	}
                    else {
                    	int N = 0;
                    	//get the integer given by the user
                    	try {
                    		N = new Integer(Integer.parseInt(remainder)); 
                    	} catch (NumberFormatException ex) {
                    		System.out.println("invalid command");
                    		break;
                    	}
                    	if (messages.isEmpty()) {
                        	System.out.println("no messages");
                        	break;
                        }                   	
                    	//positive case
                    	if (N >= 0) {
                    		//jump forward N - 1 messages
                    		for (int i = 0; i < N - 1; i++) {
                    			messages.next();
                    		}
                    		//messages.previous();
                    		//go to next message and display context
                    		contextNext();
                    	}
                    	//negative case
                    	else if (N < 0) {
                    		N *= -1;
                    		//jump backward N - 1 messages
                    		for (int i = 0; i < N - 1; i++) {
                    			messages.previous();
                    		}
                    		//messages.next();
                    		//go to the previous message and display context
                    		contextPrev();
                    	}
                    }
                	
                    break;        

                case 'a':            
                	if (remainder.equals("")) {
                		System.out.println("invalid command");
                	}
                	//insert the new message after the current one and make it current
                	else {
                		if (!messages.isEmpty()) {
                			messages.next();
                		}              		
                		messages.insert(remainder);
                		messages.next();
                		contextPrev();
                	}
                	break;

                case 'i':
                	if (remainder.equals("")) {
                		System.out.println("invalid command");
                	}
                	//insert the new message before the current one
                	else {
                		messages.insert(remainder);
                		messages.next();
                		contextPrev();
                		System.out.println("CURRENT MESSAGE:" + messages.getCurrent());
                		//messages.next();
                	}
                	break;

                case 'c':
                	if (messages.isEmpty()) {
                		System.out.println("no messages");
                	}
                	else {
                		buffer = messages.getCurrent();
                	}
                    break;
                    
                case 'v':
                    if (buffer == null) {
                    	System.out.println("nothing to paste");
                    }
                    //insert the copied message before the current one
                    else {
                    	messages.insert(buffer);
                    	messages.next();
                		contextPrev();
                    }
                    break;
                    
                case 'x':
                	if (messages.isEmpty()) {
                		System.out.println("no messages");
                	}
                	//remove the current message and store it in the buffer
                	else {
                		buffer = messages.removeCurrent();
                		if (messages.isEmpty()) {
                    		System.out.println("no messages");
                    	}
                		messages.next();
                		contextPrev();
                	}               	
                	break;

                case 'f':
                	boolean found = false;
                	if (remainder.equals("")) {
                		System.out.println("invalid command");
                	}
                	else if (messages.isEmpty()) {
                		System.out.println("no messages");
                	}
                	else {
                		messages.next();
                		Iterator<String> iter = messages.iterator();
                		//iterate through the messages
                		while(iter.hasNext() && !found) {
                			//look at data in next node
                			String next = iter.next();
                			//if it matches, make it the current message
                			if (next.contains(remainder)) {
                				found = true;
                				while (!messages.getCurrent().contains(remainder)) {
                					messages.next();
                				} 
                			}
                		}
                		if (found) {
                			//messages.next();
                			//messages.next();
                			messages.next();
                			contextPrev();
                		}
                		else {
                			messages.previous();
                			System.out.println("not found");
                		}
                	}
                	break;

                case 'r':
                	if (remainder.equals("")) {
                		System.out.println("invalid command");
                	}
                	//remove current message, insert new message in its place
                	else {
                		messages.removeCurrent();
                		messages.insert(remainder);
                		messages.previous();
                		contextNext();
                	}
                    break;
                    
                case 'q': 
                    System.out.println("quit");
                    again = false;
                    break;

                default:
                    System.out.println("invalid command");
                }
            }
        }
    }

    /**
     * If the message sequence is empty, display "no messages to save".  
     * Otherwise, save all the messages to a file named filename, one message
     * per line starting with the current message (and proceeding forward 
     * through the sequence).  If filename already exists, display "warning: 
     * file already exists, will be overwritten" before saving the messages.  
     * If filename cannot be written to, display "unable to save".
     *   
     * @param filename the name of the file to which to save the messages
     */
    private static void save(String filename) {
        if (messages.size() == 0) {
            System.out.println("no messages to save");
            return;
        }

        File file = new File(filename);
        
        // warn about overwriting existing file
        if (file.exists()) {
            System.out.print("warning: file already exists, ");
            System.out.println("will be overwritten");
        }
        
        // give message if not able to write to file
        if (file.exists() && !file.canWrite()) {
            System.out.println("unable to save");
            return;
        }
        
        // print each message to the file
        try {
            PrintStream outFile = new PrintStream(file);
            for (String msg : messages)
                outFile.println(msg);
            outFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("unable to save");
        }
    }

    /**
     * If a file named filename does not exists or cannot be read from, display
     * "unable to load".  Otherwise, load the messages from filename in the
     * order they are given before the current message.  The current message is
     * is not changed.
     * 
     * It is assumed that the input file contains one message per line, that
     * there are no blank lines, and that the file is not empty (i.e., it has
     * at least one line).
     * 
     * @param filename the name of the file from which to load the messages
     */
    private static void load(String filename) {
        File file = new File(filename);
        
        // check for existence and readability
        if (!file.exists() || !file.canRead()) {
            System.out.println("unable to load");
            return;
        }
        
        try {
            Scanner inFile = new Scanner(file);
            while (inFile.hasNext()) {
                // trim off any leading or trailing spaces before adding
                messages.insert(inFile.nextLine().trim());
                
                // since insert sets the current to the item just added,
                // move forward so the next item will get added after the one
                // that just got added
                messages.next();
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("unable to load");
        }
    }

    /**
     * If the message sequence is empty, display "no message".  Otherwise, 
     * display all of the messages in the sequence, starting with the current 
     * message, one message per line (going forward through the entire 
     * sequence).
     */
    private static void display() {
        if (messages.size() == 0)
            System.out.println("no messages");
        
        else {
            for (String msg : messages) {
                System.out.println(msg);
            }
        }
    }
    
    /**
     * Sets the current message to the previous message. Displays the context of 
     * the current message.
     */
    private static void contextPrev() {
    	if (messages.isEmpty()) {
        	System.out.println("no messages");
    	}
    	//if three messages, go back and print the message before
    	 if (messages.size() > 2) {
         	messages.previous();
         	messages.previous();
         	System.out.println(messages.getCurrent());
         }
    	messages.next();
       //print the current message
    	System.out.println(">>>" + messages.getCurrent() + "<<<");
        //print the message after
    	if(messages.size() > 1) {
        	messages.next();
        	System.out.println(messages.getCurrent());
        	messages.previous();
        }
    }
    
    /**
     * Sets the current message to the next message.  Displays the context of 
     * the current message.
     */
    private static void contextNext() {
    	if (messages.isEmpty()) {
        	System.out.println("no messages");
        }
    	//if three messages, go back and print the message before
        if(messages.size() > 2) {
        	System.out.println(messages.getCurrent());
        }
        messages.next();
        //print the current message
        System.out.println(">>>" + messages.getCurrent() + "<<<");
        //print the mssage after
        if (messages.size() > 1) {
        	messages.next();
        	System.out.println(messages.getCurrent());
        	messages.previous();
        }
    }
}
