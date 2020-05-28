import java.util.*;
import java.util.stream.Collectors;

public class PathBuilder {

    /**
     * This method returns all possible connections between origin and destination
     *
     * @param origin
     * @param destination
     * @param bounds
     * @return
     */
    public static Collection<Collection<Bound>> returnAllPossiblePaths(String origin, String destination, Collection<Bound> bounds) {
        Set<String> endPoints = new HashSet<>();
        for (Bound bound : bounds) {
            endPoints.add(bound.endPointA);
            endPoints.add(bound.endPointB);
        }
        if (!endPoints.contains(origin) || !endPoints.contains(destination)) {
            return Collections.emptyList();
        }
        Collection<Bound> allBounds = generateAllBounds(bounds);
        return buildPossiblePaths(origin, destination, allBounds,
                new ArrayList<>(), new HashSet<>(Arrays.asList(origin)));
    }

    public static Collection<Collection<Bound>> returnAllPossiblePathsOrderedByTime(String origin, String destination,
                                                                                    Collection<Bound> bounds) {
        Collection<Collection<Bound>> possiblePaths = returnAllPossiblePaths(origin, destination, bounds);
        Comparator<Collection<Bound>> compareByDuration = Comparator.comparing(PathBuilder::getTotalDuration);
        return possiblePaths.stream().sorted(compareByDuration).collect(Collectors.toList());
    }

    private static Integer getTotalDuration(Collection<Bound> bounds) {
        return bounds.stream().mapToInt(b -> b.duration).sum();
    }


    private static Collection<Bound> generateAllBounds(Collection<Bound> bounds) {
        Collection<Bound> allBounds = new ArrayList<>();
        for (Bound bound : bounds) {
            allBounds.add(bound);
            allBounds.add(new Bound(bound.endPointB, bound.endPointA, bound.duration));
        }
        return allBounds;
    }

    private static Collection<Collection<Bound>> buildPossiblePaths(String origin, String destination,
                                                                    Collection<Bound> allBounds,
                                                                    Collection<Bound> currentPath,
                                                                    HashSet<String> visited) {

        Collection<Collection<Bound>> paths = new ArrayList<>();
        for (Bound bound : allBounds) {
            if (!bound.endPointA.equals(origin)) {
                continue;
            }
            if (visited.contains(bound.endPointB)) {
                continue;
            }
            if (bound.endPointB.equals(destination)) {
                Collection<Bound> finalPath = new ArrayList<>(currentPath);
                finalPath.add(bound);
                paths.add(finalPath);
                continue;
            }
            visited.add(bound.endPointB);
            currentPath.add(bound);
            paths.addAll(buildPossiblePaths(bound.endPointB, destination, allBounds, currentPath, visited));
            currentPath.remove(bound);
            visited.remove(bound.endPointB);
        }
        return paths;
    }

}
