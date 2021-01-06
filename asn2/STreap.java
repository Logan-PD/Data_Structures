package cpsc331.assignment2;

import java.util.NoSuchElementException;
import cpsc331.collections.ElementFoundException;

/**
*
* Provides a Treap Storing Values from an Ordered Type E Using Priorities
* from an Ordered Type&nbsp;P &mdash; Used for Testing
*
*/

// This version does not really provide anything at all - it is only
// supplied so that code can be compiled

public class STreap<E extends Comparable<E>, P extends Comparable<P>> {

  // Provides a node in this Treap
  
  class STreapNode {
  
    // Data Fields
    
    E element;           // Element of E stored at this TreapNode
    P priority;          // Priority of element stored at this TreapNode
    
    STreapNode left;     // Left child of this TreapNode
    STreapNode right;    // Right child of this TreapNode
    STreapNode parent;   // Parent of this TreapNode;
    
    
    // Constructor; constructs an STreepNode storing a given element e of E
    // with a given priority p of P whose left child, right child and parent
    // are null
    
    STreapNode(E e, P p) {
    
      element = e;
      priority = p;
      left = null;
      right = null;
      parent = null;
    
    }
    
    // Returns the element stored at this node
    
    E element() {
    
      return element;
    
    }
    
    // Returns the priority of the data at this node
    
    P priority() {
    
      return priority;
    
    }
    
    // Returns the left child of this node
    
    STreapNode left() {
    
      return left;
    
    }
    
    // Returns the right child of this node
    
    STreapNode right() {
    
      return right;
    
    }
    
    // Returns the parent of this node
    
    STreapNode parent() {
    
      return parent;
    
    }
  
  }
  
  // Data Fields
  
  private STreapNode root;
  
  /**
  *
  * Constructs an empty STreap<br><br>
  *
  * Precondition: None<br>
  * Postcondition: An empty STreap (satisfying the above Treap Invariant)
  *                has been created.
  *
  */
  
  public STreap() {
  
    root = null;
    
  }

  // *******************************************************************
  //
  // Additional Code Needed for Testing
  //
  // *******************************************************************
  
  // Returns a reference to the root of this STreap
  
  STreapNode root() {
  
    return root;
    
  }
  
}
