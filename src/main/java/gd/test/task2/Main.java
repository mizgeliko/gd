package gd.test.task2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {


    public static void main(String[] args) throws Exception {


        BooksProcessor processor = new BooksProcessor(Executors.newWorkStealingPool());

        Path directory = Paths.get(MainDraft.class.getClassLoader().getResource("gd/test/books").toURI());

        try (Stream<Path> stream = Files.list(directory)) {

            CompletableFuture<Map<String, Long>> future = processor.buildFuture(stream);

            Map<Boolean, List<Map.Entry<String, Long>>> partitioned =
                    Optional.ofNullable(future.join()).orElse(Collections.emptyMap())
                            .entrySet()
                            .stream()
                            .collect(Collectors.partitioningBy(e -> e.getValue() == 1));

            System.out.println("UNIQUE WORDS");
            print(partitioned.get(true));

            System.out.println("NON-UNIQUE WORDS");
            print(partitioned.get(false));

        }

    }

    private static void print(List<Map.Entry<String, Long>> list) {
        System.out.println(list.stream().map(Map.Entry::getKey).collect(Collectors.joining(", ")));
    }

}

