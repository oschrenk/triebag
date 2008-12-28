package tries;

import java.util.Collection;
import java.util.Iterator;

/**
 * A simple generic Trie interface
 * 
 * @author mdakin
 *
 * @param <T>
 */
public interface Trie<T> {
  
  /**
   * 
   * @param str A Charsequence identifying the object T
   * @param item The object to be added to Trie
   * @throws NullPointerException if item or str is null
   */
  void add(CharSequence str, T item);
  
  /**
   * 
   * @param str
   * @return
   */
  T getItem(CharSequence str);
  
  /**
   * Returns an Iterator for all items with given prefix in the trie.
   * For obejcts o1, o2 and o3 added with "foo", "foobar" "blah" respectively,
   * getItemWithPrefix("foo") returns an Iterator over o1 an o2.
   *  
   * @param prefix
   * @return
   */
  Iterator<T> getItemsWithPrefix(CharSequence prefix);
  
  /**
   * Returns all objects of which their defining string is included
   * @param string
   * @return
   */
  Collection<T> getItemsInString(CharSequence string);
  
}
