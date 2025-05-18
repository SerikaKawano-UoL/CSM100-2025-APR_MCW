package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

/**
 * Tests for the "mul" instruction of the SML machine
 *
 * @author XXX
 */
class MulInstructionTest {
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
    void execute_MultiplyPositiveNumbers_ResultIsPositiveProduct() {
        // set values of registers
        regs.register(2, 5);
        regs.register(3, 6);

        i = new MulInstruction("lbl", 1, 2, 3);
        i.execute(m);

        // check result (stored in register 1) of executing the instruction
        Assertions.assertEquals(30, m.registers().register(1));
    }

    @Test
    void execute_MultiplyPositiveAndNegativeNumbers_ResultIsNegativeProduct() {
        // set values of registers
        regs.register(2, -5);
        regs.register(3, 6);

        i = new MulInstruction("lbl", 1, 2, 3);
        i.execute(m);

        // check result (stored in register 1) of executing the instruction
        Assertions.assertEquals(-30, m.registers().register(1));
    }

    @Test
    void execute_MultiplyZeroWithNumber_ResultIsZero() {
        // set values of registers
        regs.register(2, 20);
        regs.register(3, 0);

        i = new MulInstruction("lbl", 1, 2, 3);
        i.execute(m);

        // check result (stored in register 1) of executing the instruction
        Assertions.assertEquals(0, regs.register(1));
    }

    @Test
    void execute_MultiplyNegativeNumbers_ResultIsPositiveProduct() {
        // set values of registers
        regs.register(2, -3);
        regs.register(3, -8);

        i = new MulInstruction("lbl", 1, 2, 3);
        i.execute(m);

        // check result (stored in register 1) of executing the instruction
        Assertions.assertEquals(24, regs.register(1));
    }

    @Test
    void execute_MultiplyNumberByOne_ResultIsSameNumber() {
        // set values of registers
        regs.register(2, 80);
        regs.register(3, 1);

        i = new MulInstruction("lbl", 1, 2, 3);
        i.execute(m);

        // check result (stored in register 1) of executing the instruction
        Assertions.assertEquals(80, regs.register(1));
    }
}
