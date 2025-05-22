package sml;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the machine, the context in which programs run.
 * <p>
 * An instance contains 32 registers and methods to access and change them.
 *
 * @author KLM and xxx
 */
public final class Machine {

    /** The list of labels defined in the program */
    private Labels labels;
    /** The SML program itself (a list of Instruction objects) */
    private List<Instruction> prog;
    /** The 32 registers of the machine */
    private Registers registers;
    /** The program counter: index of the next instruction to execute */
    private int pc;

    /** 
     * Default constructor: initializes labels, prog, and pc 
     */
    public Machine() {
        this.labels = new Labels();
        this.prog = new ArrayList<>();
        this.pc = 0;
    }

    /** Fluent getter for labels */
    public Labels labels() {
        return labels;
    }

    /** Fluent setter for labels */
    public void labels(Labels labels) {
        this.labels = labels;
    }

    /** Fluent getter for program */
    public List<Instruction> prog() {
        return prog;
    }

    /** Fluent setter for program */
    public void prog(List<Instruction> prog) {
        this.prog = prog;
    }

    /** Fluent getter for registers */
    public Registers registers() {
        return registers;
    }

    /** Fluent setter for registers */
    public void registers(Registers registers) {
        this.registers = registers;
    }

    /** Fluent getter for program counter */
    public int pc() {
        return pc;
    }

    /** Fluent setter for program counter */
    public void pc(int pc) {
        this.pc = pc;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (int i = 0; i < prog().size(); i++) {
            sb.append(prog().get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Executes the program starting from instruction 0.
     * Each instruction's execute(this) is called,
     * and instructions like bnz may modify the program counter.
     */
    public void execute() {
        pc(0);
        registers(new Registers());
        while (pc() < prog().size()) {
            Instruction ins = prog().get(pc());
            pc(pc() + 1);
            ins.execute(this);
        }
    }
}
