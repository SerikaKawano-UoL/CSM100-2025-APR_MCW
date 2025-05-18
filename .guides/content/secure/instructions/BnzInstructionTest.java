package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.LabelBridge;
import sml.Machine;
import sml.Registers;

/**
 * Tests for the "bnz" instruction of the SML machine
 *
 * @author XXX
 */
class BnzInstructionTest {
    private Machine m;
    private Instruction i;
    private Registers regs;

    @BeforeEach
    void setUp() {
        m = new Machine();
        m.registers(new Registers());
        regs = m.registers();
    }

    @AfterEach
    void tearDown() {
        m = null;
        i = null;
        regs = null;
    }

    @Test
    void execute_RegisterContainsPositiveValue_ResultIsUpdatedProgramCounterWithIndexOfJumpLabel() {
        // add labels
        LabelBridge labelBridge = new LabelBridge(m.labels());
        labelBridge.addLabel("lbl");
        labelBridge.addLabel("lbl1");
        labelBridge.addLabel("lbl2");

        // set non-zero register value
        regs.register(1, 10);

        i = new BnzInstruction("lbl", 1, "lbl2");
        i.execute(m);

        // validate that program counter is pointing at the position of "lbl2"
        Assertions.assertEquals(2, m.pc());
    }

    @Test
    void execute_RegisterContainsZero_ResultIsNoUpdateToProgramCounter() {
        // add labels
        LabelBridge labelBridge = new LabelBridge(m.labels());
        labelBridge.addLabel("lbl");
        labelBridge.addLabel("lbl1");
        labelBridge.addLabel("lbl2");

        // set zero register value
        regs.register(1, 0);

        i = new BnzInstruction("lbl", 1, "lbl2");
        i.execute(m);

        // no jumping. program counter should still be 0
        Assertions.assertEquals(0, m.pc());
    }

    @Test
    void execute_RegisterContainsNegativeValue_ResultIsUpdatedProgramCounterWithIndexOfJumpLabel() {
        // add labels
        LabelBridge labelBridge = new LabelBridge(m.labels());
        labelBridge.addLabel("lbl");
        labelBridge.addLabel("lbl1");
        labelBridge.addLabel("lbl2");

        // set non-zero register value
        regs.register(1, -10);

        i = new BnzInstruction("lbl", 1, "lbl1");
        i.execute(m);

        // validate that program counter is pointing at the position of "lbl1"
        Assertions.assertEquals(1, m.pc());
    }

    @Test
    void execute_RegisterContainsNegativeValueInvalidJumpToLabel_ResultIsNoUpdateToProgramCounter() {
        // add labels
        LabelBridge labelBridge = new LabelBridge(m.labels());
        labelBridge.addLabel("lbl");
        labelBridge.addLabel("lbl1");
        labelBridge.addLabel("lbl2");

        // set non-zero register value
        regs.register(1, -10);

        i = new BnzInstruction("lbl", 1, "lbl");
        i.execute(m);

        // no jumping occurs since the provided label is invalid
        Assertions.assertEquals(0, m.pc());
    }

    @Test
    void execute_RegisterContainsPositiveValueInvalidJumpToLabel_ResultIsNoUpdateToProgramCounter() {
        // add labels
        LabelBridge labelBridge = new LabelBridge(m.labels());
        labelBridge.addLabel("lbl");
        labelBridge.addLabel("lbl1");
        labelBridge.addLabel("lbl2");

        // set non-zero register value
        regs.register(1, 10);

        i = new BnzInstruction("lbl", 1, "lbl");
        i.execute(m);

        // no jumping occurs since the provided label is invalid
        Assertions.assertEquals(0, m.pc());
    }
}
