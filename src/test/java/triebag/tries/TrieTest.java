package triebag.tries;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TrieTest {

	@Test(expected = NullPointerException.class)
	public void testNullCharStream() throws NullPointerException {
		Trie<String> trie = new Trie<String>();
		trie.add(null, new String());
	}

	@Test
	public void testEmptyTrie() {
		Trie<String> trie = new Trie<String>();
		assertEquals(0, trie.getItemsWithPrefix("foo").size());
	}

	@Test
	public void testSingleInsertion() {
		Trie<String> trie = new Trie<String>();
		trie.add("foo", "a");
		assertEquals("a", trie.getItem("foo"));
	}

	@Test
	public void testSameStringInsertionsReplaces() {
		Trie<String> trie = new Trie<String>();
		trie.add("foo", "a");
		trie.add("foo", "b");
		assertEquals("b", trie.getItem("foo"));
	}

	@Test
	public void testDifferentStringInsertion() {
		Trie<String> trie = new Trie<String>();
		trie.add("foo", "a");
		trie.add("bar", "b");
		assertEquals("a", trie.getItem("foo"));
		assertEquals("b", trie.getItem("bar"));
	}

	@Test
	public void testSubStringInsertion() {
		Trie<String> trie = new Trie<String>();
		trie.add("foo", "a");
		trie.add("foobar", "b");
		assertEquals("a", trie.getItem("foo"));
		assertEquals("b", trie.getItem("foobar"));

		trie = new Trie<String>();
		trie.add("foobar", "a");
		trie.add("foo", "b");
		assertEquals("a", trie.getItem("foobar"));
		assertEquals("b", trie.getItem("foo"));
	}

	@Test
	public void testGetItemsInString() {
		Trie<String> trie = new Trie<String>();
		trie.add("mo", "a");
		trie.add("motor", "b");
		trie.add("motorway", "c");

		assertEquals(3, trie.getItemsInString("motorway").size());
	}

	@Test
	public void testGetItemsWithPrefix() {
		Trie<String> trie = new Trie<String>();
		trie.add("mot", "a");
		trie.add("motor", "b");
		trie.add("motorway", "c");

		assertEquals(3, trie.getItemsWithPrefix("mo").size());
	}

}
