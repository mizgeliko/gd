package gd.test.task1;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;

class JsonOutputTransformer implements OutputTransformer {

    private ObjectMapper mapper;

    public JsonOutputTransformer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public <T> String transform(T data) throws IOException {
        StringWriter stringWriter = new StringWriter();
        mapper.writeValue(stringWriter, data);
        return stringWriter.toString();
    }

}
