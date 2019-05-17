package com.rokid.open.mapi.demo.demo;

import com.google.common.base.Charsets;
import com.google.protobuf.ByteString;
import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;
import com.google.protobuf.util.JsonFormat;
import com.google.protobuf.util.JsonFormat.Parser;
import com.google.protobuf.util.JsonFormat.Printer;
import io.grpc.Status;


public class JsonUtil {


  private static final Parser parser = JsonFormat.parser();
  private static final Printer printer = JsonFormat.printer();




  public static <T extends Message> ByteString encode(T instance) {

    try {
      return ByteString.copyFrom(printer.print(instance).getBytes(Charsets.UTF_8));
    } catch (Exception e) {
      throw Status.INTERNAL
          .withCause(e)
          .withDescription("Unable to print json proto")
          .asRuntimeException();
    }
  }

  @SuppressWarnings("unchecked")

  public static <T extends Message> T decode(String value, T defaultInstance) {

    try {
      Builder builder = defaultInstance.newBuilderForType();
      parser.merge(value, builder);
      return (T) builder.build();
    } catch (Exception e) {
      throw Status.INTERNAL.withDescription("Invalid protobuf byte sequence")
          .withCause(e).asRuntimeException();
    }
  }

}
