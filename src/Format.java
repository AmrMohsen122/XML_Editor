import java.util.*

public class Format {
	public static String Format(String unformatted) {
		int level = 0;
		List<String> textList = new ArrayList<String>();
		int i = 0;
		while (i > -1 && i < unformatted.length()-1) {
			if (unformatted.charAt(i) == '<' && unformatted.charAt(i+1) == '?') {
				int closingIndex = unformatted.indexOf('>', i);
				String tagName = unformatted.substring(i+2, closingIndex);
				textList.add("<?" + tagName + ">");
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
				i = unformatted.indexOf('<', closingIndex);
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
			int characterIndex = 0;
			while (text.charAt(0) == ' ' || text.charAt(0) == '\r' || text.charAt(0) == '\n') {
				text = text.substring(1);
			}
			characterIndex = text.length() - 1;
			while (text.charAt(characterIndex) == ' ' || text.charAt(characterIndex) == '\r' || text.charAt(characterIndex) == '\n') {
				text = text.substring(0, characterIndex);
				characterIndex = text.length() - 1;
			}
			textList.add(" ".repeat(level * 2) + text);
			i = nextTagStartIndex;
		}
		return String.join("\n", textList);
	}
}