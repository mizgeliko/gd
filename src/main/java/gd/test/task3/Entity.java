package gd.test.task3;

import lombok.Data;

import java.util.List;

@Data
class Entity {
    private long timestamp;
    private String name;
    private int value;
    private List<String> tags;
}
