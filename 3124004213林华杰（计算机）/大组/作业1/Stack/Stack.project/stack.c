#include "stack.h"
#include <stdio.h>
#include <stdlib.h>

// ��ʼ��ջ
void InitStack(LinkStack* S) {
    S->top = NULL;
}

// �ж�ջ�Ƿ�Ϊ��
int IsEmpty(LinkStack* S) {
    return (S->top == NULL);
}

// ��ջ������
void PushNum(LinkStack* S, double num) {
    StackNode* newNode = (StackNode*)malloc(sizeof(StackNode));
    if (!newNode) {
        fprintf(stderr, "�ڴ����ʧ��\n");
        exit(EXIT_FAILURE);
    }
    newNode->data.num = num;   // �洢������
    newNode->next = S->top;    // �½ڵ�ָ��ԭջ��
    S->top = newNode;          // ����ջ��ָ��
}

// ��ջ������
void PushOp(LinkStack* S, char op) {
    StackNode* newNode = (StackNode*)malloc(sizeof(StackNode));
    if (!newNode) {
        fprintf(stderr, "�ڴ����ʧ��\n");
        exit(EXIT_FAILURE);
    }
    newNode->data.op = op;     // �洢������
    newNode->next = S->top;    // �½ڵ�ָ��ԭջ��
    S->top = newNode;          // ����ջ��ָ��
}

// ��ջ������
double PopNum(LinkStack* S) {
    if (IsEmpty(S)) {
        fprintf(stderr, "����ջ���磨������ջΪ�գ�\n");
        exit(EXIT_FAILURE);
    }
    StackNode* temp = S->top;  // ��ʱ����ջ���ڵ�
    double num = temp->data.num; // ��ȡ������
    S->top = temp->next;       // ����ջ��ָ��
    free(temp);                // �ͷ�ԭջ���ڵ�
    return num;
}

// ��ջ������
char PopOp(LinkStack* S) {
    if (IsEmpty(S)) {
        fprintf(stderr, "����ջ���磨������ջΪ�գ�\n");
        exit(EXIT_FAILURE);
    }
    StackNode* temp = S->top;  // ��ʱ����ջ���ڵ�
    char op = temp->data.op;   // ��ȡ������
    S->top = temp->next;       // ����ջ��ָ��
    free(temp);                // �ͷ�ԭջ���ڵ�
    return op;
}

// ��ȡջ������������������
double TopNum(LinkStack* S) {
    if (IsEmpty(S)) {
        fprintf(stderr, "���󣺲�����ջΪ��\n");
        exit(EXIT_FAILURE);
    }
    return S->top->data.num;
}

// ��ȡջ������������������
char TopOp(LinkStack* S) {
    if (IsEmpty(S)) {
        fprintf(stderr, "���󣺲�����ջΪ��\n");
        exit(EXIT_FAILURE);
    }
    return S->top->data.op;
}

// ����ջ���ͷ����нڵ��ڴ�
void DestroyStack(LinkStack* S) {
    while (!IsEmpty(S)) {
        StackNode* temp = S->top;
        S->top = S->top->next;
        free(temp);
    }
}