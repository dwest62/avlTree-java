import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

/**
 * Represents a Binary search tree.
 *
 * @param <E> The type of element to be stored in nodes in the tree. (Must implement comparable).
 */
public class BST <E extends Comparable<E>> implements Tree<E> {
    protected TreeNode<E> root;
    protected int size = 0;

    /**
     * Default constructor.
     */
    public BST() {}

    /**
     * Constructs a Binary Search Tree from an array of objects.
     *
     * @param objects the objects to be inserted into the nodes of the tree.
     */
    public BST(E[] objects) {
        Arrays.stream(objects).forEach(this::insert);
    }

    /**
     * Traverses the BST to search for if an item exists in the tree.
     *
     * @param e the item to search for.
     * @return true if the item is found in the tree, otherwise false.
     */
    @Override
    public boolean search(E e) {
        TreeNode<E> current = root;
        while(current != null) {
            if(e.compareTo(current.element) < 0) current = current.left;
            else if (e.compareTo(current.element) > 0) current = current.right;
            else return true;
        }
        return false;
    }

    /**
     * Inserts a new node with element e into the Tree.
     *
     * @param e the node to be inserted.
     * @return true if the insertion was successful, otherwise false.
     */
    @Override
    public boolean insert(E e) {
        if(root == null)
           root = createNewNode(e);
        else {
            TreeNode<E> current = root;
            TreeNode<E> parent = null;
            while(current != null) {
                if(e.compareTo(current.element) < 0) {
                    parent = current;
                    current = current.left;
                }
                else if (e.compareTo(current.element) > 0) {
                    parent = current;
                    current = current.right;
                }
                else return false;
            }
            if(e.compareTo(parent.element) < 0) parent.left = createNewNode(e);
            else parent.right = createNewNode(e);
        }
        size++;
        return true;
    }

    /**
     * Creates a new TreeNode with element e.
     *
     * @param e the element to be stored in the node.
     * @return the new TreeNode.
     */
    protected TreeNode<E> createNewNode(E e) {
        return new TreeNode<>(e);
    }

    /**
     * Deletes an element from the tree.
     *
     * @param e the element to be deleted.
     * @return true if the element is found and successfully deleted, otherwise false.
     */
    @Override
    public boolean delete(E e) {
        TreeNode<E> current = root;
        TreeNode<E> parent = null;

        while(current != null) {
            if(e.compareTo(current.element) < 0) {
                parent = current;
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) {
                parent = current;
                current = current.right;
            }
            else break;
        }
        if (current == null) return false;
        if (current.left == null) {
            if (parent == null) root = current.right;
            else if (e.compareTo(parent.element) < 0) parent.left = current.right;
            else parent.right = current.right;
        } else {
            TreeNode<E> rightMostParent = current;
            TreeNode<E> rightMost = current.left;
            while(rightMost.right != null) {
                rightMostParent = rightMost;
                rightMost = rightMost.right;
            }
            current.element = rightMost.element;
            if(rightMostParent.right == rightMost) rightMostParent.right = rightMost.left;
            else rightMostParent.left = rightMost.left;
        }

        size--;
        return true;
    }

    /**
     * Prints the tree in order.
     */
    @Override
    public void inorder() {
        inorder(root);
    }

    /**
     * Helper method to print the tree inorder recursively.
     *
     * @param root the current node being processed.
     */
    private void inorder(TreeNode<E> root) {
        if(root == null) return;
        inorder(root.left);
        System.out.println(root.element + " ");
        inorder(root.right);
    }

    /**
     * Prints the tree in preorder.
     */
    @Override
    public void preorder() {
        preorder(root);
    }

    /**
     * Helper method to print the tree in preorder recursively.
     *
     * @param root the current node being processed.
     */
    private void preorder(TreeNode<E> root) {
        if(root == null) return;
        System.out.print(root.element + " ");
        preorder(root.left);
        preorder(root.right);
    }

    /**
     * Prints the tree in postorder.
     */
    @Override
    public void postorder() {
        postorder(root);
    }

    /**
     * Helper method to print the tree in postorder recursively.
     *
     * @param root the current node being processed.
     */
    private void postorder(TreeNode<E> root) {
        if(root == null) return;
        postorder(root.left);
        postorder(root.right);
        System.out.print(root.element + " ");
    }

    /**
     * Gets an ArrayList containing nodes in the path to e from the root.
     *
     * @param e the element at the end of the path.
     * @return an arraylist containing nodes in the path to e from the root of the tree.
     */
    public ArrayList<TreeNode<E>> path(E e) {
        ArrayList<TreeNode<E>> path = new ArrayList<>();
        TreeNode<E> current = root;
        while(current != null) {
            path.add(current);
            if (e.compareTo(current.element) < 0) current = current.left;
            else if (e.compareTo(current.element) > 0) current = current.right;
            else break;
        }
        return path;
    }

    /**
     * Gets the current size of the tree.
     *
     * @return the number of nodes in the tree.
     */
    @Override
    public int getSize() {
        return this.size;
    }

    /**
     * Checks if the tree is empty.
     *
     * @return true if the size of the tree is 0, otherwise false.
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Returns an in order iterator which can be used to traverse nodes in tree.
     *
     * @return the in order iterator.
     */
    @Override
    public Iterator<E> iterator() {
        return new InOrderIterator();
    }

    /**
     * Represents an in order iterator class which can be used to travers the tree.
     */
    private class InOrderIterator implements Iterator<E> {
        private final Stack<TreeNode<E>> stack = new Stack<>();

        /**
         * Constructs the iterator.
         */
        public InOrderIterator() {
            TreeNode<E> current = root;
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
        }

        /**
         * Checks if the iterator has another element.
         *
         * @return true if there is another element in the iteration, otherwise false.
         */
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /**
         * Gets the next element in the iteration.
         *
         * @return the next element in the iteration.
         */
        @Override
        public E next() {
            TreeNode<E> node = stack.pop();
            E result = node.element;
            if (node.right != null) {
                node = node.right;
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
            return result;
        }
    }
}
