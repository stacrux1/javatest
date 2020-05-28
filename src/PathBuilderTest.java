import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class PathBuilderTest {

    @Test
    void testReturnAllPossiblePaths() {
        Bound ab = new Bound("a", "b", 2);
        Bound bc = new Bound("b", "c", 1);
        Bound ac = new Bound("a", "c", 1);
        Bound ad = new Bound("a", "d", 3);
        Bound dc = new Bound("d", "c", 2);
        Bound bd = new Bound("b", "d", 1);
        Collection<Bound> bounds = Arrays.asList(ab, ac, bc, dc, bd, ad);
        Collection<Collection<Bound>> result =
                PathBuilder.returnAllPossiblePaths("a", "c", bounds);
        /*
            a
                b       -       d       -       c
                c               c
                    d               b
                    c               c
            expecting: (abc), (abdc), (adc), (adbc), (ac)
         */
        assertEquals(5, result.size());
    }

    @Test
    void testReturnAllPossiblePathsOrdered() {
        Bound ab = new Bound("a", "b", 2);
        Bound bc = new Bound("b", "c", 1);
        Bound ac = new Bound("a", "c", 5);
        Bound ad = new Bound("a", "d", 3);
        Bound dc = new Bound("d", "c", 2);
        Bound bd = new Bound("b", "d", 1);
        Collection<Bound> bounds = Arrays.asList(ab, ac, bc, dc, bd, ad);
        Collection<Collection<Bound>> result =
                    PathBuilder.returnAllPossiblePathsOrderedByTime("a", "c", bounds);
        int previous = 0;
        for (Collection<Bound> path : result){
            int pathDuration = path.stream().mapToInt(bnd -> bnd.duration).sum();
            assertTrue(pathDuration >= previous);
            previous = pathDuration;
        }
    }
}