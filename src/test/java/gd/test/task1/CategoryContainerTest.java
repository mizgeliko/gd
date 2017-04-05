package gd.test.task1;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CategoryContainerTest {

    private CategoryContainer container1;
    private CategoryContainer container2;

    @Before
    public void init() {
        container1 = new CategoryContainer();
        container1.add(new Product("_id_1", Product.Type.Shirt, "1a", 120));
        container1.add(new Product("_id_4", Product.Type.Socks, "1", 5));

        container2 = new CategoryContainer();
        container2.add(new Product("_id_2", Product.Type.Pants, "2", 10));
        container2.add(new Product("_id_3", Product.Type.Shoe, "2a", 200));
    }

    @Test
    public void testSum() {
        assertEquals(2, container1.getProducts().size());
        assertEquals(2, container2.getProducts().size());

        assertEquals(125, container1.getSum());
        assertEquals(210, container2.getSum());

    }

    @Test
    public void testSort() {
        List<CategoryContainer> list = Arrays.asList(container2, container1);
        assertEquals(210, list.get(0).getSum());
        assertEquals(125, list.get(1).getSum());

        Collections.sort(list);
        assertEquals(125, list.get(0).getSum());
        assertEquals(210, list.get(1).getSum());
    }
}
