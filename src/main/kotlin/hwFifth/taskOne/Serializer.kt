package hwFifth.taskOne

import java.io.InputStream
import java.io.OutputStream

interface Serializable {
    fun serialize(out: OutputStream)
    fun deserialize(input: InputStream)
}