import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class represents a generic AVL Tree which is a type of self-balancing binary search tree.
 * @param <E>
 */
public class AVLTree<E extends Comparable<E>> extends BST<E> {
    /**
     * Default constructor
     */
    public AVLTree() {}

    /**
     * Constructor to initialize an AVL tree with an array of elements.
     *
     * @param objects The array of elements representing nodes in the tree.
     */
    public AVLTree(E[] objects) {
        Arrays.stream(objects).forEach(this::insert);
    }

    /**
     * Creates a new Node in the AVL Tree.
     *
     * @param e the element to be included in the new node.
     * @return a new AVLTreeNode containing the element.
     */
    @Override
    protected TreeNode<E> createNewNode(E e) {
        return new AVLTreeNode<E>(e);
    }

    /**
     * Inserts a node into the AVL tree and balances the tree after insertion.
     *
     * @param e the element to be inserted.
     * @return true if the insertion is successful, otherwise false.
     */
    @Override
    public boolean insert(E e) {
        boolean isSuccessful = super.insert(e);
        if(isSuccessful) balancePath(e);
        return isSuccessful;
    }

    /**
     * Updates the height of a given AVL tree Node.
     *
     * @param node the node whose height is to be updated.
     */
    private void updateHeight(AVLTreeNode<E> node) {
        if(node.left == null && node.right == null) node.height = 0;
        else if(node.left == null) node.height = ((AVLTreeNode<E>)node.right).height + 1;
        else if(node.right == null) node.height = ((AVLTreeNode<E>)node.left).height + 1;
        else node.height = Math.max(((AVLTreeNode<E>)node.right).height, ((AVLTreeNode<E>)node.left).height) + 1;
    }


    /**
     * Balances the AVL tree along the path from a specified node to the root.
     *
     * @param e the element in the AVL tree from where the balancing starts.
     */
    private void balancePath(E e) {
        ArrayList<TreeNode<E>> path = path(e);

        for(int i = path.size() - 1; i >= 0; i--) {
            AVLTreeNode<E> A = (AVLTreeNode<E>)path.get(i);
            updateHeight(A);
            AVLTreeNode<E> parentOfA = (A == root) ? null : (AVLTreeNode<E>)(path.get(i - 1));

            switch (balanceFactor(A)) {
                case -2:
                    if(balanceFactor((AVLTreeNode<E>)A.left) <= 0) balanceLL(A, parentOfA);
                    else balanceLR(A, parentOfA);
                    break;
                case 2:
                    if(balanceFactor((AVLTreeNode<E>)A.right) >= 0) balanceRR(A, parentOfA);
                    else balanceRL(A, parentOfA);
            }
        }
    }

    /**
     * Performs a LL balance at given node A.
     * This occurs when A has a balance factor of -2 and A.left has a balance factor of less than or equal to 0.
     *
     * @param A the node with a balance factor of -2.
     * @param parentOfA the parent of node A.
     */
    private void balanceLL(TreeNode<E> A, TreeNode<E> parentOfA) {
        TreeNode<E> B = A.left;

        if(A == root) root = B;
        else {
            if(parentOfA.left == A) parentOfA.left = B;
            else parentOfA.right = B;
        }
        A.left = B.right;
        B.right = A;
        updateHeight((AVLTreeNode<E>) A);
        updateHeight((AVLTreeNode<E>) B);
    }

    /**
     * Performs a LR balance rotation at a given node A.
     * This occurs when A has a balance factor of -2 and A.left has a balance factor greater than 0.
     *
     * @param A the node with a balance factor of -2.
     * @param parentOfA the parent of node A.
     */
    private void balanceLR(TreeNode<E> A, TreeNode<E> parentOfA) {
        TreeNode<E> B = A.left;
        TreeNode<E> C = B.right;

        if(A == root) root = C;
        else {
            if(parentOfA.left == A) parentOfA.left = C;
            else parentOfA.right = C;
        }
        A.left = C.right;
        B.right = C.left;
        C.left = B;
        C.right = A;

        updateHeight((AVLTreeNode<E>) A);
        updateHeight((AVLTreeNode<E>) B);
        updateHeight((AVLTreeNode<E>) C);
    }

    /**
     * Performs a RR balance rotation at a given node A.
     * This occurs when A has a balance factor of +2 and A.right has a balance factor greater than or equal to 0.
     *
     * @param A the node with a balance factor of +2.
     * @param parentOfA the parent of node A.
     */
    private void balanceRR(TreeNode<E> A, TreeNode<E> parentOfA) {
        TreeNode<E> B = A.right;

        if(A == root) root = B;
        else {
            if(parentOfA.left == A) parentOfA.left = B;
            else parentOfA.right = B;
        }
        A.right = B.left;
        B.left = A;
        updateHeight((AVLTreeNode<E>) A);
        updateHeight((AVLTreeNode<E>) B);
    }

    /**
     * Performs a RL balance rotation at a given node A.
     * This occurs when A has a balance factor of +2 and A.right has a balance factor less than 0.
     *
     * @param A the node with a balance factor of +2.
     * @param parentOfA the parent of node A.
     */
    private void balanceRL(TreeNode<E> A, TreeNode<E> parentOfA) {
        TreeNode<E> B = A.right;
        TreeNode<E> C = B.left;

        if(A == root) root = C;
        else {
            if(parentOfA.left == A) parentOfA.left = C;
            else parentOfA.right = C;
        }
        A.right = C.left;
        B.left = C.right;
        C.left = A;
        C.right = B;
        updateHeight((AVLTreeNode<E>) A);
        updateHeight((AVLTreeNode<E>) B);
        updateHeight((AVLTreeNode<E>) C);
    }

    /**
     * Calculates the balance factor of a given node based on its height and the height of its children.
     *
     * @param node the node at which to calculate the balance factor.
     * @return the balance factor at the given node.
     */
    private int balanceFactor(AVLTreeNode<E> node) {
        if (node.right == null) return -node.height;
        else if (node.left == null) return +node.height;
        else return ((AVLTreeNode<E>)node.right).height - ((AVLTreeNode<E>)node.left).height;
    }

    /**
     * Deletes a node from the tree and re-balances the tree if needed.
     *
     * @param element the element to be deleted.
     * @return true if the element is found and successfully deleted, otherwise false.
     */
    @Override
    public boolean delete(E element) {
        if(root == null) return false;
        TreeNode<E> parent = null;
        TreeNode<E> current = root;

        while(current != null) {
            if(element.compareTo(current.element) < 0) {
                parent = current;
                current = current.left;
            } else if (element.compareTo(current.element) > 0) {
                parent = current;
                current = current.right;
            } else break;
        }

        if(current == null) return false;
        if(current.left == null) {
            if(parent == null) root = current.right;
            else {
                if(element.compareTo(parent.element) < 0) parent.left = current.right;
                else parent.right = current.right;
                balancePath(parent.element);
            }
        } else {
            TreeNode<E> parentOfRightMost = current;
            TreeNode<E> rightMost = current.left;

            while (rightMost.right != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right;
            }
            current.element = rightMost.element;
            if (parentOfRightMost.right == rightMost) parentOfRightMost.right = rightMost.left;
            else parentOfRightMost.left = rightMost.left;
            balancePath(parentOfRightMost.element);
        }
        size--;
        return true;
    }

    /**
     * Represents a TreeNode with a height (used for balancing the tree).
     *
     * @param <E> The type of element stored in the node.
     */
    protected static class AVLTreeNode<E> extends TreeNode<E> {
        protected int height;
        protected AVLTreeNode(E e){
            super(e);
        }
    }
}
