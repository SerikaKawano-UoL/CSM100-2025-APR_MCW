package sml.instructions;

import sml.Instruction;
import sml.Machine;

/**
 * r = s1 * s2
 */
public class MulInstruction extends Instruction {
    private final int result;
    private final int register1;
    private final int register2;

    public MulInstruction(String label, int result, int register1, int register2) {
        super(label, "mul");
        this.result = result;
        this.register1 = register1;
        this.register2 = register2;
    }

    @Override
    public void execute(Machine m) {
        int value1 = m.registers().register(register1);
        int value2 = m.registers().register(register2);
        m.registers().register(result, value1 * value2);
    }

    @Override
    public String toString() {
        return super.toString()
            + " store in register " + result
            + " the product of register " + register1
            + " and register " + register2;
    }
}
