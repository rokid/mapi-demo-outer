package com.rokid.open.mapi.demo.demo;

import javax.annotation.Nullable;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;


public class WebSocketTtsDemo {
  private static OkHttpClient client = null;
  private static Headers headers = null;
  private static String url = "wss://mapi.open.rokid.com/ws/tts";

  public static void main(String[] args) throws Exception {

    client = HttpUtil.getClient();
    headers = HttpUtil.getTtsHeaders();
    Request request = new Request.Builder().headers(headers).url(url).build();
    client.newWebSocket(request, new WebSocketListener() {
      @Override
      public void onOpen(WebSocket webSocket, Response response) {
        System.out.println("onOpen:" + response);
        super.onOpen(webSocket, response);

        com.rokid.manhattan.open.tts.Request ttsRequest =
            com.rokid.manhattan.open.tts.Request.newBuilder()
                .setId(1)
                .setCodec("mp3")
                .setContentType("raw")
                .setDeclaimer("zh")
                .setText("hello")
                .build();

        webSocket.send(okio.ByteString.of(ttsRequest.toByteArray()));
      }

      @Override
      public void onMessage(WebSocket webSocket, String text) {

        System.out.println("onMessage text:" + text);
        super.onMessage(webSocket, text);
      }

      @Override
      public void onMessage(WebSocket webSocket, okio.ByteString bytes) {


        com.rokid.manhattan.open.tts.Response ttsResponse = PbUtil
            .encode(bytes.toByteArray(), com.rokid.manhattan.open.tts.Response.getDefaultInstance());

        System.out.println(ttsResponse);

        super.onMessage(webSocket, bytes);
      }

      @Override
      public void onClosing(WebSocket webSocket, int code, String reason) {
        System.out.println("onClosing: code:" + code + " reason:" + reason);
        super.onClosing(webSocket, code, reason);

      }

      @Override
      public void onClosed(WebSocket webSocket, int code, String reason) {

        System.out.println("onClosed: code:" + code + " reason:" + reason);
        super.onClosed(webSocket, code, reason);
      }

      @Override
      public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
        System.out.println("onFailure:" + response);
        t.printStackTrace();
        super.onFailure(webSocket, t, response);

      }
    });
  }

}
