package cpsc331.assignment2;

import java.util.NoSuchElementException;
import cpsc331.collections.ElementFoundException;
import cpsc331.assignment2.STreap;

/**
*
* Provides a Treap Storing Values from an Ordered Type E Using Priorities
* from an Ordered Type&nbsp;P
*
*/

// Treap Invariant: A binary tree T is represented tha satisfies the
// Treap Properties given below.
//
// a) Every node in T stores an element e of E and a priority p in P.
// b) For each node x in T, each element (in E) stored in a node in the left
//    subtree of the tree with root x is less than the element stored at x,
//    and every element stored in a node in the right subtree of the subtree
//    with root x is greater than the element stored at x.
// c) For each node x in T, he priority stored at x is greater than or equal
//    to each priority stored at a child of x.
// d) Every subtree of T is also a treap, that is, every subtree satisifes
//    the above properties (a)-(c).

public class Treap<E extends Comparable<E>, P extends Comparable<P>> {

  // Provides a node in this Treap

  class TreapNode {

    // Data Fields

    E element;          // Element of E stored at this TreapNode
    P priority;         // Priority of element stored at this TreapNode

    TreapNode left;     // Left child of this TreapNode
    TreapNode right;    // Right child of this TreapNode
    TreapNode parent;   // Parent of this TreapNode;


    // Constructor; constructs a TreepNode storing a given element e of E
    // with a given priority p of P whose left child, right child and parent
    // are null

    TreapNode(E e, P p) {

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

    TreapNode left() {

      return left;

    }

    // Returns the right child of this node

    TreapNode right() {

      return right;

    }

    // Returns the parent of this node

    TreapNode parent() {

      return parent;

    }

  }

  // Data Fields

  private TreapNode root;

  /**
  *
  * Constructs an empty Treap<br><br>
  *
  * Precondition: None<br>
  * Postcondition: An empty Treap (satisfying the above Treap Invariant)
  *                has been created.
  *
  */

  public Treap() {

    root = null;

  }

  // Used for testing: produces a Treap from a STreap

  public Treap(STreap<E, P> s) {

    root = makeSubTreap(s.root());

  }

  // Used for Testing: Converts a subtree into a STreap with a given
  // STreapNode as root into a corresponding tree with TreapNodes as
  // nodes

  TreapNode makeSubTreap(STreap<E, P>.STreapNode s) {

    if (s != null) {

      TreapNode t = new TreapNode(s.element(), s.priority());

      t.left = makeSubTreap(s.left());
      if (t.left != null) {
        (t.left).parent = t;
      };

      t.right = makeSubTreap(s.right());
      if (t.right != null) {
        (t.right).parent = t;
      };

      return t;

    } else {

      return null;

    }

  }

 // *******************************************************************
 //
 // Searches
 //
 // *******************************************************************

  /**
  * Returns a TreapNode storing a given key<br>
  *
  * @param key the element to be searched for
  * @return the TreapNode in this Treap storing the input key
  * @throws NoSuchElementException if the key is not stored in this treap
  *
  */

  // Precondition:
  // a) This Treap satisfies the above Treap properties.
  // b) A non-null key with type E is given as input.
  //
  // Postcondition:
  // a) If the key is stored in this Treap then the node storing it is
  //    returned as output. A NoSuchElementException is thrown otberwise.
  // b) This Treap has not been changed, so it still satisfies the
  //    Treap properties.

  public TreapNode search(E key) throws NoSuchElementException {

    if (root == null) {

      throw new NoSuchElementException(key.toString() + " not found.");

    } else {

      return get(key, root);

    }

  }

  //
  // Searches for a given key in the subtree with a given node as root
  //
  // Precondition:
  // a) This Treap satisfies the above Treap properties.
  // b) key is a non-null input with type E.
  // c) x is a non-null TreapNode in this Treap, that is also given
  //    as input.
  //
  // Postcondition:
  // a) If the key is stored in the subtree with root x, then the node
  //    storing the key is returned as output. A NoSuchElementExeption
  //    is thrown otherwise.
  // b) This Treap has not been changed, so it still satisfies the
  //    Treap properties.
  //
  //
  //
  // *Dr. Eberlys slides and example code 'BSTDictionary.java'
  // were used to write this method*
  //
  // Bound function: Depth of subtree with root x, plus one
  //


