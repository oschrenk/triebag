package triebag.tries;

public interface Alphabet {

	boolean isValid(char c);

	int getIndex(char c);

	char getChar(int index);

	boolean isVowel(char c);

	public boolean isConsonant(char c);

	public int getSize();
}
