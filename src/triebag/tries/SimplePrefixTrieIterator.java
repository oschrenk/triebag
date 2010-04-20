package triebag.tries;

import java.util.Iterator;
import java.util.Stack;

public class SimplePrefixTrieIterator<T> implements Iterator<T> {
	Stack<CharNode<T>> others = new Stack<CharNode<T>>();
	CharNode<T> nextNodeWithItem = null;

	SimplePrefixTrieIterator(CharNode<T> startNode) {
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

	public boolean hasNext() {
		walkToNextFullNode();
		return nextNodeWithItem != null;
	}

	public T next() {
		return nextNodeWithItem.getItem();
	}

	public void remove() {

	}

}
