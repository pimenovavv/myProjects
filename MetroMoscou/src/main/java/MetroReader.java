import java.io.FileReader;
import java.io.Reader;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MetroReader {
    public static void jsonReader(String fileName) {
        try {
            Reader reader = new FileReader(fileName);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray lnResult = (JSONArray) jsonObject.get("Линии");
            Iterator<String> iterator = lnResult.iterator();

            JSONObject stOnLnResult = (JSONObject) jsonObject.get("Станции");

            while (iterator.hasNext()) {
                String str = String.valueOf(iterator.next());
                String number = str.replaceAll("\\{\"number\":\"", "").replaceAll("\",.+", "");
                String name = str.replaceAll(".+name\":\"", "").replace("\"}", "");
                Line line = new Line(number, name);
                System.out.print(line.toString() + ": станций на линии ");
                JSONArray str2 = (JSONArray) stOnLnResult.get(line.toString());
                System.out.println(str2.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

