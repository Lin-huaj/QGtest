#include "stack.h"
#include <stdio.h>
#include <stdlib.h>

// 初始化栈
void InitStack(LinkStack* S) {
    S->top = NULL;
}

// 判断栈是否为空
int IsEmpty(LinkStack* S) {
    return (S->top == NULL);
}

// 入栈操作数
void PushNum(LinkStack* S, double num) {
    StackNode* newNode = (StackNode*)malloc(sizeof(StackNode));
    if (!newNode) {
        fprintf(stderr, "内存分配失败\n");
        exit(EXIT_FAILURE);
    }
    newNode->data.num = num;   // 存储操作数
    newNode->next = S->top;    // 新节点指向原栈顶
    S->top = newNode;          // 更新栈顶指针
}

// 入栈操作符
void PushOp(LinkStack* S, char op) {
    StackNode* newNode = (StackNode*)malloc(sizeof(StackNode));
    if (!newNode) {
        fprintf(stderr, "内存分配失败\n");
        exit(EXIT_FAILURE);
    }
    newNode->data.op = op;     // 存储操作符
    newNode->next = S->top;    // 新节点指向原栈顶
    S->top = newNode;          // 更新栈顶指针
}

// 出栈操作数
double PopNum(LinkStack* S) {
    if (IsEmpty(S)) {
        fprintf(stderr, "错误：栈下溢（操作数栈为空）\n");
        exit(EXIT_FAILURE);
    }
    StackNode* temp = S->top;  // 临时保存栈顶节点
    double num = temp->data.num; // 获取操作数
    S->top = temp->next;       // 更新栈顶指针
    free(temp);                // 释放原栈顶节点
    return num;
}

// 出栈操作符
char PopOp(LinkStack* S) {
    if (IsEmpty(S)) {
        fprintf(stderr, "错误：栈下溢（操作符栈为空）\n");
        exit(EXIT_FAILURE);
    }
    StackNode* temp = S->top;  // 临时保存栈顶节点
    char op = temp->data.op;   // 获取操作符
    S->top = temp->next;       // 更新栈顶指针
    free(temp);                // 释放原栈顶节点
    return op;
}

// 获取栈顶操作数（不弹出）
double TopNum(LinkStack* S) {
    if (IsEmpty(S)) {
        fprintf(stderr, "错误：操作数栈为空\n");
        exit(EXIT_FAILURE);
    }
    return S->top->data.num;
}

// 获取栈顶操作符（不弹出）
char TopOp(LinkStack* S) {
    if (IsEmpty(S)) {
        fprintf(stderr, "错误：操作符栈为空\n");
        exit(EXIT_FAILURE);
    }
    return S->top->data.op;
}

// 销毁栈，释放所有节点内存
void DestroyStack(LinkStack* S) {
    while (!IsEmpty(S)) {
        StackNode* temp = S->top;
        S->top = S->top->next;
        free(temp);
    }
}