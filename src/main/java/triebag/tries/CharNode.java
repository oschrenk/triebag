package triebag.tries;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CharNode<T> {

	private T item;
	private Map<Character, CharNode<T>> children;

	public CharNode<T> addNode(final char c) {
		if (children == null) {
			children = new HashMap<Character, CharNode<T>>();
		}
		CharNode<T> emptyNode = new CharNode<T>();
		children.put(c, emptyNode);
		return emptyNode;
	}

	protected Collection<CharNode<T>> getChildren() {
		if (children == null) {
			return Collections.emptyList();
		}
		return children.values();
	}

	protected CharNode<T> getChildNode(final char c) {
		if (children == null) {
			return null;
		}
		return children.get(c);
	}

	protected T getItem() {
		return item;
	}

	protected void setItem(final T item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "CharNode [item=" + item + ", children=" + children + "]";
	}

}