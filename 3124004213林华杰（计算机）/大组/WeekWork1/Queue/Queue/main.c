/* main.c */
#include "queue.h"

//�˵�
void displayMenu() {
    printf("\n=== ���Ͷ��в��� ===\n");
    printf("1. ���\n");
    printf("2. ����\n");
    printf("3. ��ʾ����\n");
    printf("4. �˳�\n");
    printf("====================\n");
}

//������ӵ�����
void handleEnqueue(Queue* q) {
    DataType type;
    printf("ѡ���������� (0-����, 1-������, 2-�ַ���): ");
    scanf("%d", (int*)&type);

    void* data;
    int intVal;
    float floatVal;
    char strVal[100];

    switch (type) {
    case INT_TYPE:
        printf("��������ֵ: ");
        scanf("%d", &intVal);
        data = &intVal;
        break;
    case FLOAT_TYPE:
        printf("���븡��ֵ: ");
        scanf("%f", &floatVal);
        data = &floatVal;
        break;
    case STRING_TYPE:
        printf("�����ַ���: ");
        scanf("%s", strVal);
        data = strVal;
        break;
    default:
        printf("��Ч����!\n");
        return;
    }

    enqueue(q, data, type);
    printf("Ԫ�������\n");
}

//������
int main() {
    Queue* q = createQueue();
    int choice;

    while (1) {
        displayMenu();
        printf("������ѡ��: ");
        scanf("%d", &choice);

        switch (choice) {
        case 1:
            handleEnqueue(q);
            break;
        case 2: {
            void* data = dequeue(q);
            if (data) {
                printf("����Ԫ��: ");
                // ʵ��Ӧ������Ҫ�������ʹ����ڴ��ͷ�
                free(data);
            }
            else {
                printf("����Ϊ��!\n");
            }
            break;
        }
        case 3:
            printQueue(q);
            break;
        case 4:
            clearQueue(q);
            printf("�������˳�\n");
            exit(0);
        default:
            printf("��Чѡ��!\n");
        }
    }
    return 0;
}