package edu.greenblitz.utils.sm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StateMachine {

    private Map<State, Map<State, Boolean>> mat = new HashMap<>();

    public StateMachine(State... states){
        for (State s : states){
            mat.put(s, new HashMap<>());
        }
    }

    public void allow(State source, State dest) {
        mat.get(source).put(dest, true);
    }

    /**
     *
     * @param dest
     * @param src
     */
    public void allowGroupTo(State dest, State... src){
        for (State s : src)
            allow(s, dest);
    }

    /**
     *
     * @param src
     * @param dest
     */
    public void allowGroupFrom(State src, State... dest){
        for (State s : dest)
            allow(src, s);
    }

    public void deny(State source, State dest) {
        mat.get(source).put(dest, false);
    }

    public boolean isAllowed(State source, State dest) {
        return mat.containsKey(source) && mat.get(source).getOrDefault(dest, false);
    }

    public Set<State> getStatesTo(State dest) {
        var ret = mat.keySet();
        ret.removeIf((State s) -> mat.get(s).getOrDefault(dest, false));
        return ret;
    }

    public Set<State> getStatesFrom(State source) {
        assert (mat.containsKey(source));
        var ret = mat.keySet();
        ret.removeIf((State s) -> mat.get(source).getOrDefault(s, false));
        return ret;
    }

    @Override
    public String toString() {
        return "StateMachine{" +
                "mat=" + mat +
                '}';
    }
}
