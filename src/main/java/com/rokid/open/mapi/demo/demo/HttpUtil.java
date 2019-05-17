package com.rokid.open.mapi.demo.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import org.springframework.util.DigestUtils;


public class HttpUtil {

  public static Headers getWssAsrHeaders() {
    Map<String, String> headMap = new HashMap<>();

    String key = "you key";
    String deviceTypeId = "you deviceTypeId";
    String deviceId = "you deviceId";
    String version = "2";
    String time = String.valueOf(System.currentTimeMillis());
    String secret = "you  secret";

    String sign = String
        .format("key=%s&device_type_id=%s&device_id=%s&service=%s&version=%s&time=%s&secret=%s",
            key, deviceTypeId, deviceId, "asr", version, time, secret);

    sign = DigestUtils.md5DigestAsHex(sign.getBytes());

    headMap.put("Authorization",
        String.format("key=%s;device_type_id=%s;device_id=%s;time=%s;sign=%s;version=%s;service=%s",
            key, deviceTypeId, deviceId, time, sign, version, "asr"));


    return Headers.of(headMap);
  }

  public static Headers getTtsHeaders() {
    Map<String, String> headMap = new HashMap<>();

    String key = "you key";
    String deviceTypeId = "you deviceTypeId";
    String deviceId = "you deviceId";
    String version = "1";
    String time = String.valueOf(System.currentTimeMillis());
    String secret = "you  secret";

    String sign = String
        .format("key=%s&device_type_id=%s&device_id=%s&service=%s&version=%s&time=%s&secret=%s",
            key, deviceTypeId, deviceId, "tts", version, time, secret);

    sign = DigestUtils.md5DigestAsHex(sign.getBytes());

    headMap.put("Authorization",
        String.format("key=%s;device_type_id=%s;device_id=%s;time=%s;sign=%s;version=%s;service=%s",
            key, deviceTypeId, deviceId, time, sign, version, "tts"));


    return Headers.of(headMap);
  }

  public static Headers getHttpsAsrHeaders() {
    Map<String, String> headMap = new HashMap<>();

    String key = "you key";
    String deviceTypeId = "you deviceTypeId";
    String deviceId = "you deviceId";
    String version = "2";
    String time = String.valueOf(System.currentTimeMillis());
    String secret = "you  secret";

    String sign = String
        .format("key=%s&device_type_id=%s&device_id=%s&service=%s&version=%s&time=%s&secret=%s",
            key, deviceTypeId, deviceId, "asr", version, time, secret);

    sign = DigestUtils.md5DigestAsHex(sign.getBytes());

    headMap.put("Authorization",
        String.format("key=%s;device_type_id=%s;device_id=%s;time=%s;sign=%s;version=%s;service=%s",
            key, deviceTypeId, deviceId, time, sign, version, "asr"));




    String voiceConfig = String
        .format("codec=%s;voice_trigger=%s;engine=%s;need_hotWords=%s;no_intermediate_asr=%s",
            "pcm", "", "zh", "false", "true");

    System.out.println(voiceConfig);
    headMap.put("voice-config", voiceConfig);
    return Headers.of(headMap);
  }



  public static OkHttpClient getClient() {
    return new OkHttpClient().newBuilder().connectTimeout(10000, TimeUnit.MILLISECONDS)
        .readTimeout(10000, TimeUnit.MILLISECONDS)
        .writeTimeout(10000, TimeUnit.MILLISECONDS)
        .build();
  }
}
