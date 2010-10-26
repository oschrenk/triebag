package triebag.tries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Trie<T> implements Iterable<T> {

	private final CharNode<T> root;

	/**
	 * Default constructor
	 */
	public Trie() {
		root = new CharNode<T>();
	}

	/**
	 *
	 * @param s
	 *            A {@link CharSequence} identifying the object T
	 * @param item
	 *            The object to be added to {@link Trie}
	 * @throws NullPointerException
	 *             if item or str is {@code null}
	 */
	public void add(final CharSequence s, final T item) {

		// start with the root node
		CharNode<T> node = root;
		CharNode<T> previousNode = null;

		int i = 0;
		while (i < s.length()) {
			// remember the current node as the previous one
			previousNode = node;

			// get next node based on char at that point, node is unique
			node = node.getChildNode(s.charAt(i));

			if (node == null)
				break;
			i++;
		}

		// re-check if we reached the end of the CharSequence
		if (i < s.length()) {
			// start from the parent
			node = previousNode;
			// add one node for each char.
			while (i < s.length()) {
				node = node.addNode(s.charAt(i++));
			}
		}
		node.setItem(item);

	}

	/**
	 *
	 * @param s
	 * @return Retrieves the item identified by str, {@code null} otherwise.
	 */
	public T getItem(final CharSequence s) {

		CharNode<T> node = root;

		int i = 0;
		while (i < s.length() && node != null) {
			node = node.getChildNode(s.charAt(i));
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
	 * @param s
	 *            the search string
	 * @return a {@link Collection} of items along the path or an empty one if
	 *         none is found
	 */
	public Collection<T> getItemsInString(final CharSequence s) {

		CharNode<T> node = root;

		int i = 0;
		Collection<T> res = new ArrayList<T>();
		while (i < s.length() && node != null) {
			node = node.getChildNode(s.charAt(i));
			if (node != null && node.getItem() != null) {
				res.add(node.getItem());
			}
			i++;
		}

		return res;
	}

	/**
	 * Returns an {@link Iterator} for all items with given prefix in the
	 * {@link Trie}. For objects <code>o1</code>, <code>o2</code> and
	 * <code>o3</code> added with <code>"foo"</code>, <code>"foobar"</code> and
	 * <code>"blah"</code> respectively, <code>getItemWithPrefix("foo")</code>
	 * returns an {@link Iterator} over <code>o1</code> and <code>o2</code>.
	 *
	 * @param prefix
	 * @return An Iterator of Strings from Trie containing the Strings
	 */
	public Collection<T> getItemsWithPrefix(final CharSequence prefix) {

		CharNode<T> node = root;
		CharNode<T> previousNode = root;

		int i = 0;
		while (i < prefix.length() && node != null) {
			previousNode = node;
			node = node.getChildNode(prefix.charAt(i));
			i++;
		}

		List<T> nodes = new ArrayList<T>();

		Iterator<T> iter = new SimplePrefixTrieIterator<T>(previousNode);
		while (iter.hasNext()) {
			nodes.add(iter.next());
		}

		if (nodes.isEmpty())
			return Collections.emptyList();
		return nodes;
	}

	@Override
	public Iterator<T> iterator() {
		return new SimplePrefixTrieIterator<T>(root);
	}

}
