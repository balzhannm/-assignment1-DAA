# REPORT: Assignment 1 - Divide & Conquer & Asymptotic Notations

## 1. Asymptotic Bounds Table
| Algorithm | Best Case | Average Case | Worst Case | Reason / Input Causing Case |
| :--- | :--- | :--- | :--- | :--- |
| **MergeSort** | $\Theta(n \log n)$ | $\Theta(n \log n)$ | $\Theta(n \log n)$ | Always divides array in half and performs linear merge[cite: 1]. |
| **QuickSort** | $\Theta(n \log n)$ | $\Theta(n \log n)$ | $\Theta(n \log n)$ | Randomized pivot + 3-way partition avoids $O(n^2)$ on duplicates and sorted arrays[cite: 1]. |
| **QuickSelect**| $\Theta(n)$ | $\Theta(n)$ | $\Theta(n^2)$ | Worst case avoided on average by random pivot; only recurses into one side[cite: 1]. |
| **Insertion Sort**| $\Theta(n)$ | $\Theta(n^2)$ | $\Theta(n^2)$ | Best case on sorted array; worst on reverse sorted (used as cutoff for $n \le 15$)[cite: 1]. |

## 2. Recurrences and Master Theorem
* **MergeSort**:
    - Recurrence: $T(n) = 2T(n/2) + \Theta(n)$[cite: 1]
    - Parameters: $a = 2, b = 2, f(n) = \Theta(n)$
    - Master Theorem: Case 2 ($f(n) = \Theta(n^{\log_b a})$) -> Result: $\Theta(n \log n)$[cite: 1]

* **QuickSort (Balanced Split)**:
    - Recurrence: $T(n) = 2T(n/2) + \Theta(n)$[cite: 1]
    - Result: $\Theta(n \log n)$[cite: 1]. *Explanation*: Random pivot ensures balanced splits with high probability, achieving $O(n \log n)$ on average[cite: 1].

* **QuickSelect (Balanced Split)**:
    - Recurrence: $T(n) = T(n/2) + \Theta(n)$[cite: 1]
    - Parameters: $a = 1, b = 2, f(n) = \Theta(n)$
    - Master Theorem: Case 3 -> Result: $\Theta(n)$[cite: 1]

## 3. Discussion
1. The empirical measurements closely match the theoretical bounds ($\mathcal{O}, \Omega, \Theta$)[cite: 1].
2. Initial runs showed higher execution times due to JVM warm-up and JIT compilation, which is why we used the median of 5 runs[cite: 1].
3. The insertion sort cutoff for subarrays $\le 15$ elements successfully reduced recursion overhead and improved practical performance[cite: 1].
4. CPU cache locality and the reusable buffer in MergeSort prevented memory churn and stack overflows[cite: 1].