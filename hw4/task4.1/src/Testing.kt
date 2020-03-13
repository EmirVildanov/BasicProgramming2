fun hashFunction(string: String): Int {
    var numberPower = 1;
    var hashNumber = 0;
    for (i in i 0 until stringLength)
    {
        hashNumber += (getChar(string, i) - 'a' + 1) * numberPower
        numberPower *= hashPrimeNumber
    }
    return Math.abs(hashNumber % hashtable.size); //abs because in case of big words it will return negative answer
}

fun main() {

}