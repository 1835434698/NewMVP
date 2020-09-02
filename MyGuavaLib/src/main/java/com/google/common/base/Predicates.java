package com.google.common.base;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Predicates {

    enum ObjectPredicate implements Predicate<Object> {
        /** @see Predicates#alwaysTrue() */
        ALWAYS_TRUE {
            @Override
            public boolean apply(Object o) {
                return true;
            }

            @Override
            public String toString() {
                return "Predicates.alwaysTrue()";
            }
        },
        /** @see Predicates#alwaysFalse() */
        ALWAYS_FALSE {
            @Override
            public boolean apply(Object o) {
                return false;
            }

            @Override
            public String toString() {
                return "Predicates.alwaysFalse()";
            }
        },
        /** @see Predicates#isNull() */
        IS_NULL {
            @Override
            public boolean apply(Object o) {
                return o == null;
            }

            @Override
            public String toString() {
                return "Predicates.isNull()";
            }
        },
        /** @see Predicates#notNull() */
        NOT_NULL {
            @Override
            public boolean apply(Object o) {
                return o != null;
            }

            @Override
            public String toString() {
                return "Predicates.notNull()";
            }
        };

        @SuppressWarnings("unchecked") // safe contravariant cast
        <T> Predicate<T> withNarrowedType() {
            return (Predicate<T>) this;
        }
    }

    public static <T> Predicate<T> isNull() {
        return ObjectPredicate.IS_NULL.withNarrowedType();
    }

    public static <T> Predicate<T> notNull() {
        return ObjectPredicate.NOT_NULL.withNarrowedType();
    }


    public static Predicate<Object> instanceOf(Class<?> clazz) {
        return new InstanceOfPredicate(clazz);
    }


    /**
     * Returns a predicate that evaluates to {@code true} if the object being tested {@code equals()}
     * the given target or both are null.
     */
    public static <T> Predicate<T> equalTo(T target) {
        return (target == null) ? Predicates.<T>isNull() : new IsEqualToPredicate<T>(target);
    }

    /** @see Predicates#instanceOf(Class) */
    // Class.isInstance
    public static class InstanceOfPredicate implements Predicate<Object>, Serializable {
        private final Class<?> clazz;

        private InstanceOfPredicate(Class<?> clazz) {
            this.clazz = checkNotNull(clazz);
        }

        @Override
        public boolean apply(Object o) {
            return clazz.isInstance(o);
        }

        @Override
        public int hashCode() {
            return clazz.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof InstanceOfPredicate) {
                InstanceOfPredicate that = (InstanceOfPredicate) obj;
                return clazz == that.clazz;
            }
            return false;
        }

        @Override
        public String toString() {
            return "Predicates.instanceOf(" + clazz.getName() + ")";
        }

        private static final long serialVersionUID = 0;
    }

    /** @see Predicates#equalTo(Object) */
    public static class IsEqualToPredicate<T> implements Predicate<T>, Serializable {
        private final T target;

        private IsEqualToPredicate(T target) {
            this.target = target;
        }

        @Override
        public boolean apply(T t) {
            return target.equals(t);
        }

        @Override
        public int hashCode() {
            return target.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof IsEqualToPredicate) {
                IsEqualToPredicate<?> that = (IsEqualToPredicate<?>) obj;
                return target.equals(that.target);
            }
            return false;
        }

        @Override
        public String toString() {
            return "Predicates.equalTo(" + target + ")";
        }

        private static final long serialVersionUID = 0;
    }

    public static <T extends Object> T checkNotNull( T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public static <A, B> Predicate<A> compose(
            Predicate<B> predicate, Function<A, ? extends B> function) {
        return new CompositionPredicate<>(predicate, function);
    }

    private static class CompositionPredicate<A, B> implements Predicate<A>, Serializable {
        final Predicate<B> p;
        final Function<A, ? extends B> f;

        private CompositionPredicate(Predicate<B> p,Function<A, ? extends B> f) {
            this.p = checkNotNull(p);
            this.f = checkNotNull(f);
        }

        @Override
        public boolean apply(A a) {
            return p.apply(f.apply(a));
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof CompositionPredicate) {
                CompositionPredicate<?, ?> that = (CompositionPredicate<?, ?>) obj;
                return f.equals(that.f) && p.equals(that.p);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return f.hashCode() ^ p.hashCode();
        }

        @Override
        public String toString() {
            // TODO(cpovirk): maybe make this look like the method call does ("Predicates.compose(...)")
            return p + "(" + f + ")";
        }

        private static final long serialVersionUID = 0;
    }

    public static <T extends Object> T checkNotNull(
            T reference,  Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }
    public static <T> Predicate<T> and(Predicate<? super T> first, Predicate<? super T> second) {
        return new AndPredicate<T>(Predicates.<T>asList(checkNotNull(first), checkNotNull(second)));
    }

    private static <T> List<Predicate<? super T>> asList(
            Predicate<? super T> first, Predicate<? super T> second) {
        // TODO(kevinb): understand why we still get a warning despite @SafeVarargs!
        return Arrays.<Predicate<? super T>>asList(first, second);
    }

    /** @see Predicates#and(Iterable) */
    private static class AndPredicate<T> implements Predicate<T>, Serializable {
        private final List<? extends Predicate<? super T>> components;

        private AndPredicate(List<? extends Predicate<? super T>> components) {
            this.components = components;
        }

        @Override
        public boolean apply( T t) {
            // Avoid using the Iterator to avoid generating garbage (issue 820).
            for (int i = 0; i < components.size(); i++) {
                if (!components.get(i).apply(t)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int hashCode() {
            // add a random number to avoid collisions with OrPredicate
            return components.hashCode() + 0x12472c2c;
        }

        @Override
        public boolean equals( Object obj) {
            if (obj instanceof AndPredicate) {
                AndPredicate<?> that = (AndPredicate<?>) obj;
                return components.equals(that.components);
            }
            return false;
        }

        @Override
        public String toString() {
            return toStringHelper("and", components);
        }

        private static final long serialVersionUID = 0;
    }

    private static String toStringHelper(String methodName, Iterable<?> components) {
        StringBuilder builder = new StringBuilder("Predicates.").append(methodName).append('(');
        boolean first = true;
        for (Object o : components) {
            if (!first) {
                builder.append(',');
            }
            builder.append(o);
            first = false;
        }
        return builder.append(')').toString();
    }
}
