/* queue.c */
#include "queue.h"

//创建空队列
Queue* createQueue() {
    Queue* q = malloc(sizeof(Queue));
    q->front = q->rear = NULL;
    q->size = 0;
    return q;
}

//入队核心逻辑
void enqueue(Queue* q, void* data, DataType type) {
    Node* newNode = malloc(sizeof(Node));
    if (!newNode) {
        perror("内存分配失败");
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

//出队
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

//判断队列是否为空
int isEmpty(Queue* q) {
    return q->front == NULL;
}

//返回队列大小
size_t size(Queue* q) {
    return q->size;
}

//释放内存
void clearQueue(Queue* q) {
    while (!isEmpty(q)) {
        void* data = dequeue(q);
        free(data);
    }
    free(q);
}

//显示队列有哪些元素
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