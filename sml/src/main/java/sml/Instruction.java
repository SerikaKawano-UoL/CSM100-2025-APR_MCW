package sml;

/**
 * 抽象命令クラス。すべての SML 命令はこのクラスを継承する。
 */
public abstract class Instruction {
    /** この命令のラベル */
    private final String label;
    /** 命令コード（"add", "sub" など） */
    private final String opcode;

    /**
     * ラベルと命令コードを受け取るコンストラクタ
     * @param label この命令のラベル
     * @param opcode 命令コード
     */
    public Instruction(String label, String opcode) {
        this.label = label;
        this.opcode = opcode;
    }

    /** ラベルを返す */
    public String getLabel() {
        return label;
    }

    /** 命令コードを返す */
    public String getOpcode() {
        return opcode;
    }

    /**
     * 命令の実行ロジック。各サブクラスで実装すること。
     * @param m 実行中のマシン
     */
    public abstract void execute(Machine m);

    /**
     * デバッグ用に「label: opcode」の形式で返す。
     */
    @Override
    public String toString() {
        return label + ": " + opcode;
    }
}
