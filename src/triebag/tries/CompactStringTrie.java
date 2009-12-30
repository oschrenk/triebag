package triebag.tries;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple online compact String trie.
 *
 * @author mdakin
 *
 */
public class CompactStringTrie<T> {
  private Node<T> root = new Node<T>(null, null);
  public int nodesCreated;
  static Alphabet alphabet= new TurkishAlphabet();

  public void add(String s, T object) {
    if (s == null) {
      throw new NullPointerException("Input key can not be null");
    }
    char[] chars = s.toCharArray();
    Node<T> node = root;
    Node<T> previousNode = null;
    // i holds the char index for input
    int i = 0;
    // fragmentSplitIndex is the index of the last fragment
    int fragmentSplitIndex = 0;
    // While we still have chars left on the input, or no child marked with s[i]
    // is found in subnodes
    while (node != null) {
      previousNode = node;
      node = node.getChildNode(chars[i]);
      if (node == null) {
        // This occurs if there is no child node exist:
        // Input order to clean tree: foo(1). It adds the input to the node. 
        // or if input order is foo(1), foobar(2) 
        // Just split it from the different char, foo - bar, and add it to
        // node so it will be "root - foo(1) - bar(2)"
        previousNode.addChild(new Node<T>(object, getSuffix(chars, i)));
      }
      else {
        // fragment split index finds the different character of input
        fragmentSplitIndex = getSplitPoint(chars, i, node.fragment);
        i += fragmentSplitIndex;
        if ((fragmentSplitIndex < node.fragment.length) ||
            (i == chars.length && fragmentSplitIndex == node.fragment.length)) {
//          System.out.println("fragment: " + new String(node.fragment) + " i: " + i
//              + " input: " + s + " fragmentSplitIndex:" + fragmentSplitIndex );
          break;
        }
      }
    }
  
    //
    // if input order : foobar(1), foo(2)
    // split the first node from inequality point
    // root - foo(2) - bar(1)
    //
    // if input order : foobar (1), fox(2)
    // root - fo - x(2)
    //         \ _ obar(1)
    //
    if(node != null) {
      Node<T> newNode = new Node<T>(node.object, getSuffix(node.fragment, fragmentSplitIndex));
      if (i == chars.length) {
        node.object = object;
        if (fragmentSplitIndex < node.fragment.length) {
          newNode.children = node.children;
          node.splitAndAdd(newNode, fragmentSplitIndex);
        }
      } else {
        Node<T> n2 = new Node<T>(object, getSuffix(chars, i));
        newNode.children = node.children;
        node.splitAndAdd(newNode, fragmentSplitIndex);
        node.addChild(n2);
        // Remove the old object.
        node.object = null;
      }
    }
  }
//    System.out.println(root.dump(true));
//  }
 
  /**
   * Finds the last position of common chars for 2 char arrays relative to a given index.
   * @param input
   * @param start
   * @param fragment
   * @return 
   *   for input: "foo" fragment = "foobar" index = 0, returns 3
   *   for input: "fool" fragment = "foobar" index = 0, returns 3
   *   for input: "fool" fragment = "foobar" index = 1, returns 2
   *   for input: "foo" fragment = "obar" index = 1, returns 2
   *   for input: "xyzfoo" fragment = "foo" index = 3, returns 2
   *   for input: "xyzfoo" fragment = "xyz" index = 3, returns 0
   *   for input: "xyz" fragment = "abc" index = 0, returns 0
   * 
   */
  static int getSplitPoint(char[] input, int start, char[] fragment){
    int fragmentIndex = 0;
    while (start < input.length && fragmentIndex < fragment.length && 
        input[start++] == fragment[fragmentIndex]) {
      fragmentIndex++;
    }
    return fragmentIndex;
  }
 
  private static char[] getSuffix(char[] arr, int index) {
    return Arrays.copyOfRange(arr, index, arr.length);
//    char[] res = new char[arr.length - index];
//    System.arraycopy(arr, index, res, 0, arr.length - index);
//    return res;
  }
 
  public String toFlatString() {
    return root.dump(true);
  }

  public String toDeepString() {
    return root.dump(false);
  }
  
