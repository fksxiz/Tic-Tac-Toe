package com.example.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class TicTacController {
    @FXML
    private Label resultText;
    @FXML
    private TextField player1;
    @FXML
    private TextField player2;
    @FXML
    private Canvas TicTacCanvas;

    private enum Symbol{
        o,
        x
    }

    private enum State{
        vertical,
        horizontal,
        diagonalLR,
        diagonalRL
    }

    private Symbol[][] Matrix = new Symbol[3][3];

    private boolean isX;
    private boolean isEnded;
    private int c;
    private double offset;

    private final double lineOffset = 5;

    @FXML
    public void initialize() {
        //Установка обработчика событий
        TicTacCanvas.setOnMouseClicked(this::canvasMouseClick);
        TicTacCanvas.setHeight(TicTacCanvas.getWidth());
        isEnded=false;
        isX=true;
        c=0;
        offset = TicTacCanvas.getWidth()/3;
        drawField();
    }

    private void drawField(){
        GraphicsContext gc = TicTacCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, TicTacCanvas.getWidth(), TicTacCanvas.getHeight());
        gc.setFill(Color.BLACK);
        double offset = TicTacCanvas.getWidth()/3;
        double width = 2;
        for(int i=1;i<4;i++){
            gc.fillRect(offset*i,0,width,TicTacCanvas.getHeight());
            gc.fillRect(0,offset*i,TicTacCanvas.getWidth(),width);
        }
    }

    private void drawXO(int row, int col){
        GraphicsContext gc = TicTacCanvas.getGraphicsContext2D();
        if(Matrix[row][col]==null) {
            if (this.isX) {
                gc.setStroke(Color.RED);
                gc.strokeLine(col * offset + lineOffset, row * offset + lineOffset, offset * col + offset - lineOffset, offset * row + offset - lineOffset);
                gc.strokeLine(col * offset + offset - lineOffset, row * offset + +lineOffset, col * offset + lineOffset, row * offset + offset - lineOffset);
                Matrix[row][col]=Symbol.x;
            } else {
                gc.setStroke(Color.BLUE);
                gc.strokeOval(col * offset + lineOffset, row * offset + lineOffset, offset - lineOffset * 2, offset - lineOffset * 2);
                Matrix[row][col]=Symbol.o;
            }
            c++;
            checkLines(isX?Symbol.x:Symbol.o);
            isX=!isX;
        }
    }

    private void checkLines(Symbol s){
        //Проверка вертикальных и горизонтальных линий
        for(int i =0;i<3;i++){
            if((Matrix[i][0]==s&&Matrix[i][1]==s&&Matrix[i][2]==s)){
                resultText.setText("Победил "+(s==Symbol.x?player1.getText():player2.getText())+"!");
                isEnded=true;
                drawLine(i,0,i,2,State.horizontal);
                return;
            }else {
                if ((Matrix[0][i] == s && Matrix[1][i] == s && Matrix[2][i] == s)) {
                    resultText.setText("Победил " + (s == Symbol.x ? player1.getText() : player2.getText()) + "!");
                    isEnded = true;
                    drawLine(0,i,2,i,State.vertical);
                    return;
                }
            }
        }

        //Проверка диагональных линий
        if((Matrix[0][0]==s&&Matrix[1][1]==s&&Matrix[2][2]==s)){
            resultText.setText("Победил "+(s==Symbol.x?player1.getText():player2.getText())+"!");
            isEnded=true;
            drawLine(0,0,2,2,State.diagonalLR);
            return;
        }else {
            if ((Matrix[0][2] == s && Matrix[1][1] == s && Matrix[2][0] == s)) {
                resultText.setText("Победил " + (s == Symbol.x ? player1.getText() : player2.getText()) + "!");
                isEnded = true;
                drawLine(0,2,2,0,State.diagonalRL);
                return;
            }
        }

        //Проверка на ничью
        if(c==9){
            resultText.setText("Ничья");
            isEnded=true;
        }
    }

    private void drawLine(int r1, int c1, int r2, int c2, State s ){
        GraphicsContext gc = TicTacCanvas.getGraphicsContext2D();
        gc.setStroke(Color.DARKBLUE);
        switch(s.ordinal()){
            case 0:
                gc.strokeLine(offset * c1+offset/2 , offset * r1 , offset * c2+offset/2, offset * r2+offset);
                break;
            case 1:
                gc.strokeLine(offset * c1 , offset * r1+offset/2 , offset * c2+offset, offset * r2+offset/2);
                break;
            case 2:
                gc.strokeLine(offset * c1 , offset * r1 , offset + offset * c2, offset + offset * r2);
                break;
            case 3:
                gc.strokeLine(offset + offset * c1, offset * r1, offset * c2, offset+offset * r2);
                break;
        }
    }

    @FXML
    protected void onResetButtonClick() {
        drawField();
        resultText.setText("-");
        Matrix = new Symbol[3][3];
        isEnded=false;
        isX=true;
        c=0;
    }

    private void canvasMouseClick(MouseEvent event){
        if(event.getButton()== MouseButton.PRIMARY) {
            if (!isEnded) {
                double offset = TicTacCanvas.getWidth() / 3;
                double x = event.getX();
                double y = event.getY();
                int r = -1, c = -1;
                for (int i = 0; i < 3; i++) {
                    if (x >= i * offset && x < i * offset + offset) {
                        c = i;
                    }
                    if (y >= i * offset && y < i * offset + offset) {
                        r = i;
                    }
                }
                drawXO(r, c);
            }
        }
    }
}