package edu.greenblitz.utils.command;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public abstract class GBCommand extends Command {
    protected static final Logger logger = LogManager.getLogger(GBCommand.class);

    public GBCommand() {
    }

    public void addRequirements(Iterable<Subsystem> systems){
        for (Subsystem s : systems)
            requires(s);
    }

    @SuppressWarnings("unchecked")
    public final Set<Subsystem> getRequirements() {
        var ret = new HashSet<>(getWPILibRequirements());
        ret.addAll(getLazyRequirements());
        return ret;
    }

    @SuppressWarnings("unchecked")
    public final Set<Subsystem> getWPILibRequirements() {
        try {
            Class WPISet = Class.forName("edu.wpi.first.wpilibj.command.Set");
            var requirements = WPISet.getDeclaredField("m_set");
            requirements.setAccessible(true);
            var requirementSet = Command.class.getDeclaredField("m_requirements");
            requirementSet.setAccessible(true);
            var wpiRequires = (Vector<Subsystem>) requirements.get(requirementSet.get(this));
            return new HashSet<>(wpiRequires);
        } catch (IllegalAccessException | ClassNotFoundException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Subsystem> getLazyRequirements() {
        return Set.of();
    }

    public GBCommand(String name) {
        super(name);
    }

    public GBCommand(double timeout) {
        super(timeout);
    }

    public GBCommand(Subsystem subsystem) {
        super(subsystem);
    }

    public GBCommand(String name, Subsystem subsystem) {
        super(name, subsystem);
    }

    public GBCommand(double timeout, Subsystem subsystem) {
        super(timeout, subsystem);
    }

    public GBCommand(String name, double timeout) {
        super(name, timeout);
    }

    public GBCommand(String name, double timeout, Subsystem subsystem) {
        super(name, timeout, subsystem);
    }

    protected void reportCommandStart() {
        logger.debug("command {} has started!", getName());
    }

    protected void reportCommandEnd() {
        logger.debug("command {} has ended!", getName());
    }

    @Override
    public synchronized void start() {
        super.start();
        reportCommandStart();
    }

    @Override
    protected void end() {
        super.end();
        reportCommandEnd();
    }



}
