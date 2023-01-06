import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task2 {
    Pattern quotePattern = Pattern.compile("\"\\w+\"");
    Pattern digitPattern = Pattern.compile("-?\\d+");
    public void init() {
    }

    public void addLine(String input) {

        JSONObject json = new JSONObject(input);
       boolean rm= checkRemove(json);
       if(rm){
           out(0);
           return;
       }
        
        String inp = json.toString();
        Matcher quoteMatcher = quotePattern.matcher(inp);
        inp = quoteMatcher.replaceAll("_");

        long sum = 0;
        Matcher digitMatcher = digitPattern.matcher(inp);
        while(digitMatcher.find()){
            String grp = digitMatcher.group();
            long val = Long.parseLong(grp);
            sum+=val;
        }

        out(sum);
    }

    boolean checkRemove(JSONObject json){

        for (String key : json.keySet()) {
            String str = json.optString(key);
            if( "red".equals(str))
                return true;
        }

        Set<String> toRemove = new HashSet<>();
        for (String key : json.keySet()) {
            JSONObject obj = json.optJSONObject(key);
            if( obj!=null)
            {
                boolean rm = checkRemove(obj);
                if(rm)
                    toRemove.add(key);
            }
        }
        for (String key : toRemove) {
            json.remove(key);
        }

        for (String key : json.keySet()) {
            JSONArray arr = json.optJSONArray(key);
            if( arr!=null)
                checkRemove(arr);
        }

        return false;
    }

    void checkRemove(JSONArray arr){
        List<Integer> toRemove=new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.optJSONObject(i);
            if(obj!=null){
                boolean rm = checkRemove(obj);
                if(rm)
                    toRemove.add(i);
            }


            JSONArray subArr = arr.optJSONArray(i);
            if(subArr!=null)
                checkRemove(subArr);
        }

        Collections.reverse(toRemove); // von hinten durchgehen! da indices
        for (Integer integer : toRemove) {
            arr.remove(integer);
        }
    }


    public void afterParse() {
    }

     public void out(Object... str) {
        String out = "";
        for (Object o : str) {
            if (out.length() > 0)
                out += " ";
            out += o;
        }
        System.out.println(out);
    }

    String cleanFrom(String input, String... strings) {
        String result = input;
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            result = result.replace(string, "");
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

}
