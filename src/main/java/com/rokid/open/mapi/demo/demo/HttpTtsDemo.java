package com.rokid.open.mapi.demo.demo;

import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HttpTtsDemo {
  private static OkHttpClient client = null;
  private static Headers headers = null;
  private static String url = "https://mapi.open.rokid.com/rest/tts/123444";

  public static void main(String[] args) {
    client = HttpUtil.getClient();
    headers = HttpUtil.getTtsHeaders();

    com.rokid.manhattan.open.tts.Request ttsRequest =
        com.rokid.manhattan.open.tts.Request.newBuilder()
            .setId(1)
            .setCodec("mp3")
            .setContentType("raw")
            .setDeclaimer("zh")
            .setText("hello")
            .build();

    RequestBody formBody = FormBody
        .create(MediaType.parse("Content-Type:application/json"),
            JsonUtil.encode(ttsRequest).toStringUtf8());

    Request request = new Request.Builder()
        .headers(headers)
        .post(formBody)
        .url(url).
            build();

    try {
      Response response = client.newCall(request).execute();
      System.out.println("code:" + response.code());
      System.out.println("header:" + response.headers().toString());

      String body = response.body().string();

      System.out.println("tts: "+JsonUtil.decode(body, com.rokid.manhattan.open.tts.Response.getDefaultInstance()));


    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }
}
