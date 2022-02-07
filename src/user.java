package application;

import java.util.ArrayList;

public class user {

    int id;
    ArrayList<Integer> followers = new ArrayList();

    public user(int id) {

        this.id = id;


    }

    public static void new_parse(String input, ArrayList<user> users){
        ArrayList<String> arr = new ArrayList();
        Tree.parsing_XML(input, arr);
        int user_count=-1;
        for (int i = 0; i < arr.size(); i++) {

            if( arr.get(i).equals("<user>") ){
                while( !arr.get(i).equals("<id>") ){i++;}
                user_count++;
                i++;

                users.add(new user(Integer.parseInt(arr.get(i))));

            }
            if(arr.get(i).equals("<followers>")){
                    while(!arr.get(i).equals("</followers>")){

                        if(Tree.isNumeric(arr.get(i))){

                            users.get(user_count).followers.add(Integer.parseInt(arr.get(i)));
                        }

                        i++;

                    }

            }

        }


    }







}
