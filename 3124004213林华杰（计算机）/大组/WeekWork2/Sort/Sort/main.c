#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include "sorts.h"
#include "test_utils.h"

void print_menu() {   // �˵�
    printf("\nMenu:\n");
    printf("1. �������ݲ����浽�ļ�\n");
    printf("2. ���ļ���ȡ���ݲ��������㷨����\n");
    printf("3. �ڴ����ݹ�ģ�²��������㷨\n");
    printf("4. ��С�������²��������㷨\n");
    printf("5. �˳�\n");
    printf("���������ѡ��: ");
}

int main() {
	int choice;    // �û�ѡ��
    do {
        print_menu();
        if (scanf("%d", &choice) != 1) {
            while (getchar() != '\n'); // ������뻺����
            printf("��������룬������һ�����֣�\n");
            continue;
        }

        switch (choice) {           
        case 1: {
            int size;
            printf("�������ݴ�С�� ");
            if (scanf("%d", &size) != 1 || size <= 0) {        
                printf("����Ĵ�С.\n");
                break;
            }

            int* data = malloc(size * sizeof(int));
            if (!data) {
                printf("Memory allocation failed.\n");
                break;
            }

            printf("Choose data type (1: �������, 2: ��������, 3: ��������, 4: �̶���������: ");
            int type;
            if (scanf("%d", &type) != 1) {
                free(data);
                printf("Invalid type.\n");
                break;
            }

            switch (type) {
            case 1: generate_random_data(data, size); break;
            case 2: generate_ascending_data(data, size); break;
            case 3: generate_descending_data(data, size); break;
            case 4: generate_constant_data(data, size); break;
            default:
                free(data);
                printf("��������.\n");
                break;
            }

            char filename[256];
            printf("������ļ�����: ");
            scanf("%255s", filename);
            save_data_to_file(filename, data, size);
            free(data);
            break;
        }
        case 2: {
            char filename[256];
            printf("��ȡ���ļ�����: ");
            scanf("%255s", filename);
            int size;
            int* data = load_data_from_file(filename, &size);
            if (!data) {
                printf("��ȡʧ��.\n");
                break;
            }

            printf("��ָ�����ݹ�ģ�²��������㷨 %d\n", size);
            for (int i = 0; i < NUM_ALGORITHMS; i++) {
                double time = measure_sorting_time(algorithms[i].func, data, size);
                printf("%-16s: %.6f seconds\n", algorithms[i].name, time);
            }
            free(data);
            break;
        }
        case 3: {
            int sizes[] = { 10000, 50000, 200000 };
            for (int s = 0; s < 3; s++) {
                int size = sizes[s];
                int* data = malloc(size * sizeof(int));
                if (!data) {
                    printf("Memory allocation failed for size %d.\n", size);
                    continue;
                }
                generate_random_data(data, size);
                printf("\nTesting size %d\n", size);
                for (int i = 0; i < NUM_ALGORITHMS; i++) {
                    double time = measure_sorting_time(algorithms[i].func, data, size);
                    printf("%-16s: %.6f seconds\n", algorithms[i].name, time);
                }
                free(data);
            }
            break;
        }
        case 4: {
            const int size = 100;
            const int iterations = 100000;
            int* data = malloc(size * sizeof(int));
            generate_random_data(data, size);
            printf("����ÿ���㷨���� %d �������ݴ�СΪ %d �������\n", iterations, size);
            for (int i = 0; i < NUM_ALGORITHMS; i++) {
                double total_time = measure_multiple_runs(algorithms[i].func, data, size, iterations);
                printf("%-16s: Total %.6fs | Avg: %.6fms\n",
                    algorithms[i].name, total_time, (total_time / iterations) * 1000);
            }
            free(data);
            break;
        }
        case 5:
            printf("Exiting...\n");
            break;
        default:
            printf("Invalid choice.\n");
            break;
        }
    } while (choice != 5);

    return 0;
}