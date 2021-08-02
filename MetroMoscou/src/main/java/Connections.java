import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Connections {

    List<Station> stations;

    public Connections(Station station1, Station station2) {
        this.stations.add(station1);
        this.stations.add(station2);
    }

    public List<Station> getStations() {
        return stations;
    }

    @Override
    public String toString() {
        return stations.get(0).getLine().getNumber() + stations.get(0).getName() + stations.get(1).getLine().getNumber() + stations.get(1).getName();
    }
}
