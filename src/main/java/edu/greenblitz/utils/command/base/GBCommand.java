package edu.greenblitz.utils.command.base;

import edu.greenblitz.utils.command.GBSubsystem;
import edu.greenblitz.utils.sm.State;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;

public abstract class GBCommand extends Command {
    protected static final Logger logger = LogManager.getLogger(GBCommand.class);

    private static final Field requirements;
    private static final Field requirementsSet;

    protected boolean interrupted = false;

    static {
        try {
            Class WPISet = Class.forName("edu.wpi.first.wpilibj.command.Set");
            requirements = WPISet.getDeclaredField("m_set");
            requirements.setAccessible(true);
            requirementsSet = Command.class.getDeclaredField("m_requirements");
            requirementsSet.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public void addRequirements(Iterable<? extends Subsystem> systems) {
        for (Subsystem s : systems)
            requires(s);
    }

    public void requires(GBSubsystem... requirements) {
        for (var requirement : requirements) {
            requires(requirement);
        }
    }

    public final Set<GBSubsystem> getRequirements() {
        var ret = new HashSet<>(getWPILibRequirements());
        ret.addAll(getLazyRequirements());
        return ret;
    }

    /**
     * Reflection is love, reflection is life
     *
     * @return the wpilib managed requirements of this command
     */
    @SuppressWarnings("unchecked")
    public final Set<GBSubsystem> getWPILibRequirements() {
        try {
            var wpiRequires = (Vector<GBSubsystem>) requirements.get(requirementsSet.get(this));
            return new HashSet<>(wpiRequires);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<GBSubsystem> getLazyRequirements() {
        return Set.of();
    }

    public GBCommand() {
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

    public abstract Optional<State> getDeltaState();

    @Override
    protected final void initialize() {
        logger.debug("initializing command {}...", getName());
        atInit();
        logger.debug("command {} has initialized!", getName());
    }

    @Override
    protected final void end() {
        logger.debug("ending command {}...", getName());
        atEnd();
        logger.debug("command {} has ended!", getName());
    }

    @Override
    protected final void interrupted() {
        logger.debug("interrupting command {}", getName());
        interrupted = true;
        atInterrupt();
        logger.debug("command {} was interrupted", getName());
    }

    protected void atInterrupt() {
        atEnd();
    }

    protected void atEnd() {
    }

    public boolean isInterrupted() {
        return interrupted;
    }

    protected void atInit() {
    }
}
