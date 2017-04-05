package gd.test;

import gd.test.task1.Task1Test;
import gd.test.task3.Task3Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        Task1Test.class,
        Task3Test.class
})
public class TasksSuite {
}
