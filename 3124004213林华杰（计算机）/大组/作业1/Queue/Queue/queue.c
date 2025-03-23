/* queue.c */
#include "queue.h"

//�����ն���
Queue* createQueue() {
    Queue* q = malloc(sizeof(Queue));
    q->front = q->rear = NULL;
    q->size = 0;
    return q;
}

//��Ӻ����߼�
void enqueue(Queue* q, void* data, DataType type) {
    Node* newNode = malloc(sizeof(Node));
    if (!newNode) {
        perror("�ڴ����ʧ��");
        exit(EXIT_FAILURE);
    }

    newNode->type = type;
    newNode->next = NULL;

    switch (type) {
        case STRING_TYPE:
            newNode->data = _strdup((char*)data);
            break;
        default: {
            size_t size = (type == INT_TYPE) ? sizeof(int) : sizeof(float);
                    newNode->data = malloc(size);
                    memcpy(newNode->data, data, size);
        }
    
    }

    if (isEmpty(q)) {
        q->front = q->rear = newNode;
    }
    else {
        q->rear->next = newNode;
        q->rear = newNode;
    }
    q->size++;
}

//����
void* dequeue(Queue* q) {
    if (isEmpty(q)) return NULL;

    Node* temp = q->front;
    void* data = temp->data;
    q->front = q->front->next;

    if (q->front == NULL) q->rear = NULL;

    free(temp);
    q->size--;
    return data;
}

//�ж϶����Ƿ�Ϊ��
int isEmpty(Queue* q) {
    return q->front == NULL;
}

//���ض��д�С
size_t size(Queue* q) {
    return q->size;
}

//�ͷ��ڴ�
void clearQueue(Queue* q) {
    while (!isEmpty(q)) {
        void* data = dequeue(q);
        free(data);
    }
    free(q);
}

//��ʾ��������ЩԪ��
void printQueue(Queue* q) {
    Node* current = q->front;
    printf("Queue: [");
    while (current) {
        switch (current->type) {
        case INT_TYPE:
            printf("%d", *(int*)current->data);
            break;
        case FLOAT_TYPE:
            printf("%.2f", *(float*)current->data);
            break;
        case STRING_TYPE:
            printf("\"%s\"", (char*)current->data);
            break;
        }
        if (current->next) printf(" -> ");
        current = current->next;
    }
    printf("]\n");
}