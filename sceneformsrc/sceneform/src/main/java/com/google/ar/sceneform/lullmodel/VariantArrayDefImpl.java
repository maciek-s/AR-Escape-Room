// automatically generated by the FlatBuffers compiler, do not modify

package com.google.ar.sceneform.lullmodel;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
/**
 * Internal table for VariantArrayDef.  Unions need to be wrapped in a table
 * in order to have an array of them.
 */
public final class VariantArrayDefImpl extends Table {
  public static VariantArrayDefImpl getRootAsVariantArrayDefImpl(ByteBuffer _bb) {
    return getRootAsVariantArrayDefImpl(_bb, new VariantArrayDefImpl());
  }

  public static VariantArrayDefImpl getRootAsVariantArrayDefImpl(ByteBuffer _bb, VariantArrayDefImpl obj) {
    _bb.order(ByteOrder.LITTLE_ENDIAN);
    return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb));
  }

  public static int createVariantArrayDefImpl(FlatBufferBuilder builder,
                                              byte value_type,
                                              int valueOffset) {
    builder.startObject(2);
    VariantArrayDefImpl.addValue(builder, valueOffset);
    VariantArrayDefImpl.addValueType(builder, value_type);
    return VariantArrayDefImpl.endVariantArrayDefImpl(builder);
  }

  public static void startVariantArrayDefImpl(FlatBufferBuilder builder) {
    builder.startObject(2);
  }

  public static void addValueType(FlatBufferBuilder builder, byte valueType) {
    builder.addByte(0, valueType, 0);
  }

  public static void addValue(FlatBufferBuilder builder, int valueOffset) {
    builder.addOffset(1, valueOffset, 0);
  }

  public static int endVariantArrayDefImpl(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }

  public void __init(int _i, ByteBuffer _bb) {
    bb_pos = _i;
    bb = _bb;
    vtable_start = bb_pos - bb.getInt(bb_pos);
    vtable_size = bb.getShort(vtable_start);
  }

  public VariantArrayDefImpl __assign(int _i, ByteBuffer _bb) {
    __init(_i, _bb);
    return this;
  }

  public byte valueType() {
    int o = __offset(4);
    return o != 0 ? bb.get(o + bb_pos) : 0;
  }

  public Table value(Table obj) {
    int o = __offset(6);
    return o != 0 ? __union(obj, o) : null;
  }
}

