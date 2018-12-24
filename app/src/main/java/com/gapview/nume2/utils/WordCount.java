package com.gapview.nume2.utils;

import java.util.Arrays;
import java.util.HashMap;
import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;

public class WordCount {

    private static final String TAG = "WordCount";


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


    public static String getWords(String classification_list, String tag_list) {
        Integer maxCount = 0;
        String outputString= null;

        String[] class_strs = classification_list.split(",");
        String[] tag_strs = tag_list.split(",");

        Log.d (TAG, "class_strs : " + Arrays.toString(class_strs) + " tag_strs: " + Arrays.toString(tag_strs));

        String[] limitation = new String[] {"Ted Talk", "Dilbert", "Simpsons"};
        int[] numsToGenerate_limit           = new int[limitation.length];
        double[] discreteProbabilities_limit = new double[limitation.length];
        HashMap<Integer, String> hm_int_str_limit = new HashMap();

        for (int i = 0; i < limitation.length; i++) {
            numsToGenerate_limit[i] = i;
            discreteProbabilities_limit[i] = 1.0/limitation.length;
            hm_int_str_limit.put(i, limitation[i]);
        }
        EnumeratedIntegerDistribution distribution_limit =
                new EnumeratedIntegerDistribution(numsToGenerate_limit, discreteProbabilities_limit);
        String limit_sample = hm_int_str_limit.get(distribution_limit.sample());


            // Test code
        String[] main_categories = new String[] {"finance", "health", "home"};
        int[] numsToGenerate           = new int[main_categories.length];
        double[] discreteProbabilities = new double[main_categories.length];
        HashMap<String, Integer> hm_str_int = new HashMap();
        HashMap<Integer, String> hm_int_str = new HashMap();
        for (int i = 0; i < main_categories.length; i++) {
            numsToGenerate[i] = i;
            discreteProbabilities[i] = 1.0/main_categories.length;
            hm_str_int.put(main_categories[i], i);
            hm_int_str.put(i, main_categories[i]);
        }

        String[] finance_words = new String[]{"Investment", "Insurance", "Tax", "Estate", "Debt"};
        String[] health_words = new String[]{"Accidents and Emergencies", "Medicine",  "Mental Health", "Sleep", "Diet", "Exercise"};
        String[] home_words = new String[] {"Maintenance", "Renovation", "Room Organisation", "Housework"};


        int[] numsToGenerate_finance           = new int[finance_words.length];
        double[] discreteProbabilities_finance = new double[finance_words.length];
        HashMap<String, Integer> hm_str_int_finance = new HashMap();
        HashMap<Integer, String> hm_int_str_finance = new HashMap();
        for (int i = 0; i < finance_words.length; i++) {
            numsToGenerate_finance[i] = i;
            discreteProbabilities_finance[i] = 1.0/finance_words.length;
            hm_str_int_finance.put(finance_words[i], i);
            hm_int_str_finance.put(i, finance_words[i]);
        }

        int[] numsToGenerate_health           = new int[health_words.length];
        double[] discreteProbabilities_health = new double[health_words.length];
        HashMap<String, Integer> hm_str_int_health = new HashMap();
        HashMap<Integer, String> hm_int_str_health = new HashMap();
        for (int i = 0; i < health_words.length; i++) {
            numsToGenerate_health[i] = i;
            discreteProbabilities_health[i] = 1.0/health_words.length;
            hm_str_int_health.put(health_words[i], i);
            hm_int_str_health.put(i, health_words[i]);
        }

        int[] numsToGenerate_home           = new int[home_words.length];
        double[] discreteProbabilities_home = new double[home_words.length];
        HashMap<String, Integer> hm_str_int_home = new HashMap();
        HashMap<Integer, String> hm_int_str_home = new HashMap();
        for (int i = 0; i < home_words.length; i++) {
            numsToGenerate_home[i] = i;
            discreteProbabilities_home[i] = 1.0/home_words.length;
            hm_str_int_home.put(home_words[i], i);
            hm_int_str_home.put(i, home_words[i]);
        }

        //  Update the distribution
        //String[] class_strs = new String[] {"finance", "health"};
        //String[] tag_strs = new String[] {"Investment", "Medicine"};
        double weight = 0.2;
        double sum = 0;

        String w, t;
        for (int i =0; i < class_strs.length; i++) {
            w = class_strs[i].trim();
            t = tag_strs[i].trim();
            discreteProbabilities[hm_str_int.get(w)] += weight;
            if (w == "finance") {
                discreteProbabilities_finance[hm_str_int_finance.get(t)] += weight;
            }
            else if (w == "health") {
                discreteProbabilities_health[hm_str_int_health.get(t)] += weight;
            }
            else if (w == "home") {
                discreteProbabilities_home[hm_str_int_home.get(t)] += weight;
            }
        }

        // Total distribution
        sum = 0;
        for (double j : discreteProbabilities) {
            sum += j;
        }
        for(int j = 0; j < discreteProbabilities.length; j++) {
            discreteProbabilities[j] = discreteProbabilities[j]/sum;
        }
        // Total distribution
        sum = 0;
        for (double j : discreteProbabilities_finance) {
            sum += j;
        }
        for(int j = 0; j < discreteProbabilities_finance.length; j++) {
            discreteProbabilities_finance[j] = discreteProbabilities_finance[j]/sum;
        }

        // Total distribution
        sum = 0;
        for (double j : discreteProbabilities_health) {
            sum += j;
        }
        for(int j = 0; j < discreteProbabilities_health.length; j++) {
            discreteProbabilities_health[j] = discreteProbabilities_health[j]/sum;
        }

        // Total distribution
        sum = 0;
        for (double j : discreteProbabilities_home) {
            sum += j;
        }
        for(int j = 0; j < discreteProbabilities_home.length; j++) {
            discreteProbabilities_home[j] = discreteProbabilities_home[j]/sum;
        }

        EnumeratedIntegerDistribution distribution =
                new EnumeratedIntegerDistribution(numsToGenerate, discreteProbabilities);

        //int[] sample = distribution.sample(40);
        //int numSamples = 1;
        int samples = distribution.sample();
        String class_sample = hm_int_str.get(samples);
        String tag_sample;
        if (class_sample == "finance") {
            EnumeratedIntegerDistribution distribution_finance =
                    new EnumeratedIntegerDistribution(numsToGenerate_finance, discreteProbabilities_finance);
            tag_sample = hm_int_str_finance.get(distribution_finance.sample());
        }
        else if (class_sample == "health") {
            EnumeratedIntegerDistribution distribution_health =
                    new EnumeratedIntegerDistribution(numsToGenerate_health, discreteProbabilities_health);
            tag_sample = hm_int_str_health.get(distribution_health.sample());
        }
        else if (class_sample == "home") {
            EnumeratedIntegerDistribution distribution_home =
                    new EnumeratedIntegerDistribution(numsToGenerate_home, discreteProbabilities_home);
            tag_sample = hm_int_str_home.get(distribution_home.sample());
        }
        else {
            tag_sample = "Other";
        }

        // Test code
        outputString = class_sample + " " + tag_sample + " " + limit_sample;

        Log.d(TAG, "getWords result: " + outputString);
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
