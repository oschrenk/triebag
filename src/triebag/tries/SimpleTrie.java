package triebag.tries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class SimpleTrie<T> implements Trie<T>, Iterable<T> {

	private final CharNode<T> root;

	/**
	 * Default constructor
	 */
	SimpleTrie() {
		root = new CharNode<T>();
	}

	public void add(CharSequence str, T item) {

		// start with the root node
		CharNode<T> node = root;
		CharNode<T> previousNode = null;

		int i = 0;
		while (i < str.length()) {
			// remember the current node as the previous one
			previousNode = node;

			// get next node based on char at that point, node is unique
			node = node.getChildNode(str.charAt(i));

			if (node == null)
				break;
			i++;
		}

		// re-check if we reached the end of the CharSequence
		if (i < str.length()) {
			// start from the parent
			node = previousNode;
			// add one node for each char.
			while (i < str.length()) {
				node = node.addNode(str.charAt(i++));
			}
		}
		node.setItem(item);

	}

	/**
	 * Returns the item with given prefix from the trie
	 * 
	 * @param prefix
	 * @return
	 */
	public T getItem(CharSequence prefix) {

		CharNode<T> node = root;

		int i = 0;
		while (i < prefix.length() && node != null) {
			node = node.getChildNode(prefix.charAt(i));
			i++;
		}

		if (node != null) {
			return node.getItem();
		}

		return null;
	}

	/**
	 * Returns all items along a path in the trie.
	 * 
	 * For instance if the trie contains three items
	 * <ul>
	 * <li>mo</li>
	 * <li>moto</li>
	 * <li>motorway</li>
	 * </ul>
	 * 
	 * and you search for <code>motorway</<code> the method will return
	 * all three items associated with these items.
	 * 
	 * @param str
	 *            the search string
	 * @return a {@link Collection} of items along the path or an empty one if
	 *         none is found
	 */
	public Collection<T> getItemsInString(CharSequence str) {

		CharNode<T> node = root;

		int i = 0;
		Collection<T> res = new ArrayList<T>();
		while (i < str.length() && node != null) {
			node = node.getChildNode(str.charAt(i));
			if (node != null && node.getItem() != null) {
				res.add(node.getItem());
			}
			i++;
		}

		return res;
	}

	public Iterator<T> getItemsWithPrefix(CharSequence prefix) {

		CharNode<T> node = root;
		CharNode<T> previousNode = root;

		int i = 0;
		while (i < prefix.length() && node != null) {
			previousNode = node;
			node = node.getChildNode(prefix.charAt(i));
			i++;
		}

		return new SimplePrefixTrieIterator<T>(previousNode);
	}

	public Iterator<T> iterator() {
		return new SimplePrefixTrieIterator<T>(root);
	}

}
