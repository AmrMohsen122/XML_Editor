import java.util.*

public class Format {
	public static String Format(String unformatted) {
		int level = 0;
		Stack<String> stack = new Stack();
		List<String> textList = new ArrayList<String>();
		int i = 0;
		while (i < unformatted.length()-1) {
			if (unformatted.charAt(i) == '<' && unformatted.charAt(i+1) == '?') {
				int closingIndex = unformatted.indexOf('>', i);
				String tagName = unformatted.substring(i+2, closingIndex);
				textList.add(" ".repeat(level * 2) + "<?" + tagName + ">");
				i = closingIndex + 1;
				continue;
			}
			if (unformatted.charAt(i) == '<' && unformatted.charAt(i+1) != '/') {
				int closingIndex = unformatted.indexOf('>', i);
				String tagName = unformatted.substring(i+1, closingIndex);
				textList.add(" ".repeat(level * 2) + "<" + tagName + ">");
				level++;
				i = closingIndex;
				continue;
			}
			if (unformatted.charAt(i) == '<' && unformatted.charAt(i+1) == '/') {
				int closingIndex = unformatted.indexOf('>', i);
				String tagName = unformatted.substring(i+2, closingIndex);
				level--;
				textList.add(" ".repeat(level * 2) + "</" + tagName + ">");
				i = closingIndex;
				continue;
			}
			int nextTagStartIndex = unformatted.indexOf('<', i);
			if (unformatted.charAt(i) == '>') {
				boolean nextTagIsClosing = unformatted.charAt(nextTagStartIndex + 1) == '/';
				if (!nextTagIsClosing) {
					i = nextTagStartIndex;
					continue;
				}
				i++;
				continue;
			}
			String text = unformatted.substring(i, nextTagStartIndex);
			textList.add(" ".repeat(level * 2) + text);
			i = nextTagStartIndex;
		}
		return String.join("\n", textList);
	}
}