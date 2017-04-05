package gd.test.task3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class SomeEntityFactoryTest {
    @Parameterized.Parameters
    public static Collection<Long> data() {
        return Arrays.asList(0L, 100L, 2000L, 50000L, Long.MAX_VALUE, Long.MIN_VALUE);
    }

    @Parameterized.Parameter
    public Long offset;

    @Test
    public void createSingleEntity() {
        EntityFactory factory = new EntityFactory();
        Entity ent = factory.createObject(offset);
        assertEquals(ent.getTimestamp(), offset + Instant.now().getEpochSecond());
    }

    @Test
    public void createStream() {
        int size = 100;
        UnaryOperator<Long> incOperator = i -> i + 1;
        List<Long> elementOffset = Stream.iterate(0L, incOperator).limit(size).collect(Collectors.toList());

        EntityFactory factory = new EntityFactory();
        List<Entity> list = factory.createStream(size, offset, incOperator).collect(Collectors.toList());

        long seconds = Instant.now().getEpochSecond();

        for (int i = 0; i < list.size(); i++) {
            assertNotNull(list.get(i));
            assertEquals(list.get(i).getTimestamp(), offset + elementOffset.get(i) + seconds);
        }

    }
}
