import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class Main {

    static String url = "https://lenta.ru/";
    static String url2 = "https://skillbox.ru/";

    static Set<String> listResult = new HashSet<>();
    static Set<String> childResult = new HashSet<>();
    static Map<String, Integer> map = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Set<String> result = new ForkJoinPool().invoke(new UrlFinder(url2));
        List<String> resultSort = result.stream().collect(Collectors.toList());

        for (int i = 0; i < resultSort.size(); i++) {
            int count = 0;

            for (char element : resultSort.get(i).toCharArray()) {

                if (element == '/')
                    count++;
            }

            if (count > 3) {
                String countSpace = "";
                for (int j = 3; j <= count; j++) {
                    countSpace += "  ";
                }
                resultSort.set(i, countSpace + resultSort.get(i));
            }
        }

        PrintWriter pw = new PrintWriter(new FileOutputStream("file.txt"));
        for (String str : resultSort)
            pw.println(str);

        pw.close();
    }
}



