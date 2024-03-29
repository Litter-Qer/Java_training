---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by Jon.
--- DateTime: 7/11/2022 1:48 pm
---

local key = KEYS[1];
local threadId = ARGV[1];
local releaseTime = ARGV[2];

if (redis.call('exist', key) == 0) then
    -- creat a lock
    redis.call('hset', key, threadId, '1');
    -- expiration time
    redis.call('expire', key, releaseTime);
    return 1;
end

if redis.call('hexists', key, threadId) == 1 then
    -- increment by 1
    redis.call('hincrby', key, threadId, '1');
    -- reset expiration
    redis.call('expire', key, releaseTime);
    return 1;
end
return 0;

