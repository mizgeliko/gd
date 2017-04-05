package gd.test.task1;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Main {

    public static final String INPUT =
            "[{productId: '1234', type: 'Shirt', category: '3', price: 40},\n" +
            "{productId: '2341', type: 'Pants', category: '3a', price: 15},\n" +
            "{productId: '123', type: 'Shoe', category: '1', price: 20},\n" +
            "{productId: '234', type: 'Socks', category: '1a', price: 5}]";

    public static void main(String[] args) throws IOException {

        ObjectMapper mapper = getObjectMapper();

        List<Product> productList = new JsonProductListParser(mapper).parse(INPUT);

        OutputTransformer outputTransformer = new JsonOutputTransformer(mapper);

        List<?> result = pairGroupsAndSort(productList);

        System.out.println(outputTransformer.transform(result));

    }

    static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        return mapper;
    }

    static List<List<Product>> pairGroupsAndSort(List<Product> products) {
        Map<String, CategoryContainer> map = new HashMap<>();
        for (Product p : products) {
            String key = p.getCategory().replaceAll("\\D", "");
            map.computeIfAbsent(key, k -> new CategoryContainer()).add(p);
        }

        return map.values().stream()
                .sorted()
                .map(CategoryContainer::getProducts)
                .collect(Collectors.toList());
    }


}


