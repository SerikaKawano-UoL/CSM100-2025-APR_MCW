package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

/**
 * Tests for the "div" instruction of the SML machine
 *
 * @author XXX
 */
class DivInstructionTest {
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
    void execute_DividePositiveNumbers_ResultIsPositiveQuotient() {
        // set values of registers
        regs.register(2, 10);
        regs.register(3, 3);

        i = new DivInstruction("lbl", 1, 2, 3);
        i.execute(m);

        // check result (stored in register 1) of executing the instruction
        Assertions.assertEquals(3, m.registers().register(1));
    }

    @Test
    void execute_DividePositiveAndNegativeNumbers_ResultIsNegativeQuotient() {
        // set values of registers
        regs.register(2, 30);
        regs.register(3, -3);

        i = new DivInstruction("lbl", 1, 2, 3);
        i.execute(m);

        // check result (stored in register 1) of executing the instruction
        Assertions.assertEquals(-10, regs.register(1));
    }

    @Test
    void execute_DivideZeroByNumber_ResultIsZero() {
        // set values of registers
        regs.register(2, 0);
        regs.register(3, 100);

        i = new DivInstruction("lbl", 1, 2, 3);
        i.execute(m);

        // check result (stored in register 1) of executing the instruction
        Assertions.assertEquals(0, regs.register(1));
    }

    @Test
    void execute_DivideByZero_ArithmeticExceptionIsThrown() {
        // set values of registers
        regs.register(2, 17);
        regs.register(3, 0);

        i = new DivInstruction("lbl", 1, 2, 3);

        // division by zero should throw an exception
        Assertions.assertThrows(ArithmeticException.class, () -> i.execute(m));
    }

    @Test
    void execute_DivideNegativeNumbers_ResultIsPositiveQuotient() {
        // set values of registers
        regs.register(2, -20);
        regs.register(3, -2);

        i = new DivInstruction("lbl", 1, 2, 3);
        i.execute(m);

        // check result (stored in register 1) of executing the instruction
        Assertions.assertEquals(10, regs.register(1));
    }

    @Test
    void execute_DivideNumberByOne_ResultIsSameNumber() {
        // set values of registers
        regs.register(2, 55);
        regs.register(3, 1);

        i = new DivInstruction("lbl", 1, 2, 3);
        i.execute(m);

        // check result (stored in register 1) of executing the instruction
        Assertions.assertEquals(55, regs.register(1));
    }
}
