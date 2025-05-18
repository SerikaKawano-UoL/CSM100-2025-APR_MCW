package sml;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Arrays;

/**
 * This class represents the registers in the "Machine".
 * <p>
 * An instance contains 32 registers and methods to access and change them
 * </p>
 *
 * @author KLM and xxx
 */
@ToString
@EqualsAndHashCode
@Accessors(fluent = true)
public final class Registers {
    private static final int NUMBER_OF_REGISTERS = 32;

    final int[] registers;

    // Constructor: an instance whose registers are set to 0
    {
        registers = new int[NUMBER_OF_REGISTERS];
    }

    /**
     * Set up the registers for the SML machine.
     */
    public Registers() {
        // set all the register values to zero
        Arrays.fill(registers, 0);
    }

    // Set register i to v.
    // Precondition: 0 <= i <= NUMBER_OF_REGISTERS

    public void register(int i, int v) {
        registers[i] = v;
    }

    public int register(int i) {
        return registers[i];
    }

}
