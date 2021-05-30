package blanco.batchprocess.task;

import java.io.IOException;

import blanco.batchprocess.task.valueobject.BlancoBatchProcessProcessInput;

/**
 * Batch process class [BlancoBatchProcessBatchProcess]。
 *
 * <P>Example of a batch processing call.</P>
 * <code>
 * java -classpath (classpath) blanco.batchprocess.task.BlancoBatchProcessBatchProcess -help
 * </code>
 */
public class BlancoBatchProcessBatchProcess {
    /**
     * Normal end.
     */
    public static final int END_SUCCESS = 0;

    /**
     * Termination due to abnormal input. In the case that java.lang.IllegalArgumentException is raised internally.
     */
    public static final int END_ILLEGAL_ARGUMENT_EXCEPTION = 7;

    /**
     * Termination due to I/O exception. In the case that java.io.IOException is raised internally.
     */
    public static final int END_IO_EXCEPTION = 8;

    /**
     * Abnormal end. In the case that batch process fails to start or java.lang.Error or java.lang.RuntimeException is raised internally.
     */
    public static final int END_ERROR = 9;

    /**
     * The entry point when executed from the command line.
     *
     * @param args Agruments inherited from the console.
     */
    public static final void main(final String[] args) {
        final BlancoBatchProcessBatchProcess batchProcess = new BlancoBatchProcessBatchProcess();

        // Arguments for batch process.
        final BlancoBatchProcessProcessInput input = new BlancoBatchProcessProcessInput();

        boolean isNeedUsage = false;
        boolean isFieldMetadirProcessed = false;

        // Parses command line arguments.
        for (int index = 0; index < args.length; index++) {
            String arg = args[index];
            if (arg.startsWith("-verbose=")) {
                input.setVerbose(Boolean.valueOf(arg.substring(9)).booleanValue());
            } else if (arg.startsWith("-metadir=")) {
                input.setMetadir(arg.substring(9));
                isFieldMetadirProcessed = true;
            } else if (arg.startsWith("-targetdir=")) {
                input.setTargetdir(arg.substring(11));
            } else if (arg.startsWith("-tmpdir=")) {
                input.setTmpdir(arg.substring(8));
            } else if (arg.startsWith("-encoding=")) {
                input.setEncoding(arg.substring(10));
            } else if (arg.startsWith("-targetlang=")) {
                input.setTargetlang(arg.substring(12));
            } else if (arg.startsWith("-runtimepackage=")) {
                input.setRuntimepackage(arg.substring(16));
            } else if (arg.equals("-?") || arg.equals("-help")) {
                usage();
                System.exit(END_SUCCESS);
            } else {
                System.out.println("BlancoBatchProcessBatchProcess: The input parameter[" + arg + "] was ignored.");
                isNeedUsage = true;
            }
        }

        if (isNeedUsage) {
            usage();
        }

        if( isFieldMetadirProcessed == false) {
            System.out.println("BlancoBatchProcessBatchProcess: Failed to start the process. The required field value[metadir] in the input parameter[input] is not set to a value.");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }

        int retCode = batchProcess.execute(input);

        // Returns the exit code.
        // Note: Please note that calling System.exit().
        System.exit(retCode);
    }

    /**
     * 具体的なバッチ処理内容を記述するためのメソッドです。
     *
     * このメソッドに実際の処理内容を記述します。
     *
     * @param input バッチ処理の入力パラメータ。
     * @return バッチ処理の終了コード。END_SUCCESS, END_ILLEGAL_ARGUMENT_EXCEPTION, END_IO_EXCEPTION, END_ERROR のいずれかの値を戻します。
     * @throws IOException 入出力例外が発生した場合。
     * @throws IllegalArgumentException 入力値に不正が見つかった場合。
     */
    public int process(final BlancoBatchProcessProcessInput input) throws IOException, IllegalArgumentException {
        // 入力パラメータをチェックします。
        validateInput(input);

        // この箇所でコンパイルエラーが発生する場合、BlancoBatchProcessProcessインタフェースを実装して blanco.batchprocess.taskパッケージに BlancoBatchProcessProcessImplクラスを作成することにより解決できる場合があります。
        final BlancoBatchProcessProcess process = new BlancoBatchProcessProcessImpl();

        // 処理の本体を実行します。
        final int retCode = process.execute(input);

        return retCode;
    }