  public List<T> getMatchingObjects(String input) {
    Node<T> node = root;
    int index = 0;
    String s = "";
    List<T> objects = new ArrayList<T>();
    while (index < input.length()) {
      node = node.getChildNode(input.charAt(index));
      if (node == null) break;
      String nodeString = node.getString();
      s += nodeString;
      if (input.startsWith(s) && node.hasObject()) {
          objects.add(node.object);
      }
      index += nodeString.length();
    }
    return objects;
  }

  public void walk() {
    Node<T> node = root;
    int index = 0;
    int nodeCount = 0;
    List<T> objects = new ArrayList<T>();
    while (true) {
      //TODO(mdakin): implement walk.
    }
  }

  public Node<T> getRoot() {
    return root;
  }
  
  public String getInfo() {
    return "Nodes created: " + nodesCreated;
  }

  /**
   * 
   * @author mdakin
   */
  public static class Node <T> {
    private char[] fragment;
    int index;
    private ArrayList<Node<T>> children;
    private T object;
  
    public Node(T t, char[] fragment) {
      this.object = t;
      this.fragment = fragment;
      resetChildren();
    }
  
    @SuppressWarnings("unchecked")
    public void resetChildren() {
      children = new ArrayList<Node<T>>();
    }

    public void addChild(Node<T> node) {
      int index = 0;
      int x = alphabet.getIndex(node.getDefiningChar());
      int counter = 0;
      for (int i=0; i < children.size(); i++) {
        if (x < alphabet.getIndex(children.get(i).getChar())) {
          break;
        }
        counter++;
      }
      children.add(counter, node);
    }
  
    public void splitAndAdd(Node<T> node, int fragmentSplitIndex) {
      fragment = Arrays.copyOf(fragment, fragmentSplitIndex);
      resetChildren();
      addChild(node);     
    }  
  
    public String getString() {
      return fragment == null ? "#" : new String(fragment);
    }
  
    public char getDefiningChar(){
      return fragment[0];
    }
  
    public Node<T> getChildNode(char c) {
      if (children == null) return null;
      for (Node<T> node : children) {
        if (node.fragment[0] == c) return node;
      }
      return null;
    }
    
    @SuppressWarnings("unchecked")
    public Node<T>[] getAllChildNodes() {
      return children.toArray(new Node[children.size()]);
    }

    @Override
    public String toString() {
      String s = getString() + " : ";
      if (children != null) {
        s += "( ";
        for (Node<T> node : children) {
          if(node != null) {
             s += node.getChar() + " ";
          }
        }
        s += ")";
      } else {
        s += ".";
      }
      if (object != null) {
        s += " * ";
      }
      return s;
    }
  
    private char getChar() {
      if (fragment == null) {
        return '#';
      }
      return fragment[0];
    }

    /**
     * Returns string representation of node and all child nodes until leafs.
     *
     * @param b string buffer to append.
     * @param level level of the operation
     */
    private void toDeepString(StringBuffer b, int level) {
      char[] indentChars = new char[level * 2];
      for (int i = 0; i < indentChars.length; i++)
        indentChars[i] = ' ';
      b.append(indentChars).append(this.toString());
      b.append("\n");
      if (children != null) {
        for (Node<T> subNode : this.children) {
          if(subNode != null) {
            subNode.toDeepString(b, level + 1);
          }
        }
      }
    }

    /**
     *
     * Flat string representation of node and all child nodes.
     * Used for testing purposes only. Given a tree like this:
     *
     *      a
     *     / \
     *    ba  c*
     *   /
     *  e*
     * 
     * This method returns: a:(bc)|ba:(e)|e:(.)*|c:(.)*
     *
     * @param b stringbuffer to append.
     */
    public final void toFlatString(StringBuffer b) {
        b.append(this.toString().replaceAll(" ", "")).append("|");
      if (children != null) {
        for (Node<T> subNode : this.children) {
          if(subNode != null) {
            subNode.toFlatString(b);
          }      
        }
      }
    }
  
    /**
     * Returns string representation of Node (and subnodes) for testing.
     *
     * @param flat : if true, returns a flat version of node and all sub nodes
     * using a depth first traversal. if false, returns multiline, indented
     * version of node tree.
     * @return a flat or tree string representation of trie.
     */
    public final String dump(boolean flat) {
      StringBuffer b = new StringBuffer();
      if (flat) {
        toFlatString(b);
      } else {
        toDeepString(b, 0);
      }
      return b.toString();
    }

    public boolean hasObject() {
      return object != null;
    }
  }
 
}
