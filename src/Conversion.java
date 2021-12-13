import java.util.ArrayList;
public class Conversion {
   

	public Conversion() {
		// TODO Auto-generated constructor stub

                
	}
	static public void parsing_xml(String sa,ArrayList<String> string) {
	char space=32;
	for (int i = 0; i < sa.length(); i++) {
		if (sa.charAt(i)=='<'&&sa.charAt(i+1)=='!') {
			while(sa.charAt(i)!='>') {
				i++;}
			i++;
			
		}
		if (sa.charAt(i)=='<'&&sa.charAt(i+1)=='?') {
			while(sa.charAt(i)!='>') {
				i++;}
			i++;
			
		}
		if (sa.charAt(i)==space||sa.charAt(i)=='\r'||sa.charAt(i)=='\n')continue;
		
		
		else if (sa.charAt(i)=='<') {
			
			int j=i;
			
			while ((sa.charAt(i)!='>')) {
				i++;
			}
			//if (sa.charAt(j+1)=='/')continue;
			string.add(sa.substring(j,i+1));
			
			
		}
		else {
			int k=i;
			while((sa.charAt(i)!='<')) {
				
				if (sa.charAt(i)=='\r'||sa.charAt(i)=='\n')break;
			i++;}
			string.add(sa.substring(k,i));
			--i;
		}
	}
}
}
