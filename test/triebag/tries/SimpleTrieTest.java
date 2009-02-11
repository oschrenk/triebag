package triebag.tries;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import triebag.tries.SimpleTrie;


public class SimpleTrieTest extends TestCase{
  
  class Foo {
  }
 
  private List<Foo> toList(Iterator<Foo> it) {
    ArrayList<Foo> list = new ArrayList<Foo>();
    while(it.hasNext()) {
      list.add(it.next());
    }
    return list;
  }

  public void testNullCharStream() {
    SimpleTrie<Foo> ptrie = new SimpleTrie<Foo>();
    try{
      ptrie.add(null, new Foo());
      fail("Null pointer expected.");
    } catch(NullPointerException e) {
      //pass
    }
  }

  public void testEmptyTrie() {
    SimpleTrie<Foo> ptrie = new SimpleTrie<Foo>();
    assertEquals(0, toList(ptrie.getItemsWithPrefix("foo")).size());
  }

  public void testSingleInsertion() {
    SimpleTrie<Foo> ptrie = new SimpleTrie<Foo>();
    Foo b = new Foo();
    ptrie.add("foo", b);
    assertEquals(b, ptrie.getItem("foo"));
  }
  
  public void testSameStringInsertionsReplaces() {
    SimpleTrie<Foo> ptrie = new SimpleTrie<Foo>();
    Foo b = new Foo();
    Foo b2 = new Foo();
    ptrie.add("foo", b);
    ptrie.add("foo", b2);
    assertEquals(b2, ptrie.getItem("foo"));
  }

  public void testDifferentStrInsertion() {
    SimpleTrie<Foo> ptrie = new SimpleTrie<Foo>();
    Foo b = new Foo();
    Foo b2 = new Foo();
    ptrie.add("foo", b);
    ptrie.add("bar", b2);
    assertEquals(b, ptrie.getItem("foo"));
    assertEquals(b2, ptrie.getItem("bar"));
  }

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

  
}
