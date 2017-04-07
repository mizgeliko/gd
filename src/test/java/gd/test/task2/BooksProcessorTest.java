package gd.test.task2;

import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class BooksProcessorTest {

    @Test
    public void testMergeMapAccumulator() {
        Map<String, Long> m1 = new HashMap<>(), m2 = new HashMap<>();
        m1.put("one", 1L);
        m1.put("two", 2L);
        m1.put("three", 3L);
        m1.put("four", 4L);

        m2.put("two", 1L);
        m2.put("three", 3L);
        m2.put("four", 2L);
        m2.put("five", 17L);

        Map<String, Long> res = Stream.of(m1, m2).reduce(new HashMap<>(), BooksProcessor.MERGE_MAP_ACCUMULATOR);

        assertEquals(5, res.size());
        assertEquals(1, (long) res.get("one"));
        assertEquals(3, (long) res.get("two"));
        assertEquals(6, (long) res.get("three"));
        assertEquals(6, (long) res.get("four"));
        assertEquals(17, (long) res.get("five"));
    }

    @Test
    public void testBuildFuture() throws URISyntaxException {
        BooksProcessor processor = new BooksProcessor();

        URI uri = this.getClass().getClassLoader().getResource("gd/test/books/book1.txt").toURI();
        Map<String, Long> result = processor.buildFuture(Paths.get(uri)).join();

        assertEquals(4, result.size());
        assertEquals(1, (long) result.get("biggest"));
        assertEquals(2, (long) result.get("one"));
        assertEquals(2, (long) result.get("of"));
        assertEquals(1, (long) result.get("the"));

    }

    @Test
    public void testBuildFutureStream() throws URISyntaxException, IOException {

        BooksProcessor processor = new BooksProcessor(Executors.newWorkStealingPool());

        URI uri = this.getClass().getClassLoader().getResource("gd/test/books").toURI();
        Map<String, Long> result = processor.buildFuture(Files.list(Paths.get(uri))).join();

        assertEquals(8, result.size());
        assertEquals(1, (long) result.get("biggest"));
        assertEquals(1, (long) result.get("worst"));
        assertEquals(1, (long) result.get("ugly"));
        assertEquals(1, (long) result.get("most"));
        assertEquals(1, (long) result.get("best"));
        assertEquals(5, (long) result.get("one"));
        assertEquals(5, (long) result.get("of"));
        assertEquals(4, (long) result.get("the"));

    }

}
