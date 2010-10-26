package triebag.tries;

import java.util.Iterator;
import java.util.Stack;

public class SimplePrefixTrieIterator<T> implements Iterator<T> {

	private final Stack<CharNode<T>> others = new Stack<CharNode<T>>();
	private CharNode<T> nextNodeWithItem = null;

	protected SimplePrefixTrieIterator(final CharNode<T> startNode) {
		others.push(startNode);
	}

	private void walkToNextFullNode() {

		nextNodeWithItem = null;
		while (!others.empty()) {
			CharNode<T> n = others.pop();
			if (n.getItem() != null) {
				nextNodeWithItem = n;
			}
		}
	}

	@Override
	public boolean hasNext() {
		walkToNextFullNode();
		return nextNodeWithItem != null;
	}

	@Override
	public T next() {
		return nextNodeWithItem.getItem();
	}

	@Override
	public void remove() {

	}

}
