/**
 * Represents a node in a tree.
 *
 * @param <E> the type of the element stored in the tree.
 */
public class TreeNode<E> {
    TreeNode<E> left;
    TreeNode<E> right;
    E element;

    /**
     * Constructs a TreeNode with element e.
     *
     * @param e the element to be stored in the node.
     */
    public TreeNode(E e) {
        element = e;
    }
}
