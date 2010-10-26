package triebag.tries;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class TrieTest {

	@Test(expected = NullPointerException.class)
	public void testNullCharStream() throws NullPointerException {
		Trie<String> ptrie = new Trie<String>();
		ptrie.add(null, new String());
	}

	@Test
	public void testEmptyTrie() {
		Trie<String> ptrie = new Trie<String>();
		assertEquals(0, toList(ptrie.getItemsWithPrefix("foo")).size());
	}

	@Test
	public void testSingleInsertion() {
		Trie<String> ptrie = new Trie<String>();
		ptrie.add("foo", "a");
		assertEquals("a", ptrie.getItem("foo"));
	}

	@Test
	public void testSameStringInsertionsReplaces() {
		Trie<String> ptrie = new Trie<String>();
		ptrie.add("foo", "a");
		ptrie.add("foo", "b");
		assertEquals("b", ptrie.getItem("foo"));
	}

	@Test
	public void testDifferentStringInsertion() {
		Trie<String> ptrie = new Trie<String>();
		ptrie.add("foo", "a");
		ptrie.add("bar", "b");
		assertEquals("a", ptrie.getItem("foo"));
		assertEquals("b", ptrie.getItem("bar"));
	}

	@Test
	public void testSubStringInsertion() {
		Trie<String> ptrie = new Trie<String>();
		ptrie.add("foo", "a");
		ptrie.add("foobar", "b");
		assertEquals("a", ptrie.getItem("foo"));
		assertEquals("b", ptrie.getItem("foobar"));

		ptrie = new Trie<String>();
		ptrie.add("foobar", "a");
		ptrie.add("foo", "b");
		assertEquals("a", ptrie.getItem("foobar"));
		assertEquals("b", ptrie.getItem("foo"));
	}

	private List<String> toList(final Iterator<String> it) {
		ArrayList<String> list = new ArrayList<String>();
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}

}
