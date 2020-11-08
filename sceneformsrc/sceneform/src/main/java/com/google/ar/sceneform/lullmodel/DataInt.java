// automatically generated by the FlatBuffers compiler, do not modify

package com.google.ar.sceneform.lullmodel;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
/**
 * Data type for integer values to be stored in a VariantDef.
 */
public final class DataInt extends Table {
  public static DataInt getRootAsDataInt(ByteBuffer _bb) {
    return getRootAsDataInt(_bb, new DataInt());
  }

  public static DataInt getRootAsDataInt(ByteBuffer _bb, DataInt obj) {
    _bb.order(ByteOrder.LITTLE_ENDIAN);
    return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb));
  }

  public static int createDataInt(FlatBufferBuilder builder,
                                  int value) {
    builder.startObject(1);
    DataInt.addValue(builder, value);
    return DataInt.endDataInt(builder);
  }

  public static void startDataInt(FlatBufferBuilder builder) {
    builder.startObject(1);
  }

  public static void addValue(FlatBufferBuilder builder, int value) {
    builder.addInt(0, value, 0);
  }

  public static int endDataInt(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }

  public void __init(int _i, ByteBuffer _bb) {
    bb_pos = _i;
    bb = _bb;
    vtable_start = bb_pos - bb.getInt(bb_pos);
    vtable_size = bb.getShort(vtable_start);
  }

  public DataInt __assign(int _i, ByteBuffer _bb) {
    __init(_i, _bb);
    return this;
  }

  public int value() {
    int o = __offset(4);
    return o != 0 ? bb.getInt(o + bb_pos) : 0;
  }
}

