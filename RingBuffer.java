package com.yourcompany.tradingordersystem;

import java.util.concurrent.atomic.AtomicInteger;

public class RingBuffer<T> {

  private final Object[] buffer;
  private final AtomicInteger readIndex;
  private final AtomicInteger writeIndex;

  public RingBuffer(int size) {
    buffer = new Object[size];
    readIndex = new AtomicInteger(0);
    writeIndex = new AtomicInteger(0);
  }

  public void write(T value) {
    int currentWriteIndex = writeIndex.getAndUpdate(i -> (i + 1) % buffer.length);
    buffer[currentWriteIndex] = value;
  }

  public T read() {
    int currentReadIndex;
    do {
      currentReadIndex = readIndex.get();
      if (currentReadIndex == writeIndex.get()) {
        return null; // Empty buffer
      }
    } while (!readIndex.compareAndSet(currentReadIndex, (currentReadIndex + 1) % buffer.length));
    return (T) buffer[currentReadIndex];
  }
}

