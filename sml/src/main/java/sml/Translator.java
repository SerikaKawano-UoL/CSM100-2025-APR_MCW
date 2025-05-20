package sml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import lombok.extern.java.Log;

/**
 * SML program translator: reads a file, parses labels and instructions,
 * and (in Part II) constructs each Instruction via reflection.
 */
@Log
public final class Translator {
    private static final String PATH = "";
    private final String fileName;
    private String line = "";

    public Translator(final String file) {
        this.fileName = PATH + file;
    }

    /**
     * Read the SML program from file into lab (labels) and prog (instructions).
     */
    public boolean readAndTranslate(final Labels lab, final List<Instruction> prog) {
        try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            lab.reset();
            prog.clear();
            try {
                line = sc.nextLine();
            } catch (NoSuchElementException e) {
                return false;
            }

            while (line != null) {
                String label = scan();
                if (!label.isEmpty()) {
                    Instruction ins = getInstruction(label);
                    if (ins != null) {
                        lab.addLabel(label);
                        prog.add(ins);
                    }
                }
                try {
                    line = sc.nextLine();
                } catch (NoSuchElementException e) {
                    return false;
                }
            }
        } catch (IOException e) {
            System.err.println("File: IO error " + e);
            return false;
        }
        return true;
    }

    /**
     * Parse a single instruction line (after removing label) and return the instance.
     * Uses reflection to avoid hard-coded switch.
     */
    public Instruction getInstruction(final String label) {
        line = line.trim();
        if (line.isEmpty()) {
            return null;
        }
        String opCode = scan();
        return returnInstruction(label, opCode);
    }

    /** Build an Instruction via reflection based on opCode and remaining tokens. */
    private Instruction returnInstruction(final String label, final String opCode) {
        String pkg = "sml.instructions";
        String className = opCode.substring(0, 1).toUpperCase() + opCode.substring(1) + "Instruction";
        String fqcn = pkg + "." + className;
        Class<?> clazz;
        try {
            clazz = Class.forName(fqcn);
        } catch (ClassNotFoundException e) {
            System.err.println("Unknown instruction: " + opCode);
            return null;
        }
        Constructor<?> cons = findConstructor(clazz);
        if (cons == null) {
            log.severe("No suitable constructor found for " + fqcn);
            return null;
        }
        Object[] args = argsForConstructor(cons, label);
        try {
            return (Instruction) cons.newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            log.severe("Failed to instantiate " + fqcn + ": " + e);
            return null;
        }
    }

    /** Find the constructor whose first parameter is String (the label). */
    private Constructor<?> findConstructor(Class<?> cl) {
        for (Constructor<?> c : cl.getConstructors()) {
            Class<?>[] pts = c.getParameterTypes();
            if (pts.length > 0 && pts[0] == String.class) {
                return c;
            }
        }
        return null;
    }

    /**
     * Build argument array by scanning the remaining tokens:
     * - first argument is the label String
     * - for each int parameter: call scanInt()
     * - for each String parameter: call scan()
     */
    private Object[] argsForConstructor(Constructor<?> cons, String label) {
        Class<?>[] pts = cons.getParameterTypes();
        Object[] args = new Object[pts.length];
        args[0] = label;
        for (int i = 1; i < pts.length; i++) {
            if (pts[i] == int.class) {
                args[i] = scanInt();
            } else if (pts[i] == String.class) {
                args[i] = scan();
            } else {
                // unsupported parameter type
                args[i] = null;
            }
        }
        return args;
    }

    /** Trim leading whitespace, extract next word, and remove it from line. */
    private String scan() {
        line = line.trim();
        if (line.isEmpty()) {
            return "";
        }
        int i = 0;
        while (i < line.length() && !Character.isWhitespace(line.charAt(i))) {
            i++;
        }
        String word = line.substring(0, i);
        line = line.substring(i);
        return word;
    }

    /** Scan next word as integer, return Integer.MAX_VALUE on parse error. */
    private int scanInt() {
        String w = scan();
        try {
            return Integer.parseInt(w);
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }
}
