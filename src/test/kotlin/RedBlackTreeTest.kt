import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.random.nextInt

class RedBlackTreeTest {
    @Test
    fun driver() {
        val tree = RedBlackTree()
        val list = listOf(
            46,
            582,
            763,
            908,
            62,
            27,
            899,
            779,
            170,
            522,
            559,
            543,
            178,//
            382,
            223,
            101,
            104,
            215,
            328,
            914,
            805,
            414,
            105,
            638,
            168
        )

        for (num in list)
            tree.insert(num)

        tree.printInorder()

        assert(1 == 1)
    }

    @Test
    fun buildRandomList() {
        val list = List(25) { Random.nextInt(0..1_000) }
        print(list)
    }
}