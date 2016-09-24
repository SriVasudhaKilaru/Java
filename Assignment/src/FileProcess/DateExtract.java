package FileProcess;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Map;
import java.util.regex.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class DateExtract {

    public static final String DATE_REGEX = "\b([0-9]{1,2} ?([\\-/\\\\] ?[0-9]{1,2} ?| (Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) ?)([\\-/\\\\]? ?('?[0-9]{2}|[0-9]{4}))?)\b";
    public static final Pattern DATE_PATTERN = Pattern.compile(DATE_REGEX, Pattern.CASE_INSENSITIVE); // Case insensitive is to match also "mar" and not only "Mar" for March


    public List < String > getDateList(String fileName) {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        List < String > dateList = new ArrayList < String > ();
        Map < String, List > mapOfLists = new HashMap < String, List > ();


        try {
            fis = new FileInputStream(fileName);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, " :=-_.");
                int count_tmp = st.countTokens();
                while (st.hasMoreTokens()) {
                    String tmp = st.nextToken();
                    dateList.add(tmp);
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
        return dateList;
    }


    public static void main(String a[]) throws IOException {
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