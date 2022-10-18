package com.jon.springboot2

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.StatusResultMatchers
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class WebTest extends Specification {

    @Autowired
    MockMvc mvc

    def "test web"() {
        given:
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/books")

        when:
        def action = mvc.perform(builder)
        def status = MockMvcResultMatchers.status()
        def ok = status.isOk()

        then:
        action.andExpect(ok)
        print "test end"
    }

    def "test response body"() {
        given:
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/books")

        when:
        def action = mvc.perform(builder)
        def content = MockMvcResultMatchers.content()
        def rst = content.string("data granted")

        then:
        action.andExpect(rst)
        print "test end"
    }

    def "test json"() {
        given:
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/books")

        when:
        def action = mvc.perform(builder)
        def content = MockMvcResultMatchers.content()
        def rst = content.json("{\"id\":1,\"name\":\"java basics\",\"type\":\"textbook\",\"description\":\"learn java basic with java basics\"}")

        then:
        action.andExpect(rst)
        print "test end"
    }

    def "test response header"() {
        given:
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/books")

        when:
        def action = mvc.perform(builder)
        def header = MockMvcResultMatchers.header()
        def rst = header.string("Content-Type", "application/json")

        then:
        action.andExpect(rst)
        print "test end"
    }
}


