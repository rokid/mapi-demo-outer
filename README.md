# demo开发环境
1. 开发IDE：intellij idea、maven
2. 开发语言：java
3. 协议：websocket、http

# 依赖的protobuf文件，这三个文件已放入demo中
1. asr.proto
2. auth.proto
3. tts.proto

# 使用protobuf文件生成java类
1. 工程中的pom文件已配置protobuf生成java类插件
2. 在terminal下首先执行mvn protobuf:compile，然后再执行mvn protobuf:compile-custom命令，执行成功后会在target目录下生成对应的java文件

# demo说明
1. HttpAsrDemo.java:   http asr demo
2. HttpTtsDemo.java:   http tts demo
3. WebSocketAsrDemo:   websocket asr demo
4. WebSocketTtsDemo:   websocket tts demo
5. 需要自己在开放平台申请需要的key，secret在[开放平台语音接入](https://developer.rokid.com/docs/2-RokidDocument/2-EnableVoice/get-the-certification-file.html)获取