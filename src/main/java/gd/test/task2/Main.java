package gd.test.task2;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static Function<String, Map<String, Long>> textProcessingFunc = text ->
            Stream.of(Optional.ofNullable(text).orElse("").replaceAll("[\\.\\-:;,!?“”\\\"\\d()’\\/]", "").split(" "))
                    .filter(StringUtils::isNotBlank)
                    .map(String::toLowerCase)
                    .collect(Collectors.toMap(t -> t, t -> 1L, (l1, l2) -> l1 + l2, HashMap::new));

    private static BinaryOperator<Map<String, Long>> mergeMapAccumulator = (n, m) -> {
        m.forEach((key, value) -> n.compute(key, (k, v) -> Optional.ofNullable(v).orElse(0L) + value));
        return n;
    };

    /* this is a dirty variant. will be redesigned */
    public static void main(String[] args) throws InterruptedException {

        Map<String, Long> result = null;

        List<Callable<Map<String, Long>>> tasks = new ArrayList<>();

        try (Stream<Path> pathStream = Files.list(Paths.get(Main.class.getClassLoader().getResource("gd/test/books").toURI()))) {
            pathStream.forEach(path -> {
                tasks.add(()-> {
                    Map<String, Long> map = null;
                    try (Stream<String> stream = Files.newBufferedReader(path).lines()) {
                        map = stream.map(textProcessingFunc).reduce(new HashMap<>(), mergeMapAccumulator);
                    } catch (IOException e) {
                        map = Collections.emptyMap();
                        e.printStackTrace(); //todo log
                    }
                    return map;
                });
            });
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace(); //todo log
        }

        ExecutorService executor = Executors.newWorkStealingPool();
        result = executor.invokeAll(tasks).stream().map(future -> {
            try {
                return future.get();
            }
            catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }).reduce(new HashMap<>(), mergeMapAccumulator);


        Map<Boolean, List<Map.Entry<String, Long>>> partitioned = Optional.ofNullable(result).orElse(Collections.emptyMap())
                .entrySet().stream().collect(Collectors.partitioningBy(e -> e.getValue() == 1));

        print(partitioned.get(true));
        print(partitioned.get(false));

    }

    private static void print(List<Map.Entry<String, Long>> list) {
        System.out.println(list.stream().map(Map.Entry::getKey).collect(Collectors.toList()));
    }
}
