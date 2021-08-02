import java.util.Objects;

public class ConnectedStations {
    private String line;
    private String station;

    public ConnectedStations(String line, String station) {
        this.line = line;
        this.station = station;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectedStations that = (ConnectedStations) o;
        return Objects.equals(line, that.line) &&
                Objects.equals(station, that.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, station);
    }

    @Override
    public String toString() {
        return "ConnectedStations{" +
                "line='" + line + '\'' +
                ", station='" + station + '\'' +
                '}';
    }
}
