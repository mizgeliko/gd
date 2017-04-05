package gd.test.task1;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
class CategoryContainer implements Comparable<CategoryContainer> {
    private int sum = 0;
    private List<Product> products = new ArrayList<>();

    public void add(Product p) {
        if (p != null) {
            sum += p.getPrice();
            products.add(p);
        }
    }

    @Override
    public int compareTo(CategoryContainer o) {
        return o == null ? 1 : sum - o.sum;
    }
}
