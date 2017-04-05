package gd.test.task3;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

@RunWith(Parameterized.class)
public class MaxValuesGroupedByNamesTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                        {null, 1001L, "{1=2000}"},
                        {1029L, null, "{1=29, 2=19}"},
                        {1002L, 1009L, "{1=5, 4=49}"},
                        {1001L, 1029L, "{1=2000, 2=9, 3=39, 4=59}"},
                        {1018L, 1030L, "{1=29, 2=9, 3=39, 4=59}"},
                        {null, null, "{1=2000, 2=19, 3=39, 4=59}"},
        });
    }

    @Parameterized.Parameter(0)
    public Long tsMin;
    @Parameterized.Parameter(1)
    public Long tsMax;
    @Parameterized.Parameter(2)
    public String value;

    private List<Entity> list;

    @Before
    public void setupList() {
        list = Arrays.asList(
                createEntity("1", 1000, 1),
                createEntity("1", 1001, 2000),
                createEntity("1", 1002, 3),
                createEntity("1", 1004, 4),
                createEntity("1", 1006, 5),
                createEntity("1", 1011, 6),
                createEntity("1", 1014, 7),
                createEntity("1", 1019, 8),
                createEntity("2", 1029, 9),
                createEntity("2", 1031, 19),
                createEntity("1", 1029, 29),
                createEntity("3", 1024, 39),
                createEntity("4", 1009, 49),
                createEntity("4", 1019, 59)
        );
    }

    @Test
    public void test() {
        Map<String, Integer> res = MaxValuesGroupedByNames.withTimestampRange(tsMin, tsMax).process(list.stream());
        Assert.assertEquals(value, new TreeMap<>(res).toString());
    }

    private Entity createEntity(String name, long timestamp, int value) {
        Entity e = new Entity();
        e.setName(name);
        e.setTimestamp(timestamp);
        e.setValue(value);
        return e;
    }

}