    /**
     * The entry point for instantiating a class and running a batch.
     *
     * This method provides the following specifications.
     * <ul>
     * <li>Checks the contents of the input parameters of the method.
     * <li>Catches exceptions such as IllegalArgumentException, RuntimeException, Error, etc. and converts them to return values.
     * </ul>
     *
     * @param input Input parameters for batch process.
     * @return バッチ処理の終了コード。END_SUCCESS, END_ILLEGAL_ARGUMENT_EXCEPTION, END_IO_EXCEPTION, END_ERROR のいずれかの値を戻します。
     * @throws IllegalArgumentException If an invalid input value is found.
     */
    public final int execute(final BlancoBatchProcessProcessInput input) throws IllegalArgumentException {
        try {
            // Execute the main body of the batch process.
            int retCode = process(input);

            return retCode;
        } catch (IllegalArgumentException ex) {
            System.out.println("BlancoBatchProcessBatchProcess: An input exception has occurred. Abort the batch process.:" + ex.toString());
            // Termination due to abnormal input.
            return END_ILLEGAL_ARGUMENT_EXCEPTION;
        } catch (IOException ex) {
            System.out.println("BlancoBatchProcessBatchProcess: An I/O exception has occurred. Abort the batch process.:" + ex.toString());
            // Termination due to abnormal input.
            return END_IO_EXCEPTION;
        } catch (RuntimeException ex) {
            System.out.println("BlancoBatchProcessBatchProcess: A runtime exception has occurred. Abort the batch process.:" + ex.toString());
            ex.printStackTrace();
            // Abnormal end.
            return END_ERROR;
        } catch (Error er) {
            System.out.println("BlancoBatchProcessBatchProcess: A runtime exception has occurred. Abort the batch process.:" + er.toString());
            er.printStackTrace();
            // Abnormal end.
            return END_ERROR;
        }
    }

    /**
     * このバッチ処理クラスの使い方の説明を標準出力に示すためのメソッドです。
     */
    public static final void usage() {
        System.out.println("BlancoBatchProcessBatchProcess: Usage:");
        System.out.println("  java blanco.batchprocess.task.BlancoBatchProcessBatchProcess -verbose=値1 -metadir=値2 -targetdir=値3 -tmpdir=値4 -encoding=値5 -targetlang=値6 -runtimepackage=値7");
        System.out.println("    -verbose");
        System.out.println("      説明[verboseモードで動作させるかどうか。]");
        System.out.println("      型[真偽]");
        System.out.println("      デフォルト値[false]");
        System.out.println("    -metadir");
        System.out.println("      説明[メタディレクトリ。xlsファイルの格納先または xmlファイルの格納先を指定します。]");
        System.out.println("      型[文字列]");
        System.out.println("      必須パラメータ");
        System.out.println("    -targetdir");
        System.out.println("      説明[出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。]");
        System.out.println("      型[文字列]");
        System.out.println("      デフォルト値[blanco]");
        System.out.println("    -tmpdir");
        System.out.println("      説明[テンポラリディレクトリを指定します。無指定の場合にはカレント直下のtmpを用います。]");
        System.out.println("      型[文字列]");
        System.out.println("      デフォルト値[tmp]");
        System.out.println("    -encoding");
        System.out.println("      説明[自動生成するソースファイルの文字エンコーディングを指定します。]");
        System.out.println("      型[文字列]");
        System.out.println("    -targetlang");
        System.out.println("      説明[ターゲットとなるプログラミング言語処理系名。java, cs, jsが選択可能]");
        System.out.println("      型[文字列]");
        System.out.println("      デフォルト値[java]");
        System.out.println("    -runtimepackage");
        System.out.println("      説明[ランタイムクラスを生成する生成先を指定します。無指定の場合には 定義書の値を基準に生成されます。]");
        System.out.println("      型[文字列]");
        System.out.println("    -? , -help");
        System.out.println("      説明[使い方を表示します。]");
    }

    /**
     * このバッチ処理クラスの入力パラメータの妥当性チェックを実施するためのメソッドです。
     *
     * @param input バッチ処理の入力パラメータ。
     * @throws IllegalArgumentException 入力値に不正が見つかった場合。
     */
    public void validateInput(final BlancoBatchProcessProcessInput input) throws IllegalArgumentException {
        if (input == null) {
            throw new IllegalArgumentException("BlancoBatchProcessBatchProcess: 処理開始失敗。入力パラメータ[input]にnullが与えられました。");
        }
        if (input.getMetadir() == null) {
            throw new IllegalArgumentException("BlancoBatchProcessBatchProcess: 処理開始失敗。入力パラメータ[input]の必須フィールド値[metadir]に値が設定されていません。");
        }
    }
}
