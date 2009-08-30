package triebag.tries;

public class FastCountingSet {
	static int hashSeed = 0x13;

	/**
	 * getHashCode() differs from the standard Java hashcode algorithm. It's an
	 * shift-add-xor class algorithm, as tested in "Performance in Practise of
	 * String Hashing Functions", Jobel & Ramakrishna, 1997; and is implemented
	 * as efficiently as possible.
	 */
	public static int getHashCode(char str[]) {
		int result = hashSeed;
		for (int i = 0; i < str.length; i++) {
			result ^= ((result << 5) + str[i] + (result >>> 2));
		}
		return ((result & 0x7fffffff) & 0x1ff);
	}
	
}
