import org.jsoup.nodes.Document;

public class Main {

    static String url = "https://www.moscowmap.ru/metro.html#lines";
    static String fileName = "gson.json";

    public static void main(String[] args) {

        MetroWriter.metroWriter(url, fileName);
        MetroReader.jsonReader(fileName);
    }
}



