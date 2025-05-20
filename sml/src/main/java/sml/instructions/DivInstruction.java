package sml.instructions;

import sml.Instruction;
import sml.Machine;

public class DivInstruction extends Instruction {
    private final int result;
    private final int register1;
    private final int register2;

    public DivInstruction(String label, int result, int register1, int register2) {
        super(label, "div");
        this.result = result;
        this.register1 = register1;
        this.register2 = register2;
    }

    @Override
    public void execute(Machine m) {
        var value1 = m.registers().register(register1
