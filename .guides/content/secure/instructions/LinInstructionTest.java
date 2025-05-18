package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

/**
 * Tests for the "lin" instruction of the SML machine
 *
 * @author XXX
 */
class LinInstructionTest {
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
    void execute_SetRegisterWithPositiveValue_ResultIsSameValue() {
        // load new value into register (current value is 0)
        i = new LinInstruction("lbl", 2, 3);
        i.execute(m);

        // validate content of register
        Assertions.assertEquals(3, m.registers().register(2));
    }

    @Test
    void execute_SetRegisterWithNegativeValue_ResultIsSameValue() {
        // load new value into register (current value is 0)
        i = new LinInstruction("lbl", 0, -100);
        i.execute(m);

        // validate content of register
        Assertions.assertEquals(-100, m.registers().register(0));
    }

    @Test
    void execute_SetRegisterWithZeroValue_ResultIsSameValue() {
        // load new value into register (current value is 0)
        i = new LinInstruction("lbl", 0, 0);
        i.execute(m);

        // validate content of register
        Assertions.assertEquals(0, m.registers().register(0));
    }

    @Test
    void execute_OverwriteRegisterWithPositiveValue_ResultIsSameValue() {
        // set value of register
        regs.register(2, 20);

        // load new value into register (current value is 20)
        i = new LinInstruction("lbl", 2, 3);
        i.execute(m);

        // validate content of register
        Assertions.assertEquals(3, m.registers().register(2));
    }

    @Test
    void execute_SetMultipleRegisters_OnlyTargetRegisterIsChanged() {
        // set value of multiple registers
        regs.register(2, 20);
        regs.register(3, 100);

        // load new value only into register three (current value is 100)
        i = new LinInstruction("lbl", 3, 0);
        i.execute(m);

        // validate that only the content of register three has changed
        Assertions.assertEquals(20, m.registers().register(2));
        Assertions.assertEquals(0, m.registers().register(3));
    }
}
