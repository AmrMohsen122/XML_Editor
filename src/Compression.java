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

	public static void construct_hash_freq(String input, HashMap<String, Integer> pairs, int[] freq) {
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

	public static String encode(String s, int iterations) {
		String encoded = "";
		HashMap<String, Integer> pairs = new HashMap<String, Integer>();
		// ferquency arrays that indicated what characters are used
		int[] used_char = new int[256]; 
		// create the hashmap that contains all the repeated characters
		construct_hash_freq(s, pairs, used_char);
		do {
			String pair;
			// replacing most occruing pair with an unused character
			String most_repeated = max_repeated_pair(pairs);
			char unused = find_unused_char(used_char);
			// mark the unused char as used for later cycles
			used_char[unused]++;
			// remove most_repated pair from the hashmap because it will be replaced
			pairs.remove(most_repeated);
			for (int i = 0; i <= s.length() - 2; i++) {
				pair = s.substring(i, i + 2);
				if (pair.equals(most_repeated)) {
					i++;
					encoded += unused;
				} else if (i == s.length() - 2) {
					encoded += pair;
				} else {
					encoded += s.charAt(i);
				}
			}
			construct_hash_freq(encoded, pairs, used_char);
			s = encoded;
			encoded = "";
			iterations--;
		} while (iterations > 0 && is_compressable(pairs));
		return s;
	}

	public static void main(String[] args) {
		String a = "ABABCABCD";
		
		String b = encode(a, 2);
		System.out.println(b);
		System.out.println(a);

	}
}
