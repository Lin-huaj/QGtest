#include "sorts.h"
#include <stdlib.h>

// ��������
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

// �鲢����������
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

// ��������������
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

// ��������
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

// ��������������
static int get_max(int arr[], int n) {
    int max = arr[0];
    for (int i = 1; i < n; i++)
        if (arr[i] > max) max = arr[i];
    return max;
}

// ��������λ����
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

// ��������
void radix_sort(int arr[], int n) {
    int max = get_max(arr, n);
    for (int exp = 1; max / exp > 0; exp *= 10)
        counting_sort_by_digit(arr, n, exp);
}

// ��������ǵݹ�汾����ջʵ�֣�
void quick_sort_non_recursive(int arr[], int n) {
    if (n <= 1) return;

    // ʹ�ö�̬����ģ��ջ����¼���������䣩
    int* stack = malloc(n * 2 * sizeof(int));
    int top = -1;
    stack[++top] = 0;   // ѹ����߽�
    stack[++top] = n - 1; // ѹ���ұ߽�

    while (top >= 0) {
        int high = stack[top--];
        int low = stack[top--];

        int pi = partition(arr, low, high);

        // ѹ����������
        if (pi - 1 > low) {
            stack[++top] = low;
            stack[++top] = pi - 1;
        }

        // ѹ����������
        if (pi + 1 < high) {
            stack[++top] = pi + 1;
            stack[++top] = high;
        }
    }

    free(stack);
}

// ð�������Ż��棨�����Ż��㣩
void bubble_sort_optimized(int arr[], int n) {
    int last_swap_pos = n - 1; // ��¼���һ�ν���λ��
    for (int i = 0; i < n - 1; ) {
        int new_end = 0; // �����µĽ���λ��
        int swapped = 0; // �Ƿ�������

        // ����ɨ��
        for (int j = 0; j < last_swap_pos; j++) {
            if (arr[j] > arr[j + 1]) {
                swap(&arr[j], &arr[j + 1]);
                new_end = j;
                swapped = 1;
            }
        }
        if (!swapped) break; // �Ż�1���޽�������ǰ��ֹ
        last_swap_pos = new_end;

        // ����ɨ�裨��β���Ż���
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

// ���ѡ�����Ტ������ĩβ
static int random_partition(int arr[], int low, int high) {
    int random_idx = low + rand() % (high - low + 1);
    swap(&arr[random_idx], &arr[high]);
    return partition(arr, low, high);
}

// �������������
static void quick_sort_random(int arr[], int low, int high) {
    if (low < high) {
        int pi = random_partition(arr, low, high);
        quick_sort_random(arr, low, pi - 1);
        quick_sort_random(arr, pi + 1, high);
    }
}

void quick_sort_random_wrapper(int arr[], int n) {
    srand(time(NULL)); // ��ʼ���������
    quick_sort_random(arr, 0, n - 1);
}

// ˫�������������·�����Ż���
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

    *lp = j; // ������λ��
    *rp = g; // ������λ��
}
// ˫�����������
static void dual_pivot_quick_sort(int arr[], int low, int high) {
    if (low < high) {
        int lp, rp;
        dual_pivot_partition(arr, low, high, &lp, &rp);
        dual_pivot_quick_sort(arr, low, lp - 1);
        dual_pivot_quick_sort(arr, lp + 1, rp - 1);
        dual_pivot_quick_sort(arr, rp + 1, high);
    }
}
// ˫������������װ����
void quick_sort_dual_pivot_wrapper(int arr[], int n) {
    dual_pivot_quick_sort(arr, 0, n - 1);
}