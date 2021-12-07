import java.util.HashMap;

public class Compression {
	private static String max_repeated_pair(HashMap<String, Integer> pairs) {
		String max_pair = null;
		int max_occurence = -1;
		for (String key : pairs.keySet()) {
			if (pairs.get(key) > max_occurence) {
				max_pair = key;
				max_occurence = pairs.get(key);
			}
		}
		return max_pair;

	}

	private static char find_unused_char(int[] used_char) {
		char unused = ' ';
		for (int i = 0; i < used_char.length; i++) {
			if (used_char[i] == 0) {
				unused = (char) i;
				break;
			}
		}
		return unused;
	}

	private static boolean is_compressable(HashMap<String, Integer> h) {
		for (Integer values : h.values()) {
			if (values > 1) {
				return true;
			}
		}
		return false;
	}

	private static void construct_hash_freq(String input, HashMap<String, Integer> pairs, int[] freq) {
		String pair;
		pairs.clear();
		for (int i = 0; i <= input.length() - 2; i++) {
			freq[input.charAt(i)]++;
			pair = input.substring(i, i + 2);
			if (pairs.containsKey(pair)) {
				// add one to the current value of key
				pairs.replace(pair, pairs.get(pair) + 1);
			} else {
				// if not found put it in hashmap with value of 1
				pairs.put(pair, 1);
			}
		}

		// add the last character to the frequency array
		freq[input.charAt(input.length() - 1)]++;
	}

	/**
	 * a function that encodes a given string using BPE
	 * 
	 * @param s          the string to be encoded
	 * @param iterations the number of iterations the algorithm should run
	 * @return encoded string
	 **/
	public static String encode_string(String s, int iterations) {
		//TODO  a way to return the hashmap for later decoding
		String encoded = "";
		HashMap<String, Integer> pairs = new HashMap<String, Integer>();
		// ferquency arrays that indicated what characters are used
		int[] used_char = new int[256];
		// create the hashmap that contains all the repeated characters
		construct_hash_freq(s, pairs, used_char);
		do {
			// replacing most occruing pair with an unused character
			String most_repeated = max_repeated_pair(pairs);
			char unused = find_unused_char(used_char);
			// mark the unused char as used for later cycles
			used_char[unused]++;
			// remove most_repated pair from the hashmap because it will be replaced
			encoded = s.replace(most_repeated, unused + "");
			construct_hash_freq(encoded, pairs, used_char);
			s = encoded;
			encoded = "";
			iterations--;
		} while (iterations > 0 && is_compressable(pairs));
		return s;
	}
	//decode the produced string
//	public static String decode_string(String encoded , HashMap<String , String> encode_scheme) {
	//TODO
//	}

	public static void main(String[] args) {
		String a = "<note><to>Tove</to><from>Jani</from><heading>Reminder</heading><body>Don't forget me this weekend!</body></note>";
		String b;
		System.out.println("Iterations\ta.len\tb.len");
		final int ITERATIONS = 30;
		double eff;
		for (int i = 1; i < ITERATIONS; i++) {
			b = encode_string(a, i);
			System.out.println(i + "\t" + a.length() + "\t" + b.length());
		}
	}
}
