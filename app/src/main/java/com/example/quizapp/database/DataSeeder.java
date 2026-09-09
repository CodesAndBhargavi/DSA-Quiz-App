package com.example.quizapp.database;

import android.content.Context;
import com.example.quizapp.models.Question;
import com.example.quizapp.models.LeaderboardEntry;
import java.util.ArrayList;
import java.util.List;

public class DataSeeder {

    public static void seedDatabaseIfEmpty(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        if (db.questionDao().getCount() == 0) {
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

        // --- ARRAYS ---
        list.add(new Question(
                "Arrays", "Basic",
                "What is the average time complexity of accessing an element in an array by its index?",
                "O(1)", "O(n)", "O(log n)", "O(n²)",
                "A",
                "Arrays provide constant time O(1) random access because elements are stored contiguously in memory."
        ));

        list.add(new Question(
                "Arrays", "Medium",
                "Which algorithm finds the maximum subarray sum in an array in O(n) time complexity?",
                "Kadane's Algorithm", "Dijkstra's Algorithm", "Floyd-Warshall", "Kruskal's Algorithm",
                "A",
                "Kadane's algorithm computes the maximum contiguous subarray sum in linear O(n) time."
        ));

        list.add(new Question(
                "Arrays", "Hard",
                "In the 'Trapping Rain Water' problem with elevation bars, what is the optimal two-pointer space complexity?",
                "O(1)", "O(n)", "O(log n)", "O(n log n)",
                "A",
                "Using the two-pointer technique maintains leftMax and rightMax in place, requiring only O(1) auxiliary space."
        ));

        // --- LINKED LIST ---
        list.add(new Question(
                "LinkedList", "Basic",
                "What is the time complexity to insert a new node at the beginning (head) of a singly linked list?",
                "O(1)", "O(n)", "O(log n)", "O(n²)",
                "A",
                "Inserting at the head simply adjusts the new node's next pointer and head pointer in O(1) time."
        ));

        list.add(new Question(
                "LinkedList", "Medium",
                "Which algorithm is commonly used to detect a cycle in a Linked List?",
                "Floyd's Tortoise and Hare", "Binary Search", "Kadane's Algorithm", "Prim's Algorithm",
                "A",
                "Floyd's Cycle-Finding algorithm uses slow and fast pointers to detect loops in O(n) time and O(1) space."
        ));

        list.add(new Question(
                "LinkedList", "Hard",
                "When merging K sorted linked lists with N total nodes, what is the optimal time complexity using a Min-Heap?",
                "O(N log K)", "O(N * K)", "O(N²)", "O(K log N)",
                "A",
                "A Min-Heap of size K allows extracting the minimum and inserting the next node in O(log K) per node, totaling O(N log K)."
        ));

        // --- STACKS & QUEUES ---
        list.add(new Question(
                "StacksQueues", "Basic",
                "Which data structure follows the LIFO (Last-In, First-Out) principle?",
                "Stack", "Queue", "Binary Tree", "Hash Table",
                "A",
                "A Stack adds and removes elements from the top, obeying the Last-In First-Out order."
        ));

        list.add(new Question(
                "StacksQueues", "Medium",
                "Which data structure is ideal for checking balanced parentheses in an expression?",
                "Stack", "Queue", "PriorityQueue", "Graph",
                "A",
                "A Stack allows matching the most recently opened bracket with its corresponding closing bracket."
        ));

        list.add(new Question(
                "StacksQueues", "Hard",
                "In the 'Sliding Window Maximum' problem of size K in an array of size N, what optimal data structure achieves O(N) time?",
                "Monotonic Deque", "Max-Heap", "Binary Search Tree", "Stack",
                "A",
                "A Monotonic Deque stores indices of useful elements in decreasing order, processing each index in amortized O(1) time."
        ));

        // --- TREES ---
        list.add(new Question(
                "Trees", "Basic",
                "What is the maximum number of children a node can have in a Binary Tree?",
                "2", "1", "3", "Unlimited",
                "A",
                "By definition, each node in a binary tree has at most 2 child nodes (left and right)."
        ));

        list.add(new Question(
                "Trees", "Medium",
                "Which tree traversal outputs the keys of a Binary Search Tree (BST) in strictly ascending sorted order?",
                "Inorder Traversal", "Preorder Traversal", "Postorder Traversal", "Level-order Traversal",
                "A",
                "Inorder traversal (Left, Root, Right) of a BST visits nodes in non-decreasing sorted order."
        ));

        list.add(new Question(
                "Trees", "Hard",
                "What is the maximum height of an AVL tree with N nodes?",
                "O(log N)", "O(N)", "O(N log N)", "O(√N)",
                "A",
                "AVL trees are strictly height-balanced BSTs where the height difference is at most 1, guaranteeing O(log N) height."
        ));

        // --- GRAPHS ---
        list.add(new Question(
                "Graphs", "Basic",
                "Which graph traversal explores all neighbor vertices at the current depth before moving deeper?",
                "Breadth-First Search (BFS)", "Depth-First Search (DFS)", "Binary Search", "Linear Search",
                "A",
                "BFS explores vertices level by level using a Queue data structure."
        ));

        list.add(new Question(
                "Graphs", "Medium",
                "Dijkstra's Algorithm is used to find what in a weighted graph without negative weights?",
                "Shortest Path from a single source", "Minimum Spanning Tree", "Topological Sort", "Graph Coloring",
                "A",
                "Dijkstra's algorithm finds the shortest path from a source vertex to all other vertices with non-negative edge weights."
        ));

        list.add(new Question(
                "Graphs", "Hard",
                "Which algorithm detects negative weight cycles in a directed graph?",
                "Bellman-Ford Algorithm", "Dijkstra's Algorithm", "Kruskal's Algorithm", "Prim's Algorithm",
                "A",
                "Bellman-Ford relaxes all edges V-1 times and can detect negative cycles if a relaxation still occurs on the V-th step."
        ));

        // --- SORTING & SEARCHING ---
        list.add(new Question(
                "Sorting", "Basic",
                "What is the best-case time complexity of standard Binary Search on a sorted array?",
                "O(1)", "O(log n)", "O(n)", "O(n log n)",
                "A",
                "When the target element is at the exact middle index on the first check, Binary Search finishes in O(1) time."
        ));

        list.add(new Question(
                "Sorting", "Medium",
                "Which sorting algorithm has a worst-case time complexity of O(n log n) and is stable?",
                "Merge Sort", "Quick Sort", "Heap Sort", "Selection Sort",
                "A",
                "Merge Sort guarantees O(n log n) in all cases (best, average, worst) and preserves the relative order of equal elements (stable)."
        ));

        list.add(new Question(
                "Sorting", "Hard",
                "What is the average time complexity of QuickSelect to find the K-th smallest element in an unsorted array?",
                "O(n)", "O(n log n)", "O(n²)", "O(log n)",
                "A",
                "QuickSelect recurses only into the partition containing K, yielding an average time complexity of O(n)."
        ));

        // --- DYNAMIC PROGRAMMING ---
        list.add(new Question(
                "DP", "Basic",
                "What are the two essential properties required to solve a problem with Dynamic Programming?",
                "Optimal Substructure & Overlapping Subproblems", "Greedy Choice & Independence", "Divide and Conquer & Sorting", "Randomization & Hashing",
                "A",
                "DP solves problems that exhibit both Optimal Substructure (optimal sub-solutions build the global solution) and Overlapping Subproblems."
        ));

        list.add(new Question(
                "DP", "Medium",
                "In the classic 0/1 Knapsack problem with N items and capacity W, what is the standard DP time complexity?",
                "O(N * W)", "O(2^N)", "O(N + W)", "O(N log W)",
                "A",
                "The 2D/1D DP table iterates through N items for capacities up to W, requiring pseudo-polynomial O(N*W) time."
        ));

        list.add(new Question(
                "DP", "Hard",
                "What is the time complexity of the Longest Increasing Subsequence (LIS) problem using Binary Search (Patience Sorting)?",
                "O(n log n)", "O(n²)", "O(n³)", "O(2ⁿ)",
                "A",
                "Maintaining an array of tails and performing binary search for insertion achieves O(n log n) time complexity."
        ));

        // --- TIME COMPLEXITY ---
        list.add(new Question(
                "TimeComplexity", "Basic",
                "What Big-O complexity represents constant time execution?",
                "O(1)", "O(n)", "O(log n)", "O(n!)",
                "A",
                "O(1) signifies that the execution time is independent of the input size N."
        ));

        list.add(new Question(
                "TimeComplexity", "Medium",
                "According to the Master Theorem, what is the time complexity of T(n) = 2T(n/2) + O(n)?",
                "O(n log n)", "O(n²)", "O(n)", "O(log n)",
                "A",
                "Since a=2, b=2, and f(n)=O(n), n^(log_b a) = n^1, this matches Case 2 of the Master Theorem giving O(n log n)."
        ));

        list.add(new Question(
                "TimeComplexity", "Hard",
                "What is the time complexity of the Traveling Salesperson Problem (TSP) using Held-Karp Dynamic Programming with bitmasking?",
                "O(n² * 2ⁿ)", "O(n!)", "O(2ⁿ)", "O(n³)",
                "A",
                "Held-Karp algorithm uses bitmask states DP(mask, u) for 2^n subsets and n vertices, running in O(n² * 2ⁿ) time."
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
