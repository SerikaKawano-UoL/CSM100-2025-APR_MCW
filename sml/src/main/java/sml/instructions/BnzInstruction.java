package sml.instructions;

import sml.Instruction;
import sml.Machine;

public class BnzInstruction extends Instruction {
    private final int register;
    private final String targetLabel;

    public BnzInstruction(String label, int register, String targetLabel) {
        super(label, "bnz");
        this.register = register;
        this.targetLabel = targetLabel;
    }

    @Override
    public void execute(Machine m) {
        if (m.registers().register(register) != 0) {
            int targetIndex = m.getLabels().indexOf(targetLabel);
            m.setProgramCounter(targetIndex);
        }
    }

    @Override
    public String toString() {
        return super.toString() + " if register " + register + " is not zero, jump to label " + targetLabel;
    }
}
