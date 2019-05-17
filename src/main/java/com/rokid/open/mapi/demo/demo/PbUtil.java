package com.rokid.open.mapi.demo.demo;


import com.google.protobuf.ByteString;
import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;
import io.grpc.Status;


public class PbUtil {

  public static  <T extends Message> T encode(byte[] request, T defaultInstance){
    try {
      Builder builder = defaultInstance.newBuilderForType();
      return (T)builder.mergeFrom(request).build();
    } catch (Exception e) {
      throw Status.INTERNAL.withDescription("Invalid protobuf byte sequence")
          .withCause(e).asRuntimeException();
    }
  }

  public static <T extends Message> ByteString decode(T instance){
    return instance.toByteString();
  }

}
