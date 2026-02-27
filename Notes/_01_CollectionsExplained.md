# Java Collections — Complete Reference Guide
> **Scope:** Every Java collection — purpose, methods, complexity, examples, comparisons, and interview Q&A  

---

## Table of Contents

1. [Java Collections Framework — Big Picture](#1-java-collections-framework--big-picture)
2. [Array (Primitive & `Arrays` Utility)](#2-array-primitive--arrays-utility)
3. [ArrayList](#3-arraylist)
4. [LinkedList](#4-linkedlist)
5. [HashMap](#5-hashmap)
6. [LinkedHashMap](#6-linkedhashmap)
7. [TreeMap](#7-treemap)
8. [HashSet](#8-hashset)
9. [LinkedHashSet](#9-linkedhashset)
10. [TreeSet](#10-treeset)
11. [Stack](#11-stack)
12. [Queue & Deque (ArrayDeque)](#12-queue--deque-arraydeque)
13. [PriorityQueue (Heap)](#13-priorityqueue-heap)
14. [Comparison Tables](#14-comparison-tables)
15. [When to Use Which — Decision Guide](#15-when-to-use-which--decision-guide)
16. [Interview Q&A](#16-interview-qa)

---

## 1. Java Collections Framework — Big Picture

```
java.util
│
├── Collection (Interface)
│   ├── List (Interface)          → ArrayList, LinkedList, Vector, Stack
│   ├── Set (Interface)           → HashSet, LinkedHashSet, TreeSet
│   └── Queue (Interface)         → LinkedList, PriorityQueue, ArrayDeque
│       └── Deque (Interface)     → ArrayDeque, LinkedList
│
├── Map (Interface)               → HashMap, LinkedHashMap, TreeMap, Hashtable
│
└── Arrays (Utility Class)        → sort, binarySearch, copyOf, fill, toString
```

**Key Interfaces to know:**
| Interface | Ordered? | Sorted? | Allows Null? | Duplicates? |
|-----------|----------|---------|--------------|-------------|
| `List`    | ✅ Yes (by index) | ❌ | ✅ | ✅ |
| `Set`     | Depends  | Depends | Depends      | ❌ |
| `Map`     | Depends  | Depends | Depends (key)| Keys: ❌, Values: ✅ |
| `Queue`   | ✅ FIFO  | Depends | Depends      | ✅ |
| `Deque`   | ✅ Both ends | ❌ | ❌ (ArrayDeque) | ✅ |

---

## 2. Array (Primitive & `Arrays` Utility)

### Purpose
The most fundamental data structure. Fixed-size, contiguous memory, O(1) random access by index. Used when size is known upfront and performance is critical.

### Declaration & Initialization

```java
// Primitive array
int[] arr = new int[5];                        // [0, 0, 0, 0, 0]
int[] arr2 = {1, 2, 3, 4, 5};                 // inline
int[] arr3 = new int[]{10, 20, 30};

// 2D array
int[][] matrix = new int[3][4];
int[][] matrix2 = {{1,2},{3,4},{5,6}};

// String array
String[] names = {"Alice", "Bob", "Charlie"};

// Object array
Integer[] nums = new Integer[3];               // wrapper type — allows null
```

### Core Operations

```java
// Access & Modify
int val = arr[2];       // O(1) - get element at index 2
arr[2] = 99;            // O(1) - set element at index 2
int len = arr.length;   // O(1) - not a method, it's a field

// Iterate
for (int num : arr) { System.out.println(num); }
for (int i = 0; i < arr.length; i++) { System.out.println(arr[i]); }
```

### `java.util.Arrays` Utility Methods

| Method | Description | Time Complexity |
|--------|-------------|----------------|
| `Arrays.sort(arr)` | Sorts in ascending order (Dual-Pivot Quicksort) | O(n log n) |
| `Arrays.sort(arr, fromIdx, toIdx)` | Sort a range | O(k log k) |
| `Arrays.sort(strArr, Comparator)` | Sort with custom comparator | O(n log n) |
| `Arrays.binarySearch(arr, key)` | Search in **sorted** array, returns index | O(log n) |
| `Arrays.copyOf(arr, newLen)` | Copy array, truncate or pad with 0/null | O(n) |
| `Arrays.copyOfRange(arr, from, to)` | Copy a subarray | O(k) |
| `Arrays.fill(arr, value)` | Fill all elements with a value | O(n) |
| `Arrays.fill(arr, from, to, value)` | Fill a range | O(k) |
| `Arrays.equals(arr1, arr2)` | Element-wise equality check | O(n) |
| `Arrays.deepEquals(arr1, arr2)` | Recursive equality for nested arrays | O(n) |
| `Arrays.toString(arr)` | Printable string `[1, 2, 3]` | O(n) |
| `Arrays.deepToString(arr2D)` | Printable string for 2D arrays | O(n) |
| `Arrays.asList(arr)` | Convert array → fixed-size `List` | O(1) |
| `Arrays.stream(arr)` | Convert to `IntStream` / `Stream` | O(1) |

```java
int[] arr = {5, 2, 8, 1, 9, 3};

Arrays.sort(arr);                         // [1, 2, 3, 5, 8, 9]
int idx = Arrays.binarySearch(arr, 5);    // idx = 3
int[] copy = Arrays.copyOf(arr, 3);       // [1, 2, 3]
int[] range = Arrays.copyOfRange(arr, 1, 4); // [2, 3, 5]
Arrays.fill(arr, 0);                      // [0, 0, 0, 0, 0, 0]
System.out.println(Arrays.toString(arr)); // [0, 0, 0, 0, 0, 0]

// Convert array to List
String[] names = {"Bob", "Alice", "Charlie"};
List<String> list = Arrays.asList(names); // fixed-size list!
// list.add("Dave"); // UnsupportedOperationException — can't add to fixed-size!
```

### Complexity Summary

| Operation | Time | Space |
|-----------|------|-------|
| Access by index | O(1) | O(1) |
| Search (unsorted) | O(n) | O(1) |
| Search (sorted, binary) | O(log n) | O(1) |
| Sort | O(n log n) | O(log n) |
| Insert at end (resize → new array) | O(n) | O(n) |
| Insert at arbitrary position | O(n) | O(n) |
| Delete | O(n) | O(n) |

### From Your Solutions

**Frequency array for character counting** — `_003_HT_firstNonRepeatingChar.java`:
```java
// O(n) time, O(1) space — uses array as a hash map for ASCII chars
int[] counts = new int[128];
for (char ch : str.toCharArray()) {
    counts[ch - 'a']++;
}
for (char ch : str.toCharArray()) {
    if (counts[ch - 'a'] == 1) return ch;
}
```

**Sort array of 0s, 1s, 2s** — `_124_Sort_Array_Of_0s_1s_2s.java`:
```java
// Dutch National Flag Algorithm — O(n) time, O(1) space
int low = 0, mid = 0, high = arr.length - 1;
while (mid <= high) {
    if (arr[mid] == 0)      { swap(arr, mid++, low++); }
    else if (arr[mid] == 1) { mid++; }
    else                    { swap(arr, mid, high--); }
}
```

---

## 3. ArrayList

### Purpose
Dynamic array that grows/shrinks automatically. Backed by a plain array internally (default capacity 10, grows by 50% on resize). Best for random access and iteration. The go-to `List` implementation.

### Declaration & Initialization

```java
import java.util.ArrayList;
import java.util.List;

ArrayList<Integer> list = new ArrayList<>();             // empty
ArrayList<Integer> list2 = new ArrayList<>(20);         // initial capacity hint
ArrayList<String> list3 = new ArrayList<>(Arrays.asList("a", "b", "c"));
List<String> list4 = new ArrayList<>(anotherList);      // copy constructor

// Prefer List interface type (more flexible)
List<Integer> nums = new ArrayList<>();
```

### All Methods

| Method | Description | Time Complexity |
|--------|-------------|----------------|
| `add(E e)` | Append to end | O(1) amortized |
| `add(int index, E e)` | Insert at position (shifts right) | O(n) |
| `addAll(Collection c)` | Append all elements | O(k) |
| `addAll(int index, Collection c)` | Insert all at position | O(n+k) |
| `get(int index)` | Get element at index | O(1) |
| `set(int index, E e)` | Replace element at index | O(1) |
| `remove(int index)` | Remove by index (shifts left) | O(n) |
| `remove(Object o)` | Remove first occurrence of object | O(n) |
| `removeAll(Collection c)` | Remove all elements in c | O(n*m) |
| `retainAll(Collection c)` | Keep only elements in c | O(n*m) |
| `contains(Object o)` | Check if element exists | O(n) |
| `containsAll(Collection c)` | Check if all elements exist | O(n*m) |
| `indexOf(Object o)` | First index of element (-1 if not found) | O(n) |
| `lastIndexOf(Object o)` | Last index of element | O(n) |
| `size()` | Number of elements | O(1) |
| `isEmpty()` | Check if empty | O(1) |
| `clear()` | Remove all elements | O(n) |
| `toArray()` | Convert to Object[] | O(n) |
| `toArray(T[] arr)` | Convert to typed array | O(n) |
| `subList(int from, int to)` | Returns view of sub-range | O(1) |
| `sort(Comparator c)` | Sort in-place with comparator | O(n log n) |
| `Collections.sort(list)` | Sort (natural order) | O(n log n) |
| `Collections.reverse(list)` | Reverse the list | O(n) |
| `Collections.shuffle(list)` | Random shuffle | O(n) |
| `Collections.min(list)` | Find minimum | O(n) |
| `Collections.max(list)` | Find maximum | O(n) |
| `Collections.frequency(list, o)` | Count occurrences of o | O(n) |
| `iterator()` | Get iterator | O(1) |
| `listIterator()` | Get bidirectional iterator | O(1) |
| `forEach(Consumer)` | Java 8 lambda iteration | O(n) |
| `stream()` | Convert to Stream | O(1) |
| `ensureCapacity(int minCapacity)` | Grow internal array proactively | O(n) |
| `trimToSize()` | Shrink internal array to fit | O(n) |

```java
List<Integer> list = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6));

list.add(7);                          // [3,1,4,1,5,9,2,6,7]
list.add(0, 0);                       // [0,3,1,4,1,5,9,2,6,7]
list.get(2);                          // 1
list.set(2, 99);                      // [0,3,99,4,1,5,9,2,6,7]
list.remove(Integer.valueOf(99));     // removes by value: [0,3,4,1,5,9,2,6,7]
list.remove(0);                       // removes by index: [3,4,1,5,9,2,6,7]
list.contains(5);                     // true
list.indexOf(1);                      // 2
list.size();                          // 7
list.isEmpty();                       // false
Collections.sort(list);               // [1,2,3,4,5,6,7,9]
list.subList(1, 4);                   // [2,3,4] — backed view
list.clear();                         // []
```

### Complexity Summary

| Operation | Time | Notes |
|-----------|------|-------|
| `get(index)` | O(1) | Direct array access |
| `add(end)` | O(1) amortized | Occasional O(n) resize |
| `add(index)` | O(n) | Shifts elements right |
| `remove(index)` | O(n) | Shifts elements left |
| `contains` / `indexOf` | O(n) | Linear scan |
| `sort` | O(n log n) | TimSort |
| Memory | — | 10 default, 1.5x growth |

### From Your Solutions

**Finding duplicates** — `_002_HT_findDuplicatesInArray.java`:
```java
List<Integer> duplicates = new ArrayList<Integer>();
for (Map.Entry<Integer, Integer> entry : hm.entrySet()) {
    if (entry.getValue() > 1)
        duplicates.add(entry.getKey());
}
return duplicates;
```

**Grouping anagrams** — `_004_HT_groupAnagrams.java`:
```java
// ArrayList as value in HashMap — grouping elements
HashMap<String, List<String>> anagramGroup = new HashMap<>();
List<String> group = new ArrayList<String>();
group.add(str);
anagramGroup.put(sortedStr, group);
return new ArrayList<List<String>>(anagramGroup.values());
```

**Graph adjacency list** — `_055_Graph_Adding_Vertex.java`:
```java
// HashMap<String, ArrayList<String>> — classic graph representation
private HashMap<String, ArrayList<String>> adjList = new HashMap<>();
adjList.put(vertex, new ArrayList<String>());
```

---

## 4. LinkedList

### Purpose
Doubly-linked list implementing both `List` and `Deque`. Elements stored as nodes with `prev`/`next` pointers. Efficient insertions/deletions at both ends (O(1)) but slow random access (O(n)). In Java, commonly used as a `Queue` or `Deque` rather than a `List`.

### Declaration & Initialization

```java
import java.util.LinkedList;
import java.util.Deque;
import java.util.Queue;

LinkedList<Integer> ll = new LinkedList<>();
LinkedList<String> ll2 = new LinkedList<>(Arrays.asList("a", "b", "c"));

// As Queue (FIFO)
Queue<Integer> queue = new LinkedList<>();

// As Deque (double-ended queue)
Deque<Integer> deque = new LinkedList<>();
```

### All Methods (List + Deque combined)

**List operations:**
| Method | Description | Time |
|--------|-------------|------|
| `add(E e)` | Add to end | O(1) |
| `add(int index, E e)` | Insert at index | O(n) |
| `addAll(Collection c)` | Add all to end | O(k) |
| `get(int index)` | Get by index (traversal!) | O(n) |
| `set(int index, E e)` | Set by index | O(n) |
| `remove(int index)` | Remove by index | O(n) |
| `remove(Object o)` | Remove first occurrence | O(n) |
| `contains(Object o)` | Linear search | O(n) |
| `indexOf(Object o)` | First index | O(n) |
| `size()` | Element count | O(1) |
| `clear()` | Remove all | O(n) |

**Deque/Queue operations (preferred over List operations):**
| Method | Description | Throws? | Time |
|--------|-------------|---------|------|
| `addFirst(E e)` | Add to head | Yes (if full) | O(1) |
| `addLast(E e)` | Add to tail | Yes | O(1) |
| `offerFirst(E e)` | Add to head (safe) | No | O(1) |
| `offerLast(E e)` | Add to tail (safe) | No | O(1) |
| `removeFirst()` | Remove from head | Yes (if empty) | O(1) |
| `removeLast()` | Remove from tail | Yes | O(1) |
| `pollFirst()` | Remove from head (safe) | No — returns null | O(1) |
| `pollLast()` | Remove from tail (safe) | No — returns null | O(1) |
| `getFirst()` / `peekFirst()` | Peek at head | Yes / No | O(1) |
| `getLast()` / `peekLast()` | Peek at tail | Yes / No | O(1) |
| `push(E e)` | Stack push (= addFirst) | — | O(1) |
| `pop()` | Stack pop (= removeFirst) | — | O(1) |
| `peek()` | Stack peek (= peekFirst) | — | O(1) |
| `offer(E e)` | Queue enqueue (= addLast) | — | O(1) |
| `poll()` | Queue dequeue (= pollFirst) | — | O(1) |

```java
LinkedList<Integer> ll = new LinkedList<>();
ll.addFirst(10);   // [10]
ll.addLast(20);    // [10, 20]
ll.addFirst(5);    // [5, 10, 20]
ll.peekFirst();    // 5  (no removal)
ll.peekLast();     // 20 (no removal)
ll.pollFirst();    // 5  → [10, 20]
ll.pollLast();     // 20 → [10]

// As a Queue
Queue<String> queue = new LinkedList<>();
queue.offer("A");   // enqueue
queue.offer("B");
queue.peek();       // "A" — look without removing
queue.poll();       // "A" — remove and return
```

### Complexity Summary

| Operation | Time | vs ArrayList |
|-----------|------|-------------|
| `addFirst` / `addLast` | O(1) | ArrayList: O(n) for addFirst |
| `removeFirst` / `removeLast` | O(1) | ArrayList: O(n) for removeFirst |
| `get(index)` | O(n) | ArrayList: O(1) ← **ArrayList wins** |
| `add(index)` | O(n) (traversal) | Same |
| `contains` | O(n) | Same |
| Memory | Higher (node objects) | Lower (pure array) |

### Custom Linked List — From Your Solutions

**Nth from last (Two-pointer technique)** — `_121_udemy_nthFromLastLinkedList.java`:
```java
// Uses two pointers n nodes apart
Node left = head, right = head;
for (int i = 0; i < n; i++) {
    if (right == null) return null;
    right = right.child;
}
while (right != null) {
    left = left.child;
    right = right.child;
}
return left;  // O(L) time, O(1) space
```

**Reverse linked list iteratively** — `_09_reverseLinkedList.java`:
```java
Node temp = list.head, before = null, after;
while (temp != null) {
    after = temp.next;
    temp.next = before;
    before = temp;
    temp = after;
}
list.head = before;  // O(n) time, O(1) space
```

---

## 5. HashMap

### Purpose
Key-value store using hashing. The most-used collection in coding interviews. O(1) average for get/put/remove. **No guaranteed order**. Allows one `null` key and multiple `null` values.

**Internally:** Array of buckets + LinkedList/Tree per bucket. Hash collision resolution via chaining (Java 8: converts to Red-Black Tree when bucket size > 8).

### Declaration & Initialization

```java
import java.util.HashMap;
import java.util.Map;

HashMap<String, Integer> map = new HashMap<>();
HashMap<String, Integer> map2 = new HashMap<>(16, 0.75f); // capacity, load factor
Map<String, Integer> map3 = new HashMap<>(otherMap);      // copy constructor

// Prefer Map interface
Map<String, Integer> freq = new HashMap<>();
```

### All Methods

| Method | Description | Time |
|--------|-------------|------|
| `put(K key, V value)` | Insert/update key-value pair | O(1) avg |
| `get(Object key)` | Get value by key (null if absent) | O(1) avg |
| `getOrDefault(key, defaultValue)` | Get value or default if key absent | O(1) avg |
| `remove(Object key)` | Remove entry by key | O(1) avg |
| `remove(Object key, Object value)` | Remove only if key maps to value | O(1) avg |
| `containsKey(Object key)` | Check if key exists | O(1) avg |
| `containsValue(Object value)` | Check if value exists (full scan) | O(n) |
| `putIfAbsent(K key, V value)` | Insert only if key not present | O(1) avg |
| `putAll(Map m)` | Copy all entries from m | O(k) |
| `replace(K key, V value)` | Replace value only if key exists | O(1) avg |
| `replace(K key, V old, V new)` | Replace only if maps to old value | O(1) avg |
| `merge(K key, V value, BiFunction)` | Merge value using function | O(1) avg |
| `compute(K key, BiFunction)` | Compute/update value for key | O(1) avg |
| `computeIfAbsent(K key, Function)` | Compute if key not present | O(1) avg |
| `computeIfPresent(K key, BiFunction)` | Compute only if key present | O(1) avg |
| `keySet()` | Returns `Set` of all keys | O(1) |
| `values()` | Returns `Collection` of all values | O(1) |
| `entrySet()` | Returns `Set<Map.Entry<K,V>>` of all pairs | O(1) |
| `size()` | Number of entries | O(1) |
| `isEmpty()` | Check if empty | O(1) |
| `clear()` | Remove all entries | O(n) |
| `forEach(BiConsumer)` | Lambda iteration | O(n) |

```java
Map<String, Integer> freq = new HashMap<>();

// put / getOrDefault pattern (most common in interviews!)
freq.put("apple", freq.getOrDefault("apple", 0) + 1);

// putIfAbsent
freq.putIfAbsent("banana", 0);

// merge (elegant frequency counting)
freq.merge("apple", 1, Integer::sum);

// computeIfAbsent (grouping pattern)
Map<String, List<String>> groups = new HashMap<>();
groups.computeIfAbsent("fruit", k -> new ArrayList<>()).add("apple");

// Iteration — 4 ways
// 1. entrySet (most efficient)
for (Map.Entry<String, Integer> entry : freq.entrySet()) {
    System.out.println(entry.getKey() + " -> " + entry.getValue());
}
// 2. keySet
for (String key : freq.keySet()) {
    System.out.println(key + " -> " + freq.get(key));
}
// 3. values only
for (int val : freq.values()) {
    System.out.println(val);
}
// 4. forEach lambda
freq.forEach((k, v) -> System.out.println(k + " -> " + v));
```

### Complexity Summary

| Operation | Average | Worst (hash collisions) |
|-----------|---------|------------------------|
| `put` | O(1) | O(n) |
| `get` | O(1) | O(n) |
| `remove` | O(1) | O(n) |
| `containsKey` | O(1) | O(n) |
| `containsValue` | O(n) | O(n) |
| Space | O(n) | — |

### From Your Solutions

**Common elements in two arrays** — `_001_HT_CommonElementsInArray.java`:
```java
HashMap<Integer, Boolean> hm = new HashMap<>();
for (int num : array1) hm.put(num, true);
for (int num : array2) if (hm.containsKey(num)) return true;
// O(n) time vs O(n²) brute force
```

**Two Sum** — `_005_HT_indicesOfTwoNumbersThatGivesTarget.java` & `_007_TwoSum.java`:
```java
HashMap<Integer, Integer> hm = new HashMap<>();
for (int i = 0; i < arr.length; i++) {
    int complement = target - arr[i];
    if (hm.containsKey(complement)) return new int[]{hm.get(complement), i};
    hm.put(arr[i], i);   // store value → index mapping
}
```

**Frequency counting** — `_002_HT_findDuplicatesInArray.java`:
```java
HashMap<Integer, Integer> hm = new HashMap<>();
for (int number : arr) {
    hm.put(number, hm.getOrDefault(number, 0) + 1);
}
```

**First non-repeating character** — `_003_HT_firstNonRepeatingChar.java`:
```java
Map<Character, Integer> hm = new HashMap<>();
for (char ch : str.toCharArray()) hm.put(ch, hm.getOrDefault(ch, 0) + 1);
for (char ch : str.toCharArray()) if (hm.get(ch) == 1) return ch;
```

**Grouping anagrams** — `_004_HT_groupAnagrams.java`:
```java
HashMap<String, List<String>> anagramGroup = new HashMap<>();
char[] chars = str.toCharArray();
Arrays.sort(chars);
String sortedStr = new String(chars);   // sorted string = canonical key
anagramGroup.computeIfAbsent(sortedStr, k -> new ArrayList<>()).add(str);
```

**5 ways to iterate a HashMap** — `_053_iterateThroughHashMap.java`:
- `entrySet()` for-each
- `keySet()` for-each
- `values()` for-each
- `forEach()` lambda
- `Iterator` on `entrySet()`

---

## 6. LinkedHashMap

### Purpose
HashMap + doubly-linked list to maintain **insertion order** (or optionally access order). Every `put` appends to the linked list. O(1) average for get/put. Use when you need a HashMap but also need predictable iteration order.

### Declaration & Initialization

```java
import java.util.LinkedHashMap;

LinkedHashMap<String, Integer> map = new LinkedHashMap<>();                   // insertion order
LinkedHashMap<String, Integer> lruMap = new LinkedHashMap<>(16, 0.75f, true); // access order (LRU cache!)
```

### Key Differences from HashMap

```java
// HashMap — no guaranteed order
Map<String, Integer> hm = new HashMap<>();
hm.put("banana", 1); hm.put("apple", 2); hm.put("cherry", 3);
System.out.println(hm);  // {banana=1, cherry=3, apple=2} — arbitrary

// LinkedHashMap — insertion order preserved
Map<String, Integer> lhm = new LinkedHashMap<>();
lhm.put("banana", 1); lhm.put("apple", 2); lhm.put("cherry", 3);
System.out.println(lhm); // {banana=1, apple=2, cherry=3} — preserved!
```

### All Methods
*Same as HashMap* — every HashMap method works. The only difference is iteration order.

| Feature | HashMap | LinkedHashMap |
|---------|---------|---------------|
| Iteration order | Random | Insertion order (or access order) |
| Performance | Slightly faster | Slightly slower (linked list overhead) |
| Memory | Less | More (prev/next pointers per entry) |
| `null` keys | 1 allowed | 1 allowed |
| Thread-safe | No | No |

### LRU Cache Pattern

```java
// LRU Cache using LinkedHashMap with access order
int capacity = 3;
LinkedHashMap<Integer, Integer> lru = new LinkedHashMap<>(capacity, 0.75f, true) {
    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;  // auto-evict oldest when full
    }
};
lru.put(1, 1); lru.put(2, 2); lru.put(3, 3);
lru.get(1);    // Access 1 → moves to end
lru.put(4, 4); // Evicts 2 (least recently used)
System.out.println(lru); // {3=3, 1=1, 4=4}
```

### From Your Solutions

**Frequency map preserving insertion order** — `_011_frequencyOfEachElementArray.java`:
```java
private static LinkedHashMap<Integer, Integer> _04_Using_LinkedHashMap(int[] arr) {
    // O(n) time, O(n) space — preserves insertion order
    LinkedHashMap<Integer, Integer> freqMap = new LinkedHashMap<>();
    for (int num : arr) {
        freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
    }
    return freqMap;  // {10=3, 20=4, 5=1} — same order as first appearance
}
```

---

## 7. TreeMap

### Purpose
Red-Black Tree based Map. Always **sorted by key** (natural order or custom `Comparator`). O(log n) for get/put/remove. Use when you need a sorted map or range-query operations.

### Declaration & Initialization

```java
import java.util.TreeMap;

TreeMap<String, Integer> map = new TreeMap<>();                    // natural (alphabetical) order
TreeMap<Integer, String> reverseMap = new TreeMap<>(Comparator.reverseOrder()); // reverse order
TreeMap<String, Integer> customMap = new TreeMap<>((a, b) -> b.compareTo(a));   // custom
```

### All Methods

**Standard Map methods** (same as HashMap) plus **navigation methods**:

| Method | Description | Time |
|--------|-------------|------|
| `firstKey()` | Smallest key | O(log n) |
| `lastKey()` | Largest key | O(log n) |
| `floorKey(K key)` | Greatest key ≤ given key | O(log n) |
| `ceilingKey(K key)` | Smallest key ≥ given key | O(log n) |
| `lowerKey(K key)` | Greatest key strictly < given key | O(log n) |
| `higherKey(K key)` | Smallest key strictly > given key | O(log n) |
| `firstEntry()` | Entry with smallest key | O(log n) |
| `lastEntry()` | Entry with largest key | O(log n) |
| `pollFirstEntry()` | Remove & return smallest entry | O(log n) |
| `pollLastEntry()` | Remove & return largest entry | O(log n) |
| `headMap(K toKey)` | View of map with keys < toKey | O(log n) |
| `tailMap(K fromKey)` | View of map with keys ≥ fromKey | O(log n) |
| `subMap(K from, K to)` | View of map with keys in [from, to) | O(log n) |
| `descendingMap()` | Reversed view of this map | O(1) |
| `navigableKeySet()` | Set of keys in ascending order | O(1) |

```java
TreeMap<Integer, String> map = new TreeMap<>();
map.put(5, "E"); map.put(1, "A"); map.put(3, "C"); map.put(7, "G"); map.put(2, "B");

System.out.println(map);          // {1=A, 2=B, 3=C, 5=E, 7=G}  — always sorted
map.firstKey();                   // 1
map.lastKey();                    // 7
map.floorKey(4);                  // 3  (greatest ≤ 4)
map.ceilingKey(4);                // 5  (smallest ≥ 4)
map.lowerKey(5);                  // 3  (strictly < 5)
map.higherKey(5);                 // 7  (strictly > 5)
map.headMap(4);                   // {1=A, 2=B, 3=C}
map.tailMap(4);                   // {5=E, 7=G}
map.subMap(2, 6);                 // {2=B, 3=C, 5=E}
map.pollFirstEntry();             // removes {1=A}, map = {2=B,3=C,5=E,7=G}
```

### Complexity Summary

| Operation | Time | vs HashMap |
|-----------|------|-----------|
| `put`, `get`, `remove` | O(log n) | HashMap: O(1) avg |
| `containsKey` | O(log n) | HashMap: O(1) avg |
| Navigation methods | O(log n) | HashMap: N/A |
| Iteration | O(n) sorted | HashMap: O(n) unsorted |
| Space | O(n) | O(n) |

### From Your Solutions

**Frequency map with sorted key output** — `_011_frequencyOfEachElementArray.java`:
```java
private static TreeMap<Integer, Integer> _03_Using_TreeMap_Sorted_Output(int[] arr) {
    // O(n log n) time — keys automatically sorted ascending
    TreeMap<Integer, Integer> freqMap = new TreeMap<>();
    for (int num : arr) {
        freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
    }
    return freqMap;  // {5=1, 10=3, 20=4} — sorted by key
}
```

---

## 8. HashSet

### Purpose
Unordered collection of **unique** elements. Backed by a `HashMap` internally (elements stored as keys, dummy `Boolean.TRUE` as value). O(1) average for add/remove/contains. The fastest way to check membership.

### Declaration & Initialization

```java
import java.util.HashSet;
import java.util.Set;

HashSet<Integer> set = new HashSet<>();
HashSet<String> set2 = new HashSet<>(Arrays.asList("a", "b", "c"));
Set<Integer> set3 = new HashSet<>(anotherCollection);
Set<Integer> set4 = new HashSet<>(16, 0.75f);   // initial capacity, load factor
```

### All Methods

| Method | Description | Time |
|--------|-------------|------|
| `add(E e)` | Add element (no-op if already exists) | O(1) avg |
| `remove(Object o)` | Remove element | O(1) avg |
| `contains(Object o)` | Check if element exists | O(1) avg |
| `addAll(Collection c)` | Union — add all elements | O(k) avg |
| `retainAll(Collection c)` | Intersection — keep only common elements | O(n) avg |
| `removeAll(Collection c)` | Difference — remove elements in c | O(k) avg |
| `containsAll(Collection c)` | Check if this is a superset of c | O(k) avg |
| `size()` | Number of elements | O(1) |
| `isEmpty()` | Check if empty | O(1) |
| `clear()` | Remove all elements | O(n) |
| `toArray()` | Convert to Object[] | O(n) |
| `iterator()` | Get iterator | O(1) |
| `forEach(Consumer)` | Lambda iteration | O(n) |
| `stream()` | Convert to Stream | O(1) |

```java
Set<Integer> setA = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
Set<Integer> setB = new HashSet<>(Arrays.asList(3, 4, 5, 6, 7));

// Set operations
Set<Integer> union = new HashSet<>(setA);
union.addAll(setB);              // {1,2,3,4,5,6,7}

Set<Integer> intersection = new HashSet<>(setA);
intersection.retainAll(setB);   // {3,4,5}

Set<Integer> difference = new HashSet<>(setA);
difference.removeAll(setB);     // {1,2}

// add returns false if already present
boolean added = setA.add(3);    // false — 3 already exists
boolean added2 = setA.add(10);  // true  — 10 is new
```

### Complexity Summary

| Operation | Average | Worst |
|-----------|---------|-------|
| `add` | O(1) | O(n) |
| `remove` | O(1) | O(n) |
| `contains` | O(1) | O(n) |
| Space | O(n) | — |

### From Your Solutions

**Longest consecutive sequence** — `_010_Set_longestConsecutiveSequence.java`:
```java
Set<Integer> numSet = new HashSet<>();
for (int num : nums) numSet.add(num);

int longestStreak = 0;
for (int num : numSet) {
    if (!numSet.contains(num - 1)) {   // only start of sequence
        int currentNum = num, currentStreak = 1;
        while (numSet.contains(currentNum + 1)) { currentNum++; currentStreak++; }
        longestStreak = Math.max(longestStreak, currentStreak);
    }
}
// O(n) time — HashSet makes contains O(1)
```

**Contains duplicate** — `_011_ContainsDuplicate.java`:
```java
HashSet<Integer> hs = new HashSet<>();
for (int num : nums) {
    if (!hs.add(num)) return true;   // add() returns false if already present
}
return false;  // O(n) time, O(n) space
```

**Remove duplicates from array** — `_03_removeDuplicates.java`:
```java
HashSet<Integer> hs = new HashSet<>();
for (int number : arr) hs.add(number);
int[] result = new int[hs.size()];
int i = 0;
for (int num : hs) result[i++] = num;   // O(n) — but order NOT preserved
```

---

## 9. LinkedHashSet

### Purpose
HashSet + linked list to maintain **insertion order**. Elements are unique like HashSet but iteration is predictable. Use when uniqueness matters AND order of first insertion matters.

### Declaration & Initialization

```java
import java.util.LinkedHashSet;

LinkedHashSet<String> lhs = new LinkedHashSet<>();
LinkedHashSet<Integer> lhs2 = new LinkedHashSet<>(Arrays.asList(3, 1, 2, 1, 3));
// lhs2 = [3, 1, 2] — duplicates removed, insertion order preserved
```

### All Methods
*Same as HashSet* — all methods identical. Only difference is iteration order.

```java
LinkedHashSet<String> lhs = new LinkedHashSet<>();
lhs.add("banana");
lhs.add("apple");
lhs.add("cherry");
lhs.add("apple");   // duplicate — ignored
System.out.println(lhs);  // [banana, apple, cherry] — insertion order!

// vs HashSet
Set<String> hs = new HashSet<>(Arrays.asList("banana", "apple", "cherry"));
System.out.println(hs);   // [apple, cherry, banana] — random order!
```

| Feature | HashSet | LinkedHashSet | TreeSet |
|---------|---------|---------------|---------|
| Order | None | Insertion | Sorted |
| `add`/`contains` | O(1) | O(1) | O(log n) |
| Memory | Less | Medium | Most |
| Null | 1 allowed | 1 allowed | No (natural order) |

---

## 10. TreeSet

### Purpose
Red-Black Tree based Set. Elements always **sorted** in natural order or by custom `Comparator`. No duplicates. O(log n) for add/remove/contains. Use when you need a sorted set or range queries.

### Declaration & Initialization

```java
import java.util.TreeSet;

TreeSet<Integer> ts = new TreeSet<>();                           // natural ascending order
TreeSet<String> ts2 = new TreeSet<>(Comparator.reverseOrder()); // reverse order
TreeSet<Integer> ts3 = new TreeSet<>(Arrays.asList(5, 2, 8, 1, 9));
// ts3 = [1, 2, 5, 8, 9] — automatically sorted
```

### All Methods

**Standard Set methods** plus **navigation methods** (same as TreeMap):

| Method | Description | Time |
|--------|-------------|------|
| `add(E e)` | Add element (sorted) | O(log n) |
| `remove(Object o)` | Remove element | O(log n) |
| `contains(Object o)` | Check existence | O(log n) |
| `first()` | Smallest element | O(log n) |
| `last()` | Largest element | O(log n) |
| `floor(E e)` | Greatest element ≤ e | O(log n) |
| `ceiling(E e)` | Smallest element ≥ e | O(log n) |
| `lower(E e)` | Greatest element strictly < e | O(log n) |
| `higher(E e)` | Smallest element strictly > e | O(log n) |
| `pollFirst()` | Remove & return smallest | O(log n) |
| `pollLast()` | Remove & return largest | O(log n) |
| `headSet(E toElement)` | View of elements < toElement | O(log n) |
| `tailSet(E fromElement)` | View of elements ≥ fromElement | O(log n) |
| `subSet(E from, E to)` | View in [from, to) | O(log n) |
| `descendingSet()` | Reverse view | O(1) |
| `size()`, `isEmpty()`, `clear()` | Standard | O(1)/O(n) |

```java
TreeSet<Integer> ts = new TreeSet<>(Arrays.asList(5, 1, 8, 3, 9, 2, 7));
// ts = [1, 2, 3, 5, 7, 8, 9]

ts.first();         // 1
ts.last();          // 9
ts.floor(6);        // 5  (greatest ≤ 6)
ts.ceiling(6);      // 7  (smallest ≥ 6)
ts.lower(5);        // 3  (strictly < 5)
ts.higher(5);       // 7  (strictly > 5)
ts.headSet(5);      // [1, 2, 3]
ts.tailSet(5);      // [5, 7, 8, 9]
ts.subSet(3, 8);    // [3, 5, 7]
ts.pollFirst();     // 1 — removes and returns smallest
```

### From Your Solutions

**Remove duplicates with sorted output** — `_03_removeDuplicates.java`:
```java
static int[] _04_Using_TreeSet(int[] arr) {
    // O(n log n) — sorted unique elements
    TreeSet<Integer> ts = new TreeSet<>();
    for (int number : arr) ts.add(number);
    int[] result = new int[ts.size()];
    int i = 0;
    for (int num : ts) result[i++] = num;  // iteration is in sorted order
    return result;
}
```

---

## 11. Stack

### Purpose
LIFO (Last In, First Out) data structure. The legacy `Stack` class extends `Vector` (synchronized, slow). **Prefer `ArrayDeque` over `Stack` in modern Java** for better performance.

### Declaration & Initialization

```java
import java.util.Stack;
import java.util.ArrayDeque;
import java.util.Deque;

// Legacy (avoid in new code)
Stack<Integer> stack = new Stack<>();

// Modern preferred approach
Deque<Integer> stack2 = new ArrayDeque<>();
```

### Stack Methods (Legacy `java.util.Stack`)

| Method | Description | Throws? | Time |
|--------|-------------|---------|------|
| `push(E item)` | Push onto top | No | O(1) |
| `pop()` | Remove and return top | Yes — `EmptyStackException` | O(1) |
| `peek()` | View top without removing | Yes — `EmptyStackException` | O(1) |
| `empty()` | Check if stack is empty | No | O(1) |
| `search(Object o)` | 1-based distance from top (-1 if absent) | No | O(n) |
| `size()` | Number of elements | No | O(1) |

### ArrayDeque as Stack (Preferred)

| Stack Operation | `ArrayDeque` Method | Description |
|----------------|---------------------|-------------|
| `push(e)` | `push(e)` / `addFirst(e)` | Add to front (top) |
| `pop()` | `pop()` / `removeFirst()` | Remove from front (top) |
| `peek()` | `peek()` / `peekFirst()` | View front without removing |
| `isEmpty()` | `isEmpty()` | Check if empty |

```java
Deque<Integer> stack = new ArrayDeque<>();

stack.push(10);   // [10]
stack.push(20);   // [20, 10]  <- top is 20
stack.push(30);   // [30, 20, 10]
stack.peek();     // 30  (top, no removal)
stack.pop();      // 30  -> [20, 10]
stack.isEmpty();  // false

// Classic use: balanced parentheses
String s = "({[]})";
Deque<Character> stk = new ArrayDeque<>();
for (char c : s.toCharArray()) {
    if (c == '(' || c == '{' || c == '[') {
        stk.push(c);
    } else {
        if (stk.isEmpty()) return false;
        char top = stk.pop();
        if (c == ')' && top != '(') return false;
        if (c == '}' && top != '{') return false;
        if (c == ']' && top != '[') return false;
    }
}
return stk.isEmpty();
```

### Complexity Summary

| Operation | Time |
|-----------|------|
| `push` | O(1) amortized |
| `pop` | O(1) |
| `peek` | O(1) |
| `isEmpty` | O(1) |
| Space | O(n) |

---

## 12. Queue & Deque (ArrayDeque)

### Purpose
- **Queue**: FIFO (First In, First Out). Elements added at tail, removed from head. BFS, task scheduling, sliding window.
- **Deque**: Double-ended queue. Elements can be added/removed from both ends. Can work as both Stack and Queue.

**`ArrayDeque`** is the recommended implementation for both Queue and Stack — faster than `LinkedList` (no node allocation overhead, cache-friendly array).

### Declaration & Initialization

```java
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Deque;

// As Queue (FIFO)
Queue<Integer> queue = new ArrayDeque<>();
Queue<String> queue2 = new LinkedList<>();  // also valid

// As Deque
Deque<Integer> deque = new ArrayDeque<>();
```

### Queue Methods

| Method | Throws if empty/full? | Description | Time |
|--------|-----------------------|-------------|------|
| `offer(E e)` | No | Enqueue (add to tail) | O(1) amortized |
| `add(E e)` | Yes (capacity) | Enqueue (add to tail) | O(1) amortized |
| `poll()` | No — returns null | Dequeue (remove from head) | O(1) |
| `remove()` | Yes — `NoSuchElementException` | Dequeue | O(1) |
| `peek()` | No — returns null | View head without removing | O(1) |
| `element()` | Yes | View head without removing | O(1) |
| `isEmpty()` | — | Check if empty | O(1) |
| `size()` | — | Number of elements | O(1) |

### Deque Methods

| Operation | Front (head) | Rear (tail) |
|-----------|-------------|-------------|
| Add | `addFirst(e)` / `offerFirst(e)` | `addLast(e)` / `offerLast(e)` |
| Remove | `removeFirst()` / `pollFirst()` | `removeLast()` / `pollLast()` |
| Peek | `peekFirst()` / `getFirst()` | `peekLast()` / `getLast()` |

```java
// Queue (FIFO) — BFS pattern
Queue<Integer> queue = new ArrayDeque<>();
queue.offer(1);   // [1]
queue.offer(2);   // [1, 2]
queue.offer(3);   // [1, 2, 3]
queue.peek();     // 1 (head, no removal)
queue.poll();     // 1 → [2, 3]
queue.poll();     // 2 → [3]

// Deque — Sliding window maximum
Deque<Integer> deque = new ArrayDeque<>();
int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
int k = 3;
// (implementation uses deque to maintain max in current window)
deque.offerLast(0);  // store indices
deque.peekFirst();   // front = max of window
deque.pollFirst();   // evict out-of-window elements
```

### Complexity Summary

| Operation | ArrayDeque | LinkedList |
|-----------|-----------|------------|
| `offer`/`add` | O(1) amortized | O(1) |
| `poll`/`remove` | O(1) | O(1) |
| `peek` | O(1) | O(1) |
| `get(index)` | O(n) | O(n) |
| Memory | Lower (array) | Higher (node overhead) |

---

## 13. PriorityQueue (Heap)

### Purpose
Min-Heap by default (smallest element always at top). Max-Heap with `Comparator.reverseOrder()`. Used for: Kth largest/smallest, median finding, task scheduling by priority, Dijkstra's algorithm.

**Internally:** Binary heap stored as an array. Parent at index `i`, children at `2i+1` and `2i+2`.

### Declaration & Initialization

```java
import java.util.PriorityQueue;
import java.util.Comparator;

// Min-Heap (default) — smallest element polled first
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// Max-Heap — largest element polled first
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
// or: new PriorityQueue<>((a, b) -> b - a);

// Custom comparator (e.g., sort by string length)
PriorityQueue<String> pq = new PriorityQueue<>((a, b) -> a.length() - b.length());

// Initial capacity
PriorityQueue<Integer> pq2 = new PriorityQueue<>(10);

// Initialize from collection (heapify — O(n))
PriorityQueue<Integer> pq3 = new PriorityQueue<>(Arrays.asList(5, 1, 3, 2, 4));
```

### All Methods

| Method | Description | Throws? | Time |
|--------|-------------|---------|------|
| `offer(E e)` / `add(E e)` | Insert element | add: Yes | O(log n) |
| `poll()` | Remove and return min/max | No — null | O(log n) |
| `remove()` | Remove and return min/max | Yes — exception | O(log n) |
| `peek()` | View min/max without removing | No — null | O(1) |
| `element()` | View min/max without removing | Yes | O(1) |
| `remove(Object o)` | Remove specific element (linear scan!) | No | O(n) |
| `contains(Object o)` | Check if element exists | No | O(n) |
| `size()` | Number of elements | No | O(1) |
| `isEmpty()` | Check if empty | No | O(1) |
| `clear()` | Remove all elements | No | O(n) |
| `toArray()` | Snapshot — NOT sorted! | No | O(n) |

> ⚠️ **Critical:** `PriorityQueue` does NOT support random access. `toArray()` is NOT sorted. Only `peek()`/`poll()` give you the min/max.

```java
// Min-Heap example
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
minHeap.offer(5); minHeap.offer(1); minHeap.offer(3); minHeap.offer(2);
// Internal structure: not sorted array, just heap property maintained

minHeap.peek();  // 1 — minimum, no removal
minHeap.poll();  // 1 — removed, next: 2
minHeap.poll();  // 2
// draining: always gives sorted order
while (!minHeap.isEmpty()) System.out.print(minHeap.poll() + " ");  // 3 5

// Max-Heap example
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
maxHeap.offer(5); maxHeap.offer(1); maxHeap.offer(3);
maxHeap.peek();   // 5 — maximum
maxHeap.poll();   // 5
```

### Complexity Summary

| Operation | Time |
|-----------|------|
| `offer` / `add` | O(log n) |
| `poll` / `remove()` | O(log n) |
| `peek` | O(1) |
| `remove(Object)` | O(n) — linear scan |
| `contains` | O(n) — linear scan |
| Build from n elements | O(n) heapify |
| Space | O(n) |

### From Your Solutions

**Median of array** — `_071_Median_Of_Array.java`:
```java
// Max-heap (lower half) + Min-heap (upper half)
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

for (int num : nums) {
    maxHeap.offer(num);
    minHeap.offer(maxHeap.poll());  // balance: push to max, pop max to min
    if (maxHeap.size() < minHeap.size()) {
        maxHeap.offer(minHeap.poll());  // re-balance sizes
    }
}
// maxHeap.peek() = lower median, minHeap.peek() = upper median
return maxHeap.size() > minHeap.size() ? maxHeap.peek()
                                       : (maxHeap.peek() + minHeap.peek()) / 2.0;
```

**Kth smallest element** — `_064_Heap_Kth_Smallest.java`:
```java
// Max-heap of size k: keep only k smallest
Heap maxHeap = new Heap();
for (int num : nums) {
    maxHeap.insert(num);
    if (maxHeap.getHeap().size() > k) maxHeap.remove();  // evict largest
}
return maxHeap.remove();  // top of max-heap = kth smallest
// O(n log k) time, O(k) space
```

**Custom Max Heap implementation** — `_060_Heap_Max_Insert.java`:
```java
// Max-Heap built on ArrayList
private List<Integer> heap = new ArrayList<>();
// Insert: add to end, bubble up
// Remove: swap root with last, remove last, sink down
```

---

## 14. Comparison Tables

### Map Implementations

| Feature | `HashMap` | `LinkedHashMap` | `TreeMap` | `Hashtable` |
|---------|-----------|-----------------|-----------|-------------|
| Order | None | Insertion / Access | Sorted (key) | None |
| `null` key | 1 allowed | 1 allowed | ❌ Not allowed | ❌ |
| `null` value | Many | Many | Many | ❌ |
| `get`/`put` speed | O(1) avg | O(1) avg | O(log n) | O(1) avg |
| Thread-safe | ❌ | ❌ | ❌ | ✅ (legacy) |
| Navigation methods | ❌ | ❌ | ✅ floor/ceiling | ❌ |
| Best use case | General purpose | LRU cache, ordered output | Range queries, sorted keys | Legacy code only |
| Internal structure | Hash table | Hash table + DLL | Red-Black Tree | Hash table |

### Set Implementations

| Feature | `HashSet` | `LinkedHashSet` | `TreeSet` |
|---------|-----------|-----------------|-----------|
| Order | None | Insertion order | Natural/custom sorted |
| `null` | 1 allowed | 1 allowed | ❌ (NullPointerException) |
| `add`/`contains` | O(1) avg | O(1) avg | O(log n) |
| Navigation methods | ❌ | ❌ | ✅ floor/ceiling/first/last |
| Best use case | Fast lookup, dedup | Ordered dedup | Sorted unique elements, range |
| Internal structure | HashMap keys | LinkedHashMap keys | TreeMap keys |

### List Implementations

| Feature | `ArrayList` | `LinkedList` | `Vector` |
|---------|-------------|--------------|---------|
| Access by index | O(1) | O(n) | O(1) |
| Add at end | O(1) amortized | O(1) | O(1) amortized |
| Add at start | O(n) | O(1) | O(n) |
| Remove from middle | O(n) | O(n) (find) + O(1) (unlink) | O(n) |
| Memory | Less (array) | More (node overhead) | Less (array) |
| Thread-safe | ❌ | ❌ | ✅ (legacy, slow) |
| As Queue/Deque | ❌ | ✅ | ❌ |
| Best use case | General, random access | Frequent head/tail ops, as Queue | Legacy only |

### Queue/Stack Implementations

| Feature | `Stack` | `ArrayDeque` | `LinkedList` | `PriorityQueue` |
|---------|---------|-------------|--------------|-----------------|
| Order | LIFO | LIFO or FIFO | LIFO or FIFO | Priority (min/max) |
| `push`/`pop` speed | O(1) | O(1) amortized | O(1) | O(log n) |
| `null` allowed | ✅ | ❌ | ✅ | ❌ |
| Thread-safe | ✅ (legacy, slow) | ❌ | ❌ | ❌ |
| Best use case | Legacy only | Stack or Queue (preferred) | As Queue or Deque | Kth element, priority |
| Internal structure | Vector (array) | Resizable array | Doubly-linked list | Binary heap array |

---

## 15. When to Use Which — Decision Guide

### Need to store key-value pairs?
```
                    ┌─ Need sorted keys?          → TreeMap  (O(log n))
                    │   └─ Need range queries?     → TreeMap  (floor/ceiling/headMap)
HashMap-like? ──────┤
                    ├─ Need insertion order?       → LinkedHashMap
                    │   └─ Building LRU cache?     → LinkedHashMap (access-order mode)
                    └─ None of above?              → HashMap  (O(1) — fastest)
```

### Need to store unique elements?
```
                    ┌─ Need sorted elements?       → TreeSet  (O(log n))
                    │   └─ Need range queries?     → TreeSet  (floor/ceiling/headSet)
Set? ───────────────┤
                    ├─ Need insertion order?       → LinkedHashSet
                    └─ None of above?              → HashSet  (O(1) — fastest)
```

### Need an ordered sequence (duplicates OK)?
```
                    ┌─ Random access by index?     → ArrayList
                    │
List? ──────────────┤─ Frequent add/remove ends?   → LinkedList (or ArrayDeque)
                    │
                    └─ Both? (rare)                → ArrayList if reads >> writes
```

### Need LIFO (stack) behavior?
```
→ ArrayDeque (preferred over Stack class)
  Use: push(), pop(), peek(), isEmpty()
```

### Need FIFO (queue) behavior?
```
→ ArrayDeque (preferred over LinkedList for queue)
  Use: offer(), poll(), peek(), isEmpty()
```

### Need priority / min / max efficiently?
```
→ PriorityQueue
  Min-heap: new PriorityQueue<>()
  Max-heap: new PriorityQueue<>(Comparator.reverseOrder())

Common patterns:
  Kth largest  → Min-heap of size k
  Kth smallest → Max-heap of size k
  Median       → Max-heap (lower half) + Min-heap (upper half)
```

### Need fast membership check?
```
Membership only (no value) → HashSet    O(1)
Key-value lookup           → HashMap    O(1)
Sorted + navigation        → TreeSet / TreeMap  O(log n)
```

### Interview Pattern → Collection Map

| Problem Pattern | Best Collection | Why |
|----------------|-----------------|-----|
| Frequency counting | `HashMap<E, Integer>` | O(1) get/put |
| Two Sum / pair problems | `HashMap<value, index>` | O(1) complement lookup |
| Duplicate detection | `HashSet` | O(1) contains |
| Group by key | `HashMap<K, List<V>>` | Group into lists |
| Kth largest/smallest | `PriorityQueue` | O(n log k) |
| Sliding window | `ArrayDeque` | O(1) add/remove both ends |
| BFS | `Queue` (ArrayDeque) | FIFO processing |
| DFS / recursion tracking | `Stack` (ArrayDeque) or call stack | LIFO |
| Sorted unique elements | `TreeSet` | Auto-sorted + O(log n) ops |
| Sorted map | `TreeMap` | Navigation methods |
| Preserve order + unique | `LinkedHashSet` | Ordered dedup |
| LRU Cache | `LinkedHashMap` | Access-order eviction |
| Consecutive sequence | `HashSet` | O(1) range check |
| Anagram grouping | `HashMap<sorted_str, List>` | Canonical key |

---

## 16. Interview Q&A

### Arrays

**Q: What is the difference between `Array` and `ArrayList`?**
> `Array` is a fixed-size primitive/object structure with O(1) access. `ArrayList` is dynamic, backed by an array that resizes automatically. Arrays can store primitives (int[], double[]); ArrayList only stores objects (uses autoboxing). Arrays have `.length` field; ArrayList has `.size()` method.

**Q: What does `Arrays.asList()` return?**
> It returns a **fixed-size** `List` backed by the original array. You can call `set()` but NOT `add()` or `remove()` — those throw `UnsupportedOperationException`. To get a mutable list: `new ArrayList<>(Arrays.asList(...))`.

**Q: How do you sort an array in descending order?**
```java
Integer[] arr = {5, 2, 8, 1};                    // must be Integer[], not int[]
Arrays.sort(arr, Comparator.reverseOrder());
// For int[]: sort ascending then reverse manually, or use streams
int[] primitiveArr = {5, 2, 8, 1};
int[] sorted = IntStream.of(primitiveArr).boxed()
    .sorted(Comparator.reverseOrder())
    .mapToInt(Integer::intValue).toArray();
```

---

### HashMap / HashSet

**Q: How does `HashMap` work internally?**
> `HashMap` uses an array of buckets. Each key is hashed (`hashCode()` + bit operations), and the hash determines the bucket index. Collisions are resolved by chaining (linked list). In Java 8+, when a bucket has >8 entries, the linked list converts to a Red-Black Tree for O(log n) worst case. Default capacity is 16, load factor 0.75 — resize (double + rehash) when 75% full.

**Q: What is the difference between `HashMap`, `LinkedHashMap`, and `TreeMap`?**
> - `HashMap`: O(1) get/put, no order guarantee  
> - `LinkedHashMap`: O(1) get/put, maintains insertion (or access) order  
> - `TreeMap`: O(log n) get/put, always sorted by key, supports navigation (floor/ceiling)

**Q: Can `HashMap` have a `null` key? Can `TreeMap`?**
> `HashMap`: Yes, exactly 1 null key and multiple null values.  
> `TreeMap`: No — null keys throw `NullPointerException` because keys must be compared.

**Q: What is `getOrDefault()` and why is it useful?**
> `map.getOrDefault(key, defaultValue)` returns the value if the key exists, otherwise returns `defaultValue`. Eliminates null checks in frequency counting:
> ```java
> map.put(key, map.getOrDefault(key, 0) + 1);  // vs checking containsKey first
> ```

**Q: What is the difference between `HashMap` and `Hashtable`?**
> `Hashtable` is synchronized (thread-safe), doesn't allow null keys/values, and is legacy. `HashMap` is not synchronized, allows one null key, and is faster. For thread safety, prefer `ConcurrentHashMap` over `Hashtable`.

**Q: `HashSet` vs `HashMap` — which to use when?**
> Use `HashSet` when you only care about presence/absence of elements (no value attached). Use `HashMap` when you need to associate a value with each key. Internally, `HashSet` IS a `HashMap` where all values are `Boolean.TRUE`.

---

### ArrayList / LinkedList

**Q: When would you choose `LinkedList` over `ArrayList`?**
> Choose `LinkedList` when you need frequent insertions/deletions at the **head or tail** (O(1) vs O(n)). Choose `ArrayList` for random access (O(1)) and when memory is a concern. In practice, `ArrayList` outperforms `LinkedList` in most scenarios due to better cache locality.

**Q: How does `ArrayList` resize?**
> Default capacity is 10. When full, it creates a new array of capacity `(old * 3/2 + 1)` ≈ 50% growth, copies all elements. This is why `add()` is O(1) amortized — occasional O(n) resize spreads cost.

**Q: What is the difference between `remove(int index)` and `remove(Object o)` in ArrayList?**
> `remove(int index)` removes by position. `remove(Object o)` removes the first occurrence by value. Ambiguity with Integer:
> ```java
> List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
> list.remove(1);              // removes INDEX 1 → [1, 3]
> list.remove(Integer.valueOf(1)); // removes VALUE 1 → [2, 3]
> ```

---

### Stack / Queue / PriorityQueue

**Q: Why prefer `ArrayDeque` over `Stack` for stack operations?**
> `Stack` extends `Vector` which is synchronized — unnecessary overhead. `ArrayDeque` is not thread-safe but much faster (no synchronization). `ArrayDeque` is backed by a resizable array (cache-friendly), while `Stack`/`Vector` have the legacy overhead. Java docs themselves recommend `ArrayDeque` for stack use.

**Q: What is the difference between `offer()` and `add()` in Queue?**
> Both add to the queue. `add()` throws `IllegalStateException` if capacity-restricted queue is full. `offer()` returns `false` instead. For unbounded queues (`LinkedList`, `ArrayDeque`), they behave identically.

**Q: What is the difference between `poll()` and `remove()` in Queue?**
> Both remove and return the head. `remove()` throws `NoSuchElementException` if empty. `poll()` returns `null` if empty. Prefer `poll()` to avoid exception handling.

**Q: How do you implement a Min-Heap and Max-Heap in Java?**
```java
PriorityQueue<Integer> minHeap = new PriorityQueue<>();                      // default
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
// or maxHeap = new PriorityQueue<>((a, b) -> b - a);
```

**Q: What is the time complexity of finding the Kth largest element?**
> Using a min-heap of size k: O(n log k). For each element, offer to heap; if size > k, poll (removes smallest). The heap top is the Kth largest. From `_064_Heap_Kth_Smallest.java` — same pattern with max-heap for Kth smallest.

**Q: How does `PriorityQueue` handle duplicate elements?**
> `PriorityQueue` allows duplicates. The heap property (parent ≤ children for min-heap) is maintained but duplicates are stored as separate entries.

---

### TreeSet / TreeMap

**Q: What comparator does `TreeSet` use by default?**
> Natural ordering — elements must implement `Comparable`. For integers: ascending numeric. For strings: lexicographic. Custom ordering via constructor: `new TreeSet<>(Comparator.reverseOrder())`.

**Q: What is `floor()` vs `lower()` in TreeSet/TreeMap?**
> - `floor(e)` → greatest element **≤** e (inclusive)  
> - `lower(e)` → greatest element **<** e (strictly less)  
> - `ceiling(e)` → smallest element **≥** e (inclusive)  
> - `higher(e)` → smallest element **>** e (strictly greater)

---

### General

**Q: What is the difference between `Comparable` and `Comparator`?**
> - `Comparable` (`java.lang`): Natural ordering, implemented by the class itself (`compareTo()`). One ordering per class.  
> - `Comparator` (`java.util`): External ordering, passed as argument. Multiple orderings possible.
> ```java
> // Comparable
> class Student implements Comparable<Student> {
>     public int compareTo(Student other) { return this.age - other.age; }
> }
> // Comparator
> Comparator<Student> byName = (a, b) -> a.name.compareTo(b.name);
> students.sort(byName);
> ```

**Q: What is `fail-fast` vs `fail-safe` iterators?**
> - **Fail-fast** (`ArrayList`, `HashMap`, `HashSet`): Throw `ConcurrentModificationException` if the collection is modified during iteration (detects via `modCount`). Use `Iterator.remove()` to safely remove during iteration.
> - **Fail-safe** (`CopyOnWriteArrayList`, `ConcurrentHashMap`): Work on a copy of the collection, no exception but may see stale data.

**Q: How do you make a collection thread-safe?**
```java
// Option 1: Synchronized wrapper (legacy)
List<String> syncList = Collections.synchronizedList(new ArrayList<>());
Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());

// Option 2: Concurrent collections (preferred)
Map<String, Integer> concMap = new ConcurrentHashMap<>();
List<String> cowList = new CopyOnWriteArrayList<>();
Queue<Integer> concQueue = new ConcurrentLinkedQueue<>();
```

**Q: What is the difference between `Collections.sort()` and `Arrays.sort()`?**
> `Collections.sort(List)` sorts a `List` using TimSort — O(n log n), stable. `Arrays.sort(int[])` uses Dual-Pivot Quicksort — O(n log n) average, not stable. `Arrays.sort(Object[])` uses TimSort (stable). Use `Collections.sort()` for Lists, `Arrays.sort()` for arrays.

**Q: What does "fail-fast" mean and how does it work?**
> When you modify a collection (add/remove) while iterating with an `Iterator` (not using `Iterator.remove()`), the iterator detects a `modCount` change and throws `ConcurrentModificationException`. This is a protection against inconsistent iteration state — it "fails fast" rather than silently producing wrong results.

---

*Document created for Sr SDET interview preparation. Based on 196+ coding solutions across InterviewQuestions, Leetcode, and Udemy packages.*
