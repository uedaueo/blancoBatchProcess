blancoBatchProcess は、blanco Framework の基礎部分を構成するモジュールです。

現時点（2019/05/2）ではコンパイルが通りませんが、v2.0.0 の jar ファイルを maven repository にdeployすることだけが可能です。

```command line
mvn deploy -Dmaven.main.skip=true -Dmaven.test.skip=true
```