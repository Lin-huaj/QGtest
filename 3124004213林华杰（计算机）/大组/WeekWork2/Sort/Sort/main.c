#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include "sorts.h"
#include "test_utils.h"

void print_menu() {   // 菜单
    printf("\nMenu:\n");
    printf("1. 产生数据并保存到文件\n");
    printf("2. 从文件读取数据并用排序算法排序\n");
    printf("3. 在大数据规模下测试排序算法\n");
    printf("4. 在小数据量下测试排序算法\n");
    printf("5. 退出\n");
    printf("请输入你的选择: ");
}

int main() {
	int choice;    // 用户选择
    do {
        print_menu();
        if (scanf("%d", &choice) != 1) {
            while (getchar() != '\n'); // 清除输入缓冲区
            printf("错误的输入，请输入一个数字：\n");
            continue;
        }

        switch (choice) {           
        case 1: {
            int size;
            printf("输入数据大小： ");
            if (scanf("%d", &size) != 1 || size <= 0) {        
                printf("错误的大小.\n");
                break;
            }

            int* data = malloc(size * sizeof(int));
            if (!data) {
                printf("Memory allocation failed.\n");
                break;
            }

            printf("Choose data type (1: 随机生成, 2: 升序生成, 3: 降序生成, 4: 固定数据生成: ");
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
                printf("错误类型.\n");
                break;
            }

            char filename[256];
            printf("保存的文件名称: ");
            scanf("%255s", filename);
            save_data_to_file(filename, data, size);
            free(data);
            break;
        }
        case 2: {
            char filename[256];
            printf("读取的文件名称: ");
            scanf("%255s", filename);
            int size;
            int* data = load_data_from_file(filename, &size);
            if (!data) {
                printf("读取失败.\n");
                break;
            }

            printf("在指定数据规模下测试排序算法 %d\n", size);
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
            printf("测试每个算法运行 %d 次在数据大小为 %d 的情况下\n", iterations, size);
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