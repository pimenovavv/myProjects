import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParsingMetro {
    static Document doc = getURL(Main.url);

    public static Document getURL(String url) {
        if (doc == null) {
            try {
                doc = Jsoup.connect(url).maxBodySize(0)
                        .get();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return doc;
    }

    public static List<String> getNameLines() {
        Elements nameLine = doc.select("span.js-metro-line");
        List<String> lines = new ArrayList();
        for (Element el : nameLine) {
            lines.add(el.text());
        }
        return lines;
    }

    public static List<String> getNumberLines() {
        String number;
        Pattern pattern = Pattern.compile("[0-9, A, D]{1,3}");
        Matcher matcher;
        List<String> numberLines = new ArrayList<>();

        Elements link = doc.getElementsByTag("div");

        for (Element el : link) {
            number = el.attr("data-line");

            matcher = pattern.matcher(number);
            if (matcher.matches()) {
                numberLines.add(number.trim());
            }
        }
        return numberLines;
    }

    public static List<String> stationsOnLines() {
        List<String> stationsOnLine = new ArrayList<>();
        Elements stationsOnLines = doc.select("div.t-metrostation-list-table");
        for (Element el : stationsOnLines) {
            stationsOnLine.add(el.text().trim());
        }
        return stationsOnLine;
    }


    public static List<List<String>> connections() {

        List<List<String>> connections = new ArrayList<>();

        Elements elements = doc.select("#metrodata > div > div > div > p > a");

        for (Element e : elements) {
            List<String> stations = new ArrayList<>();
            Elements select = e.select("span.t-icon-metroln");

            if (select.size() > 0) {
                String station = e.selectFirst("span.name").text();
                stations.add(station);

                for (Element ee : select) {

                    String lineNumber = ee.toString().substring(ee.toString().lastIndexOf("ln") + 3, ee.toString().lastIndexOf("title") - 2);
                    System.out.println(ee.text());
                    String connectedStations = ee.attr("title") + "*" + lineNumber;
                    stations.add(connectedStations);
                }
            }
            connections.add(stations);
        }
        return connections;
    }
}


