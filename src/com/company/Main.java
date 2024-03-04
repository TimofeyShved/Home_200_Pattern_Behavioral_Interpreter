package com.company;

import java.util.Stack;

public class Main {

    public static void main(String[] args) {
	    // Паттерн (интерпретатор) заставляет строчку разбить на значение
        // и выполнить те или иные действия
        String s = "1+2+3-9";
        Expression ex = new Evaluate(s);
        System.out.println(ex.interpret(ex));
    }
}

// например у нас есть класс числа и действий над ними
interface Expression{
    int interpret(Expression expression);
}

class num implements Expression{
    int num;

    public num(int num) {
        this.num = num;
    }

    @Override
    public int interpret(Expression expression) {
        return num;
    }
}

class plus implements Expression{
    Expression leftExpresoin;
    Expression rightExpresoin;

    public plus(Expression leftExpresoin, Expression rightExpresoin) {
        this.leftExpresoin = leftExpresoin;
        this.rightExpresoin = rightExpresoin;
    }

    @Override
    public int interpret(Expression context) {
        return leftExpresoin.interpret(context) + rightExpresoin.interpret(context);
    }
}

class minus extends plus{
    public minus(Expression leftExpresoin, Expression rightExpresoin) {
        super(leftExpresoin, rightExpresoin);
    }

    @Override
    public int interpret(Expression context) {
        return leftExpresoin.interpret(context) - rightExpresoin.interpret(context);
    }
}


// А теперь нам нужно их правильно интерперетировать

class Evaluate implements Expression{
    Expression evaluete;

    // принимаем строку
    public Evaluate(String expression) {
        Stack<Expression> exStack = new Stack<>();
        // переворачиваем её
        String expresionRever = new StringBuilder(expression).reverse().toString();

        // находим числа
        for (String s: expresionRever.split("\\D")){
            exStack.push(new num(Integer.parseInt(s)));
        }

        // по очереди берутся из стека 2 числа, действие над ними и обратно в стек
        for (String s: expression.split("\\d")){
            if(s.equals("+")){
                exStack.push(new plus(exStack.pop(), exStack.pop()));
            }else if(s.equals("-")){
                exStack.push(new minus(exStack.pop(), exStack.pop()));
            }
        }

        evaluete = exStack.pop();
    }

    // в данном случе не нужен, хотя можно было и сюда всё запихать
    @Override
    public int interpret(Expression context) {
        return evaluete.interpret(context);
    }
}