  private TreapNode get(E key, TreapNode x) throws NoSuchElementException {

    if(x==null){
      throw new NoSuchElementException("No node associated with that key was found.");

    }else{

      int result = key.compareTo(x.element);

      if(result < 0){ //key is less than nodes element
        return get(key, x.left);  // branch left

      }else if(result > 0){ //key is greater than nodes element
        return get(key, x.right); //branch right

      }else{  // key == nodes element
        return x;
      }
    }
  }

  // *******************************************************************
  //
  // Utility Methods Needed for Insertions and Deletions
  //
  // *******************************************************************



  //
  // Implements a Left Rotation at a Node
  //
  // Precondition:
  // a) T is a binary tree whose nodes store non-null ordered pairs of
  //    elements of E and priorities in P.
  // b) A non-null node z in T, which has a non-null right child, is given
  //    as input.
  //
  // Postcondition:
  // a) A left rotation is carried out at z - and T is otherwise unchanged.
  //
  //
  // Pseudo-code from "Cormen, Introduction to Algorithms" was used as a
  // source for this code.
  //

  private void leftRotate (TreapNode z) {
    TreapNode oldright = z.right; //save a reference to z's right child
    z.right = oldright.left;
    if(oldright.left != null){
      oldright.left.parent = z; //update parent if it exists
    }
    oldright.parent = z.parent;
    if(z.parent == null){ //update root if z was the root
      root = oldright;
      root.parent = null;
    }else if(z == z.parent.left){ //else if z is a left child
      z.parent.left = oldright;
    }else{  //else z is a right child
      z.parent.right = oldright;
    }
    oldright.left = z;
    z.parent = oldright;
  }

  //
  // Implements a Right Rotation at a Node
  //
  // Precondition:
  // a) T is a binary tree whose nodes store non-null ordered pairs of
  //    elements of E and priorities in P.
  // b) A non-null node z in T, which has a non-null left child, is given
  //    as input.
  //
  // Postcondition;
  // a) A right rotation is carried out at z - and T is otherwise unchanged.
  //
  //
  // Pseudo-code from "Cormen, Introduction to Algorithms" was used as a
  // source for this code.
  //

  private void rightRotate (TreapNode z) {
    TreapNode oldleft = z.left; //save a reference to z's left child
    z.left = oldleft.right;
    if(oldleft.right != null){
      oldleft.right.parent = z; //update parent if it exists
    }
    oldleft.parent = z.parent;
    if(z.parent == null){   //update root if z was the root
      root = oldleft;
      root.parent = null;
    }else if(z == z.parent.right){  //else if z is a right child
      z.parent.right = oldleft;
    }else{  //else z is a left child
      z.parent.left = oldleft;
    }
    oldleft.right = z;
    z.parent = oldleft;
  }


  // *******************************************************************
  //
  // Insertions
  //
  // *******************************************************************

  /**
  *
  * Inserts a given element into a treap with a given priority<br>
  * @param key the element to be inserted
  * @param priority the priority to be used
  * @throws ElementFoundException if the element is already stored
  *         in this treap
  *
  */

  // Precondition:
  // a) This Treap satisfies the above Treap properties.
  // b) key is a non-null input with type E and priority is a
  //    non-null input with type p
  //
  // Postcondition:
  // a) If the key does not already belong to the subset of E stored
  //    at the nodes in this Treap then it is added to this set - with
  //    the input priority used as the priority for the node storing
  //    this key. No other changes have been made to the subset of E
  //    stored at nodes in this Treap, and the priorities of the
  //    elements of E already in this subset have not been changed.
  //    An ElementFound Exception is thrown and this Treap is not
  //    changed, if the input key already belongs to this subset
  //    of E.
  // b) This Treap satisfies the Treap properties given above.

  public void insert (E key, P priority) throws ElementFoundException {

    if (root == null) {

      root = new TreapNode(key, priority);

    } else {

      TreapNode x = add(key, priority, root);
      restoreAfterInsertion(x);

    }

  }

