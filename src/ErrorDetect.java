import java.util.Stack;
public class ErrorDetect {
    
        static Stack<String> s = new Stack<String>();
        
	static String getTag(String str, int index){
            int j =0;
            char tag[] = new char[15];                                      //3ayz at2ked mn length el tag
            for(; !Character.isWhitespace(str.charAt(index)) && index != str.length() && str.charAt(index) != '>'; index++){
                        tag[j] = str.charAt(index);
                        j++;
                    }
        
        String tagStr = new String(tag);
        return tagStr;
        
        }
        
        
        static Boolean error(String str){
                
                int index = 0;
                
                for(; index < str.length(); index++){
                
                if(str.charAt(index) == '<'){
                    
               
                    
                                                 
                    index++;                   //momken a3mlha b i = index +1  badal m a3ml fe index 3la tool
                    if(str.charAt(index) == '?'){
                    
                    for(; str.charAt(index) != '\n';index++);
                    
                    continue;
                    }
                    
                    if(str.charAt(index) != '/'){
                        
                    
                    String tagStr = getTag(str,index);
                    s.push(tagStr);
                        System.out.println(s);
                }
                    if(str.charAt(index) == '/'){
                        index++;
                        String closingTag = getTag(str, index);
                        if( s.empty() || !s.peek().equals( closingTag)){
                            System.out.println("Unmatched tag");
                            return false;
                        }
                        s.pop();
                        System.out.println(s);
                        
                        
                    
                    }
                }
                continue;
        
                }
        
        
        
        if(s.empty())
            System.out.println("XML file is without errors");
        else
                System.out.println("XML file contains errors");
        return s.empty();
        }

}
