package com.annis.baselib.utils.picasso;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.StatFs;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import com.squareup.picasso.Downloader;
import okhttp3.*;

import java.io.File;
import java.io.IOException;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR2;

/**
 * @author Lee
 * @date 2018/12/12 14:29
 * @Description
 */
//0. 缓存类
public final class OkHttp3Downloader implements Downloader {
    private final Call.Factory client;
    private final Cache cache;
    private boolean sharedClient = true;
    private static final String PICASSO_CACHE = "picasso-cache";
    private static final int MIN_DISK_CACHE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final int MAX_DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

    private static File createDefaultCacheDir(Context context) {
        File cache = new File(context.getApplicationContext().getCacheDir(), PICASSO_CACHE);
        if (!cache.exists()) {
            //noinspection ResultOfMethodCallIgnored
            cache.mkdirs();
        }
        return cache;
    }

    @TargetApi(JELLY_BEAN_MR2)
    private static long calculateDiskCacheSize(File dir) {
        long size = MIN_DISK_CACHE_SIZE;

        try {
            StatFs statFs = new StatFs(dir.getAbsolutePath());
            //noinspection deprecation
            long blockCount = SDK_INT < JELLY_BEAN_MR2 ? (long) statFs.getBlockCount() : statFs.getBlockCountLong();
            //noinspection deprecation
            long blockSize = SDK_INT < JELLY_BEAN_MR2 ? (long) statFs.getBlockSize() : statFs.getBlockSizeLong();
            long available = blockCount * blockSize;
            // Target 2% of the total space.
            size = available / 50;
        } catch (IllegalArgumentException ignored) {
        }

        // Bound inside min/max size for disk cache.
        return Math.max(Math.min(size, MAX_DISK_CACHE_SIZE), MIN_DISK_CACHE_SIZE);
    }

    /**
     * Create new downloader that uses OkHttp. This will install an image cache into your application
     * cache directory.
     */
    public OkHttp3Downloader(final Context context) {
        this(createDefaultCacheDir(context));
    }

    /**
     * Create new downloader that uses OkHttp. This will install an image cache into the specified
     * directory.
     *
     * @param cacheDir The directory in which the cache should be stored
     */
    public OkHttp3Downloader(final File cacheDir) {
        this(cacheDir, calculateDiskCacheSize(cacheDir));
    }

    /**
     * Create new downloader that uses OkHttp. This will install an image cache into your application
     * cache directory.
     *
     * @param maxSize The size limit for the cache.
     */
    public OkHttp3Downloader(final Context context, final long maxSize) {
        this(createDefaultCacheDir(context), maxSize);
    }

    /**
     * Create new downloader that uses OkHttp. This will install an image cache into the specified
     * directory.
     *
     * @param cacheDir The directory in which the cache should be stored
     * @param maxSize  The size limit for the cache.
     */
    public OkHttp3Downloader(final File cacheDir, final long maxSize) {
        this(new OkHttpClient.Builder().cache(new Cache(cacheDir, maxSize)).build());
        sharedClient = false;
    }

    /**
     * Create a new downloader that uses the specified OkHttp instance. A response cache will not be
     * automatically configured.
     */
    public OkHttp3Downloader(OkHttpClient client) {
        this.client = client;
        this.cache = client.cache();
    }

    /**
     * Create a new downloader that uses the specified {@link Call.Factory} instance.
     */
    public OkHttp3Downloader(Call.Factory client) {
        this.client = client;
        this.cache = null;
    }

    @VisibleForTesting
    Cache getCache() {
        return ((OkHttpClient) client).cache();
    }

    @NonNull
    @Override
    public Response load(@NonNull Request request) throws IOException {
        okhttp3.Response response = client.newCall(request).execute();
        int responseCode = response.code();
        if (responseCode >= 300) {
            response.body().close();
        }
        boolean fromCache = response.cacheResponse() != null;
        return response;
    }

    @Override
    public void shutdown() {
        if (!sharedClient) {
            if (cache != null) {
                try {
                    cache.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}