package edu.greenblitz.utils.sm;

import java.util.*;

public class StateMachine {

    private Map<State, Map<State, Boolean>> mat = new HashMap<>();

    private State current;

    public State getCurrentState() {
        return current;
    }

    public void setCurrentState(State neww) {
        if (current == null || isAllowed(current, neww))
            this.current = neww;
    }

    public StateMachine(State... states){
        add(states);
    }

    public void add(State... states){
        for (State s : states){
            mat.put(s, new HashMap<>());
        }
    }

    public void allow(State source, State dest) {
        if (!mat.containsKey(dest))
            throw new IllegalArgumentException("destination not in machine.");
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
        if (!mat.containsKey(dest))
            throw new IllegalArgumentException("destination not in machine.");
        mat.get(source).put(dest, false);
    }

    public boolean isAllowed(State source, State dest) {
        return mat.containsKey(source) && (mat.get(source).getOrDefault(dest, false)
        || source.equals(dest));
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
        StringBuilder ret = new StringBuilder("StateMachine {\n");
        List<State> elems = Arrays.asList(mat.keySet().toArray(new State[] {}));

        for (int i = 0; i < elems.size(); i++){
            ret.append(elems.get(i)).append(" ->");
            for (int j = 0; j < elems.size(); j++) {
                if (isAllowed(elems.get(i), elems.get(j)))
                    if (i == j)
                        ret.append(" ").append("self");
                    else
                        ret.append(" ").append(elems.get(i).differenceString(elems.get(j)));
            }
            ret.append("\n\n");
        }
//        ret.append("\nLegend: \n");
//
//        for (int i = 0; i < elems.size(); i++)
//            ret.append(i).append(" - ").append(elems.get(i)).append("\n");

        return ret.append("}").toString();
    }
}
