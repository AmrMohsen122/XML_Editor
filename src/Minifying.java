package Testproject;

import java.util.Scanner;

public class Minifying {
	static String minify (String input)  {
		char space = ' ';
		String noSpacesString = "";

		Scanner myReader = new Scanner(input);
		while (myReader.hasNextLine()) {
			boolean flag = true;
			String data = myReader.nextLine();
			for (int i = 0; i < data.length(); i++) {
				if (data.charAt(i) == space && flag)
					continue;
				flag = false;
				noSpacesString += data.charAt(i);
			}
			
		}
		
		return noSpacesString;
	}
	static String stringTrim(String input, char firstChar, char lastChar) // function to trim string
	{
		String tempString = "";
		boolean isFound = false;
		if (input.charAt(0) == firstChar)
			isFound = true;
		if (isFound) {

			int i = 0, j = 0;
			while (!((input.charAt(i) == lastChar) || input.charAt(i) == 32)) // exit loop if last char or space
			{

				i++;
			}
			tempString = input.substring(j, i) + '>';
		} else {
			tempString = input;
		}
		return tempString;
	}

	static String removeLines(String input) // function to remove version line and comment lines
	{
		String tempString = "";
		for (int i = 0; i < input.length(); i++) {
			if ((input.charAt(i) == '<' && input.charAt(i + 1) == '?')
					|| (input.charAt(i) == '<' && input.charAt(i + 1) == '!')) {
				while (!(input.charAt(i) == '>')) {
					i++;
					continue;
				}
				i++;
			}
			tempString += input.charAt(i);

		}
		return tempString;

	}

}
