# QuickStart

## generate ssl cmd in Windows 10
1. 生成keystore文件

    ```keytool -genkeypair -alias "thrift_ssl_test" -keyalg "RSA" -keystore "thrift_ssl_test.keystore"```

2. 验证keystore文件

    ```keytool -list -v -keystore thrift_ssl_test.keystore```

3. 导出公钥证书文件

    ```keytool -export -alias thrift_ssl_test -keystore thrift_ssl_test.keystore -rfc -file thrift_ssl_test.cer```

4. 公钥证书导入到truststore文件

    ```keytool -import -alias thrift_ssl_test -file thrift_ssl_test.cer  -keystore thrift_ssl_test.truststore```

5. 验证keystore文件

    ```keytool -list -v -keystore thrift_ssl_test.truststore```

## password
keystore is `thrift`

truststore is `thrift2`

# REF
- [用keytool创建Keystore和Trustsotre文件](http://zjumty.iteye.com/blog/1885356)