  // Inserts a given element, with a given priority, into the subtree
  // of this Treap with a given node as root
  //
  // Precondition:
  // a) This Treap satisfies the above Treap properties.
  // b) key is a non-null input with type E and priority is a non-null
  //    input with type P.
  // c) x is a non-null node in this Treap such that, if the input key
  //    was stored at a node in this Treap then it would necessarily be
  //    stored at a node in the subtree of this Treap with root x.
  //
  // Postcondition:
  // a) If the key does not already belong to the subset of E stored
  //    at the nodes in this tree then it is added to this set - with
  //    the input priority used as the priority for the node storing
  //    this key. No other changes have been made to the subset of E
  //    stored at nodes in this tree, and the priorities of the
  //    elements of E already in this subset have not been changed.
  //    The new node, storing the input key and priority, is returned
  //    as output in this case. On the other hand, an
  //    ElementfoundException is thrown and this tree is not changed,
  //    if the input key already belongs to this subset of E.
  // b) This tree satisfies the Treap prpoperties given above, except
  //    that if they key was not already stored in this Treap, then
  //    the priority of the new leaf, storing the key, and this node
  //    is not the root, then the priority of this new leaf may be
  //    greater than the priority of its parent.
  //
  //
  // *Dr. Eberlys slides and example code 'BSTDictionary.java'
  // were used to write this method*
  //
  // Bound function: Depth of subtree with root x
  //

  private TreapNode add (E key, P priority, TreapNode x) throws ElementFoundException {

    int result = key.compareTo(x.element);

    if(result < 0){ //key < x.element
      if(x.left == null){ //x does not have a left child, create a new node
        TreapNode newNode = new TreapNode(key,priority);
        x.left = newNode;
        newNode.parent = x;
        return newNode;
      }else{  //x has a left child, branch left
        return add(key,priority,x.left);
      }
    }else if (result > 0){  //key > x.element
      if(x.right == null){  //x does not have a right child, create a new node
        TreapNode newNode = new TreapNode(key,priority);
        x.right = newNode;
        newNode.parent = x;
        return newNode;
      }else{  //x has a right child, branch right
        return add(key,priority,x.right);
      }
    }else{  //key == x.element, throw exception
      throw new ElementFoundException("A node was found with that key. Insertion failed.");
    }
  }



  // Restores Treap properties after insertion of a new node
  //
  // Precondition:
  // a) This tree satisfies Treap properties (a) and (b).
  // b) A non-null node x of this tree is given as input.
  // b) For every node y in this tree except for x, if y is
  //    not the root of this tree (so that y has a parent)
  //    then the priority stored at y is less than or equal
  //    to the priority stored at the parent of y. Furthermore,
  //    if the parent of y also has a parent (the "grandparent"
  //    of y) then the priority stored at y is also less than
  //    or equal to the priority stored at the grandparent of y.
  //
  // Postcondition:
  // a) The subset of elements of E stored at nodes of this tree
  //    has not been changed.
  // b) The priority associated with each element of E that is
  //    stored in this tree has not been changed. That is, if an
  //    element e of E and a priority p in P were stored were stored
  //    in the same node of this tree before this computation began,
  //    then e and p are also both stored in the same node of this
  //    tree after the computation ends.
  // c) This tree is a Treap, that is, it satisfies all of the
  //    Treap properties given above.
  //
  //
  // Bound function: depth of input node x
  //
  // Execution of this algorithm is linear in the depth of the input node
  //



  private void restoreAfterInsertion(TreapNode x){

    if(x.parent != null){   //x is not the root

      int result = x.priority.compareTo(x.parent.priority);

      if(result > 0){     //x priority is bigger than parents

        if(x.parent.left == x){   //x is a left child
          rightRotate(x.parent);

        }else{    //x is a right child
          leftRotate(x.parent);
        }
        //Call recursively to ensure that x's priority is not bigger
        //than it's new parents priority
        restoreAfterInsertion(x);
      }
    }
  }




  // *******************************************************************
  //
  // Deletions
  //
  // *******************************************************************

  /**
  *
  * Removes an input element from the subset of E stored at the
  * nodes of this Treap
  *
  * @param key the element of&nbsp;E to be removed
  * @throws NoSuchElementException if the key is not stored
  *
  */

  // Precondition:
  // a) This Treap satisfies the above Treap properties.
  // b) key is a non-nyll input with type E.<br>
  // Postcondition:
  // a) If the input key is included of the subset of E stored at
  //    the nodes of this Treap then it is removed from this
  //    subset (with this subset being otherwise unchanged); the
  //    priorities associated with other elements of E in this subset
  //    are also unchanged. A NoSuchElementException is thrown, and
  //    this Treap is not changed, if the input key does not already
  //    belong to this susbset of E.
  // b) This Treap satisfies the Treap properties given above.

