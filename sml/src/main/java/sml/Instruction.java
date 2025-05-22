package sml;

/**
 * Abstract instruction class. All SML instructions extend this class.
 */
public abstract class Instruction {
    /** The label of this instruction */
    private final String label;
    /** The opcode (for example, "add", "sub") */
    private final String opcode;

    /**
     * Constructor accepting a label and an opcode.
     *
     * @param label  the label of this instruction
     * @param opcode the opcode of this instruction
     */
    public Instruction(String label, String opcode) {
        this.label = label;
        this.opcode = opcode;
    }

    /** Returns the label of this instruction */
    public String getLabel() {
        return label;
    }

    /** Returns the opcode of this instruction */
    public String getOpcode() {
        return opcode;
    }

    /**
     * Executes the instruction's logic; must be implemented by each subclass.
     *
     * @param m the machine on which this instruction runs
     */
    public abstract void execute(Machine m);

    /**
     * Returns a debug representation in the form "label: opcode".
     */
    @Override
    public String toString() {
        return label + ": " + opcode;
    }
}
