package triebag.tries;

import java.util.HashMap;
import java.util.Map;

public class CharNode<T> {

	private T item;
	private Map<Character, CharNode<T>> children;

	public CharNode<T> addNode(char c) {
		if (children == null) {
			children = new HashMap<Character, CharNode<T>>();
		}
		CharNode<T> emptyNode = new CharNode<T>();
		children.put(c, emptyNode);
		return emptyNode;
	}

	protected CharNode<T> getChildNode(char c) {
		if (children == null) {
			return null;
		}
		return children.get(c);
	}

	protected T getItem() {
		return item;
	}

	protected void setItem(T item) {
		this.item = item;
	}
}