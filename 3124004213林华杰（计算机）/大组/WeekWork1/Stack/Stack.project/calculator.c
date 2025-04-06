#include "calculator.h"
#include "stack.h"
#include <ctype.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

// 判断字符是否为操作符（+、-、*、/）
static int IsOperator(char c) {
    return (c == '+' || c == '-' || c == '*' || c == '/');
}

// 获取操作符的优先级
static int GetPrecedence(char op) {
    switch (op) {
    case '+':
    case '-':
        return 1;  // 加减优先级为1
    case '*':
    case '/':
        return 2;  // 乘除优先级为2
    default:
        return 0;  // 其他字符（如括号）返回0
    }
}

// 执行运算：a op b
static double ApplyOperation(double a, double b, char op) {
    switch (op) {
    case '+':
        return a + b;
    case '-':
        return a - b;
    case '*':
        return a * b;
    case '/':
        if (b == 0) {
            fprintf(stderr, "错误：除数不能为零\n");
            exit(EXIT_FAILURE);
        }
        return a / b;
    default:
        fprintf(stderr, "错误：未知操作符 '%c'\n", op);
        exit(EXIT_FAILURE);
    }
}

// 表达式求值核心函数
double EvaluateExpression(const char* expr) {
    LinkStack numStack; // 操作数栈
    LinkStack opStack;  // 操作符栈
    InitStack(&numStack);
    InitStack(&opStack);

    for (int i = 0; expr[i] != '\0'; i++) {
        // 跳过空格
        if (expr[i] == ' ') {
            continue;
        }

        // 处理数字（包括小数）
        if (isdigit(expr[i]) || expr[i] == '.') {
            char numStr[32]; // 存储数字字符串
            int j = 0;
            // 提取完整数字（如123、45.67）
            while (expr[i] != '\0' && (isdigit(expr[i]) || expr[i] == '.')) {
                numStr[j++] = expr[i++];
            }
            numStr[j] = '\0'; // 字符串结束符
            i--;              // 回退一个字符（因为循环会i++）
            double num = atof(numStr); // 转换为浮点数
            PushNum(&numStack, num);  // 数字入栈
        }

        // 处理左括号
        else if (expr[i] == '(') {
            PushOp(&opStack, '(');
        }

        // 处理右括号：计算括号内的表达式
        else if (expr[i] == ')') {
            // 弹出操作符并计算，直到遇到左括号
            while (TopOp(&opStack) != '(') {
                char op = PopOp(&opStack);
                double b = PopNum(&numStack);
                double a = PopNum(&numStack);
                PushNum(&numStack, ApplyOperation(a, b, op));
            }
            PopOp(&opStack); // 弹出左括号
        }

        // 处理操作符
        else if (IsOperator(expr[i])) {
            // 比较优先级：若当前操作符优先级 <= 栈顶操作符，则先计算栈顶操作
            while (!IsEmpty(&opStack) && GetPrecedence(expr[i]) <= GetPrecedence(TopOp(&opStack))) {
                char op = PopOp(&opStack);
                double b = PopNum(&numStack);
                double a = PopNum(&numStack);
                PushNum(&numStack, ApplyOperation(a, b, op));
            }
            PushOp(&opStack, expr[i]); // 当前操作符入栈
        }

        // 非法字符处理
        else {
            fprintf(stderr, "错误：非法字符 '%c'\n", expr[i]);
            DestroyStack(&numStack);
            DestroyStack(&opStack);
            exit(EXIT_FAILURE);
        }
    }

    // 处理剩余操作符
    while (!IsEmpty(&opStack)) {
        char op = PopOp(&opStack);
        double b = PopNum(&numStack);
        double a = PopNum(&numStack);
        PushNum(&numStack, ApplyOperation(a, b, op));
    }

    // 获取最终结果并清理栈
    double result = PopNum(&numStack);
    DestroyStack(&numStack);
    DestroyStack(&opStack);
    return result;
}

// 用户交互函数
void RunCalculator() {
    char expr[256];
    printf("请输入四则运算表达式（支持括号，例如：(3+5)*2-4/2）：\n");
    if (fgets(expr, sizeof(expr), stdin) == NULL) {
        fprintf(stderr, "输入错误\n");
        return;
    }
    expr[strcspn(expr, "\n")] = '\0'; // 去除换行符

    double result = EvaluateExpression(expr);
    printf("计算结果：%.2f\n", result);
}