package com.example.quizapp.utils;

public class DsaTips {
    public static final String[] TIPS = new String[]{
            "💡 Two-Pointers: When searching for pairs in a sorted array, two-pointers cut time complexity from O(n²) to O(n)!",
            "⚡ Fast & Slow Pointers: Floyd's cycle detection can find both linked-list loops and the middle node in a single pass.",
            "📊 Binary Search on Answer: Whenever a problem asks for min/max value with monotonic validity, think Binary Search!",
            "🌲 Tree Inorder: Inorder traversal of a valid Binary Search Tree (BST) always produces sorted ascending values.",
            "🚀 Sliding Window: Great for problems asking for contiguous subarrays/substrings matching a sum or distinct count condition.",
            "🎯 Monotonic Stack: Finds the Next Greater Element or Next Smaller Element for all items in linear O(n) time.",
            "🔑 Hash Map Tradeoff: O(1) average lookup comes at the cost of O(n) auxiliary memory.",
            "🧠 DP State Tip: If a problem asks for optimal count/min/max with overlapping choices, define a subproblem table!"
    };

    public static String getDailyTip(PreferenceManager pref) {
        int idx = pref.getNextTipIndex(TIPS.length);
        return TIPS[idx];
    }
}
