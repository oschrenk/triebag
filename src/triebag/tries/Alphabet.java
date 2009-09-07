package triebag.tries;

public interface Alphabet {
  
  int getSize();
  
  int getIndex(char c);
  
  char getChar(int index);
  
  boolean isValid(char c);

  byte[] toIndexes(String s);
}
