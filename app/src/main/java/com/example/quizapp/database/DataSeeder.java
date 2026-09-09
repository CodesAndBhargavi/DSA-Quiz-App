package com.example.quizapp.database;

import android.content.Context;
import com.example.quizapp.models.Question;
import com.example.quizapp.models.LeaderboardEntry;
import java.util.ArrayList;
import java.util.List;

public class DataSeeder {

    public static void seedDatabaseIfEmpty(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        if (db.questionDao().getCount() < 40) {
            db.questionDao().deleteAll();
            List<Question> questions = getSeedQuestions();
            db.questionDao().insertAll(questions);
        }

        if (db.leaderboardDao().getAllLeaderboard().isEmpty()) {
            List<LeaderboardEntry> leaderboard = getSeedLeaderboard();
            db.leaderboardDao().insertAll(leaderboard);
        }
    }

    public static List<Question> getSeedQuestions() {
        List<Question> list = new ArrayList<>();

        // ==========================================
        // 1. ARRAYS
        // ==========================================
        list.add(new Question(
                "Arrays", "Basic",
                "What is the average time complexity of accessing an element in an array by its index?",
                "O(1)", "O(n)", "O(log n)", "O(n²)",
                "A",
                "Arrays provide constant time O(1) random access because elements are stored contiguously in memory."
        ));

        list.add(new Question(
                "Arrays", "Basic",
                "In Java, what is the default value of elements in a newly allocated integer array int[] arr = new int[5]?",
                "null", "0", "-1", "Garbage Value",
                "B",
                "In Java, numeric primitive arrays are automatically initialized to zero by default."
        ));

        list.add(new Question(
                "Arrays", "Medium",
                "Which algorithm finds the maximum subarray sum in an array in linear O(n) time complexity?",
                "Dijkstra's Algorithm", "Kruskal's Algorithm", "Kadane's Algorithm", "Floyd-Warshall",
                "C",
                "Kadane's algorithm computes the maximum contiguous subarray sum in linear O(n) time by maintaining running sum."
        ));

        list.add(new Question(
                "Arrays", "Medium",
                "Given a sorted array of size N, what is the time complexity to search for a target element using Binary Search?",
                "O(n)", "O(n²)", "O(n log n)", "O(log n)",
                "D",
                "Binary Search halves the search interval at each step, yielding O(log n) time complexity."
        ));

        list.add(new Question(
                "Arrays", "Hard",
                "In the 'Trapping Rain Water' problem with elevation bars, what is the optimal two-pointer auxiliary space complexity?",
                "O(1)", "O(n)", "O(log n)", "O(n log n)",
                "A",
                "The two-pointer technique maintains leftMax and rightMax in place, requiring only O(1) auxiliary space."
        ));

        list.add(new Question(
                "Arrays", "Hard",
                "Which algorithm finds the majority element (> n/2 occurrences) in an array in O(n) time and O(1) space?",
                "QuickSelect", "Boyer-Moore Voting Algorithm", "Hash Map Counter", "Binary Lifting",
                "B",
                "Boyer-Moore Voting Algorithm maintains a candidate and count, finding the majority element in O(1) extra memory."
        ));

        // ==========================================
        // 2. LINKED LIST
        // ==========================================
        list.add(new Question(
                "LinkedList", "Basic",
                "What is the time complexity to insert a new node at the beginning (head) of a singly linked list?",
                "O(n)", "O(n²)", "O(1)", "O(log n)",
                "C",
                "Inserting at the head simply adjusts the new node's next pointer and head pointer in O(1) time."
        ));

        list.add(new Question(
                "LinkedList", "Basic",
                "Unlike arrays, what is the primary advantage of a Linked List?",
                "Faster random index access", "Better CPU cache locality", "Zero pointer overhead", "Dynamic sizing with efficient insertions and deletions",
                "D",
                "Linked lists do not require contiguous memory and allow insertions/deletions without shifting elements."
        ));

        list.add(new Question(
                "LinkedList", "Medium",
                "Which algorithm is commonly used to detect a cycle in a Linked List using slow and fast pointers?",
                "Floyd's Tortoise and Hare", "Prim's Algorithm", "Binary Search", "KMP Algorithm",
                "A",
                "Floyd's Cycle-Finding algorithm uses slow and fast pointers to detect loops in O(n) time and O(1) space."
        ));

        list.add(new Question(
                "LinkedList", "Medium",
                "How can you reverse a singly linked list iteratively in a single pass?",
                "Using two stacks", "Using 3 pointers (prev, curr, next)", "Swapping head and tail nodes", "Using recursion only",
                "B",
                "Using three pointers allows reversing pointers in-place in a single O(n) pass."
        ));

        list.add(new Question(
                "LinkedList", "Hard",
                "When merging K sorted linked lists with N total nodes, what is the optimal time complexity using a Min-Heap?",
                "O(N * K)", "O(N²)", "O(N log K)", "O(K log N)",
                "C",
                "A Min-Heap of size K allows extracting the minimum and inserting the next node in O(log K) per node, totaling O(N log K)."
        ));

        list.add(new Question(
                "LinkedList", "Hard",
                "In an LRU Cache design, which two data structures are combined to achieve O(1) get and put operations?",
                "Binary Tree & Stack", "Queue & Array", "Min-Heap & Trie", "Doubly Linked List & HashMap",
                "D",
                "A Doubly Linked List maintains access order with O(1) removals while HashMap provides O(1) key lookups."
        ));

        // ==========================================
        // 3. STACKS & QUEUES
        // ==========================================
        list.add(new Question(
                "StacksQueues", "Basic",
                "Which data structure follows the LIFO (Last-In, First-Out) principle?",
                "Stack", "Queue", "Binary Tree", "Hash Table",
                "A",
                "A Stack adds and removes elements from the top, obeying the Last-In First-Out order."
        ));

        list.add(new Question(
                "StacksQueues", "Basic",
                "Which data structure follows the FIFO (First-In, First-Out) principle?",
                "Stack", "Queue", "PriorityQueue", "Max-Heap",
                "B",
                "A standard Queue inserts elements at the rear and removes from the front, obeying First-In First-Out."
        ));

        list.add(new Question(
                "StacksQueues", "Medium",
                "Which data structure is ideal for checking balanced parentheses in an expression?",
                "PriorityQueue", "Graph", "Stack", "Queue",
                "C",
                "A Stack allows matching the most recently opened bracket with its corresponding closing bracket."
        ));

        list.add(new Question(
                "StacksQueues", "Medium",
                "How can a Queue be implemented using two Stacks with amortized O(1) operations?",
                "By sorting the stack", "By using recursive calls", "By using an auxiliary array", "By using Stack 1 for Enqueue and Stack 2 for Dequeue",
                "D",
                "Elements are pushed to Stack 1, and popped from Stack 2. When Stack 2 is empty, Stack 1 is dumped into Stack 2 in O(1) amortized time."
        ));

        list.add(new Question(
                "StacksQueues", "Hard",
                "In the 'Sliding Window Maximum' problem of size K in an array of size N, what optimal data structure achieves O(N) time?",
                "Monotonic Deque", "Max-Heap", "Binary Search Tree", "Stack",
                "A",
                "A Monotonic Deque stores indices of useful elements in decreasing order, processing each index in amortized O(1) time."
        ));

        list.add(new Question(
                "StacksQueues", "Hard",
                "In the 'Largest Rectangle in Histogram' problem, what data structure yields an optimal O(n) solution?",
                "Two Pointers", "Monotonic Stack", "Trie", "Disjoint Set Union",
                "B",
                "A Monotonic Stack maintains increasing heights to find the nearest smaller element to the left and right in O(n) time."
        ));

        // ==========================================
        // 4. TREES
        // ==========================================
        list.add(new Question(
                "Trees", "Basic",
                "What is the maximum number of children a node can have in a Binary Tree?",
                "3", "Unlimited", "2", "1",
                "C",
                "By definition, each node in a binary tree has at most 2 child nodes (left and right)."
        ));

        list.add(new Question(
                "Trees", "Basic",
                "Which tree traversal outputs the keys of a Binary Search Tree (BST) in strictly ascending sorted order?",
                "Preorder Traversal", "Postorder Traversal", "Level-order Traversal", "Inorder Traversal",
                "D",
                "Inorder traversal (Left, Root, Right) of a BST visits nodes in non-decreasing sorted order."
        ));

        list.add(new Question(
                "Trees", "Medium",
                "What is the worst-case search time complexity in an unbalanced skewed Binary Search Tree with N nodes?",
                "O(n)", "O(log n)", "O(1)", "O(n log n)",
                "A",
                "A completely skewed BST degenerates into a linked list where searching takes linear O(n) time."
        ));

        list.add(new Question(
                "Trees", "Medium",
                "What is the height balance condition for an AVL Tree?",
                "Left subtree height equals right subtree", "Balance factor difference between left and right subtrees is at most 1", "All leaf nodes must be at the exact same level", "Tree must be a complete binary tree",
                "B",
                "An AVL Tree is a self-balancing BST where the height difference between left and right subtrees of any node is at most 1."
        ));

        list.add(new Question(
                "Trees", "Hard",
                "What is the time complexity of Lowest Common Ancestor (LCA) queries after preprocessing a tree with Binary Lifting?",
                "O(n)", "O(n²)", "O(log n)", "O(1)",
                "C",
                "Binary Lifting jumps up powers of 2 (2^k parents), resolving LCA queries in O(log n) time."
        ));

        list.add(new Question(
                "Trees", "Hard",
                "What is the time complexity to construct a Segment Tree for an array of size N?",
                "O(N log N)", "O(N²)", "O(log N)", "O(N)",
                "D",
                "Building a Segment Tree processes 2N-1 nodes in a bottom-up manner, which takes linear O(N) time."
        ));

        // ==========================================
        // 5. GRAPHS
        // ==========================================
        list.add(new Question(
                "Graphs", "Basic",
                "Which graph traversal explores all neighbor vertices at the current depth before moving deeper?",
                "Breadth-First Search (BFS)", "Depth-First Search (DFS)", "Binary Search", "Topological Sort",
                "A",
                "BFS explores vertices level by level using a Queue data structure."
        ));

        list.add(new Question(
                "Graphs", "Basic",
                "Which graph traversal explores as deep as possible along each branch before backtracking?",
                "Breadth-First Search (BFS)", "Depth-First Search (DFS)", "Linear Search", "Prim's Algorithm",
                "B",
                "DFS explores deep into the graph using recursion or an explicit Stack."
        ));

        list.add(new Question(
                "Graphs", "Medium",
                "Which algorithm finds the shortest path from a single source in a weighted graph with non-negative edge weights?",
                "Kruskal's Algorithm", "Floyd-Warshall", "Dijkstra's Algorithm", "Kosaraju's Algorithm",
                "C",
                "Dijkstra's algorithm finds single-source shortest paths in O((V + E) log V) with non-negative weights."
        ));

        list.add(new Question(
                "Graphs", "Medium",
                "In an unweighted directed graph, which algorithm determines if a valid Topological Ordering exists using in-degrees?",
                "Prim's Algorithm", "Bellman-Ford", "Dijkstra's Algorithm", "Kahn's Algorithm",
                "D",
                "Kahn's Algorithm uses in-degrees and a Queue (BFS) to produce a topological sort and detect cycles in DAGs."
        ));

        list.add(new Question(
                "Graphs", "Hard",
                "Which algorithm detects negative weight cycles in a directed graph?",
                "Bellman-Ford Algorithm", "Dijkstra's Algorithm", "Kruskal's Algorithm", "Prim's Algorithm",
                "A",
                "Bellman-Ford relaxes all edges V-1 times and can detect negative cycles if a relaxation still occurs on the V-th step."
        ));

        list.add(new Question(
                "Graphs", "Hard",
                "What is the time complexity of the Floyd-Warshall all-pairs shortest path algorithm for V vertices?",
                "O(V² log V)", "O(V³)", "O(V + E)", "O(V² E)",
                "B",
                "Floyd-Warshall uses three nested loops over V vertices, resulting in O(V³) time complexity."
        ));

        // ==========================================
        // 6. SORTING & SEARCHING
        // ==========================================
        list.add(new Question(
                "Sorting", "Basic",
                "What is the best-case time complexity of standard Binary Search on a sorted array?",
                "O(n)", "O(log n)", "O(1)", "O(n log n)",
                "C",
                "When the target element is at the exact middle index on the first check, Binary Search finishes in O(1) time."
        ));

        list.add(new Question(
                "Sorting", "Basic",
                "Which sorting algorithm repeatedly finds the minimum element from the unsorted part and puts it at the beginning?",
                "Bubble Sort", "Insertion Sort", "Quick Sort", "Selection Sort",
                "D",
                "Selection Sort finds the minimum element in each pass and swaps it with the current position."
        ));

        list.add(new Question(
                "Sorting", "Medium",
                "Which sorting algorithm has a guaranteed worst-case time complexity of O(n log n) and is stable?",
                "Merge Sort", "Quick Sort", "Heap Sort", "Selection Sort",
                "A",
                "Merge Sort guarantees O(n log n) in all cases (best, average, worst) and preserves the relative order of equal elements (stable)."
        ));

        list.add(new Question(
                "Sorting", "Medium",
                "What is the worst-case time complexity of QuickSort when the chosen pivot is always the smallest or largest element?",
                "O(n log n)", "O(n²)", "O(n)", "O(log n)",
                "B",
                "When poorly partitioned, QuickSort degenerates into recursive subproblems of size n-1, leading to O(n²) time."
        ));

        list.add(new Question(
                "Sorting", "Hard",
                "What is the average time complexity of QuickSelect to find the K-th smallest element in an unsorted array?",
                "O(n log n)", "O(n²)", "O(n)", "O(log n)",
                "C",
                "QuickSelect recurses only into the partition containing K, yielding an average linear time complexity of O(n)."
        ));

        list.add(new Question(
                "Sorting", "Hard",
                "Which non-comparison sorting algorithm runs in O(n + k) time for integers in the range [0, k]?",
                "Merge Sort", "Heap Sort", "Quick Sort", "Counting Sort",
                "D",
                "Counting Sort counts occurrences of each value to place them in sorted order in O(n + k) time."
        ));

        // ==========================================
        // 7. DYNAMIC PROGRAMMING
        // ==========================================
        list.add(new Question(
                "DP", "Basic",
                "What are the two essential properties required to solve a problem with Dynamic Programming?",
                "Optimal Substructure & Overlapping Subproblems", "Greedy Choice & Independence", "Divide and Conquer & Sorting", "Randomization & Hashing",
                "A",
                "DP solves problems that exhibit both Optimal Substructure (optimal sub-solutions build the global solution) and Overlapping Subproblems."
        ));

        list.add(new Question(
                "DP", "Basic",
                "What is the top-down optimization technique of caching recursive function results called?",
                "Tabulation", "Memoization", "Backtracking", "Branch and Bound",
                "B",
                "Memoization stores previous function return values to avoid redundant recomputations in top-down recursion."
        ));

        list.add(new Question(
                "DP", "Medium",
                "In the classic 0/1 Knapsack problem with N items and capacity W, what is the standard DP time complexity?",
                "O(2ⁿ)", "O(N + W)", "O(N * W)", "O(N log W)",
                "C",
                "The 2D/1D DP table iterates through N items for capacities up to W, requiring pseudo-polynomial O(N*W) time."
        ));

        list.add(new Question(
                "DP", "Medium",
                "What is the optimal time complexity to compute the N-th Fibonacci number using Matrix Exponentiation?",
                "O(N)", "O(2ⁿ)", "O(N²)", "O(log N)",
                "D",
                "Raising the Fibonacci transformation matrix to power (N-1) using binary exponentiation runs in O(log N) time."
        ));

        list.add(new Question(
                "DP", "Hard",
                "What is the time complexity of the Longest Increasing Subsequence (LIS) problem using Binary Search (Patience Sorting)?",
                "O(n log n)", "O(n²)", "O(n³)", "O(2ⁿ)",
                "A",
                "Maintaining an array of tails and performing binary search for insertion achieves O(n log n) time complexity."
        ));

        list.add(new Question(
                "DP", "Hard",
                "In the Matrix Chain Multiplication problem for N matrices, what is the standard DP time complexity?",
                "O(N²)", "O(N³)", "O(2ⁿ)", "O(N log N)",
                "B",
                "Matrix Chain Multiplication tests all possible split points k for each subarray length, taking O(N³) time."
        ));

        // ==========================================
        // 8. TIME COMPLEXITY
        // ==========================================
        list.add(new Question(
                "TimeComplexity", "Basic",
                "What Big-O complexity represents constant time execution independent of input size?",
                "O(n)", "O(log n)", "O(1)", "O(n!)",
                "C",
                "O(1) signifies that the execution time is independent of the input size N."
        ));

        list.add(new Question(
                "TimeComplexity", "Basic",
                "If an algorithm halves the search space at each iteration, what is its asymptotic time complexity?",
                "O(n)", "O(n²)", "O(1)", "O(log n)",
                "D",
                "Halving the problem size at each step (e.g. Binary Search) requires log2(N) steps, giving O(log n)."
        ));

        list.add(new Question(
                "TimeComplexity", "Medium",
                "According to the Master Theorem, what is the time complexity of T(n) = 2T(n/2) + O(n)?",
                "O(n log n)", "O(n²)", "O(n)", "O(log n)",
                "A",
                "Since a=2, b=2, and f(n)=O(n), n^(log_b a) = n^1, this matches Case 2 of the Master Theorem giving O(n log n)."
        ));

        list.add(new Question(
                "TimeComplexity", "Medium",
                "What is the average time complexity of insertion and lookup in a Hash Table with good hash distribution?",
                "O(n)", "O(1)", "O(log n)", "O(n log n)",
                "B",
                "Under uniform hashing assumptions, Hash Tables execute lookups and inserts in amortized O(1) time."
        ));

        list.add(new Question(
                "TimeComplexity", "Hard",
                "What is the time complexity of the Traveling Salesperson Problem (TSP) using Held-Karp Dynamic Programming with bitmasking?",
                "O(n!)", "O(2ⁿ)", "O(n² * 2ⁿ)", "O(n³)",
                "C",
                "Held-Karp algorithm uses bitmask states DP(mask, u) for 2^n subsets and n vertices, running in O(n² * 2ⁿ) time."
        ));

        list.add(new Question(
                "TimeComplexity", "Hard",
                "What is the time complexity of finding all Strongly Connected Components (SCCs) in a directed graph using Tarjan's Algorithm?",
                "O(V²)", "O(V * E)", "O(V³)", "O(V + E)",
                "D",
                "Tarjan's SCC algorithm performs a single DFS traversal visiting every vertex and edge once, running in O(V + E) time."
        ));

        return list;
    }

    public static List<LeaderboardEntry> getSeedLeaderboard() {
        List<LeaderboardEntry> list = new ArrayList<>();
        list.add(new LeaderboardEntry("Alex Rivera", 980, "🦁", 1, false));
        list.add(new LeaderboardEntry("Sophia Chen", 920, "🦊", 2, false));
        list.add(new LeaderboardEntry("Marcus Vance", 850, "🚀", 3, false));
        list.add(new LeaderboardEntry("Elena Rostova", 780, "⚡", 4, false));
        list.add(new LeaderboardEntry("David Kim", 720, "🎯", 5, false));
        list.add(new LeaderboardEntry("You (Learner)", 450, "⭐", 6, true));
        list.add(new LeaderboardEntry("Aarav Patel", 410, "🐼", 7, false));
        list.add(new LeaderboardEntry("Zoe Martin", 350, "🌸", 8, false));
        return list;
    }
}
