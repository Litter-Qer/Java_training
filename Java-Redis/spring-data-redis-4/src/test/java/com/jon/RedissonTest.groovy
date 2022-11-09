package com.jon

import lombok.extern.flogger.Flogger
import lombok.extern.slf4j.Slf4j
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.cache.CacheProperties
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Shared
import spock.lang.Specification

import javax.annotation.Resource
import java.util.concurrent.TimeUnit

@SpringBootTest
class RedissonTest extends Specification {
    @Shared
    public static final Logger logger = LoggerFactory.getLogger(RedissonClient.class)

    @Resource
    private RedissonClient redissonClient

    def "quick start"() {
        given:
        RLock lock = redissonClient.getLock("nyLock")

        when:
        Thread t1 = new Thread(doLock(lock)).start()
        Thread t2 = new Thread(doLock(lock)).start()

        then:
        logger.debug "test completed"
    }

    def doLock(RLock lock) {
        logger.debug "try lock"
        boolean isLock = lock.tryLock(1, 10, TimeUnit.SECONDS)
        if (isLock) {
            try {
                logger.debug "succeed"
                TimeUnit.SECONDS.sleep(5)
            } finally {
                lock.unlock()
            }
        } else
            logger.debug "fail"
    }
}
