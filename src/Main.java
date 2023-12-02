import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Main driver class for assignment 5.
 */
public class Main {
    private static String fileName = "processList.txt";
    private static final AVLTree<ProcessInfo> tree = new AVLTree<>();

    /**
     * The entry point for the application.
     *
     * @param args may optionally include a fileName to be processed.
     */
    public static void main(String[] args) {
        printIntro();
        initializeFileName(args);
        loadFile();
        System.out.println();
        tree.inorder();
        System.out.println();
        printTreeByLevel();
        System.out.println();
        executeProcesses();
    }

    /**
     * Prints the intro for the assignment.
     */
    private static void printIntro() {
        System.out.println("Submitted by James West - westj4@csp.edu");
        System.out.println("I certify this is my own work\n");
    }

    /**
     * Loads the file and inserts the parsed string into the tree.
     */
    private static void loadFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                ProcessInfo processInfo = new ProcessInfo(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                tree.insert(processInfo);
                System.out.printf("Adding process %s\n", processInfo);
            }
        } catch (IOException e) {
            System.out.println("Error loading data from file: " + fileName + "\n" + e.getMessage());
        }
    }

    /**
     * Grabs the optionally specified file name from args if it exists.
     *
     * @param args optionally contains a filename to be processed.
     */
    private static void initializeFileName(String[] args) {
        if (args.length > 0) {
            fileName = args[0];
        }
    }

    /**
     * Prints the tree level by level.
     */
    private static void printTreeByLevel() {
        if (tree.root == null) return;
        Queue<AVLTree.AVLTreeNode<ProcessInfo>> queue = new LinkedList<>();
        queue.add((AVLTree.AVLTreeNode<ProcessInfo>) tree.root);
        int level = 0;

        while (!queue.isEmpty()) {
            int levelLength = queue.size();
            System.out.println("Level " + level++ + " >");

            for (int i = 0; i < levelLength; i++) {
                AVLTree.AVLTreeNode<ProcessInfo> current = queue.poll();
                System.out.println(current.element);
                if (current.left != null) queue.add((AVLTree.AVLTreeNode<ProcessInfo>) current.left);
                if (current.right != null) queue.add((AVLTree.AVLTreeNode<ProcessInfo>) current.right);
            }
        }
    }

    /**
     * Executes the processes in the tree by priority and prints results.
     */
    private static void executeProcesses() {
        List<ProcessInfo> completedProcessInfos = new ArrayList<>();
        while (tree.size > 0) {
            for (ProcessInfo current : tree) {
                if (current.executeProcess(10 - current.getProcessPriority())) {
                    tree.delete(current);
                    completedProcessInfos.add(current);
                    System.out.println("Process completed: " + current.getProcessName() + " (ID: " + current.getProcessId() + ")");
                }
            }
        }
        System.out.println("Results >");
        completedProcessInfos.forEach(e -> System.out.println(e.displayCompletedInfo()));
    }
}
