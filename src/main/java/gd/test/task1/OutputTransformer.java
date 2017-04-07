package gd.test.task1;

import java.io.IOException;
import java.io.OutputStream;

interface OutputTransformer {
    <T> void write(T data, OutputStream stream) throws IOException;
}
