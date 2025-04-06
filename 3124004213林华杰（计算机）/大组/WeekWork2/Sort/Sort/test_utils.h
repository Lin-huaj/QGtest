#define _CRT_SECURE_NO_WARNINGS
#ifndef TEST_UTILS_H
#define TEST_UTILS_H

typedef struct {
    const char* name;
    void (*func)(int*, int);
} SortAlgorithm;   // �����㷨�ṹ��

extern SortAlgorithm algorithms[];     // �����㷨����
extern const int NUM_ALGORITHMS;    // �����㷨����

void generate_random_data(int arr[], int n);   // �����������
void generate_ascending_data(int arr[], int n);   // ������������
void generate_descending_data(int arr[], int n);   // ���ɽ�������
void generate_constant_data(int arr[], int n);   // ���ɳ�������
void save_data_to_file(const char* filename, int arr[], int n);   // �������ݵ��ļ�
int* load_data_from_file(const char* filename, int* n);    // ���ļ���������
double measure_sorting_time(void (*sort_func)(int*, int), int arr[], int n);   // ��������ʱ��
double measure_multiple_runs(void (*sort_func)(int*, int), int arr[], int n, int iterations);    // ����������е�ƽ��ʱ��

#endif