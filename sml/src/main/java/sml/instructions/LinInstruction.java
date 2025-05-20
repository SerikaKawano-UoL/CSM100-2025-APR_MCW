package sml.instructions;

import sml.Instruction;
import sml.Machine;

public class LinInstruction extends Instruction {
    private final int register;
    private final int value;

    public LinInstruction(String label, int register, int value) {
        super(label, "lin");
        this.register = register;
        this.value = value;
    }

    @Override
    public void execute(Machine m) {
        m.registers().register(register, value);
    }

    @Override
    public String toString() {
        return super.toString() + " store the value " + value + " in register " + register;
    }
}
