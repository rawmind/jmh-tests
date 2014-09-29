package com.github.rawmind.microbenchmarks.reflection;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import com.github.rawmind.microbenchmarks.reflection.testpack.ProtectedObj;
import com.github.rawmind.microbenchmarks.reflection.testpack.SimpleObj;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

/**
 * Intel(R) Core(TM) i7-3632QM CPU @ 2.20GHz
 * ht/c/s
 * 2x4x1
 * <p/>
 * java 1.7.0_25
 * <p/>
 * Benchmark                                                            Mode  Samples     Score  Score error   Units
 * t.r.ReflectionInstanceCreationTest.baseline                         thrpt       20  1489.596       26.935  ops/us
 * t.r.ReflectionInstanceCreationTest.constructor                      thrpt       20   296.824        9.847  ops/us
 * t.r.ReflectionInstanceCreationTest.testNewClassInstance             thrpt       20   125.776        6.040  ops/us
 * t.r.ReflectionInstanceCreationTest.testNewInstanceViaConstructor    thrpt       20     3.120        0.041  ops/us
 * <p/>
 * 1.7.0_65
 * <p/>
 * Benchmark                                                            Mode  Samples     Score  Score error   Units
 * t.r.ReflectionInstanceCreationTest.baseline                         thrpt       20  1512.673       16.502  ops/us
 * t.r.ReflectionInstanceCreationTest.constructor                      thrpt       20   314.252        2.444  ops/us
 * t.r.ReflectionInstanceCreationTest.testNewClassInstance             thrpt       20   140.031        4.633  ops/us
 * t.r.ReflectionInstanceCreationTest.testNewInstanceViaConstructor    thrpt       20     3.865        0.068  ops/us
 */

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class ReflectionInstanceCreationTest {


    @Benchmark
    public void baseline() {
    }

    @Benchmark
    public Object constructor() {
        return new SimpleObj();
    }

    @Benchmark
    public Object testNewInstanceViaConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<ProtectedObj> clazz = ProtectedObj.class;
        Constructor<ProtectedObj> declaredConstructor = clazz.getDeclaredConstructor();
        Constructor.setAccessible(new AccessibleObject[]{declaredConstructor}, true);
        return declaredConstructor.newInstance();
    }

    @Benchmark
    public SimpleObj testNewClassInstance() throws IllegalAccessException, InstantiationException {
        Class<SimpleObj> clazz = SimpleObj.class;
        return clazz.newInstance();
    }


}
