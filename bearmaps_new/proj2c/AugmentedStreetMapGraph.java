package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.WeirdPointSet;
import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private List<Point> points = new LinkedList<>();
    private HashMap<Point, Node> mainMap = new HashMap<>();
    private TrieClass mainSet = new TrieClass();
    private HashMap<String, ArrayList<Node>> returner = new HashMap<>();

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        List<Node> nodes = this.getNodes();
        for (Node n : nodes) {
            if (n.name() != null) {
                mainSet.add(cleanString(n.name()));
                if (returner.containsKey(cleanString(n.name())) == false) {
                    returner.put(cleanString(n.name()), new ArrayList<>());
                }
                returner.get(cleanString(n.name())).add(n);
                continue;
            }
            int size = neighbors(n.id()).size();
            if (size > 0) {
                Point p = new Point(n.lon(), n.lat());
                points.add(p);
                mainMap.put(p, n);
            }
        }
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {

        WeirdPointSet mainTree = new WeirdPointSet(points);
        Point p = mainTree.nearest(lon, lat);
        long returnVal = mainMap.get(p).id();
        return returnVal;
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> locationsWithPrefix = new ArrayList<>();

        for (String s : mainSet.keysWithPrefix(cleanString(prefix))) {
            for (Node n : returner.get(s)) {
                locationsWithPrefix.add(n.name());
            }
        }

        return locationsWithPrefix;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Map<String, Object>> returnerMap = new ArrayList<>();

        for (Node n : returner.get(cleanString(locationName))) {
            HashMap<String, Object> adder = new HashMap<>();
            adder.put("lat", n.lat());
            adder.put("lon", n.lon());
            adder.put("id", n.id());
            adder.put("name", n.name());
            returnerMap.add(adder);
        }
        return returnerMap;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
