#ifndef STACK_H
#define STACK_H

// 定义链栈节点，支持存储操作数（double）或操作符（char）
typedef struct StackNode {
    union {
        double num;  // 操作数
        char op;     // 操作符（+、-、*、/、括号）
    } data;
    struct StackNode* next; // 指向下一节点的指针
} StackNode;

// 链栈结构定义
typedef struct {
    StackNode* top; // 栈顶指针
} LinkStack;

// 函数声明
void InitStack(LinkStack* S);       // 初始化栈
int IsEmpty(LinkStack* S);         // 判断栈是否为空
void PushNum(LinkStack* S, double num); // 入栈操作数
void PushOp(LinkStack* S, char op);    // 入栈操作符
double PopNum(LinkStack* S);        // 出栈操作数
char PopOp(LinkStack* S);           // 出栈操作符
double TopNum(LinkStack* S);        // 获取栈顶操作数
char TopOp(LinkStack* S);           // 获取栈顶操作符
void DestroyStack(LinkStack* S);    // 销毁栈，释放内存

#endif