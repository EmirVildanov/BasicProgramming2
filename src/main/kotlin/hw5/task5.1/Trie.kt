import java.io.InputStream
import java.io.OutputStream

/*


boolean add(String element); (возвращает true, если такой строки ещё не было, работает за O(|element|))
boolean contains(String element); (работает за O(|element|))
boolean remove(String element); (возвращает true, если элемент реально был в дереве, работает за O(|element|))
int size(); (работает за O(1))
int howManyStartWithPrefix(String prefix); (работает за O(|prefix|))
Также бор должен реализовывать интерфейс с методами:

void serialize(OutputStream out) throws IOException;
void deserialize(InputStream in) throws IOException; (заменяет старое дерево данными из стрима)
Стандартный сериализатор использовать нельзя.
 */

class Trie {

    val size = 0
        get() = field

    fun add(element: String): Boolean {
        TODO()
    }

    fun contains(element: String): Boolean {
        TODO()
    }

    fun remove(element: String): Boolean {
        TODO()
    }

    fun howManyStartWithPrefix(prefix: String): Int {
        TODO()
    }

    fun serialize(out: OutputStream) {

    }

    fun deserialize(input: InputStream) {
        
    }
}