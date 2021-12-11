import java.util.Stack;

public class Format {
	public static String spaces(int levels)
	{
	int x = 2*levels;
	int y = 0;
	String s = "";
	while(y < x)
	{
	s+= " ";
	y++
	}
	return s;
	}
	public static StringBuffer Format(String unformatted) {
		String xml = unformatted;
		char c = '>';
		int x = xml.length(); // get length for the string
		StringBuffer formatted = new StringBuffer();   // create an empty string to put formatted string in it
		Stack<StringBuffer> stack = new Stack<>(); // Create a stack
		int level = 0;
		
		for(int i = 0; i<x; i++)
		{
			StringBuffer nameinsidestack = new StringBuffer();
			if(xml.charAt(i) == '<' && xml.charAt(i+1) == '?')
			{
				int a = xml.indexOf(c,i);
				formatted.append(xml.substring(i, a+1)) ;
				i = a;
				formatted.append("\n");
			}
			if(xml.charAt(i) == '<' && xml.charAt(i+1) != '?')
			{
				if(level != 0)
				{
				formatted.append(spaces(level));
				}
				while(xml.charAt(i) != '>')
				{
				formatted.append(xml.charAt(i));
				nameinsidestack.append(xml.charAt(i));
				i++;
				}
				formatted.append(">\n");
				stack.push(nameinsidestack);
				if(i > x - 2)
				{
					break;
				}
				if(xml.charAt(i+1) == '<' && xml.charAt(i+2) != '/')
				{
					level++;	
				}
				else if(xml.charAt(i+1) == '<' && xml.charAt(i+2) == '/')
				{
					if(level != 0)
					{
					formatted.append(spaces(level));
					}
					while(xml.charAt(i) != '>')
					{
					formatted.append("\n" + xml.charAt(i));
					nameinsidestack.append(xml.charAt(i));
					i++;
					}
					formatted.append(">\n");
					level--;
				}
				else
				{
					formatted.append(spaces(level+1));
					while(xml.charAt(i+1) != '>')
					{
					if(xml.charAt(i+1) == '<')
					{
						formatted.append("\n" + spaces(level));
					}
					formatted.append(xml.charAt(i+1));
					nameinsidestack.append(xml.charAt(i));
					i++;
					}
					formatted.append(">\n");
					level--;
				}
			}
			
		}
		return formatted;
	}

}