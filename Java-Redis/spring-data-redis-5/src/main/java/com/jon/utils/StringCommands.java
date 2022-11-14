package com.jon.utils;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.dynamic.Commands;
import io.lettuce.core.dynamic.batch.BatchSize;
import io.lettuce.core.dynamic.batch.CommandBatching;

@BatchSize(50)
public interface StringCommands extends Commands {

    void set(String key, String value);

    RedisFuture<String> get(String key);

    RedisFuture<String> get(String key, CommandBatching batching);
}