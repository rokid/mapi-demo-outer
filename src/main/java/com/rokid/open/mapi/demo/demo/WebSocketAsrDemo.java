package com.rokid.open.mapi.demo.demo;

import com.google.common.io.ByteStreams;
import com.google.protobuf.ByteString;
import com.rokid.manhattan.open.speech.v2.Asr.AsrRequest;
import com.rokid.manhattan.open.speech.v2.Asr.AsrResponse;
import com.rokid.manhattan.open.speech.v2.Asr.Options;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nullable;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;



public class WebSocketAsrDemo {

  private static OkHttpClient client = null;
  private static Headers headers = null;
  private static String url = "wss://mapi.open.rokid.com/ws/asr";


  public static void main(String[] args) throws Exception {

    client = HttpUtil.getClient();
    headers = HttpUtil.getWssAsrHeaders();
    Request request = new Request.Builder().headers(headers).url(url).build();
    client.newWebSocket(request, new WebSocketListener() {
      @Override
      public void onOpen(WebSocket webSocket, Response response) {
        System.out.println("onOpen:" + response);
        super.onOpen(webSocket, response);

        InputStream voices = null;
        try {

          /****==========ONESHOT==========*****/
          voices = this.getClass().getClassLoader().getResource("rqjttqzmy.pcm")
              .openStream();
          AsrRequest asrRequest = AsrRequest.newBuilder()
              .setId(1)
              .setType("ONESHOT")
              .setVoices(ByteString.copyFrom(ByteStreams.toByteArray(voices)))
              .setOptions(Options.newBuilder()
                  .setCodec("pcm")
                  .setNoIntermediateAsr(false)
                  .setNeedHotWords(false)
                  .setEngine("zh")
                  .setVoiceTrigger("")
                  .build())
              .build();
          webSocket.send(okio.ByteString.of(asrRequest.toByteArray()));

          voices.close();


          /****==========START-VOICE-END==========*****/
          byte voice[] = new byte[10240];
          int length = 0;
          boolean first = true;
          int id = 100;
          voices = this.getClass().getClassLoader().getResource("rqjttqzmy.pcm").openStream();
          while ((length = voices.read(voice)) > 0) {
            if (first) {
              asrRequest = AsrRequest.newBuilder()
                  .setId(id)
                  .setType("START")
                  .setVoices(ByteString.copyFrom(voice))
                  .setOptions(Options.newBuilder()
                      .setCodec("pcm")
                      .setNoIntermediateAsr(true)
                      .setNeedHotWords(false)
                      .setEngine("zh")
                      .setVoiceTrigger("")
                      .build())

                  .build();

              webSocket.send(okio.ByteString.of(asrRequest.toByteArray()));
              first = false;
            } else {
              asrRequest = AsrRequest.newBuilder()
                  .setId(id)
                  .setType("VOICE")
                  .setVoices(ByteString.copyFrom(voice, 0, length))
                  .build();
              webSocket.send(okio.ByteString.of(asrRequest.toByteArray()));
            }
          }
          asrRequest = AsrRequest.newBuilder()
              .setId(id)
              .setType("END")
              .build();
          webSocket.send(okio.ByteString.of(asrRequest.toByteArray()));


        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          try {
            if (voices != null) {
              voices.close();
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }


      }

      @Override
      public void onMessage(WebSocket webSocket, String text) {

        System.out.println("onMessage text:" + text);
        super.onMessage(webSocket, text);
      }

      @Override
      public void onMessage(WebSocket webSocket, okio.ByteString bytes) {

        System.out.println("onMessage binary:" + PbUtil
            .encode(bytes.toByteArray(), AsrResponse.getDefaultInstance()));

        AsrResponse asrResponse = PbUtil
            .encode(bytes.toByteArray(), AsrResponse.getDefaultInstance());

        System.out.println(asrResponse);
        System.out.println(asrResponse.getAsr());
        super.onMessage(webSocket, bytes);
      }

      @Override
      public void onClosing(WebSocket webSocket, int code, String reason) {
        System.out.println("onClosing: code:" + code + " reason:" + reason);
        super.onClosing(webSocket, code, reason);
        webSocket.close(code,reason);
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
    client.dispatcher().executorService().shutdown();
    client.connectionPool().evictAll();
  }


}
