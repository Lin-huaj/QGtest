#ifndef STACK_H
#define STACK_H

// ������ջ�ڵ㣬֧�ִ洢��������double�����������char��
typedef struct StackNode {
    union {
        double num;  // ������
        char op;     // ��������+��-��*��/�����ţ�
    } data;
    struct StackNode* next; // ָ����һ�ڵ��ָ��
} StackNode;

// ��ջ�ṹ����
typedef struct {
    StackNode* top; // ջ��ָ��
} LinkStack;

// ��������
void InitStack(LinkStack* S);       // ��ʼ��ջ
int IsEmpty(LinkStack* S);         // �ж�ջ�Ƿ�Ϊ��
void PushNum(LinkStack* S, double num); // ��ջ������
void PushOp(LinkStack* S, char op);    // ��ջ������
double PopNum(LinkStack* S);        // ��ջ������
char PopOp(LinkStack* S);           // ��ջ������
double TopNum(LinkStack* S);        // ��ȡջ��������
char TopOp(LinkStack* S);           // ��ȡջ��������
void DestroyStack(LinkStack* S);    // ����ջ���ͷ��ڴ�

#endif