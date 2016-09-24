package FileProcess;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyDistinctFileWords extends DateExtract{


    public List < String > getDistinctWordList(String fileName) {
    	System.out.println("Reading File From Path:'" + fileName +"'");
        System.out.println("________________________________");
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        List < String > wordList = new ArrayList < String > ();
        int totword = 0;
        int value = 1;
        HashMap < String, Integer > wordCount = new HashMap();
        try {
            fis = new FileInputStream(fileName);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, " :=-_./0123456789%");
                while (st.hasMoreTokens()) {
                    String tmp = st.nextToken();
                    if (!wordList.contains(tmp)) {
                        wordList.add(tmp);

                    }
                    if (tmp.length() > 0 && wordCount.containsKey(tmp)) {
                        wordCount.put(tmp, wordCount.get(tmp) + 1);
                        totword += 1;
                    } else if (tmp.length() > 0) {
                        value = 1;
                        wordCount.put(tmp, value);
                        totword += 1;
                    }
                }
                System.out.println("Total of words " + totword);
                System.out.println("Output of Each Unique word and its Count");
                for (Map.Entry < String, Integer > entry: wordCount.entrySet()) {
                    String key = entry.getKey();
                    int value2 = entry.getValue();
                    System.out.println("Key:" + key + "\tValue:" + value2);
                }


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (Exception ex) {}
        }
        return wordList;
    }

    public static void main(String a[]) throws IOException {
        MyDistinctFileWords distFw = new MyDistinctFileWords();
        List < String > wordList = distFw.getDistinctWordList("C:/Users/Vasudha Kilaru/workspace/Ass1/src/input.txt");
        HashMap < String, Integer > lekat = new HashMap();
        ValueComparator bvc = new ValueComparator(lekat);
        TreeMap < String, Integer > sorted_map = new TreeMap < String, Integer > (bvc);
        HashMap < String, Integer > ascii = new HashMap();
        ValueComparator bvcASCII = new ValueComparator(ascii);
        TreeMap < String, Integer > sorted_ascii = new TreeMap < String, Integer > (bvcASCII);
        String outputFile = "C:/Users/Vasudha Kilaru/workspace/Ass1/src/output.txt";
        //write to outputFile
        PrintWriter writer = new PrintWriter(outputFile, "UTF-8");
        System.out.println("________________________________");
        System.out.println("Reversing the Unique Words:");
        for (String str: wordList) {
            char[] temparray = str.toCharArray();
            int length = str.length();
            int left, right = 0;
            right = temparray.length - 1;
            int sum = 0;
            char[] revArr;
            for (left = 0; left < right; left++, right--) {
                // Swap values of left and right 
                char temp = temparray[left];
                temparray[left] = temparray[right];
                temparray[right] = temp;
            }
            System.out.print(str + ": ");
            for (char c: temparray) {
                int temp = (int) c;
                sum = sum + temp;
                System.out.print(c);
            }
          //  System.out.println(" # " + length + " " + sum);
            System.out.println();
            lekat.put(str, length);
            ascii.put(str, sum);
            if (!str.isEmpty() && Character.isUpperCase(str.charAt(1))) {
                writer.println("Upper case: " + str);
            }
        }
        writer.close();
        System.out.println("________________________________");
        System.out.println("ASCII value of each character of a word, Summing each character ASCII value");
        System.out.println(ascii);
        sorted_ascii.putAll(ascii);
        System.out.println("________________________________");
        System.out.println("Sorting in Decesending by Order Sum");
        System.out.println("results: " + sorted_ascii);
        System.out.println("________________________________");
        System.out.println("# of characters in a word");
        System.out.println(lekat);
        sorted_map.putAll(lekat);
        System.out.println("________________________________");
        System.out.println("Sorting in Decesending by Order # of Characters");
        System.out.println("results: " + sorted_map);
        System.out.println("________________________________");
        System.out.println("Original words which are in UPPERCASE are exported to external file " + outputFile);
        System.out.println("________________________________");
        System.out.println(" Extracting Date with Pattern MM/dd/yy:");        
        DateExtract dateFw = new DateExtract();
        List < String > dateList = dateFw.getDateList("C:/Users/Vasudha Kilaru/workspace/Ass1/src/input.txt");
        for (String str: dateList) {
            String expectedPattern = "MM/dd/yyyy";
            SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
            try {
                // (2) give the formatter a String that matches the SimpleDateFormat pattern
                if (!str.isEmpty() && !Character.isAlphabetic(0) && str.length() == 8) {
                    Date date = formatter.parse(str);
                    System.out.println(str + " Converting Date: " + date);
                }

                // (3) prints out "Tue Sep 22 00:00:00 EDT 2009"

            } catch (ParseException e) {
                // execution will come here if the String that is given
                // does not match the expected format.
                //e.printStackTrace();
            }
        }
    }
}

class ValueComparator implements Comparator < String > {
    Map < String, Integer > base;

    public ValueComparator(Map < String, Integer > base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with
    // equals.
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}