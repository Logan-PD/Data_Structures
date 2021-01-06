package cpsc331.collections;

/**
* Exception to be thrown if an attempt is made to add an element
* to a set when this element already belongs to it.
*
*/

public class ElementFoundException extends Exception {

 /**
 * Constructs an ElementFoundException with the specified
 * message.
 *
 * @param message Message to report the error that has been identified
 *
 */

 public ElementFoundException (String message) {
  super(message);
 };

}
