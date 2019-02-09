package edu.greenblitz.utils;

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
}
