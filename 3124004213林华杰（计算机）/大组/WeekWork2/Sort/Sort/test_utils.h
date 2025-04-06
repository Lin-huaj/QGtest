#define _CRT_SECURE_NO_WARNINGS
#ifndef TEST_UTILS_H
#define TEST_UTILS_H

typedef struct {
    const char* name;
    void (*func)(int*, int);
} SortAlgorithm;   // 排序算法结构体

extern SortAlgorithm algorithms[];     // 排序算法数组
extern const int NUM_ALGORITHMS;    // 排序算法数量

void generate_random_data(int arr[], int n);   // 生成随机数据
void generate_ascending_data(int arr[], int n);   // 生成升序数据
void generate_descending_data(int arr[], int n);   // 生成降序数据
void generate_constant_data(int arr[], int n);   // 生成常数数据
void save_data_to_file(const char* filename, int arr[], int n);   // 保存数据到文件
int* load_data_from_file(const char* filename, int* n);    // 从文件加载数据
double measure_sorting_time(void (*sort_func)(int*, int), int arr[], int n);   // 测量排序时间
double measure_multiple_runs(void (*sort_func)(int*, int), int arr[], int n, int iterations);    // 测量多次运行的平均时间

#endif