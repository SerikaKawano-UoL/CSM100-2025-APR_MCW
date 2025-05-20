package sml.instructions;

import sml.Instruction;
import sml.Machine;

public class OutInstruction extends Instruction {
    private final int register;

    public OutInstruction(String label, int register) {
        super(label, "out");
        this.register = register;
    }

    @Override
    public void execute(Machine m) {
        System.out.println(m.registers().register(register));
    }

    @Override
    public String toString() {
        return super.toString() + " output the value of register " + register;
    }
}
