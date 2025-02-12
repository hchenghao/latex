package com.example.latexplotter;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private XYPlot plot;
    private EditText expressionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plot = findViewById(R.id.plot);
        expressionInput = findViewById(R.id.expressionInput);
        Button plotButton = findViewById(R.id.plotButton);

        plotButton.setOnClickListener(v -> plotFunction());
    }

    private void plotFunction() {
        String latexInput = expressionInput.getText().toString();
        if (latexInput.isEmpty()) {
            Toast.makeText(this, "请输入LaTeX表达式", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // 将LaTeX转换为普通数学表达式
            String mathExpression = LatexParser.convertLatexToExpression(latexInput);
            
            List<Number> xVals = new ArrayList<>();
            List<Number> yVals = new ArrayList<>();

            Expression expression = new ExpressionBuilder(mathExpression)
                    .variable("x")
                    .build();

            // 生成 100 个点用于绘图
            for (double x = -10; x <= 10; x += 0.2) {
                expression.setVariable("x", x);
                double y = expression.evaluate();
                xVals.add(x);
                yVals.add(y);
            }

            XYSeries series = new SimpleXYSeries(xVals, yVals, latexInput);
            
            // 设置线条样式
            LineAndPointFormatter formatter = new LineAndPointFormatter(
                    Color.BLUE, null, null, null);

            plot.clear();
            plot.addSeries(series, formatter);
            plot.redraw();

        } catch (Exception e) {
            Toast.makeText(this, "表达式格式错误: " + e.getMessage(), 
                    Toast.LENGTH_SHORT).show();
        }
    }
} 