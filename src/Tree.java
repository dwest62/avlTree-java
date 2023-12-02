/**
 * Represents a tree interface.
 *
 * @param <E> the element to be stored in the nodes of the tree.
 */
public interface Tree<E> extends Iterable<E> {
    boolean search(E e);
    boolean insert(E e);
    boolean delete(E e);
    void inorder();
    void preorder();
    void postorder();
    int getSize();
    boolean isEmpty();

}