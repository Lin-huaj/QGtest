#include "sorts.h"
#include <stdlib.h>

// 插入排序
void insertion_sort(int arr[], int n) {
    for (int i = 1; i < n; i++) {
        int key = arr[i];
        int j = i - 1;
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j];
            j--;
        }
        arr[j + 1] = key;
    }
}

// 归并排序辅助函数
static void merge(int arr[], int l, int m, int r) {
    int n1 = m - l + 1;
    int n2 = r - m;
    int* L = malloc(n1 * sizeof(int));
    int* R = malloc(n2 * sizeof(int));

    for (int i = 0; i < n1; i++)
        L[i] = arr[l + i];
    for (int j = 0; j < n2; j++)
        R[j] = arr[m + 1 + j];

    int i = 0, j = 0, k = l;
    while (i < n1 && j < n2) {
        if (L[i] <= R[j])
            arr[k++] = L[i++];
        else
            arr[k++] = R[j++];
    }

    while (i < n1) arr[k++] = L[i++];
    while (j < n2) arr[k++] = R[j++];

    free(L);
    free(R);
}

static void merge_sort(int arr[], int l, int r) {
    if (l < r) {
        int m = l + (r - l) / 2;
        merge_sort(arr, l, m);
        merge_sort(arr, m + 1, r);
        merge(arr, l, m, r);
    }
}

void merge_sort_wrapper(int arr[], int n) {
    merge_sort(arr, 0, n - 1);
}

// 快速排序辅助函数
static void swap(int* a, int* b) {
    int t = *a;
    *a = *b;
    *b = t;
}

static int partition(int arr[], int low, int high) {
    int pivot = arr[high];
    int i = (low - 1);

    for (int j = low; j < high; j++) {
        if (arr[j] < pivot) {
            i++;
            swap(&arr[i], &arr[j]);
        }
    }
    swap(&arr[i + 1], &arr[high]);
    return (i + 1);
}

static void quick_sort(int arr[], int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);
        quick_sort(arr, low, pi - 1);
        quick_sort(arr, pi + 1, high);
    }
}

void quick_sort_wrapper(int arr[], int n) {
    quick_sort(arr, 0, n - 1);
}

// 计数排序
void counting_sort(int arr[], int n) {
    if (n <= 0) return;

    int max = arr[0], min = arr[0];
    for (int i = 1; i < n; i++) {
        if (arr[i] > max) max = arr[i];
        if (arr[i] < min) min = arr[i];
    }

    int range = max - min + 1;
    int* count = calloc(range, sizeof(int));
    int* output = malloc(n * sizeof(int));

    for (int i = 0; i < n; i++)
        count[arr[i] - min]++;

    for (int i = 1; i < range; i++)
        count[i] += count[i - 1];

    for (int i = n - 1; i >= 0; i--) {
        output[count[arr[i] - min] - 1] = arr[i];
        count[arr[i] - min]--;
    }

    for (int i = 0; i < n; i++)
        arr[i] = output[i];

    free(count);
    free(output);
}

// 基数排序辅助函数
static int get_max(int arr[], int n) {
    int max = arr[0];
    for (int i = 1; i < n; i++)
        if (arr[i] > max) max = arr[i];
    return max;
}

// 计数排序按位排序
static void counting_sort_by_digit(int arr[], int n, int exp) {
    int* output = malloc(n * sizeof(int));
    int count[10] = { 0 };

    for (int i = 0; i < n; i++)
        count[(arr[i] / exp) % 10]++;

    for (int i = 1; i < 10; i++)
        count[i] += count[i - 1];

    for (int i = n - 1; i >= 0; i--) {
        int index = (arr[i] / exp) % 10;
        output[count[index] - 1] = arr[i];
        count[index]--;
    }

    for (int i = 0; i < n; i++)
        arr[i] = output[i];

    free(output);
}

// 基数排序
void radix_sort(int arr[], int n) {
    int max = get_max(arr, n);
    for (int exp = 1; max / exp > 0; exp *= 10)
        counting_sort_by_digit(arr, n, exp);
}

