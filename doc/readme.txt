blancoBatchProcessは 「バッチ処理定義書」というExcel様式を記入するだけで 簡単に バッチ処理をおこなうクラスの抽象親クラスソースコードが作成できるようにするためのツールです。
Java のソースコード自動生成に対応しています。
# C#.NETのソースコード自動生成には、まだ対応していません。

【javaコマンドそのものの戻り値について】
  下記の値は javaコマンドそのものの戻り値として利用されています。
  ・Windows XPの場合
    java.exe -version : 戻り値(0)
    java.exe : Could not create the Java virtual machine. : 戻り値(1)
    java.exe : NoClassDefFoundError : 戻り値(1)

[開発者]
 1.伊賀敏樹 (Tosiki Iga / いがぴょん): 開発および維持メンテ担当
 2.山本耕司 (ymoto) : 仕様決定およびリリース判定担当

[ライセンス]
 1.blancoBatchProcess は ライセンス として GNU Lesser General Public License を採用しています。

[依存するライブラリ]
blancoBatchProcessは下記のライブラリを利用しています。
※各オープンソース・プロダクトの提供者に感謝します。
 1.JExcelApi - Java Excel API - A Java API to read, write and modify Excel spreadsheets
     http://jexcelapi.sourceforge.net/
     http://sourceforge.net/projects/jexcelapi/
     http://www.andykhan.com/jexcelapi/ 
   概要: JavaからExcelブック形式を読み書きするためのライブラリです。
   ライセンス: GNU Lesser General Public License
 2.blancoCg
   概要: ソースコード生成ライブラリ
   ライセンス: GNU Lesser General Public License
 3.その他の blanco Framework
   概要: このプロダクトは それ自身が blanco Frameworkにより自動生成されています。
         このプロダクトは 実行時に blanco Framework各種プロダクトに依存して動作します。
   ライセンス: GNU Lesser General Public License
