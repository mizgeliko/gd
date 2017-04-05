package gd.test.task3;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        long timeFrameInSeconds = TimeUnit.MINUTES.toSeconds(10);
        long tsMaxValue = Instant.now().getEpochSecond();
        long tsMinValue = tsMaxValue - timeFrameInSeconds;

        EntityFactory factory = new EntityFactory();

        Stream<Entity> stream = factory.createStream(20_000_000, -1 * (long) (timeFrameInSeconds * 6), x -> x + 1);

        Map<String, Integer> result = MaxValuesGroupedByNames.withTimestampRange(tsMinValue, tsMaxValue).process(stream);

        System.out.println(result);

    }

}


