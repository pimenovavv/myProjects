public class Station {

    private Line line;
    private String station;

    public Station(String name, Line line) {
        this.station = name;
        this.line = line;
    }

    public Line getLine() {
        return line;
    }

    public String getName() {
        return station;
    }

    @Override
    public String toString() {
        return station;
    }
}
