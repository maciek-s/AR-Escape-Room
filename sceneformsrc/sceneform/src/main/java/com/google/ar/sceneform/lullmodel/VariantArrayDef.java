// automatically generated by the FlatBuffers compiler, do not modify

package com.google.ar.sceneform.lullmodel;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
/**
 * The flatbuffer equivalent for lull::VariantArray.
 */
public final class VariantArrayDef extends Table {
  public static VariantArrayDef getRootAsVariantArrayDef(ByteBuffer _bb) {
    return getRootAsVariantArrayDef(_bb, new VariantArrayDef());
  }

  public static VariantArrayDef getRootAsVariantArrayDef(ByteBuffer _bb, VariantArrayDef obj) {
    _bb.order(ByteOrder.LITTLE_ENDIAN);
    return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb));
  }

  public static int createVariantArrayDef(FlatBufferBuilder builder,
                                          int valuesOffset) {
    builder.startObject(1);
    VariantArrayDef.addValues(builder, valuesOffset);
    return VariantArrayDef.endVariantArrayDef(builder);
  }

  public static void startVariantArrayDef(FlatBufferBuilder builder) {
    builder.startObject(1);
  }

  public static void addValues(FlatBufferBuilder builder, int valuesOffset) {
    builder.addOffset(0, valuesOffset, 0);
  }

  public static int createValuesVector(FlatBufferBuilder builder, int[] data) {
    builder.startVector(4, data.length, 4);
    for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]);
    return builder.endVector();
  }

  public static void startValuesVector(FlatBufferBuilder builder, int numElems) {
    builder.startVector(4, numElems, 4);
  }

  public static int endVariantArrayDef(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }

  public void __init(int _i, ByteBuffer _bb) {
    bb_pos = _i;
    bb = _bb;
    vtable_start = bb_pos - bb.getInt(bb_pos);
    vtable_size = bb.getShort(vtable_start);
  }

  public VariantArrayDef __assign(int _i, ByteBuffer _bb) {
    __init(_i, _bb);
    return this;
  }

  public VariantArrayDefImpl values(int j) {
    return values(new VariantArrayDefImpl(), j);
  }

  public VariantArrayDefImpl values(VariantArrayDefImpl obj, int j) {
    int o = __offset(4);
    return o != 0 ? obj.__assign(__indirect(__vector(o) + j * 4), bb) : null;
  }

  public int valuesLength() {
    int o = __offset(4);
    return o != 0 ? __vector_len(o) : 0;
  }
}