  public void delete (E key) throws NoSuchElementException {

    if (root != null) {

      TreapNode z = deleteFromSubtree(key, root);
      if (z != null) {
        restoreAfterDeletion(z);
      };

    } else {

      throw new NoSuchElementException(key.toString() + " not found.");

    }

  }

  // Removes an input element from the subset of E stored in the
  // subtree of this Treap with a given node as root.
  //
  // Precondition:
  // a) This Treap satisfies the above Treap properties.
  // b) key is a non-null input with type E.
  // c) x is a non-null node in this Treap.
  // d) Either key is not an element of the subset of E
  //    stored at the nodes of this Treap, or key is stored
  //    at a node in the subtree of this Treap with x as root.
  //
  // Postcondition:
  // a) If the input key was originally stored a node in the
  //    subtree of this binary tree  with root x then
  //    - every node in this binary tree stores an element
  //      of E and element of P (called a "priority")
  //    - the key is removed from the subset of E stored at
  //      the nodes of this binary tree, and this subset is
  //      otherwise unchanged; the priorities associated with
  //      other elements of this subset are not changed.
  //    - For every non-null node y in this binary tree, every
  //      element of E stored in the left subtree of the
  //      subtree with root y is less than the element of E
  //      stored at y, and every element of E stored in the
  //      right subtree of the subtree with root y is
  //      greater than the element of E stored at y.
  //    - A (possibly null) node z in this tree is returned as
  //      output. For every non-null node y in this binary
  //      tree except for x, if y has a parent then the
  //      the priority stored at the parent of y is greater
  //      than or equal to the priority stored at y, and if
  //      y has a grandparent then the priority stored at the
  //      grandparent of y is greater than or equal to the
  //      priority stored at y.
  // b) - If the input key was not originally stored in the
  //      subtree of this binary tree with root x then
  //      a NoSuchElementException is thrown and this binary
  //      tree is not changed - so that it still satisfies
  //      the Treap properties and is, therefore, a Treap.
  //
  //
  //
  //
  // *Dr. Eberlys slides and example code 'BSTDictionary.java'
  // were used to write this method*
  //
  // Bound function: Depth of subtree with root x
  //

  private TreapNode deleteFromSubtree (E key, TreapNode x) throws NoSuchElementException {

    if(x == null){
      throw new NoSuchElementException("No node with that key was found: Deletetion failed.");

    }else{

      int result = key.compareTo(x.element);

      if(result < 0){ //key < x.element
        return deleteFromSubtree(key, x.left);

      }else if(result > 0){ //key > x.element
        return deleteFromSubtree(key, x.right);

      }else{  //key == x.element
        return deleteNode(x);
      }
    }
  }



  //
  // *Dr. Eberlys slides and example code 'BSTDictionary.java'
  // were used to write this method*
  //
  //
  // Removes a given node from this BSTDictionary
  // and returns the node that replaced the deleted nodes position
  // which is possibly null.
  //
  //
  // Params: a non-null node x
  //
  // Returns: a possibly null node that replaced the deleted nodes position
  //
  //
  private TreapNode deleteNode(TreapNode x){

    if(x.left == null){

      if(x.right == null){

        if(x.parent == null){   // x is the only node, i.e. the root
          root = null;
          return null;  //no node took x's place

        } else {      //x has a parent and no children: remove x as a child
          int result = x.element.compareTo(x.parent.element);

          if(result < 0){   //x is a left child
            x.parent.left = null;

          }else{  //x is a right child
            x.parent.right = null;
          }
          return null;  // no node took x's place
        }
      }else{  // x does not have a left child but does have a right child
        TreapNode rightChild = x.right;

        if(x.parent == null){   // x is the root with one right child:
          rightChild.parent = null;
          root = rightChild;
          return root;  // x has been replaed with its right child, which is now the root

        } else {      //x has a parent and a right child, but no left child
          TreapNode parent = x.parent;
          int result = x.element.compareTo(x.parent.element);

          if(result < 0){   //x is a left child and has a right child
            parent.left = rightChild;

          }else{  //x is a right child and has a right child
            parent.right = rightChild;
          }
          rightChild.parent = parent;
          return rightChild;  // the right child of x took x's place
        }
      }
    }else if(x.right == null){  //x has a left child, but no right child
      TreapNode leftChild = x.left;

      if(x.parent == null){   // x is the root with one left child:
        leftChild.parent = null;
        root = leftChild;
        return root;  //x has been replaced with its left child which is now the root

      } else {      //x has a parent and a left child
        TreapNode parent = x.parent;
        int result = x.element.compareTo(x.parent.element);

        if(result < 0){   //x is a left child and has a left child
          parent.left = leftChild;

        }else{  //x is a right child and has a left child
          parent.right = leftChild;
        }
        leftChild.parent = parent;
        return leftChild; //x has been replaced with its left child

      }
    }else{  //x has two children, replace x with its successor
      TreapNode successor = successor(x);
      x.element = successor.element;
      x.priority = successor.priority;
      deleteNode(successor);
      return x;
    }
  }



