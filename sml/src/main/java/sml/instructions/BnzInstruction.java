package sml.instructions;

import sml.Instruction;
import sml.Machine;

/**
 * bnz s1 L  (if register s1 is not zero, then jump to label L)
 */
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
        int value = m.registers().register(register);
        if (value != 0) {
            int targetIndex = m.labels().indexOf(targetLabel);
            if (targetIndex != -1) {
                m.pc(targetIndex);  // set the programme counter via fluent setter
            }
        }
    }

    @Override
    public String toString() {
        return super.toString()
            + " if register " + register
            + " is not zero, jump to label " + targetLabel;
    }
}
