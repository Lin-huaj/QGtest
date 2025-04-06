#ifndef SORTS_H
#define SORTS_H

void insertion_sort(int arr[], int n);  //��������
void merge_sort_wrapper(int arr[], int n); //�鲢����
void quick_sort_wrapper(int arr[], int n);	//��������
void counting_sort(int arr[], int n);	//��������
void radix_sort(int arr[], int n);	//��������
//ѡ��
void quick_sort_non_recursive(int arr[], int n); // �ǵݹ��������
void bubble_sort_optimized(int arr[], int n); // �Ż���ð������
void quick_sort_random_wrapper(int arr[], int n);   // �������������
void quick_sort_dual_pivot_wrapper(int arr[], int n); // ˫�����������


#endif