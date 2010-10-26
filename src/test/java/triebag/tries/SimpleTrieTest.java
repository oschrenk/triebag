package triebag.tries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class SimpleTrieTest {

	@Test
	public void testNullCharStream() {
		SimpleTrie<Foo> ptrie = new SimpleTrie<Foo>();
		try {
			ptrie.add(null, new Foo());
			fail("Null pointer expected.");
		} catch (NullPointerException e) {
			// pass
		}
	}

	public void testEmptyTrie() {
		SimpleTrie<Foo> ptrie = new SimpleTrie<Foo>();
		assertEquals(0, toList(ptrie.getItemsWithPrefix("foo")).size());
	}

	@Test
	public void testSingleInsertion() {
		SimpleTrie<Foo> ptrie = new SimpleTrie<Foo>();
		Foo b = new Foo();
		ptrie.add("foo", b);
		assertEquals(b, ptrie.getItem("foo"));
	}

	@Test
	public void testSameStringInsertionsReplaces() {
		SimpleTrie<Foo> ptrie = new SimpleTrie<Foo>();
		Foo b = new Foo();
		Foo b2 = new Foo();
		ptrie.add("foo", b);
		ptrie.add("foo", b2);
		assertEquals(b2, ptrie.getItem("foo"));
	}

	@Test
	public void testDifferentStrInsertion() {
		SimpleTrie<Foo> ptrie = new SimpleTrie<Foo>();
		Foo b = new Foo();
		Foo b2 = new Foo();
		ptrie.add("foo", b);
		ptrie.add("bar", b2);
		assertEquals(b, ptrie.getItem("foo"));
		assertEquals(b2, ptrie.getItem("bar"));
	}

	@Test
	public void testSubStrInsertion() {
		SimpleTrie<Foo> ptrie = new SimpleTrie<Foo>();
		Foo b = new Foo();
		Foo b2 = new Foo();
		ptrie.add("foo", b);
		ptrie.add("foobar", b2);
		assertEquals(b, ptrie.getItem("foo"));
		assertEquals(b2, ptrie.getItem("foobar"));
		ptrie = new SimpleTrie<Foo>();
		ptrie.add("foobar", b);
		ptrie.add("foo", b2);
		assertEquals(b, ptrie.getItem("foobar"));
		assertEquals(b2, ptrie.getItem("foo"));
	}

	private List<Foo> toList(final Iterator<Foo> it) {
		ArrayList<Foo> list = new ArrayList<Foo>();
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}

	/**
	 * Empty inner class for test purposes
	 */
	class Foo {
	}

}
