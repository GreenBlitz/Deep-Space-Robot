package edu.greenblitz.utils.sm;

import java.util.*;

public class StateMachine {

    private Map<State, Map<State, Boolean>> mat = new HashMap<>();

    private State robotState;

    public State getCurrentState() {
        return robotState;
    }

    public void setCurrentState(State newState) {
        if (robotState == null || isAllowed(robotState, newState))
            this.robotState = newState;
    }

    public StateMachine(State initialState){
        add(initialState);
        this.robotState = initialState;
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

    public void allowGroupTo(State dest, State... src){
        for (State s : src)
            allow(s, dest);
    }


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

        return ret.append("}").toString();
    }
}
