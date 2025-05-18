package sml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

import lombok.extern.java.Log;

import sml.instructions.AddInstruction;

/**
 * This class is the main translation mechanism.
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 *
 * @author KLM and xxx
 */
 @Log
public final class Translator {

    private static final String PATH = "";

    // word + line is the part of the current line that's not yet processed
    // word has no whitespace
    // If word and line are not empty, line begins with whitespace
    private final String fileName; // source file of SML code
    private String line = "";

    public Translator(final String file) {
        fileName = PATH + file;
    }

    // translate the small program in the file into lab (the labels) and
    // prog (the program)
    // return "no errors were detected"

    public boolean readAndTranslate(final Labels lab, final List<Instruction> prog) {
        try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            // Scanner attached to the file chosen by the user
            // The labels of the program being translated
            lab.reset();
            // The program to be created
            prog.clear();

            try {
                line = sc.nextLine();
            } catch (NoSuchElementException ioE) {
                return false;
            }

            // Each iteration processes line and reads the next input
            // line into "line"
            while (line != null) {
                // Store the label in label
                var label = scan();

                if (label.length() > 0) {
                    var ins = getInstruction(label);
                    if (ins != null) {
                        lab.addLabel(label);
                        prog.add(ins);
                    }
                }

                try {
                    line = sc.nextLine();
                } catch (NoSuchElementException ioE) {
                    return false;
                }
            }
        } catch (IOException ioE) {
            System.err.println("File: IO error " + ioE);
            return false;
        }
        return true;
    }

    // The input line should consist of an SML instruction, with its label already removed.
    // Translate line into an instruction with label "label" and return the instruction.
    public Instruction getInstruction(final String label) {
        int s1; // Possible operands of the instruction
        int s2;
        int r;

        if (line.equals("")) {
            return null;
        }
        var opCode = scan();

        switch (opCode) {
            case "add" -> {
                r = scanInt();
                s1 = scanInt();
                s2 = scanInt();
                return new AddInstruction(label, r, s1, s2);
            }

            // TODO: You have to write code here for the other instructions.

            default -> System.out.println("Unknown instruction: " + opCode);
        }
        return null; // FIX THIS

        // In the second phase you will replace the switch with...
        // return returnInstruction(label, opCode);
    }

    /*
     * Return the first word of line and remove it from line. If there is no word,
     * return ""
     */
    private String scan() {
        line = line.trim();
        if (line.length() == 0) {
            return "";
        }

        int i = 0;
        while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
            i = i + 1;
        }
        String word = line.substring(0, i);
        line = line.substring(i);
        return word;
    }

    // Return the first word of line as an integer. 
    // If there is any error, return the maximum int
    private int scanInt() {
        String word = scan();
        if (word.length() == 0) {
            return Integer.MAX_VALUE;
        }

        try {
            return Integer.parseInt(word);
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }

    private Instruction returnInstruction(final String label, String opCode) {
        String pkg = "sml.instructions"; // hardwired = bad!
        String base = "Instruction"; // ditto

        // Transform add -> sml.instructions.AddInstruction
        String fqcn = pkg + "." + opCode.substring(0, 1).toUpperCase(Locale.ROOT) + opCode.substring(1) + base;

        // get the class
        Class clazz;
        try {
            clazz = Class.forName(fqcn);
        } catch (ClassNotFoundException e) {
            System.err.println("Unknown instruction: " + fqcn);
            return null;
        }

        // find the correct constructor
        Constructor cons = findConstructor(clazz);
        var objArray = argsForConstructor(null, label);

        try {
            return (Instruction) cons.newInstance(objArray); // create an instance with the ctor args
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NullPointerException e) {
            log.severe(String.format("In %s: issue with creating the instruction", this.getClass()));
        }
        return null; // bad!!!
    }

    private Constructor findConstructor(Class cl) {
        Constructor cons = null;
        // TODO
        return null;
    }

    private Object[] argsForConstructor(Constructor cons, String label) {
        Object[] argsArray = null;
        // TODO
        return null;
    }
}