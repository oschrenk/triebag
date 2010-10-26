package triebag.tries;

import java.util.Iterator;
import java.util.Stack;

public class SimplePrefixTrieIterator<T> implements Iterator<T> {

	private final Stack<CharNode<T>> nodesToInspect = new Stack<CharNode<T>>();
	private CharNode<T> nextNodeWithItem = null;

	protected SimplePrefixTrieIterator(final CharNode<T> startNode) {
		for (CharNode<T> node : startNode.getChildren()) {
			nodesToInspect.push(node);
		}
	}

	private void walkToNextFullNode() {

		nextNodeWithItem = null;
		while (!nodesToInspect.empty()) {
			CharNode<T> n = nodesToInspect.pop();

			for (CharNode<T> node : n.getChildren()) {
				nodesToInspect.push(node);
			}

			if (n.getItem() != null) {
				nextNodeWithItem = n;
				break;
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