// 快速排序非递归版本（用栈实现）
void quick_sort_non_recursive(int arr[], int n) {
    if (n <= 1) return;

    // 使用动态数组模拟栈（记录待处理区间）
    int* stack = malloc(n * 2 * sizeof(int));
    int top = -1;
    stack[++top] = 0;   // 压入左边界
    stack[++top] = n - 1; // 压入右边界

    while (top >= 0) {
        int high = stack[top--];
        int low = stack[top--];

        int pi = partition(arr, low, high);

        // 压入左子区间
        if (pi - 1 > low) {
            stack[++top] = low;
            stack[++top] = pi - 1;
        }

        // 压入右子区间
        if (pi + 1 < high) {
            stack[++top] = pi + 1;
            stack[++top] = high;
        }
    }

    free(stack);
}

// 冒泡排序优化版（三个优化点）
void bubble_sort_optimized(int arr[], int n) {
    int last_swap_pos = n - 1; // 记录最后一次交换位置
    for (int i = 0; i < n - 1; ) {
        int new_end = 0; // 本轮新的结束位置
        int swapped = 0; // 是否发生交换

        // 正向扫描
        for (int j = 0; j < last_swap_pos; j++) {
            if (arr[j] > arr[j + 1]) {
                swap(&arr[j], &arr[j + 1]);
                new_end = j;
                swapped = 1;
            }
        }
        if (!swapped) break; // 优化1：无交换则提前终止
        last_swap_pos = new_end;

        // 反向扫描（鸡尾酒优化）
        swapped = 0;
        for (int j = last_swap_pos; j > i; j--) {
            if (arr[j] < arr[j - 1]) {
                swap(&arr[j], &arr[j - 1]);
                swapped = 1;
            }
        }
        if (!swapped) break;
        i++;
    }
}

// 随机选择枢轴并交换到末尾
static int random_partition(int arr[], int low, int high) {
    int random_idx = low + rand() % (high - low + 1);
    swap(&arr[random_idx], &arr[high]);
    return partition(arr, low, high);
}

// 随机化快速排序
static void quick_sort_random(int arr[], int low, int high) {
    if (low < high) {
        int pi = random_partition(arr, low, high);
        quick_sort_random(arr, low, pi - 1);
        quick_sort_random(arr, pi + 1, high);
    }
}

void quick_sort_random_wrapper(int arr[], int n) {
    srand(time(NULL)); // 初始化随机种子
    quick_sort_random(arr, 0, n - 1);
}

// 双枢轴快速排序（三路划分优化）
static void dual_pivot_partition(int arr[], int low, int high, int* lp, int* rp) {
    if (arr[low] > arr[high]) swap(&arr[low], &arr[high]);

    int j = low + 1;
    int g = high - 1, k = low + 1;
    int p = arr[low], q = arr[high];

    while (k <= g) {
        if (arr[k] < p) {
            swap(&arr[k], &arr[j]);
            j++;
        }
        else if (arr[k] >= q) {
            while (arr[g] > q && k < g) g--;
            swap(&arr[k], &arr[g]);
            g--;
            if (arr[k] < p) {
                swap(&arr[k], &arr[j]);
                j++;
            }
        }
        k++;
    }
    j--;
    g++;

    swap(&arr[low], &arr[j]);
    swap(&arr[high], &arr[g]);

    *lp = j; // 左枢轴位置
    *rp = g; // 右枢轴位置
}
// 双枢轴快速排序
static void dual_pivot_quick_sort(int arr[], int low, int high) {
    if (low < high) {
        int lp, rp;
        dual_pivot_partition(arr, low, high, &lp, &rp);
        dual_pivot_quick_sort(arr, low, lp - 1);
        dual_pivot_quick_sort(arr, lp + 1, rp - 1);
        dual_pivot_quick_sort(arr, rp + 1, high);
    }
}
// 双枢轴快速排序包装函数
void quick_sort_dual_pivot_wrapper(int arr[], int n) {
    dual_pivot_quick_sort(arr, 0, n - 1);
}