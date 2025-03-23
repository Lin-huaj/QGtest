#include "calculator.h"
#include "stack.h"
#include <ctype.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

// �ж��ַ��Ƿ�Ϊ��������+��-��*��/��
static int IsOperator(char c) {
    return (c == '+' || c == '-' || c == '*' || c == '/');
}

// ��ȡ�����������ȼ�
static int GetPrecedence(char op) {
    switch (op) {
    case '+':
    case '-':
        return 1;  // �Ӽ����ȼ�Ϊ1
    case '*':
    case '/':
        return 2;  // �˳����ȼ�Ϊ2
    default:
        return 0;  // �����ַ��������ţ�����0
    }
}

// ִ�����㣺a op b
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
            fprintf(stderr, "���󣺳�������Ϊ��\n");
            exit(EXIT_FAILURE);
        }
        return a / b;
    default:
        fprintf(stderr, "����δ֪������ '%c'\n", op);
        exit(EXIT_FAILURE);
    }
}

// ���ʽ��ֵ���ĺ���
double EvaluateExpression(const char* expr) {
    LinkStack numStack; // ������ջ
    LinkStack opStack;  // ������ջ
    InitStack(&numStack);
    InitStack(&opStack);

    for (int i = 0; expr[i] != '\0'; i++) {
        // �����ո�
        if (expr[i] == ' ') {
            continue;
        }

        // �������֣�����С����
        if (isdigit(expr[i]) || expr[i] == '.') {
            char numStr[32]; // �洢�����ַ���
            int j = 0;
            // ��ȡ�������֣���123��45.67��
            while (expr[i] != '\0' && (isdigit(expr[i]) || expr[i] == '.')) {
                numStr[j++] = expr[i++];
            }
            numStr[j] = '\0'; // �ַ���������
            i--;              // ����һ���ַ�����Ϊѭ����i++��
            double num = atof(numStr); // ת��Ϊ������
            PushNum(&numStack, num);  // ������ջ
        }

        // ����������
        else if (expr[i] == '(') {
            PushOp(&opStack, '(');
        }

        // ���������ţ����������ڵı��ʽ
        else if (expr[i] == ')') {
            // ���������������㣬ֱ������������
            while (TopOp(&opStack) != '(') {
                char op = PopOp(&opStack);
                double b = PopNum(&numStack);
                double a = PopNum(&numStack);
                PushNum(&numStack, ApplyOperation(a, b, op));
            }
            PopOp(&opStack); // ����������
        }

        // ���������
        else if (IsOperator(expr[i])) {
            // �Ƚ����ȼ�������ǰ���������ȼ� <= ջ�������������ȼ���ջ������
            while (!IsEmpty(&opStack) && GetPrecedence(expr[i]) <= GetPrecedence(TopOp(&opStack))) {
                char op = PopOp(&opStack);
                double b = PopNum(&numStack);
                double a = PopNum(&numStack);
                PushNum(&numStack, ApplyOperation(a, b, op));
            }
            PushOp(&opStack, expr[i]); // ��ǰ��������ջ
        }

        // �Ƿ��ַ�����
        else {
            fprintf(stderr, "���󣺷Ƿ��ַ� '%c'\n", expr[i]);
            DestroyStack(&numStack);
            DestroyStack(&opStack);
            exit(EXIT_FAILURE);
        }
    }

    // ����ʣ�������
    while (!IsEmpty(&opStack)) {
        char op = PopOp(&opStack);
        double b = PopNum(&numStack);
        double a = PopNum(&numStack);
        PushNum(&numStack, ApplyOperation(a, b, op));
    }

    // ��ȡ���ս��������ջ
    double result = PopNum(&numStack);
    DestroyStack(&numStack);
    DestroyStack(&opStack);
    return result;
}

// �û���������
void RunCalculator() {
    char expr[256];
    printf("����������������ʽ��֧�����ţ����磺(3+5)*2-4/2����\n");
    if (fgets(expr, sizeof(expr), stdin) == NULL) {
        fprintf(stderr, "�������\n");
        return;
    }
    expr[strcspn(expr, "\n")] = '\0'; // ȥ�����з�

    double result = EvaluateExpression(expr);
    printf("��������%.2f\n", result);
}