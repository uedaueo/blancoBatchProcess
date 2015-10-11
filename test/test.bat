rem Eclipseの中からではなく、コマンドラインを開いてその中から実行する必要があります。
rem binフォルダにclassを生成するために、Ecilpseでクリーンをおこなう必要があります。
echo off

java.exe -classpath ../bin blanco.sample.batchprocess.SampleBatchProcess -require=必須パラメータ -ignore=間違ったパラメータ

echo java.exeの戻り値は(%ERRORLEVEL%)です。
