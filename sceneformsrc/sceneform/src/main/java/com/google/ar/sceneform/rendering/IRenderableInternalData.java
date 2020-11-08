package com.google.ar.sceneform.rendering;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.filament.Entity;
import com.google.android.filament.IndexBuffer;
import com.google.android.filament.VertexBuffer;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.RenderableInternalData.MeshData;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;


// TODO: Split IRenderableInternalData into RenderableInternalSfbData and
// RenderableInternalDefinitionData
interface IRenderableInternalData {

  Vector3 getCenterAabb();

  void setCenterAabb(Vector3 minAabb);

  Vector3 getExtentsAabb();

  void setExtentsAabb(Vector3 maxAabb);

  Vector3 getSizeAabb();

  float getTransformScale();

  void setTransformScale(float scale);

  Vector3 getTransformOffset();

  void setTransformOffset(Vector3 offset);

  ArrayList<MeshData> getMeshes();

  @Nullable
  IndexBuffer getIndexBuffer();

  void setIndexBuffer(@Nullable IndexBuffer indexBuffer);

  @Nullable
  VertexBuffer getVertexBuffer();

  void setVertexBuffer(@Nullable VertexBuffer vertexBuffer);

  @Nullable
  IntBuffer getRawIndexBuffer();

  void setRawIndexBuffer(@Nullable IntBuffer rawIndexBuffer);

  @Nullable
  FloatBuffer getRawPositionBuffer();

  void setRawPositionBuffer(@Nullable FloatBuffer rawPositionBuffer);

  @Nullable
  FloatBuffer getRawTangentsBuffer();

  void setRawTangentsBuffer(@Nullable FloatBuffer rawTangentsBuffer);

  @Nullable
  FloatBuffer getRawUvBuffer();

  void setRawUvBuffer(@Nullable FloatBuffer rawUvBuffer);

  @Nullable
  FloatBuffer getRawColorBuffer();

  void setRawColorBuffer(@Nullable FloatBuffer rawColorBuffer);

  @NonNull
  List<String> getAnimationNames();

  void setAnimationNames(@NonNull List<String> animationNames);

  void buildInstanceData(Renderable renderable, @Entity int renderedEntity);

  /**
   * Removes any memory used by the object.
   *
   * @hide
   */
  void dispose();
}
