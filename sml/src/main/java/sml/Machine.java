// package sml;

// import lombok.EqualsAndHashCode;
// import lombok.Getter;
// import lombok.Setter;
// import lombok.experimental.Accessors;

// import java.util.ArrayList;
// import java.util.List;

// /**
//  * Represents the machine, the context in which programs run.
//  * <p>
//  * An instance contains 32 registers and methods to access and change them.
//  *
//  * @author KLM and xxx
//  */

// @Accessors(fluent = true)
// @EqualsAndHashCode
// public final class Machine {

//     // The labels in the SML program, in the order in
//     // which they appear (are defined) in the program
//     @Getter
//     @Setter
//     private Labels labels;

//     // The SML program, consisting of prog.size() instructions,
//     // each of class Instruction (or one of its subclasses)
//     @Getter
//     @Setter
//     private List<Instruction> prog;

//     // The registers of the SML machine

//     @Getter
//     @Setter
//     private Registers registers;

//     // The program counter; it contains the index (in prog)
//     // of the next instruction to be executed.
//     @Getter
//     @Setter
//     private int pc;

//     {
//         labels = new Labels();
//         prog = new ArrayList<>();
//         pc = 0;
//     }

//     /**
//      * String representation of the program under execution.
//      *
//      * @return pretty formatted version of the code.
//      */
//     @Override
//     public String toString() {
//         var s = new StringBuilder();
//         for (int i = 0; i != prog().size(); i++) {
//             s.append(prog().get(i)).append("\n");
//         }
//         return s.toString();
//     }

//     /**
//      * Execute the program in prog, beginning at instruction 0. Precondition: the
//      * program and its labels have been stored properly.
//      */
//     public void execute() {
//         pc(0);
//         registers(new Registers());
//         while (pc() < prog().size()) {
//             Instruction ins = prog().get(pc());
//             pc(pc() + 1);
//             ins.execute(this); // so convoluted
//         }
//     }

// }

package sml;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the machine, the context in which programs run.
 * <p>
 * An instance contains 32 registers and methods to access and change them.
 *
 * @author KLM and xxx
 */
public final class Machine {

    /** プログラム中に定義されたラベル一覧 */
    private Labels labels;
    /** SML プログラム本体（Instruction のリスト） */
    private List<Instruction> prog;
    /** 32 個のレジスタ */
    private Registers registers;
    /** プログラムカウンタ：次に実行する命令のインデックス */
    private int pc;

    /** デフォルトコンストラクタ：labels, prog, pc 初期化 */
    public Machine() {
        this.labels = new Labels();
        this.prog = new ArrayList<>();
        this.pc = 0;
    }

    /** ラベル一覧を取得（fluent getter） */
    public Labels labels() {
        return labels;
    }

    /** ラベル一覧を設定（fluent setter） */
    public void labels(Labels labels) {
        this.labels = labels;
    }

    /** プログラムを取得（fluent getter） */
    public List<Instruction> prog() {
        return prog;
    }

    /** プログラムを設定（fluent setter） */
    public void prog(List<Instruction> prog) {
        this.prog = prog;
    }

    /** レジスタ群を取得（fluent getter） */
    public Registers registers() {
        return registers;
    }

    /** レジスタ群を設定（fluent setter） */
    public void registers(Registers registers) {
        this.registers = registers;
    }

    /** プログラムカウンタを取得（fluent getter） */
    public int pc() {
        return pc;
    }

    /** プログラムカウンタを設定（fluent setter） */
    public void pc(int pc) {
        this.pc = pc;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (int i = 0; i < prog().size(); i++) {
            sb.append(prog().get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * プログラムを 0 番目の命令から実行する。
     * 各命令の execute(this) が呼ばれ、bnz などで pc を変更できる。
     */
    public void execute() {
        pc(0);
        registers(new Registers());
        while (pc() < prog().size()) {
            Instruction ins = prog().get(pc());
            pc(pc() + 1);
            ins.execute(this);
        }
    }
}
