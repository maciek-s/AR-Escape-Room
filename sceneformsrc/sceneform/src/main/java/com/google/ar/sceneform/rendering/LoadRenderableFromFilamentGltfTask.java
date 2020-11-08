package com.google.ar.sceneform.rendering;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.filament.gltfio.ResourceLoader;
import com.google.ar.sceneform.utilities.Preconditions;
import com.google.ar.sceneform.utilities.SceneformBufferUtils;

import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Function;

/**
 * Task for initializing a renderable with glTF data loaded with gltfio.
 */
@SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"}) // CompletableFuture
public class LoadRenderableFromFilamentGltfTask<T extends Renderable> {
  private static final String TAG = LoadRenderableFromFilamentGltfTask.class.getSimpleName();
  private final T renderable;
  private final RenderableInternalFilamentAssetData renderableData;

  LoadRenderableFromFilamentGltfTask(
          T renderable, Context context, Uri sourceUri, @Nullable Function<String, Uri> urlResolver) {
    this.renderable = renderable;
    IRenderableInternalData data = renderable.getRenderableData();
    if (data instanceof RenderableInternalFilamentAssetData) {
      this.renderableData =
              (com.google.ar.sceneform.rendering.RenderableInternalFilamentAssetData) data;
    } else {
      throw new IllegalStateException("Expected task type " + TAG);
    }
    this.renderableData.resourceLoader =
            new ResourceLoader(EngineInstance.getEngine().getFilamentEngine());
    this.renderableData.urlResolver =
            missingPath -> getUriFromMissingResource(sourceUri, missingPath, urlResolver);
    this.renderableData.context = context.getApplicationContext();
    this.renderable.getId().update();
  }

  @NonNull
  static Uri getUriFromMissingResource(
          @NonNull Uri parentUri,
          @NonNull String missingResource,
          @Nullable Function<String, Uri> urlResolver) {

    if (urlResolver != null) {
      return urlResolver.apply(missingResource);
    }

    if (missingResource.startsWith("/")) {
      missingResource = missingResource.substring(1);
    }

    // Ensure encoding.
    Uri decodedMissingResUri = Uri.parse(Uri.decode(missingResource));

    if (decodedMissingResUri.getScheme() != null) {
      throw new AssertionError(
              String.format(
                      "Resource path contains a scheme but should be relative, uri: (%s)",
                      decodedMissingResUri));
    }

    // Build uri to missing resource.
    String decodedMissingResPath = Preconditions.checkNotNull(decodedMissingResUri.getPath());
    Uri decodedParentUri = Uri.parse(Uri.decode(parentUri.toString()));
    String scheme = Preconditions.checkNotNull(decodedParentUri.getScheme());
    String authority = Preconditions.checkNotNull(decodedParentUri.getAuthority());
    String path = Preconditions.checkNotNull(decodedParentUri.getPath());
    // Remove root file
    path = path.replace(Preconditions.checkNotNull(decodedParentUri.getLastPathSegment()), "");

    Uri uri =
            new Uri.Builder()
                    .scheme(scheme)
                    .authority(authority)
                    .path(path)
                    .appendPath(decodedMissingResPath)
                    .build();
    // Normalize and return Uri.
    return Uri.parse(Uri.decode(URI.create(uri.toString()).normalize().toString()));
  }

  /**
   * Returns {@link CompletableFuture} for a new {@link Renderable}.
   */
  @SuppressWarnings({"AndroidApiChecker"})
  public CompletableFuture<T> downloadAndProcessRenderable(
          Callable<InputStream> inputStreamCreator) {

    return CompletableFuture.supplyAsync(
            // Download byte buffer via thread pool
            () -> {
              try {
                byte[] gltfByteBuffer =
                        SceneformBufferUtils.inputStreamCallableToByteArray(inputStreamCreator);
                return ByteBuffer.wrap(gltfByteBuffer);
              } catch (Exception e) {
                throw new CompletionException(e);
              }
            },
            ThreadPools.getThreadPoolExecutor())
            .thenApplyAsync(
                    gltfByteBuffer -> {
                      this.renderableData.gltfByteBuffer = gltfByteBuffer;
                      return renderable;
                    },
                    ThreadPools.getMainExecutor());
  }
}
