#ifndef SORTS_H
#define SORTS_H

void insertion_sort(int arr[], int n);  //插入排序
void merge_sort_wrapper(int arr[], int n); //归并排序
void quick_sort_wrapper(int arr[], int n);	//快速排序
void counting_sort(int arr[], int n);	//计数排序
void radix_sort(int arr[], int n);	//基数排序
//选做
void quick_sort_non_recursive(int arr[], int n); // 非递归快速排序
void bubble_sort_optimized(int arr[], int n); // 优化版冒泡排序
void quick_sort_random_wrapper(int arr[], int n);   // 随机化快速排序
void quick_sort_dual_pivot_wrapper(int arr[], int n); // 双枢轴快速排序


#endif