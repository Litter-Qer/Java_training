import spock.lang.Shared
import spock.lang.Specification

class InsertionSortTest extends Specification {
    @Shared InsertionSort insertionSort = new InsertionSort()

    def "test sort"() {
        expect:
        // a sorted array (in-place)
        insertionSort.sort(unsorted as int[]) == sorted

        where:
        unsorted               || sorted
        [10,12,9,1,6,3,8,4,10] || [1,3,4,6,8,9,10,10,12]
        [8,10,9,7,12,6,6,10]   || [6,6,7,8,9,10,10,12]
        [10,11,9,1,5,3,8,4,9]  || [1,3,4,5,8,9,9,10,11]
        [8,11,9,7,12,5,6,10]   || [5,6,7,8,9,10,11,12]
    }


}
