package com.rokid.open.mapi.demo.demo;



import com.google.common.io.ByteStreams;
import com.google.protobuf.ByteString;
import com.rokid.manhattan.open.speech.v2.Asr.AsrRequest;
import com.rokid.manhattan.open.speech.v2.Asr.AsrResponse;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpAsrDemo {
  private static OkHttpClient client = null;
  private static Headers headers = null;
  private static String url = "https://mapi.open.rokid.com/rest/asr/11";

  public static void main(String[] args) throws Exception {

    client = HttpUtil.getClient();
    headers = HttpUtil.getHttpsAsrHeaders();

    InputStream voices = HttpAsrDemo.class.getClassLoader().getResource("rqjttqzmy.pcm").openStream();
    AsrRequest asrRequest = AsrRequest.newBuilder()
        .setId(1)
        .setType("ONESHOT")
        .setVoices(ByteString.copyFrom(ByteStreams.toByteArray(voices)))
        .build();

    RequestBody formBody = FormBody
        .create(MediaType.parse("Content-Type:application/octet-stream"),
            asrRequest.toByteArray());

    Request request = new Request.Builder()
        .headers(headers)
        .post(formBody)
        .url(url).
            build();

    doRequestCall(client, request);
    voices.close();





  }


  static  Response doRequestCall(OkHttpClient client,Request request) {
    try {
      Response response = client.newCall(request).execute();
      System.out.println("code:" + response.code());
      System.out.println("header:" + response.headers().toString());
      String body = response.body().string();
      AsrResponse asrResponse = JsonUtil.decode(body,AsrResponse.getDefaultInstance());
      System.out.println("asrResponse:"+asrResponse);

      return response;
    } catch (IOException e) {
      e.printStackTrace();
      throw  new  RuntimeException();
    }

  }
}
