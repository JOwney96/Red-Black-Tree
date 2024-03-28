/**
 * Class representing a Red-Black Tree.
 *
 * The Red-Black Tree is a self-balancing binary search tree with the following properties:
 *
 * 1. Each node is either red or black.
 * 2. The root is black.
 * 3. All leaves (null nodes) are black.
 * 4. If a node is red, then both its children are black.
 * 5. Every path from a node to its descendant leaves contains the same number of black nodes.
 */
class RedBlackTree {
    private var head: NodeDataClass = nullLeaf()
    private lateinit var current: NodeDataClass
    private lateinit var parent: NodeDataClass
    private lateinit var grandParent: NodeDataClass
    private lateinit var greatGrandParent: NodeDataClass
    
    companion object {
        fun nullLeaf() = NodeDataClass()
        fun black() = 1
        fun red() = 0
    }

    /**
     * Checks if the Red-Black Tree is empty.
     *
     * @return true if the Red-Black Tree is empty, false otherwise.
     */
    fun isEmpty() = head == nullLeaf()

    /**
     * Sets the head of the Red-Black Tree to be an empty leaf node.
     */
    fun makeEmpty() {
        head = nullLeaf()
    }


    /**
     * Inserts an item into the binary tree.
     *
     * @param item the item to be inserted
     */
    fun insert(item: Int) {
        if (head == nullLeaf())
            return createHead(item)

        grandParent = head
        parent = head
        current = head

        while (current.data != item
            && current.data != null
        ) {
            greatGrandParent = grandParent
            grandParent = parent
            parent = current
            current =
                if (item < current.data!!)
                    current.leftNode ?: nullLeaf()
                else
                    current.rightNode ?: nullLeaf()

            if (current.leftNode?.color == red() &&
                current.rightNode?.color == red()
            )
                recolor(item)
        }
        if (current != nullLeaf())
            return

        current = NodeDataClass(item)
        if (item < parent.data!!)
            parent.leftNode = current
        else
            parent.rightNode = current

        recolor(item)
    }

    /**
     * Creates a new head node with the specified item.
     * This method will create a new node using the provided item and set it as the head node.
     *
     * @param item The item value for the new head node.
     */
    private fun createHead(item: Int) {
        head = NodeDataClass(item, color = black())
    }

    /**
     * Recolors the current item and its left and right nodes.
     *
     * @param item the value of the current item
     */
    private fun recolor(item: Int) {
        current.color = red()
        if (current.leftNode != null)
            current.leftNode!!.color = black()
        if (current.rightNode != null)
            current.rightNode!!.color = black()

        if (parent.color == red()) {
            grandParent.color = red()
            if (item < grandParent.data!! != item < parent.data!!)
                parent = rotate(item, grandParent)
            current = rotate(item, greatGrandParent)
            current.color = black()
        }
        head.color = black()
    }

    /**
     * Rotates the given item within the parent node.
     *
     * @param item The item to be rotated within the parent node.
     * @param parent The parent node.
     * @return The parent node after the rotation.
     */
    private fun rotate(item: Int, parent: NodeDataClass): NodeDataClass {
        return if (item < parent.data!!) {
            parent.leftNode =
                if (item < parent.leftNode?.data!!)
                    rotateWithLeft(parent.leftNode!!)
                else
                    rotateWithRight(parent.leftNode!!)
            parent
        } else {
            parent.rightNode =
                if (item < parent.rightNode?.data!!)
                    rotateWithLeft(parent.rightNode!!)
                else
                    rotateWithRight(parent.rightNode!!)
            parent
        }
    }

    /**
     * Rotates the given node with its left child node.
     *
     * @param node the node to rotate with its left child node
     * @return the new root node after rotation
     */
    private fun rotateWithLeft(node: NodeDataClass): NodeDataClass? {
        val newNode = node.leftNode
        node.leftNode = newNode?.rightNode
        newNode?.rightNode = node
        return newNode
    }

    /**
     * Rotate the given node with its right child.
     *
     * @param node The node to rotate.
     * @return The updated node after rotation.
     */
    private fun rotateWithRight(node: NodeDataClass): NodeDataClass? {
        val newNode = node.rightNode
        node.rightNode = newNode?.leftNode
        newNode?.leftNode = node
        return newNode
    }

    /**
     * Counts the number of nodes in the tree.
     * @return The total number of nodes in the tree.
     */
    fun countNodes(): Int {
        return countNodes(head)
    }

    /**
     * Counts the number of nodes in a binary tree rooted at the given node.
     *
     * @param node The root of the binary tree.
     * @return The number of nodes in the binary tree.
     */
    private fun countNodes(node: NodeDataClass?): Int {
        if (node == nullLeaf() || node == null)
            return 0
        var count = 1
        count += countNodes(node.leftNode)
        count += countNodes(node.rightNode)
        return count
    }

    /**
     * Searches for a given value in the linked list.
     *
     * @param value the value to search for
     * @return true if the value is found, false otherwise
     */
    fun search(value: Int): Boolean {
        return search(head, value)
    }

    /**
     * Recursively searches for a value in a binary tree starting from the given node.
     *
     * @param node The starting node for the search.
     * @param value The value to search for.
     *
     * @return True if the value is found in the tree, otherwise false.
     */
    private fun search(node: NodeDataClass?, value: Int): Boolean {
        var found = false
        var currentNode = node
        while ((currentNode != nullLeaf() || !found)) {
            val data = currentNode?.data ?: return false
            if (value < data)
                currentNode = currentNode.leftNode
            else if (value > data)
                currentNode = currentNode.rightNode
            else {
                found = true
                break
            }
            found = search(currentNode, value)
        }

        return found
    }

    /**
     * Prints the elements of the binary tree in inorder traversal.
     */
    fun printInorder() {
        printInorder(head)
    }

    /**
     * Prints the elements of the binary tree in inorder traversal.
     *
     * @param node The root node of the binary tree.
     */
    private fun printInorder(node: NodeDataClass?) {
        if (node == nullLeaf() || node == null)
            return

        printInorder(node.leftNode)
        var c = 'B'
        if (node.color == red())
            c = 'R'
        print("${node.data} $c ")
        printInorder(node.rightNode)
    }

    /**
     * Prints the elements of the binary tree in preorder traversal.
     */
    fun printPreorder() {
        printPreorder(head)
    }

    /**
     * Prints the elements of the binary tree in preorder traversal.
     *
     * @param node The root node of the binary tree.
     */
    private fun printPreorder(node: NodeDataClass?) {
        if (node == nullLeaf() || node == null)
            return

        var c = 'B'
        if (node.color == red())
            c = 'R'
        print("${node.data} $c ")
        printPreorder(node.leftNode)
        printPreorder(node.rightNode)
    }

    /**
     * Prints the elements of the binary tree in postorder traversal.
     */
    fun printPostorder() {
        printPostorder(head)
    }

    /**
     * Prints the elements of the binary tree in postorder traversal.
     *
     * @param node The root node of the binary tree.
     */
    private fun printPostorder(node: NodeDataClass?) {
        if (node == nullLeaf() || node == null)
            return

        printPostorder(node.leftNode)
        printPostorder(node.rightNode)
        var c =
            if (node.color == red())
                'R'
            else
                'B'

        print("${node.data} $c ")
    }
}