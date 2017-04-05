package gd.test.task3;

import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MaxValuesGroupedByNames {

    private Predicate<Entity> filterPredicate;

    private MaxValuesGroupedByNames(Long min, Long max) {
        this.filterPredicate = obj -> (obj != null)
                && (min == null || obj.getTimestamp() >= min)
                && (max == null || obj.getTimestamp() <= max);
    }

    public static MaxValuesGroupedByNames withTimestampRange(Long tsMin, Long tsMax) {
        return new MaxValuesGroupedByNames(tsMin, tsMax);
    }

    public Map<String, Integer> process(Stream<Entity> stream) {
        if (stream == null) {
            throw new IllegalArgumentException("stream shouldn't be null");
        }
        return stream.filter(filterPredicate).collect(
                Collectors.toMap(Entity::getName, Entity::getValue, (i1, i2) -> ObjectUtils.max(i1, i2), HashMap::new)
        );
    }
}
