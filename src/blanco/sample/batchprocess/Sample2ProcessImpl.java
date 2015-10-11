package blanco.sample.batchprocess;

import java.io.IOException;

import blanco.sample.batchprocess.valueobject.Sample2ProcessInput;

/**
 * サンプル・バッチ処理のコンパイルを通すためのダミークラス。
 */
public class Sample2ProcessImpl implements Sample2Process {
    /**
     * クラスをインスタンス化して処理を実行する際のエントリポイントです。
     * 
     * @param input
     *            処理の入力パラメータ。
     * @return 処理の実行結果。
     * @throws IOException
     *             入出力例外が発生した場合。
     * @throws IllegalArgumentException
     *             入力値に不正が見つかった場合。
     */
    public int execute(Sample2ProcessInput input) throws IOException,
            IllegalArgumentException {
        // 特に処理は無し。
        return 0;
    }

    /**
     * 処理の中でアイテムが処理されるたびに進捗報告としてコールバックします。
     * 
     * @param argProgressMessage
     *            現在処理しているアイテムに関するメッセージ。
     * @return 処理をそのまま継続する場合は false。処理中断をリクエストしたい場合は true。
     */
    public boolean progress(String argProgressMessage) {
        return false;
    }
}
