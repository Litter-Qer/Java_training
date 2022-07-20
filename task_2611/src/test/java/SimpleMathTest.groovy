import spock.lang.*

class SimpleMathTest extends Specification {
    SimpleMath simpleMath = new SimpleMath()

    def "test divide"() {
        when:
        simpleMath.divide()

        then:
        false//todo - validate something
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme