package gd.test;

import gd.test.task1.Task1Suite;
import gd.test.task3.Task3Suite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        Task1Suite.class,
        Task3Suite.class
})
public class TasksSuite {
}
