#define _CRT_SECURE_NO_WARNINGS
#include "test_utils.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include "sorts.h"

SortAlgorithm algorithms[] = {       //�����㷨����
    {"��������", insertion_sort},
    {"�鲢����", merge_sort_wrapper},
    {"�������򣨵ݹ飩", quick_sort_wrapper},
    {"��������", counting_sort},
	{"��������", radix_sort},
	{"�ǵݹ��������", quick_sort_non_recursive},
	{"�Ż���ð������", bubble_sort_optimized},
	{"�������������", quick_sort_random_wrapper},
	{"˫�����������", quick_sort_dual_pivot_wrapper}
};
const int NUM_ALGORITHMS = sizeof(algorithms) / sizeof(algorithms[0]);     //�����㷨����

void generate_random_data(int arr[], int n) {        // �����������
    srand(time(NULL));
    for (int i = 0; i < n; i++)
        arr[i] = rand() % 100000;
}

void generate_ascending_data(int arr[], int n) {    // ������������
    for (int i = 0; i < n; i++)
        arr[i] = i;
}

void generate_descending_data(int arr[], int n) {       // ���ɽ�������
    for (int i = 0; i < n; i++)
        arr[i] = n - i - 1;
}

void generate_constant_data(int arr[], int n) {         // ���ɳ�������
    for (int i = 0; i < n; i++) 
        arr[i] = 42;
}

void save_data_to_file(const char* filename, int arr[], int n) {    // �������ݵ��ļ�
    FILE* file = fopen(filename, "w");
    if (!file) {
        perror("Failed to open file");
        return;
    }
    for (int i = 0; i < n; i++)
        fprintf(file, "%d\n", arr[i]);
    fclose(file);
}

int* load_data_from_file(const char* filename, int* n) {    // ���ļ���������
    FILE* file = fopen(filename, "r");
    if (!file) {
        perror("Failed to open file");
        return NULL;
    }

    int capacity = 10000;
    int* data = malloc(capacity * sizeof(int));
    *n = 0;
    int value;

    while (fscanf(file, "%d", &value) == 1) {
        if (*n >= capacity) {
            capacity *= 2;
            data = realloc(data, capacity * sizeof(int));
            if (!data) {
                fclose(file);
                return NULL;
            }
        }
        data[(*n)++] = value;
    }

    fclose(file);
    return data;
}

double measure_sorting_time(void (*sort_func)(int*, int), int arr[], int n) {          // ��������ʱ��
    int* temp = malloc(n * sizeof(int));
    memcpy(temp, arr, n * sizeof(int));

    clock_t start = clock();
    sort_func(temp, n);
    clock_t end = clock();

    free(temp);
    return (double)(end - start) / CLOCKS_PER_SEC;
}

double measure_multiple_runs(void (*sort_func)(int*, int), int arr[], int n, int iterations) {	// ����������е�ƽ��ʱ��
    int* orig = malloc(n * sizeof(int));
    memcpy(orig, arr, n * sizeof(int));
    int* temp = malloc(n * sizeof(int));
    double total_time = 0.0;

    for (int i = 0; i < iterations; i++) {
        memcpy(temp, orig, n * sizeof(int));
        clock_t start = clock();
        sort_func(temp, n);
        clock_t end = clock();
        total_time += (double)(end - start) / CLOCKS_PER_SEC;
    }

    free(orig);
    free(temp);
    return total_time;
}