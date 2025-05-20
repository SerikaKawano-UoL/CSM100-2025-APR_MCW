package sml;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import lombok.extern.java.Log;
import sml.instructions.AddInstruction;
import sml.instructions.SubInstruction;
import sml.instructions.MulInstruction;
import sml.instructions.DivInstruction;
import sml.instructions.LinInstruction;
import sml.instructions.OutInstruction;
import sml.instructions.BnzInstruction;

/**
 * SMLプログラムを読み込み、ラベル一覧（Labels）と命令一覧（List<Instruction>）に変換するクラス
 */
@Log
public final class Translator {
    private static final String PATH = "";
    private final String fileName;   // 読み込むファイル名
    private String line = "";        // 現在処理中の行

    public Translator(final String file) {
        this.fileName = PATH + file;
    }

    /**
     * ファイルを読み込んで lab（ラベル）と prog（命令）に変換
     * @return IOエラーなければ true
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
     * ラベルと残りの行から命令をパースし、対応する Instruction インスタンスを返す
     */
    public Instruction getInstruction(final String label) {
        if (line.isBlank()) {
            return null;
        }
        String opCode = scan();
        int r, s1, s2;
        switch (opCode) {
            case "add" -> {
                r  = scanInt();
                s1 = scanInt();
                s2 = scanInt();
                return new AddInstruction(label, r, s1, s2);
            }
            case "sub" -> {
                r  = scanInt();
                s1 = scanInt();
                s2 = scanInt();
                return new SubInstruction(label, r, s1, s2);
            }
            case "mul" -> {
                r  = scanInt();
                s1 = scanInt();
                s2 = scanInt();
                return new MulInstruction(label, r, s1, s2);
            }
            case "div" -> {
                r  = scanInt();
                s1 = scanInt();
                s2 = scanInt();
                return new DivInstruction(label, r, s1, s2);
            }
            case "lin" -> {
                r = scanInt();
                int value = scanInt();
                return new LinInstruction(label, r, value);
            }
            case "out" -> {
                s1 = scanInt();
                return new OutInstruction(label, s1);
            }
            case "bnz" -> {
                s1 = scanInt();
                String target = scan();
                return new BnzInstruction(label, s1, target);
            }
            default -> {
                System.err.println("Unknown instruction: " + opCode);
                return null;
            }
        }
    }

    /** line から次の空白区切り単語を取り出す */
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

    /** scan()した単語を int に変換。失敗時は Integer.MAX_VALUE を返す */
    private int scanInt() {
        String w = scan();
        try {
            return Integer.parseInt(w);
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }

    // Part II（リフレクション化フェーズ）用のメソッド。現状未使用です。
    @SuppressWarnings("unused")
    private Instruction returnInstruction(final String label, String opCode) { /* … */ return null; }
    @SuppressWarnings("unused")
    private java.lang.reflect.Constructor<?> findConstructor(Class<?> cl)    { /* … */ return null; }
    @SuppressWarnings("unused")
    private Object[] argsForConstructor(java.lang.reflect.Constructor<?> c, String label) { /* … */ return null; }
}
