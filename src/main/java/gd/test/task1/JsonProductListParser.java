package gd.test.task1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

class JsonProductListParser implements ProductListParser {

    private ObjectMapper mapper;

    public JsonProductListParser(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Product> parse(String input) throws IOException {
        return mapper.readValue(input, new TypeReference<List<Product>>() {
        });
    }
}
