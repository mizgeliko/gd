package gd.test.task1;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;

class JsonOutputTransformer implements OutputTransformer {

    private ObjectMapper mapper;

    public JsonOutputTransformer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public <T> void write(T data, OutputStream stream) throws IOException {
        mapper.writeValue(stream, data);
    }

}
