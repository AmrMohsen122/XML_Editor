import java.util.Stack;
import java.util.*;
public class ErrorDetect {
    
   static Stack<String> s = new Stack<String>();
    static StringBuffer newXML;
    static Vector<Integer> errorIndecies = new Vector<Integer>(1);
    static Vector<String> errorCodes = new Vector<String>();
    //static int push_pop_index = 0;
    
    /*
        static int incrementIndex(int index, String str){
        
            for(int i = 0;  i<= str.length();i++)
            index++;
            return index;
        }
     */
    static int findPreviousLine(int index, StringBuffer str) {
        int j = index;
        for (; str.charAt(j) != 10; j--);

        return j;
    }

    static int incrementIndex(int index, String str) {

        for (int i = 0; i < str.length(); i++, index++);

        return index;
    }
    //Function for removing spaces to get the same format for correction whatever the input is
    static StringBuffer removeSpace(StringBuffer str) {
        StringBuffer formattedXML = new StringBuffer(str);

        for (int i = 1; i < formattedXML.length() - 1; i++) {
            if(formattedXML.charAt(i) == '.')
                formattedXML.deleteCharAt(i);
            //32 is ascii code for blank space and 10 is ascii code for new lines
            if (formattedXML.charAt(i) == 32 && formattedXML.charAt(i - 1) == 10 || formattedXML.charAt(i) == 32 && formattedXML.charAt(i - 1) == 32) {

                formattedXML.deleteCharAt(i);
                i--;

            }
           

        }

        return formattedXML;
    }
    //Function for removing new lines between opening tags and closing tags and their corresponding tag values
    static StringBuffer removeLine(StringBuffer str) {
        StringBuffer formattedXML = new StringBuffer(str);

        for (int i = 1; i < formattedXML.length() - 1; i++) {
            //32 is ascii code for blank space and 10 is ascii code for new lines
           if(formattedXML.charAt(i)==10 &&(formattedXML.charAt(i +1)==10||formattedXML.charAt(i-1)==10))
              formattedXML.deleteCharAt(i);
            if(i >= 2&&formattedXML.charAt(i) == '/' &&formattedXML.charAt(i - 2) == 10&& Character.isLetterOrDigit(formattedXML.charAt(i - 3)) || formattedXML.charAt(i) == '>' && i <formattedXML.length()-1 && formattedXML.charAt(i + 1)==10&&Character.isLetterOrDigit(formattedXML.charAt(i + 2))){
            if(formattedXML.charAt(i - 2)==10)
                formattedXML.deleteCharAt(i - 2);
            
            if(formattedXML.charAt(i + 1)==10){
                boolean tag = false;
                int j = i;
                j++;
                j++;
                for(;formattedXML.charAt(j)!=10;j++)
                    if(formattedXML.charAt(j) == '<')
                        tag = true;
                if(!tag)
                formattedXML.deleteCharAt(i + 1);
            }
            }
           

        }

        return formattedXML;
    }
    //Function for inserting the missing tags in the XML file
    static void insertTag(StringBuffer str, int index, String ins) {

        newXML = new StringBuffer(str.insert(index, ins));

    }
    //Function for getting opening tags and closing tags to push them into our stack or pop them
    static String getTag(StringBuffer str, int index) {
        int j = 0;
        char tag[] = new char[15];                                      //3ayz at2ked mn length el tag
        for (; !Character.isWhitespace(str.charAt(index)) && index != str.length() && str.charAt(index) != '>'; index++) {
            tag[j] = str.charAt(index);
            j++;
        }

        String tagStr = new String(tag);
        return tagStr;

    }
    //main function for detecting and correcting errors
    static void error(StringBuffer str){
        
        newXML = new StringBuffer(str);

        Boolean tagValue = false;
        int index = 0;
        int newXMLindex = 0;
        int count = 0;
        
        
        for (; newXMLindex < newXML.length(); index++, newXMLindex++) {
            
                
            
            if (newXML.charAt(newXMLindex) == 10) {
                count++;
            }
            
            if (newXML.charAt(newXMLindex) == '<') {

                newXMLindex++;
                index++;                   
                //to ignore starting line informing of the xml version
                if (newXML.charAt(newXMLindex) == '?') {

                    for (; newXML.charAt(newXMLindex) != '\n'; index++, newXMLindex++);

                    continue;
                    
                } 
                //Detecting in case of an invalid xml comment
                else if (newXML.charAt(newXMLindex) == '!' && newXML.charAt(newXMLindex + 1) == '-' && newXML.charAt(newXMLindex + 2) == '-') {
                    int occurence = 0;
                    for (int i = 0; newXML.charAt(newXMLindex) != '>'; index++, newXMLindex++, i++) {
                        if (i > 2) {
                            if (newXML.charAt(newXMLindex) == '-' && newXML.charAt(newXMLindex - 1) == '-') {
                                occurence++;
                                if (occurence > 1) {
                                    errorCodes.add("Comment format is Incorrect at line: " + count + 1);
                                    errorIndecies.add(count + 1);
                                }
                            }

                        }

                    }
                    if (newXML.charAt(newXMLindex - 1) != '-' || newXML.charAt(newXMLindex - 2) != '-') {
                        errorCodes.add("Comment format is Incorrect at line: " + count + 1);
                        errorIndecies.add(count + 1);
                    }

                } 
                //Detecting the opening tags and inseting them into the stack
                else if (newXML.charAt(newXMLindex) != '/' /*&& !Character.isWhitespace(str.charAt(index))*/) {

                    String tagStr = getTag(str, index);
                    
                    //Function for correction in case a closing parent tag is missing and a new tag of the same name is inserted as in case of ..missing</user>.. <user>
                      if (!s.empty() && tagStr.equals(s.peek())) {
                        
                        errorIndecies.add(count);
                        
                        String unmatchingTag = new String("</" + s.peek() + ">");
                        errorCodes.add("Closing tag not found: " + unmatchingTag );
                        
                        int j = findPreviousLine(newXMLindex, newXML);
                        

                        insertTag(newXML, j, unmatchingTag);
                        s.pop();
                        newXMLindex = incrementIndex(newXMLindex, unmatchingTag);
                        
                    }
                    s.push(tagStr);
                    
                } 
                //Detecting the closing tags
                else if (newXML.charAt(newXMLindex) == '/') {
                    index++;
                    newXMLindex++;
                   
                    String closingTag = getTag(str, index);
                    
                    //correcting the error in case of a missing openning tag
                    if(!s.empty())
                    if ( !s.peek().equals(closingTag) && tagValue) {
                        
                        errorIndecies.add(count + 1);
                        errorCodes.add("Missing openning tag: " + '<'+closingTag+'>');
                        int j = findPreviousLine(newXMLindex, newXML);
                        j++;
                        

                        String unmacthedTag = new String("<" + closingTag + ">");
                        insertTag(newXML, j, unmacthedTag);
                        
                        for (int i = 0; i < unmacthedTag.length(); i++, newXMLindex++, j++);
                        
                        //Detecting a Mismatching Opening Tag
                        if (newXML.charAt(j) == '<') {
                            
                            j++;
                            j++;
                            if (getTag(newXML, j).equals(closingTag)) {
                               
                                continue;
                            } 
                            //We always assume that in case of mismatching tags that the closing tag is the correct one   
                            else {
                                j--;

                                newXML.deleteCharAt(--j);

                                newXMLindex--;

                                
                                for (int k = 0; newXML.charAt(j) != '>'; k++, newXMLindex--) {

                                    newXML.deleteCharAt(j);

                                    
                                }
                                newXML.deleteCharAt(j);
                                newXMLindex--;
                                s.pop();
                                

                            }

                        }

                        tagValue = false;
                        continue;
                        
                    
                    }
                    //case for correcting in case the closing tag of a parent node is missing after the closing tag of its child
                    else if (!s.empty() && !s.peek().equals(closingTag) && !tagValue) {
                        int j = findPreviousLine(newXMLindex, newXML);

                        String missingParentClosing = new String("</" + s.peek() + ">");
                        insertTag(newXML, j, missingParentClosing);
                        newXMLindex = incrementIndex(newXMLindex, missingParentClosing);
                        
                        s.pop();
                        errorCodes.add("Missing closing parent tag: " + missingParentClosing );
                        errorIndecies.add(count);
                        
                    }

                    if (!s.empty()) {
                        s.pop();
                       
                        tagValue = false;
                        
                    }
                }
            } else if ((Character.isLetterOrDigit(newXML.charAt(newXMLindex)) && (newXML.charAt(newXMLindex - 1) == '>'|| newXML.charAt(newXMLindex - 2) == '>')) || (Character.isLetterOrDigit(newXML.charAt(newXMLindex)) &&(newXML.charAt(newXMLindex + 1) == '<'))) {

                tagValue = true;
                
            }
            
            //function for correcting in case there is no closing tag for a certain node
            if(!s.empty())
            if (newXML.charAt(newXMLindex) == 10 && tagValue) {

                errorIndecies.add(count);
                String unmatchingTag = new String("</" + s.peek() + ">");
                errorCodes.add("Closing child tag not found: " + unmatchingTag);
                
                insertTag(newXML, newXMLindex, unmatchingTag);

                s.pop();
                tagValue = false;
                newXMLindex = incrementIndex(newXMLindex, unmatchingTag);
                

            }
            continue;
        
        }
        if(!s.empty()){
            errorIndecies.add(newXML.length());
            insertTag(newXML,newXML.length(),"</"+s.peek()+">");
        }
        if (errorIndecies.isEmpty()) {
            
            errorCodes.add("XML file is without Errors");
        } else if (!s.empty() && newXMLindex == newXML.length() - 1) {
            insertTag(newXML, newXMLindex, "</" + s.peek() + ">");
            errorIndecies.add(count + 1);
            errorCodes.add("Missing closing root tag");
            s.pop();
            
        }
        
    }

}
