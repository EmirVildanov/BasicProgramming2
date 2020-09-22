package homework.hw6.task1

class QuickSort {

    fun usualQuickSort(items: IntArray, leftIndex: Int, rightIndex: Int) {
        if (leftIndex < rightIndex) {
            val rightPointer = sortFunctionInner(items, leftIndex, rightIndex)
            usualQuickSort(items, leftIndex, rightPointer - 1)
            usualQuickSort(items, rightPointer + 1, rightIndex)
        }
    }

    suspend fun asyncQuickSort(items: IntArray, leftIndex: Int, rightIndex: Int) {
        if (leftIndex < rightIndex) {
            val rightPointer = sortFunctionInner(items, leftIndex, rightIndex)
            asyncQuickSort(items, leftIndex, rightPointer - 1)
            asyncQuickSort(items, rightPointer + 1, rightIndex)
        }
    }

    private fun sortFunctionInner(items: IntArray, left: Int, right: Int): Int {
        var leftPointer = left
        var rightPointer = right
        val mainstayElement = items[leftPointer]
        while (leftPointer < rightPointer) {
            while (items[leftPointer] < mainstayElement) {
                leftPointer += 1
            }
            while (items[rightPointer] > mainstayElement) {
                rightPointer -= 1
            }
            items[leftPointer] = items[rightPointer].also { items[rightPointer] = items[leftPointer] }
            if (items[leftPointer] == items[rightPointer]) {
                break
            }
        }
        return rightPointer
    }
}
