package homework.hw6.task1

class QuickSort {

    fun usualQuickSort(items: IntArray, leftIndex: Int, rightIndex: Int) {
        if (leftIndex < rightIndex) {
            val rightPointer = findPartition(items, leftIndex, rightIndex)
            usualQuickSort(items, leftIndex, rightPointer)
            usualQuickSort(items, rightPointer + 1, rightIndex)
        }
    }

    suspend fun asyncQuickSort(items: IntArray, leftIndex: Int, rightIndex: Int) {
        if (leftIndex < rightIndex) {
            val rightPointer = findPartition(items, leftIndex, rightIndex)
            asyncQuickSort(items, leftIndex, rightPointer)
            asyncQuickSort(items, rightPointer + 1, rightIndex)
        }
    }

    private fun findPartition(items: IntArray, left: Int, right: Int): Int {
        var leftPointer = left
        var rightPointer = right
        val mainstayElement = items[(left + right) / 2]
        while (leftPointer <= rightPointer) {
            while (items[leftPointer] < mainstayElement) {
                leftPointer += 1
            }
            while (items[rightPointer] > mainstayElement) {
                rightPointer -= 1
            }
            if (leftPointer >= rightPointer) {
                break
            }
            items[leftPointer] = items[rightPointer].also { items[rightPointer] = items[leftPointer] }
            leftPointer += 1
            rightPointer -= 1
        }
        return rightPointer
    }
}
