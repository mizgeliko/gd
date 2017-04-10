package gd.test.task2;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BooksProcessor {

    static final String REGEX = "[^a-z ]";

    static final Function<String, Map<String, Long>> TEXT_PROCESSING_FUNC = text ->
            Stream.of(Optional.ofNullable(text).orElse("").toLowerCase().replaceAll(REGEX, " ").split(" "))
                    .filter(StringUtils::isNotBlank)
                    // .map(String::toLowerCase)
                    .collect(Collectors.toMap(t -> t, t -> 1L, (l1, l2) -> l1 + l2, HashMap::new));

    static final BinaryOperator<Map<String, Long>> MERGE_MAP_ACCUMULATOR = (n, m) -> {
        m.forEach((key, value) -> n.compute(key, (k, v) -> Optional.ofNullable(v).orElse(0L) + value));
        return n;
    };

    private final ExecutorService executor;

    public BooksProcessor() {
        executor = ForkJoinPool.commonPool();
    }

    public BooksProcessor(ExecutorService executor) {
        this.executor = executor;
    }

    Supplier<Map<String, Long>> fileProcessingSupplier(Path filePath) {
        return () -> {
            Map<String, Long> map;
            try (Stream<String> stream = Files.newBufferedReader(filePath).lines()) {
                map = stream.map(TEXT_PROCESSING_FUNC).reduce(new HashMap<>(), MERGE_MAP_ACCUMULATOR);
            } catch (IOException e) {
                map = Collections.emptyMap();
                e.printStackTrace(); //todo log
            }
            return map;
        };
    }

    public CompletableFuture<Map<String, Long>> buildFuture(Stream<Path> pathStream) {

        List<CompletableFuture<Map<String, Long>>> futures = new ArrayList<>();

        pathStream.forEach(path -> futures.add(buildFuture(path)));

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
                .thenApply(v -> futures.stream().map(CompletableFuture::join))
                .thenApply(s -> s.reduce(new HashMap<>(), MERGE_MAP_ACCUMULATOR));


    }

    public CompletableFuture<Map<String, Long>> buildFuture(Path path) {
        return CompletableFuture
                        .supplyAsync(fileProcessingSupplier(path), this.executor)
                        .exceptionally(t -> Collections.emptyMap());

    }

}
