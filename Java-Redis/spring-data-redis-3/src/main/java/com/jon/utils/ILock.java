package com.jon.utils;

public interface ILock {
    boolean tryLock(long timeoutSec);

    void unlock();
}
