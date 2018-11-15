package com.gapview.nume2.utils;

import java.util.HashMap;

public class WordCount {

    public static String getHighestWord(String inputString) {
        Integer maxCount = 0;
        String outputString= null;

        String[] ws = inputString.split(",");
        // 做的单词分割，
        // 如果你要空格分割 String[] ws = input.split(" ");
        HashMap<String, Integer> hm = new HashMap<String, Integer>();
        // 初始化HashMap

        for (String s : ws) {
            s = s.trim();
            if (isNormalWord(s) && s.matches("[a-zA-Z]\\w+"))
            // 这里是一个正则表达式，
            // 判断的是第一个必须是字母，
            // 后面的是一个字母或多个字母，
            // 一个数字或多个数字
            {
                Integer currentCount = hm.get(s);
                // get()方法将产生一个与键相关联的Integer值，
                // 然后这个值被递增，为的是记录标识符的个数
                currentCount = currentCount == null ? 1 : currentCount + 1;
                hm.put(s, currentCount);
                // 保存到HashMap中以单词做key,判断单词是否为空，空为1，或则加1

                if (currentCount > maxCount) {
                    maxCount = currentCount;
                    outputString = s;
                }
            }
        }

        return outputString;
    }

    public static boolean isNormalWord(String msg){
        return (!(msg.equals("and") ||
                msg.equals("in")||
                msg.equals("to")||
                msg.equals("had")||
                msg.equals("to")||
                msg.equals("have")||
                msg.equals("a")||
                msg.equals("the")||
                msg.equals("me")||
                msg.equals("would")||
                msg.equals("I")||
                msg.equals("as")||
                msg.equals("of")||
                msg.equals("but")||
                msg.equals("were")||
                msg.equals("still")||
                msg.equals("below")||
                msg.equals("he")||
                msg.equals("was")||
                msg.equals("that")||
                msg.equals("his")||
                msg.equals("could")||
                msg.equals("for")||
                msg.equals("He")||
                msg.equals("on")||
                msg.equals("it")||
                msg.equals("us")||
                msg.equals("not")||
                msg.equals("We")||
                msg.equals("we")||
                msg.equals("with")||
                msg.equals("When")||
                msg.equals("when")||
                msg.equals("him")||
                msg.equals("our")||
                msg.equals("before")||
                msg.equals("few")||
                msg.equals("how")||
                msg.equals("at")));
    }
}
