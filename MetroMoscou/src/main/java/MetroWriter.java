import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.nodes.Document;

public class MetroWriter {

    public static void metroWriter(String url, String fileName) {
        Document doc = ParsingMetro.getURL(url);

        MetroMoscou metroMoscou = new MetroMoscou();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String json = gson.toJson(metroMoscou);

        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
