package com.amaiz.cache.impl;

import com.amaiz.cache.Cache;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import net.openhft.chronicle.bytes.NativeBytesStore;
import net.openhft.chronicle.values.Values;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.*;

public class OffHeapCache<K, V> implements Cache<K, V> {

    private class CacheItem {

        private final Class clazz;
        private final NativeBytesStore nativeBytesStore;
        private final long endOfLifeTimestamp;

        public CacheItem(Class clazz, NativeBytesStore nativeBytesStore, long endOfLifeTimestamp) {
            this.clazz = clazz;
            this.nativeBytesStore= nativeBytesStore;
            this.endOfLifeTimestamp = endOfLifeTimestamp;
        }
    }

    private final ConcurrentMap<K, CacheItem> cache = new ConcurrentHashMap<>();
    private final ThreadLocal<Kryo> kryoTL = ThreadLocal.withInitial(Kryo::new);

    private V getValueFromCacheItem(CacheItem cacheItem) {
        if (cacheItem == null) {
            return null;
        }
        Input input = new Input(cacheItem.nativeBytesStore.toByteArray());
        V v = (V) kryoTL.get().readObject(input, cacheItem.clazz);
        return v;
    }

    public OffHeapCache() {

    }

    public V put(K k, V v) {
        return this.put(k, v, TimeUnit.MINUTES, 1);
    }

    public V put(K k, V v, TimeUnit timeUnit, long ttl) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryoTL.get().writeObject(output, v);
        output.close();

        NativeBytesStore nativeBytesStore = NativeBytesStore.from(baos.toByteArray());
        long endOfLifeTimestamp = System.nanoTime() + timeUnit.toNanos(ttl);
        CacheItem prev = cache.put(k, new CacheItem(v.getClass(), nativeBytesStore, endOfLifeTimestamp));

        return getValueFromCacheItem(prev);
    }

    public V get(K k) {
        CacheItem cacheItem = cache.get(k);
        if (cacheItem == null) {
            return null;
        }
        if (cacheItem.endOfLifeTimestamp < System.nanoTime()) {
            cache.remove(k, cacheItem);
            return null;
        }
        return getValueFromCacheItem(cacheItem);
    }

    public void clear() {
        cache.clear();
    }

    public long size() {
        return cache.size();
    }
}
