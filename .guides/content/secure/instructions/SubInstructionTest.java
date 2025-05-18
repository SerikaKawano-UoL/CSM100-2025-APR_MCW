package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

/**
 * Tests for the "sub" instruction of the SML machine
 *
 * @author XXX
 */
class SubInstructionTest {
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
    void execute_SubtractPositiveNumbers_ResultIsNegativeDifference() {
        // set values of registers
        regs.register(2, 5);
        regs.register(3, 6);

        i = new SubInstruction("lbl", 1, 2, 3);
        i.execute(m);

        // check result (stored in register 1) of executing the instruction
        Assertions.assertEquals(-1, regs.register(1));
    }

    @Test
    void execute_SubtractPositiveNumbers_ResultIsPositiveDifference() {
        // set values of registers
        regs.register(2, 20);
        regs.register(3, 6);

        i = new SubInstruction("lbl", 1, 2, 3);
        i.execute(m);

        // check result (stored in register 1) of executing the instruction
        Assertions.assertEquals(14, regs.register(1));
    }

    @Test
    void execute_SubtractPositiveAndNegativeNumbers_ResultIsNegativeDifference() {
        // set values of registers
        regs.register(2, -5);
        regs.register(3, 6);

        i = new SubInstruction("lbl", 1, 2, 3);
        i.execute(m);

        // check result (stored in register 1) of executing the instruction
        Assertions.assertEquals(-11, m.registers().register(1));
    }

    @Test
    void execute_SubtractZeroFromNumber_ResultIsSameNumber() {
        // set values of registers
        regs.register(2, 3);
        regs.register(3, 0);

        i = new SubInstruction("lbl", 1, 2, 3);
        i.execute(m);

        // check result (stored in register 1) of executing the instruction
        Assertions.assertEquals(3, regs.register(1));
    }

    @Test
    void execute_SubtractNegativeNumbers_ResultIsNegativeDifference() {
        // set values of registers
        regs.register(2, -8);
        regs.register(3, -2);

        i = new SubInstruction("lbl", 1, 2, 3);
        i.execute(m);

        // check result (stored in register 1) of executing the instruction
        Assertions.assertEquals(-6, regs.register(1));
    }

    @Test
    void execute_SubtractLargerNumberFromSmallerNumber_ResultIsNegativeDifference() {
        // set values of registers
        regs.register(2, 67);
        regs.register(3, 100);

        i = new SubInstruction("lbl", 1, 2, 3);
        i.execute(m);

        // check result (stored in register 1) of executing the instruction
        Assertions.assertEquals(-33, regs.register(1));
    }
}
