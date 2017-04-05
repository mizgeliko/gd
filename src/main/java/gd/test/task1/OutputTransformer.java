package gd.test.task1;

import java.io.IOException;

interface OutputTransformer {
    <T> String transform(T data) throws IOException;
}
