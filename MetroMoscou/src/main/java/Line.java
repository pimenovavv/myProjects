import java.util.List;

public class Line {

    private String number;
    private String name;
    private List<Station> stations;

    public Line(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public Line() {
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }


    public void addStation(Station station) {
        stations.add(station);
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    @Override
    public String toString() {
        return name + " " + number;
    }
}


