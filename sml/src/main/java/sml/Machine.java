package sml;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the machine, the context in which programs run.
 * <p>
 * An instance contains 32 registers and methods to access and change them.
 *
 * @author KLM and xxx
 */

@Accessors(fluent = true)
@EqualsAndHashCode
public final class Machine {

    // The labels in the SML program, in the order in
    // which they appear (are defined) in the program
    @Getter
    @Setter
    private Labels labels;

    // The SML program, consisting of prog.size() instructions,
    // each of class Instruction (or one of its subclasses)
    @Getter
    @Setter
    private List<Instruction> prog;

    // The registers of the SML machine

    @Getter
    @Setter
    private Registers registers;

    // The program counter; it contains the index (in prog)
    // of the next instruction to be executed.
    @Getter
    @Setter
    private int pc;

    {
        labels = new Labels();
        prog = new ArrayList<>();
        pc = 0;
    }

    /**
     * String representation of the program under execution.
     *
     * @return pretty formatted version of the code.
     */
    @Override
    public String toString() {
        var s = new StringBuilder();
        for (int i = 0; i != prog().size(); i++) {
            s.append(prog().get(i)).append("\n");
        }
        return s.toString();
    }

    /**
     * Execute the program in prog, beginning at instruction 0. Precondition: the
     * program and its labels have been stored properly.
     */
    public void execute() {
        pc(0);
        registers(new Registers());
        while (pc() < prog().size()) {
            Instruction ins = prog().get(pc());
            pc(pc() + 1);
            ins.execute(this); // so convoluted
        }
    }

}
