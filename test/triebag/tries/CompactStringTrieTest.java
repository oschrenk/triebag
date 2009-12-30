package triebag.tries;

import junit.framework.TestCase;

import triebag.tries.CompactStringTrie;

import java.util.Arrays;
import java.util.Random;
import static triebag.tries.CompactStringTrie.*;

public class CompactStringTrieTest extends TestCase {
  
  public void testOnlineCompactTrieTest() {
    assertEquals(0, getSplitPoint("foo".toCharArray(), 0, "bar".toCharArray()));
    assertEquals(0, getSplitPoint("foo".toCharArray(), 0, "ar".toCharArray()));
    assertEquals(0, getSplitPoint("b".toCharArray(), 0, "x".toCharArray()));
    assertEquals(0, getSplitPoint("fo".toCharArray(), 0, "barx".toCharArray()));
  
    assertEquals(1, getSplitPoint("foo".toCharArray(), 0, "far".toCharArray()));
    assertEquals(1, getSplitPoint("far".toCharArray(), 0, "foo".toCharArray()));
    assertEquals(1, getSplitPoint("fo".toCharArray(), 0, "fxy".toCharArray()));
    assertEquals(1, getSplitPoint("f".toCharArray(), 0, "f".toCharArray()));

    assertEquals(3, getSplitPoint("foo".toCharArray(), 0, "foo".toCharArray()));
    assertEquals(3, getSplitPoint("foo".toCharArray(), 0, "foobar".toCharArray()));
    assertEquals(3, getSplitPoint("fool".toCharArray(), 0, "foo".toCharArray()));
  
    assertEquals(3, getSplitPoint("xyzfoo".toCharArray(), 3, "foo".toCharArray()));
    assertEquals(2, getSplitPoint("foo".toCharArray(), 1, "oobar".toCharArray()));
    assertEquals(3, getSplitPoint("bool".toCharArray(), 1, "ool".toCharArray()));  
    
    assertEquals(1, getSplitPoint("fool".toCharArray(), 2, "o".toCharArray()));
    assertEquals(1, getSplitPoint("foobar".toCharArray(), 5, "rex".toCharArray()));
    assertEquals(0, getSplitPoint("bool".toCharArray(), 4, "boolean".toCharArray()));  
    
  }
 
  public void testSimpleInsertion() {
    CompactStringTrie<String> ost = new CompactStringTrie<String>();
    ost.add("foo", "f");
    ost.add("foobar", "f2");
    ost.add("foobarjazz", "f3");  
    assertEquals("#:(f)|foo:(b)*|bar:(j)*|jazz:()*|", ost.toFlatString());
  }
  
  public void testSimpleInsertionDifferentOrder() {
    CompactStringTrie<String> ost = new CompactStringTrie<String>();
    ost.add("foobarjazz", "f");
    ost.add("foobar", "f2");
    ost.add("foo", "f3");
    assertEquals("#:(f)|foo:(b)*|bar:(j)*|jazz:()*|", ost.toFlatString());
  }
 
  public void testSimpleInsertionSeperate() {
    CompactStringTrie<String> ost = new CompactStringTrie<String>();
    ost.add("foo", "f");
    ost.add("bar", "f2");
    ost.add("jazz", "f3");  
    assertEquals("#:(bfj)|bar:()*|foo:()*|jazz:()*|", ost.toFlatString());
  }
 
  public void testSimpleInsertionSimpleSplit() {
    CompactStringTrie<String> ost = new CompactStringTrie<String>();
    ost.add("foobar", "f");
    ost.add("foo", "f2");
    assertEquals("#:(f)|foo:(b)*|bar:()*|", ost.toFlatString());
  }
 

  public void testSimpleInsertionComplexSplit() {
    CompactStringTrie<String> ost = new CompactStringTrie<String>();
    ost.add("foobar", "f");
    ost.add("foz", "f2");
    assertEquals("#:(f)|fo:(oz)|obar:()*|z:()*|", ost.toFlatString());
  }


  public void testInsertionOfAllCombinationsResultsSameTrie() {
    CompactStringTrie<String> ost = new CompactStringTrie<String>();
    String[] testStr = {"aba", "abc", "a", "ab", "abba", "aaa", "baa"};
    for(String s : testStr) {
      ost.add(s, s);
    }
    System.out.println(ost.getInfo());
    for(int i = 0; i < testStr.length; i++ ) {
      CompactStringTrie<String> newOst = new CompactStringTrie<String>();
      for(int j = i; j < i + testStr.length; j++) {
        String s = testStr[j % testStr.length];
        newOst.add(s, s);
      }
      assertEquals(ost.toFlatString(), newOst.toFlatString());
    }
  }
 
  public void testSimpleInsertionComplexSplit6() {
    CompactStringTrie<String> ost = new CompactStringTrie<String>();
    String[] testStr = {"blahfoobar", "blahfoojazz", "blahjazz", "blahfoo"};
    for(String s : testStr) {
      ost.add(s, s);
    }
    assertEquals("#:(b)|blah:(fj)|foo:(bj)*|bar:()*|jazz:()*|jazz:()*|", ost.toFlatString());
  }
 
  public void testrandomInsertionsInAllCombinationsResultsTheSameTrie() {
    CompactStringTrie<String> ost = new CompactStringTrie<String>();
    String[] testStrArray = new String[100];
    for (int i = 0; i < testStrArray.length; i++) {
      testStrArray[i] = createRandomStringFrom("01234567-", 10);
    }
    System.out.println(Arrays.toString(testStrArray));
    for(String s : testStrArray) {
      ost.add(s, s);
    }
    System.out.println(ost.toDeepString());
    System.out.println(ost.toFlatString());
    for(int i = 0; i < testStrArray.length; i++ ) {
      CompactStringTrie<String> newOst = new CompactStringTrie<String>();
      for(int j = i; j < i + testStrArray.length; j++) {
        String s = testStrArray[j % testStrArray.length];
        newOst.add(s, s);
      }
      assertEquals(ost.toFlatString(), newOst.toFlatString());
    }
  }
 
 
  private static Random r = new Random(1);
  private String createRandomStringFrom(String s, int maxSize) {
    char[] c = new char[r.nextInt(maxSize) + 1];
    char[] chars = s.toCharArray();
    for(int i=0; i < c.length ; i++) {
      c[i] = chars[r.nextInt(chars.length)];
    }
    return new String(c);
  }
}
