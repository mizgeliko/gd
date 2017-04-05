package gd.test.task1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class MainTest {

    private List<Product> productList;

    @Before
    public void init() throws IOException {
        ObjectMapper mapper = Main.getObjectMapper();
        productList = new JsonProductListParser(mapper).parse(Main.INPUT);
    }

    @Test
    public void test() {
        List<List<Product>> lists = Main.pairGroupsAndSort(productList);
        assertEquals(2, lists.size());
        assertEquals(2, lists.get(0).size());
        assertEquals(2, lists.get(1).size());
        assertEquals(25, getProductsSum(lists.get(0)));
        assertEquals(55, getProductsSum(lists.get(1)));

        assertEquals(1, getReducedCategories(lists.get(0)).size());
        assertEquals(1, getReducedCategories(lists.get(1)).size());

        assertEquals("1", getReducedCategories(lists.get(0)).iterator().next());
        assertEquals("3", getReducedCategories(lists.get(1)).iterator().next());

    }


    private int getProductsSum(List<Product> list) {
        return list.stream().mapToInt(Product::getPrice).sum();
    }

    private Set<String> getReducedCategories(List<Product> list) {
        return list.stream().map(p -> p.getCategory().replaceAll("\\D","")).collect(Collectors.toSet());
    }
}