  // *Dr. Eberlys slides and example code 'BSTDictionary.java'
  // were used to write this method*
  //
  //
  // Reports the node storing the smallest key in the right subtree
  // of an input node when the right subtree of this node is not
  // empty.
  //
  // Params: a non-null node
  //
  // Returns: the successor of that node
  //

  private  TreapNode successor(TreapNode x){
    TreapNode y = x.right;


    // Loop Invariant:
    // a) The BSTDictionary Invariant is satisfied.
    // b) A non-null node x in this BSTDictionary, whose
    //    right subtree is not empty, has been given as input.
    // c) y is a node in the right subtree. If the left subtree
    //    of y is nonempty then the node the right subtree of x
    //    storing the smallest key is the node with smallest key
    //    in the left subtree of y. Otherwise y is this node.
    //
    // Bound Function: Depth of subtree with root y.

    while(y.left != null){
      y = y.left;
    }
    return y;
  }


  // Restores Treap properties after a node has been deleted.
  //
  // Ppstcondition:
  // a) Each node in this binary tree stores an element of E
  //    and an element of P (called a "priority").
  // b) A non-null node x in this binary tree is given as input.
  // c) For every non-null node y in this binary tree, every
  //    element of E stored in the left subtree of the subtree
  //    with root y is less than the element of E stored at y,
  //    and every element of E stored in the right subtree of
  //    the subtree with root y is greater than the element
  //    of E stored at y.
  // d) For every non-null node y in this binary tree except
  //    for x, if y has any children then the priority stored
  //    at each child of y is less than or equal to the priority
  //    stored at y, and if y has any grandchildren then the
  //    priority stored at each grandchild of y is less than or
  //    equal to the priority stored at y.
  //
  // Postcondition:
  // a) The binary tree satisfies the above Treap properties, that
  //    is, it is a Treap.
  // b) The subset of E stored at the nodes of this binary tree has
  //    not been changed.
  // c) The priority associated with each element of E, stored in
  //    this binary tree, has not been changed.
  //
  // Bound Function: Two plus the sum of the depths of the left
  //                 and right subtrees of x

  private void restoreAfterDeletion (TreapNode x) {

    if(x.left != null){ //x has a left child
      int left_result = (x.left.priority).compareTo(x.priority);

      if(x.right != null){  //x has a left child and a right child
        int right_result = (x.right.priority).compareTo(x.priority);

        int child_result = (x.left.priority).compareTo(x.right.priority);

        if(left_result > 0){ //left.priority > x.priority
          if(child_result >= 0){  //left.priority >= right.priority
            //if left.priority == right.priority, it doesn't matter if left or right rotate
            rightRotate(x);
            restoreAfterDeletion(x);
          }else{  //right.priority > left.priority
            leftRotate(x);
            restoreAfterDeletion(x);
          }
        }else if(right_result > 0){ // right.priority > x.priority
          if(child_result >= 0){  //left.priority >= right.priority
            rightRotate(x);
            restoreAfterDeletion(x);
          }else {  //right.priority > left.priority
            leftRotate(x);
            restoreAfterDeletion(x);
          }
        }
      }else{  //x has a left child but no right child
        if(left_result > 0){  //left.priority > x.priority
          rightRotate(x);
          restoreAfterDeletion(x);
        }
      }
    }else if(x.right != null){  //x has a right child, but no left child
      int right_result = (x.right.priority).compareTo(x.priority);
      if(right_result > 0){ //right.priority > x.priority
        leftRotate(x);
        restoreAfterDeletion(x);
      }
    }
  }


  // *******************************************************************
  //
  // Additional Code Needed for Testing
  //
  // *******************************************************************

  // Returns a reference to the root of this Treap

  TreapNode root() {

    return root;

  }

}
