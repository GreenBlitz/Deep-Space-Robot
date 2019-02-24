package edu.greenblitz.utils;

import java.util.Objects;

public class Tuple<X, Y> {
    private X m_first;
    private Y m_second;
    
    public Tuple(X first, Y second) {
        m_first = first;
        m_second = second;
    }

    public X first() {
        return m_first;
    }

    public Y second() {
        return m_second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equals(m_first, tuple.m_first) &&
                Objects.equals(m_second, tuple.m_second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_first, m_second);
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "first=" + m_first +
                ", second=" + m_second +
                '}';
    }
}
