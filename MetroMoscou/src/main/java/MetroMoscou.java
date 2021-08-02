import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MetroMoscou {

    @SerializedName("Линии")
    List<Line> lines;
    @SerializedName("Станции")
    Map<String, List<String>> stations;
    @SerializedName("Переходы")
    List<List<ConnectedStations>> connection;

    public MetroMoscou() {
        this.lines = getAllLines();
        this.stations = getStations();
        this.connection = getConnections();
    }

    public static List<Line> getAllLines() {
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            Line line = new Line(ParsingMetro.getNumberLines().get(i),
                    ParsingMetro.getNameLines().get(i));
            lines.add(line);
        }
        return lines;
    }

    public static Map<String, List<String>> getStations() {
        List<Line> lines = getAllLines();
        Map<String, List<String>> stations = new LinkedHashMap<>();
        List<String> stationsOnLine = ParsingMetro.stationsOnLines();

        for (int i = 0; i < stationsOnLine.size(); i++) {
            List<String> list = new ArrayList<>();
            String[] strings = stationsOnLine.get(i).split("\\d\\.");
            for (int j = 1; j < strings.length; j++) {
                Station station = new Station(strings[j].replaceAll(",", ""), lines.get(i));
                list.add(strings[j].replaceAll(",", "").trim());
            }
            stations.put(lines.get(i).toString(), list);
        }
        return stations;
    }


    public static List<List<ConnectedStations>> getConnections() {
        List<List<String>> connections = ParsingMetro.connections();
        List<List<ConnectedStations>> resultConnections = new ArrayList<>();

        for (List<String> list : connections) {
            List<ConnectedStations> oneConnection = new ArrayList<>();
            if (list.size() > 0) {
                Line line = new Line();
                ConnectedStations connectedStations_0 = new ConnectedStations(numberLineForSration(list.get(0)), list.get(0));
                oneConnection.add(connectedStations_0);

                for (int i = 1; i < list.size(); i++) {
                    int char1 = list.get(i).indexOf('«');
                    int char2 = list.get(i).indexOf('»');

                    ConnectedStations connectedStation = new ConnectedStations(list.get(i).substring(list.get(i).indexOf('*') + 1), list.get(i).substring(char1 + 1, char2));
                    oneConnection.add(connectedStation);

                    if (connectedStations_0.getStation().equals(connectedStation.getStation())
                            && connectedStations_0.getLine().equals(connectedStation.getLine()))
                        oneConnection.clear();
                }
            }

            if (oneConnection.size() > 1)
                resultConnections.add(oneConnection);

            if (resultConnections.size() > 0 && oneConnection.size() > 1) {
                for (int j = 0; j < resultConnections.size() - 1; j++) {
                    for (ConnectedStations conSt : resultConnections.get(j)) {
                        for (int k = 0; k < oneConnection.size(); k++) {
                            if (conSt.getLine().equals(oneConnection.get(k).getLine()) &&
                                    conSt.getStation().equals(oneConnection.get(k).getStation()))
                                resultConnections.remove(oneConnection);
                        }
                    }
                }
            }
        }
        return resultConnections;
    }

    public static String numberLineForSration(String name) {
        Map<String, List<String>> stations = getStations();
        Line line = new Line();
        Pattern pattern1 = Pattern.compile(".+" + name + ".*");
        String number = "-1";
        for (Map.Entry entry : stations.entrySet()) {
            Matcher matcher1 = pattern1.matcher(entry.getValue().toString());
            if (matcher1.matches()) {
                line.setNumber(entry.getKey().toString().substring(entry.getKey().toString().lastIndexOf(' ') + 1));
                number = line.getNumber();
            }
        }
        return number;
    }
}

