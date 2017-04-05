package gd.test.task3;

import java.time.Instant;
import java.util.Random;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

class EntityFactory {
    private static final String[] NAMES = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight"};
    private Random rnd = new Random();

    Stream<Entity> createStream(int streamSize, long timeOffset, UnaryOperator<Long> incrementer) {
        return Stream.iterate(timeOffset, incrementer)
                .limit(streamSize)
                .map(this::createObject);
    }

    Entity createObject(long timeOffset) {
        Entity obj = new Entity();
        obj.setTimestamp(Instant.now().getEpochSecond() + timeOffset);
        obj.setName(NAMES[rnd.nextInt(8)]);
        obj.setValue(rnd.nextInt());
        return obj;
    }
}
