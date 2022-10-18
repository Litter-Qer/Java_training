package com.jon

import com.jon.mapper.BookMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ApplicationTest extends Specification {
    @Autowired
    BookMapper bookMapper

    def "test"() {
        given:
        def book = bookMapper.selectById(1)

        when:
        println book

        then:
        println "test end"
    }
}