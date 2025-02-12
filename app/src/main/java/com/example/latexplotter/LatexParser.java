package com.example.latexplotter;

public class LatexParser {
    public static String convertLatexToExpression(String latex) {
        // 移除所有空格
        latex = latex.replaceAll("\\s+", "");
        
        // 基本的 LaTeX 到表达式的转换
        latex = latex
            // 分数
            .replaceAll("\\\\frac\\{([^}]+)\\}\\{([^}]+)\\}", "($1)/($2)")
            // 平方
            .replaceAll("\\^2", "^2")
            // 平方根
            .replaceAll("\\\\sqrt\\{([^}]+)\\}", "sqrt($1)")
            // 三角函数
            .replaceAll("\\\\sin", "sin")
            .replaceAll("\\\\cos", "cos")
            .replaceAll("\\\\tan", "tan")
            // 常数
            .replaceAll("\\\\pi", "PI")
            .replaceAll("\\\\e", "E")
            // 乘法
            .replaceAll("\\\\cdot", "*")
            // 指数
            .replaceAll("\\^\\{([^}]+)\\}", "^($1)")
            // 对数
            .replaceAll("\\\\log", "log")
            .replaceAll("\\\\ln", "log");
        
        return latex;
    }
} 