package com.machine.sdk.base.tool;

import java.util.List;

/**
 * 元组工具类
 */
public class Tuples {

    private Tuples() {}

    public record Tuple2<T1, T2>(T1 _1, T2 _2) {
        public List<Object> toList() {
            return List.of(_1, _2);
        }

        public Tuple2<T2, T1> swap() {
            return new Tuple2<>(_2, _1);
        }
    }

    public record Tuple3<T1, T2, T3>(T1 _1, T2 _2, T3 _3) {
        public List<Object> toList() {
            return List.of(_1, _2, _3);
        }
    }

    public record Tuple4<T1, T2, T3, T4>(T1 _1, T2 _2, T3 _3, T4 _4) {
        public List<Object> toList() {
            return List.of(_1, _2, _3, _4);
        }
    }

    public record Tuple5<T1, T2, T3, T4, T5>(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) {
        public List<Object> toList() {
            return List.of(_1, _2, _3, _4, _5);
        }
    }

    public record Tuple6<T1, T2, T3, T4, T5, T6>(
            T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6) {
        public List<Object> toList() {
            return List.of(_1, _2, _3, _4, _5, _6);
        }
    }

    public record Tuple7<T1, T2, T3, T4, T5, T6, T7>(
            T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7) {
        public List<Object> toList() {
            return List.of(_1, _2, _3, _4, _5, _6, _7);
        }
    }

    public record Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>(
            T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8) {
        public List<Object> toList() {
            return List.of(_1, _2, _3, _4, _5, _6, _7, _8);
        }
    }


    public static <T1, T2> Tuple2<T1, T2> of(T1 t1, T2 t2) {
        return new Tuple2<>(t1, t2);
    }

    public static <T1, T2, T3> Tuple3<T1, T2, T3> of(T1 t1, T2 t2, T3 t3) {
        return new Tuple3<>(t1, t2, t3);
    }

    public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> of(T1 t1, T2 t2, T3 t3, T4 t4) {
        return new Tuple4<>(t1, t2, t3, t4);
    }

    public static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> of(
            T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
        return new Tuple5<>(t1, t2, t3, t4, t5);
    }

    public static <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> of(
            T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
        return new Tuple6<>(t1, t2, t3, t4, t5, t6);
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Tuple7<T1, T2, T3, T4, T5, T6, T7> of(
            T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
        return new Tuple7<>(t1, t2, t3, t4, t5, t6, t7);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> of(
            T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
        return new Tuple8<>(t1, t2, t3, t4, t5, t6, t7, t8);
    }
}