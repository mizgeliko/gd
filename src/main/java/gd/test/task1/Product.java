package gd.test.task1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class Product {
    private String productId;
    private Type type;
    private String category;
    private int price;

    enum Type {
        Shirt, Pants, Shoe, Socks
    }
}
