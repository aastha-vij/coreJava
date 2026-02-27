# SDET Coding Interview Questions

> Questions only — no solutions. Use these to practise. Refer to your solution files in `src/test/java/Java/` for answers.

---

## Table of Contents
1. [Arrays](#1-arrays)
2. [Strings](#2-strings)
3. [HashMap & HashSet](#3-hashmap--hashset)
4. [Linked List](#4-linked-list)
5. [Stack & Queue](#5-stack--queue)
6. [Trees & Graphs](#6-trees--graphs)
7. [Sorting & Searching](#7-sorting--searching)
8. [Recursion & Backtracking](#8-recursion--backtracking)
9. [Dynamic Programming](#9-dynamic-programming)
10. [Two Pointers & Sliding Window](#10-two-pointers--sliding-window)
11. [Bit Manipulation](#11-bit-manipulation)
12. [Math & Numbers](#12-math--numbers)
13. [Mixed / Hard](#13-mixed--hard)

---

## 1. Arrays

**Easy**
1. Find the largest and smallest element in an array.
2. Reverse an array in place.
3. Find the second largest element in an array (without sorting).
4. Check if an array is sorted.
5. Remove duplicates from a sorted array.
6. Move all zeros to the end of the array maintaining relative order.
7. Find the sum of all elements in an array.
8. Find the missing number in an array of 1 to n.
9. Rotate an array to the right by k steps.
10. Check if two arrays are equal.
11. Find the intersection of two arrays.
12. Find the union of two arrays.
13. Count the frequency of each element in an array.
14. Find if the target sum exists in a pair of elements (Two Sum).
15. Find the maximum product of two integers in an array.

**Medium**
16. Find all duplicates in an array (numbers in range 1 to n).
17. Rearrange array in alternating positive and negative items.
18. Find the maximum subarray sum (Kadane's Algorithm).
19. Find the contiguous subarray with the maximum product.
20. Sort an array of 0s, 1s and 2s without using sort (Dutch National Flag).
21. Find all triplets that sum to zero (3Sum).
22. Find indices of two numbers that add up to a target (Two Sum – return indices).
23. Given an array of intervals, merge overlapping intervals.
24. Find the minimum and maximum in an array using fewest comparisons.
25. Rotate an n×n matrix 90 degrees clockwise in-place.
26. Find the longest consecutive sequence in an unsorted array.
27. Given an array of stock prices, find the maximum profit (buy low, sell high, one transaction).
28. Trap rainwater — given heights, compute total trapped water.
29. Find the majority element (appears more than n/2 times) — Boyer-Moore.
30. Jump Game — given array of jump lengths, can you reach the last index?

**Hard**
31. Find all permutations of an array.
32. Given n numbers in range 1 to n², find all duplicates.
33. First missing positive integer in an unsorted array (O(n) time, O(1) space).
34. Maximum sum of a subarray of size k (Sliding Window).
35. Find the kth largest element in an unsorted array.

---

## 2. Strings

**Easy**
1. Reverse a string.
2. Check if a string is a palindrome.
3. Count vowels and consonants in a string.
4. Remove all whitespace from a string.
5. Check if two strings are anagrams.
6. Find the first non-repeating character in a string.
7. Convert a string to uppercase/lowercase without built-in methods.
8. Count occurrences of a character in a string.
9. Remove duplicate characters from a string.
10. Check if a string contains only digits.
11. Find the most frequent character.
12. Reverse words in a sentence.
13. Find the length of the longest word in a sentence.
14. Check if a string is a rotation of another.
15. Compress a string (run-length encoding: "aaabbb" → "a3b3").

**Medium**
16. Find all permutations of a string.
17. Find the longest substring without repeating characters.
18. Find all substrings of a string.
19. Check if a string is a valid palindrome ignoring non-alphanumeric characters.
20. Group anagrams from an array of strings.
21. Find the longest palindromic substring.
22. Implement `strStr()` — find index of first occurrence of needle in haystack.
23. Check if one string is a subsequence of another.
24. Count and say problem — generate nth term.
25. Find the minimum window substring that contains all characters of a pattern.
26. Given a string, find the longest repeating subsequence.
27. Determine if two strings match a wildcard pattern (`*` and `?`).
28. Given a string of digits, return all possible letter combinations (phone number mapping).
29. Find the longest common prefix among an array of strings.
30. Decode ways — how many ways can a digit string be decoded (A=1, B=2, ..., Z=26)?

**Hard**
31. Implement a basic regex matcher with `.` and `*`.
32. Minimum number of characters to add to make a string palindrome.
33. Shortest palindrome (add characters to front).
34. Word break — can the string be segmented using dictionary words?
35. Find all palindromic substrings in a string.

---

## 3. HashMap & HashSet

**Easy**
1. Find the two numbers that sum to a target (using HashMap).
2. Find duplicate elements in an array (using HashMap).
3. Count frequency of each character in a string.
4. Find the first non-repeating character (using LinkedHashMap).
5. Check if two strings are isomorphic.
6. Find all elements with frequency > n/3.
7. Find common elements in two arrays (using HashSet).
8. Remove duplicates from an array (using HashSet).
9. Check if an array contains duplicates.
10. Check if two arrays have the same elements (regardless of order).

**Medium**
11. Group anagrams from a list of strings (HashMap<String, List<String>>).
12. Find the longest consecutive sequence (using HashSet).
13. Top K frequent elements in an array.
14. Subarray sum equals k — count subarrays with sum = k.
15. Find if a contiguous subarray with zero sum exists.
16. Largest subarray with equal number of 0s and 1s.
17. Find all pairs with a given difference.
18. Given a list of words, find words that can be formed using characters from a board (similar characters).
19. Find four elements that sum to a given target.
20. Clone a graph (deep copy using HashMap for visited nodes).

---

## 4. Linked List

**Easy**
1. Reverse a singly linked list (iteratively and recursively).
2. Find the length of a linked list.
3. Find the middle node of a linked list.
4. Check if a linked list has a cycle.
5. Delete a node from a linked list (given only that node).
6. Remove Nth node from end of list.
7. Merge two sorted linked lists.
8. Remove duplicates from a sorted linked list.
9. Print a linked list in reverse (without reversing it).
10. Check if a linked list is a palindrome.

**Medium**
11. Find the starting node of a cycle in a linked list (Floyd's Cycle Detection).
12. Add two numbers represented as linked lists.
13. Flatten a multilevel doubly linked list.
14. Reverse a linked list in groups of k.
15. Sort a linked list (Merge Sort on linked list).
16. Partition a linked list around value x.
17. Intersection of two linked lists (find the node where they merge).
18. Rotate a linked list to the right by k places.
19. Clone a linked list with random pointers.
20. Swap nodes in pairs in a linked list.

---

## 5. Stack & Queue

**Easy**
1. Implement a stack using arrays.
2. Implement a queue using two stacks.
3. Implement a stack using two queues.
4. Check if parentheses are balanced (`{}[]()` matching).
5. Reverse a string using a stack.
6. Evaluate a simple expression with `+` and `-` using a stack.
7. Implement a min-stack (getMin() in O(1)).
8. Sort a stack using only stack operations.
9. Reverse a queue.
10. Check if a sequence of push/pop operations is valid.

**Medium**
11. Evaluate a postfix (RPN) expression.
12. Convert infix to postfix expression.
13. Find the next greater element for each element in an array (Monotonic Stack).
14. Find the next smaller element.
15. Largest rectangle in histogram (Stack approach).
16. Implement a circular queue.
17. Sliding window maximum using Deque.
18. Design a LRU cache (LinkedHashMap or Deque + HashMap).
19. Celebrity problem — find the person everyone knows but knows nobody.
20. Simplify a file path (Unix style: `/a/./b/../../c/`).

---

## 6. Trees & Graphs

**Binary Tree — Easy**
1. Find the height/depth of a binary tree.
2. Count the number of nodes in a binary tree.
3. Check if two binary trees are identical.
4. Find the mirror/invert of a binary tree.
5. Print all root-to-leaf paths.
6. Level-order traversal (BFS) of a binary tree.
7. Inorder, Preorder, Postorder traversal (recursive and iterative).
8. Count leaf nodes.
9. Check if a binary tree is symmetric.
10. Find the maximum element in a binary tree.

**Binary Search Tree — Medium**
11. Validate a Binary Search Tree.
12. Find the kth smallest element in a BST.
13. Find the lowest common ancestor (LCA) in a BST.
14. Find the lowest common ancestor in a general binary tree.
15. Convert a sorted array to a balanced BST.
16. Delete a node from a BST.
17. Find the inorder successor of a node in a BST.
18. Check if a binary tree is height-balanced.
19. Find the diameter of a binary tree.
20. Maximum path sum in a binary tree.

**Graph — Medium/Hard**
21. BFS and DFS of a graph.
22. Detect a cycle in an undirected graph.
23. Detect a cycle in a directed graph.
24. Find all connected components.
25. Topological sort (for DAG).
26. Find the shortest path in an unweighted graph (BFS).
27. Number of islands (2D grid BFS/DFS).
28. Clone a graph (deep copy).
29. Bipartite graph check.
30. Course schedule — can you finish all courses given prerequisites?

---

## 7. Sorting & Searching

**Sorting (implement from scratch)**
1. Bubble Sort — time/space complexity?
2. Selection Sort.
3. Insertion Sort — when is it better than Quick Sort?
4. Merge Sort (recursive).
5. Quick Sort — best/average/worst case?
6. Counting Sort — when to use it?
7. Heap Sort using PriorityQueue.
8. Radix Sort.

**Searching**
9. Binary search in a sorted array.
10. Binary search for first/last occurrence of a target.
11. Search in a rotated sorted array.
12. Find the peak element.
13. Find the square root of a number using binary search (integer part).
14. Find the minimum in a rotated sorted array.
15. Search a 2D matrix (each row sorted, first element of row > last of previous).

---

## 8. Recursion & Backtracking

1. Fibonacci series (recursive + memoized + iterative).
2. Factorial of a number.
3. Sum of digits of a number.
4. Power of a number (fast exponentiation).
5. Tower of Hanoi — print moves.
6. Print all subsets of a set (power set).
7. Print all permutations of an array/string.
8. Solve Sudoku.
9. N-Queens problem — place N queens on NxN board.
10. Rat in a maze — find all paths.
11. Generate all valid parentheses of n pairs.
12. Word search in a 2D board of letters.
13. Combination sum — find all combinations that add up to target.
14. Letter combinations of a phone number.
15. Palindrome partitioning — partition string such that every substring is a palindrome.

---

## 9. Dynamic Programming

**1D DP**
1. Fibonacci series (memoization and tabulation).
2. Climbing stairs — how many ways to reach step n (1 or 2 steps at a time)?
3. Coin change — minimum number of coins to make amount.
4. Coin change II — number of ways to make amount.
5. House robber — max sum without robbing adjacent houses.
6. Maximum sum of non-adjacent elements.
7. Decode ways — how many ways to decode a string of digits.
8. Jump Game — minimum jumps to reach last index.

**2D DP**
9. Longest Common Subsequence (LCS).
10. Longest Common Substring.
11. Edit distance (minimum insertions, deletions, replacements).
12. 0/1 Knapsack problem.
13. Unbounded Knapsack.
14. Longest Palindromic Subsequence.
15. Matrix chain multiplication.
16. Unique paths in a grid.
17. Minimum path sum in a grid.
18. Interleaving strings.
19. Burst balloons.
20. Wildcard matching.

---

## 10. Two Pointers & Sliding Window

**Two Pointers**
1. Two Sum (sorted array — two pointer approach).
2. Three Sum — find all triplets that sum to zero.
3. Container with most water.
4. Remove duplicates from sorted array.
5. Move all zeros to end.
6. Valid palindrome (two pointer approach).
7. Trapping rainwater.
8. Reverse a string in place.
9. Sorted squares — square each element and return sorted.
10. Find pair with given sum in a sorted array.

**Sliding Window**
11. Maximum sum subarray of size k.
12. Longest substring without repeating characters.
13. Minimum window substring.
14. Longest substring with at most k distinct characters.
15. Permutation in string — check if s2 contains a permutation of s1.
16. Find all anagrams in a string.
17. Fruit into baskets (at most 2 distinct).
18. Max consecutive ones III (can flip at most k zeros).
19. Substring with concatenation of all words.
20. Count occurrences of anagrams.

---

## 11. Bit Manipulation

1. Check if a number is even or odd using bitwise AND.
2. Check if a number is a power of 2.
3. Find the number of 1s in binary representation (Brian Kernighan's algorithm).
4. Find the single number (XOR trick) — every element appears twice except one.
5. Find two non-repeating numbers when rest appear twice.
6. Swap two numbers without a temp variable (XOR swap).
7. Find the position of the rightmost set bit.
8. Set, clear, and toggle a specific bit.
9. Reverse bits of an integer.
10. Count bits to convert integer A to integer B.
11. Find missing number using XOR.
12. Multiply a number by 2 and divide by 2 using bit shift.
13. Find the largest power of 2 less than or equal to n.
14. Check if all characters in a string are unique using a bit vector.
15. Sum of two integers without `+` operator (using bit manipulation).

---

## 12. Math & Numbers

1. Check if a number is prime.
2. Find all prime numbers up to n (Sieve of Eratosthenes).
3. Find GCD of two numbers (Euclidean algorithm).
4. Find LCM of two numbers.
5. Check if a number is a perfect square.
6. Find the square root without `Math.sqrt()`.
7. Check if a number is a palindrome.
8. Reverse an integer (handle overflow).
9. Count digits in a number.
10. Find the sum of digits.
11. Check if a number is an Armstrong number.
12. Fibonacci using matrix exponentiation.
13. Check if a number is a happy number.
14. Roman numeral to integer conversion.
15. Integer to Roman numeral.
16. Excel column name to number (A=1, Z=26, AA=27...).
17. Excel column number to name.
18. Find the nth ugly number (divisible by 2, 3, or 5 only).
19. Count trailing zeros in n factorial.
20. Find the number of ways to represent n as sum of consecutive integers.

---

## 13. Mixed / Hard

1. LRU Cache — implement `get` and `put` in O(1).
2. LFU Cache — implement `get` and `put` in O(1).
3. Design a Hit Counter — count hits in the last 300 seconds.
4. Design a parking lot system.
5. Design a vending machine.
6. Find the median of a stream of integers (two heaps).
7. Find the kth largest element in a stream.
8. Serialize and deserialize a binary tree.
9. Design a Twitter-like feed (top 10 tweets from followed users).
10. Skyline problem — given building rectangles, find the skyline outline.
11. Find the largest rectangle containing only 1s in a binary matrix.
12. Word Ladder — transform one word to another changing one letter at a time (BFS).
13. Longest increasing subsequence.
14. Maximum profit with at most k stock transactions.
15. Regular expression matching (`.` and `*`).
16. Implement a trie (prefix tree) with insert, search, startsWith.
17. Find all words in a Boggle board.
18. Design a file system (in-memory).
19. Implement a thread-safe blocking queue.
20. Find the critical connections (bridges) in a graph.

---

## Quick Complexity Reference

| Algorithm / Operation | Time Complexity | Space |
|----------------------|-----------------|-------|
| Linear search | O(n) | O(1) |
| Binary search | O(log n) | O(1) |
| Bubble / Selection / Insertion sort | O(n²) | O(1) |
| Merge sort | O(n log n) | O(n) |
| Quick sort (avg) | O(n log n) | O(log n) |
| Heap sort | O(n log n) | O(1) |
| BFS / DFS | O(V + E) | O(V) |
| HashMap get/put | O(1) avg | — |
| HashSet add/contains | O(1) avg | — |
| TreeMap/TreeSet ops | O(log n) | — |
| PriorityQueue poll | O(log n) | — |

---

## Patterns to Recognize

| Pattern | When to use | Data structure |
|---------|-------------|----------------|
| Sliding window | Contiguous subarray/substring | Array + 2 pointers |
| Two pointers | Sorted array, palindrome, pair sum | Array |
| Fast & slow pointer | Cycle detection, middle of list | LinkedList |
| BFS | Shortest path, level order | Queue |
| DFS | All paths, tree traversal | Stack / Recursion |
| Topological sort | Dependencies, scheduling | Queue (Kahn's) |
| Dynamic programming | Optimization, counting, feasibility | Array/HashMap |
| Divide & conquer | Reduce to subproblems | Recursion |
| Greedy | Local optimum → global | PriorityQueue |
| Backtracking | All combinations/permutations | Recursion + undo |
| Monotonic stack | Next greater/smaller element | Stack |
| Union-Find | Connected components, cycle detection | int[] parent |
| Trie | Prefix search, autocomplete | Nested HashMap / Array |
| Segment tree | Range queries with updates | Array |
| Heap / k-way merge | Kth element, top K, merge sorted lists | PriorityQueue |
