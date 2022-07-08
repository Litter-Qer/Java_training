package pojo

import spock.lang.*

class RestaurantTest extends Specification {
    @Shared rs1 = new Restaurant()
    @Shared rs2 = new Restaurant()
    @Shared rs3 = new Restaurant()

    def "one rs can receive multiple orders"() {
        expect:
        rs1.order(orders1)
        rs1.order(orders2)
        rs1.order(orders3)

        where:
        orders1 = ["a","b","c","d"]
        orders2 = ["a","b","c","d"]
        orders3 = ["a","b","c","d"]
    }

    def "one rs can serve multiple orders"() {
        expect:
        rs1.order(orders1)
        rs1.order(orders2)
        rs1.order(orders3)
        rs1.serving(order1)
        rs1.serving(order2)
        rs1.serving(order2)

        where:
        orders1 = ["a","b","c","d"]
        orders2 = ["a","b","c","d"]
        orders3 = ["a","b","c","d"]
        order1 = 10001
        order2 = 10002
        order3 = 10003
    }

    def "multiple rs can receive the same order"() {
        expect:
        rs1.order(orders1)
        rs2.order(orders1)
        rs3.order(orders1)

        where:
        orders1 = ["a","b","c","d"]
        orders2 = ["a","b","c","d"]
        orders3 = ["a","b","c","d"]
    }
}
