package gd.test.task1;

import java.io.IOException;
import java.util.List;

interface ProductListParser {
    List<Product> parse(String input) throws IOException;
}
