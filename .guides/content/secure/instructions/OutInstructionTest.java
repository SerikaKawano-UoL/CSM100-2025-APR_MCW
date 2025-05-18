package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Tests for the "out" instruction of the SML machine
 *
 * @author XXX
 */
class OutInstructionTest {
    private final PrintStream originalOut = System.out;
    private Machine m;
    private Instruction i;
    private Registers regs;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        m = new Machine();
        m.registers(new Registers());
        regs = m.registers();

        // Redirect console output to a ByteArrayOutputStream
        outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
    }

    @AfterEach
    void tearDown() {
        m = null;
        i = null;
        regs = null;

        // Reset console output
        System.setOut(originalOut);
    }

    @Test
    void execute_OutputRegisterWithPositiveValue_ResultIsOutputStreamWithPositiveValue() {
        // set values of register
        regs.register(2, 10);

        i = new OutInstruction("lbl", 2);
        i.execute(m);

        // get print stream created by executing the OutInstruction and parse value
        int actualOutput = Integer.parseInt(outputStream.toString(StandardCharsets.UTF_8).trim());

        // check if value parsed from stream matches value stored in register two
        Assertions.assertEquals(10, actualOutput);
    }

    @Test
    void execute_OutputRegisterWithLargePositiveValue_ResultIsOutputStreamWithLargePositiveValue() {
        // set values of register
        regs.register(31, 100000000);

        i = new OutInstruction("lbl", 31);
        i.execute(m);

        // get print stream created by executing the OutInstruction and parse value
        int actualOutput = Integer.parseInt(outputStream.toString(StandardCharsets.UTF_8).trim());

        // check if value parsed from stream matches value stored in register 31
        Assertions.assertEquals(100000000, actualOutput);
    }

    @Test
    void execute_OutputRegisterWithNegativeValue_ResultIsOutputStreamWithNegativeValue() {
        // set values of register
        regs.register(1, -10);

        i = new OutInstruction("lbl", 1);
        i.execute(m);

        // get print stream created by executing the OutInstruction and parse value
        int actualOutput = Integer.parseInt(outputStream.toString(StandardCharsets.UTF_8).trim());

        // check if value parsed from stream matches value stored in register one
        Assertions.assertEquals(-10, actualOutput);
    }

    @Test
    void execute_OutputRegisterWithLargeNegativeValue_ResultIsOutputStreamWithLargeNegativeValue() {
        // set values of register
        regs.register(1, -100000000);

        i = new OutInstruction("lbl", 1);
        i.execute(m);

        // get print stream created by executing the OutInstruction and parse value
        int actualOutput = Integer.parseInt(outputStream.toString(StandardCharsets.UTF_8).trim());

        // check if value parsed from stream matches value stored in register one
        Assertions.assertEquals(-100000000, actualOutput);
    }

    @Test
    void execute_OutputRegisterWithDefaultValue_ResultIsOutputStreamWithZero() {
        i = new OutInstruction("lbl", 1);
        i.execute(m);

        // get print stream created by executing the OutInstruction and parse value
        int actualOutput = Integer.parseInt(outputStream.toString(StandardCharsets.UTF_8).trim());

        // check if value parsed from stream matches value stored in register one (default value of zero)
        Assertions.assertEquals(0, actualOutput);
    }
}
