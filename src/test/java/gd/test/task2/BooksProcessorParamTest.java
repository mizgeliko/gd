package gd.test.task2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BooksProcessorParamTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"test, one?", "test one", "{test=1, one=1}"},
                {"test:- one!", "test one", "{test=1, one=1}"},
        });
    }

    @Parameterized.Parameter
    public String input;
    @Parameterized.Parameter(1)
    public String regexRes;
    @Parameterized.Parameter(2)
    public String mapAsStr;

    @Test
    public void testRegex() {
        assertEquals(regexRes, input.replaceAll(BooksProcessor.REGEX,""));
    }


    @Test
    public void testTextProcessingFunction() {
        assertEquals(mapAsStr, BooksProcessor.TEXT_PROCESSING_FUNC.apply(input).toString());
    }
}
