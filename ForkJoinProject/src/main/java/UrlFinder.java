import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.*;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;


public class UrlFinder extends RecursiveTask<Set<String>> {
    static Set<String> listResult = new TreeSet<>();
    private String url;

    public UrlFinder(String url) {
        this.url = url;
        listResult.add(url);
    }

    @Override
    protected Set<String> compute() {
        ArrayList<UrlFinder> taskList = new ArrayList<>();
        getChildren().forEach(
                child -> {
                    taskList.add(
                            (UrlFinder) new UrlFinder(child)
                                    .fork());   //форкаем каждый доверний url
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {}
                });

        taskList.forEach(ForkJoinTask::join); //добавляем данные в общий список
        return listResult;
    }


    private Set<String> getChildren() {
        Set<String> childrenUrl = new TreeSet<>();
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            doc.select("a")
                    .forEach(element -> {
                        String u = element.absUrl("href");
                        if(checkURL(u)){
                            childrenUrl.add(u);
                            System.out.println(u);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return childrenUrl;
    }

    private boolean checkURL(String u) {
        return !listResult.contains(u) && u.endsWith("/") && u.contains(url);
    }
}

