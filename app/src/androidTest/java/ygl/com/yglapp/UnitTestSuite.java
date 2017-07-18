package ygl.com.yglapp;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

// Runs all unit tests.
@RunWith(Suite.class)
@Suite.SuiteClasses({LoginInstrumentedTest.class,
        MainInstrumentedTest.class})
public class UnitTestSuite {}
