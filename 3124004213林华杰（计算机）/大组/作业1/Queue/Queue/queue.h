/* queue.h */
#ifndef QUEUE_H
#define QUEUE_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

//数据类型枚举：标识队列中存储的数据类型
typedef enum {
    INT_TYPE,
    FLOAT_TYPE,
    STRING_TYPE
} DataType;

//队列节点结构体
typedef struct Node {
    void* data;
    DataType type;
    struct Node* next;
} Node;

//队列结构体
typedef struct {
    Node* front;
    Node* rear;
    size_t size;
} Queue;

Queue* createQueue();
void enqueue(Queue* q, void* data, DataType type);
void* dequeue(Queue* q);
int isEmpty(Queue* q);
size_t size(Queue* q);
void clearQueue(Queue* q);
void printQueue(Queue* q);

#endif