/* main.c */
#include "queue.h"

//菜单
void displayMenu() {
    printf("\n=== 泛型队列操作 ===\n");
    printf("1. 入队\n");
    printf("2. 出队\n");
    printf("3. 显示队列\n");
    printf("4. 退出\n");
    printf("====================\n");
}

//处理进队的数据
void handleEnqueue(Queue* q) {
    DataType type;
    printf("选择数据类型 (0-整型, 1-浮点型, 2-字符串): ");
    scanf("%d", (int*)&type);

    void* data;
    int intVal;
    float floatVal;
    char strVal[100];

    switch (type) {
    case INT_TYPE:
        printf("输入整数值: ");
        scanf("%d", &intVal);
        data = &intVal;
        break;
    case FLOAT_TYPE:
        printf("输入浮点值: ");
        scanf("%f", &floatVal);
        data = &floatVal;
        break;
    case STRING_TYPE:
        printf("输入字符串: ");
        scanf("%s", strVal);
        data = strVal;
        break;
    default:
        printf("无效类型!\n");
        return;
    }

    enqueue(q, data, type);
    printf("元素已入队\n");
}

//主函数
int main() {
    Queue* q = createQueue();
    int choice;

    while (1) {
        displayMenu();
        printf("请输入选项: ");
        scanf("%d", &choice);

        switch (choice) {
        case 1:
            handleEnqueue(q);
            break;
        case 2: {
            void* data = dequeue(q);
            if (data) {
                printf("出队元素: ");
                // 实际应用中需要根据类型处理内存释放
                free(data);
            }
            else {
                printf("队列为空!\n");
            }
            break;
        }
        case 3:
            printQueue(q);
            break;
        case 4:
            clearQueue(q);
            printf("程序已退出\n");
            exit(0);
        default:
            printf("无效选项!\n");
        }
    }
    return 0;
}