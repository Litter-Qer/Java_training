import spock.lang.*

class InsertionSortTest extends Specification {
    InsertionSort insertionSort = new InsertionSort()


    def "test sort"() {
        expect:
        // a sorted array (in-place)
        insertionSort.sort(unsorted) == sorted

        where:
        unsorted               || sorted
        [10,12,9,1,6,3,8,4,10] || [1,3,4,6,8,9,10,10,12]
        [8,10,9,7,12,6,6,10]   || [6,6,7,8,9,10,10,12]
        [10,11,9,1,5,3,8,4,9]  || [1,3,4,5,8,9,9,10,11]
        [8,11,9,7,12,5,6,10]   || [5,6,7,8,9,10,11,12]
    }

    def "test sort1"() {
        when:
        int[] result = insertionSort.sort1([0] as int[])

        then:
        result == [0] as int[]
    }

    def "test sort2"() {
        when:
        int[] result = insertionSort.sort2([0] as int[])

        then:
        result == [0] as int[]
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme