// automatically generated by the FlatBuffers compiler, do not modify

package com.google.ar.sceneform.lullmodel;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Struct;

import java.nio.ByteBuffer;

@SuppressWarnings("unused")
public final class Mat4x3 extends Struct {
  public void __init(int _i, ByteBuffer _bb) {
    bb_pos = _i;
    bb = _bb;
  }

  public Mat4x3 __assign(int _i, ByteBuffer _bb) {
    __init(_i, _bb);
    return this;
  }

  public Vec4 c0() {
    return c0(new Vec4());
  }

  public Vec4 c0(Vec4 obj) {
    return obj.__assign(bb_pos + 0, bb);
  }

  public Vec4 c1() {
    return c1(new Vec4());
  }

  public Vec4 c1(Vec4 obj) {
    return obj.__assign(bb_pos + 16, bb);
  }

  public Vec4 c2() {
    return c2(new Vec4());
  }

  public Vec4 c2(Vec4 obj) {
    return obj.__assign(bb_pos + 32, bb);
  }

  public static int createMat4x3(FlatBufferBuilder builder, float c0_x, float c0_y, float c0_z, float c0_w, float c1_x, float c1_y, float c1_z, float c1_w, float c2_x, float c2_y, float c2_z, float c2_w) {
    builder.prep(4, 48);
    builder.prep(4, 16);
    builder.putFloat(c2_w);
    builder.putFloat(c2_z);
    builder.putFloat(c2_y);
    builder.putFloat(c2_x);
    builder.prep(4, 16);
    builder.putFloat(c1_w);
    builder.putFloat(c1_z);
    builder.putFloat(c1_y);
    builder.putFloat(c1_x);
    builder.prep(4, 16);
    builder.putFloat(c0_w);
    builder.putFloat(c0_z);
    builder.putFloat(c0_y);
    builder.putFloat(c0_x);
    return builder.offset();
  }
}
