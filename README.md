MemoApp-API
==========

Spring Bootを利用した、[MemoApp-UI](https://github.com/takushinohara/memoapp-ui)用のREST APIです。

## Usage
```
$ git clone https://github.com/takushinohara/memoapp-api.git
$ cd memoapp-api
$ mvn package
$ cd target
$ java -jar memoapp-api-0.0.1-SNAPSHOT.jar
```
ポート番号をデフォルト(8080)から変更する場合は、--server.port オプションを指定します。
```
$ java -jar memoapp-api-0.0.1-SNAPSHOT.jar --server.port=8081
```

### curl sample
```
// 全件取得
$ curl http://localhost:8080/api/memoapp -v -X GET
// １件取得
$ curl http://localhost:8080/api/memoapp/1 -v -X GET
// 登録
$ curl http://localhost:8080/api/memoapp -v -X POST -H 'Content-Type:application/json' -d '{"title":"タイトル", "content":"本文"}'
// 更新
$ curl http://localhost:8080/api/memoapp/1 -v -X PUT -H 'Content-Type:application/json' -d '{"id":1,"title":"タイトル", "content":"本文"}'
// 削除
$ curl http://localhost:8080/api/memoapp/1 -v -X DELETE
```
