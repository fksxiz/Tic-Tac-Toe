package com.example.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class TicTacController {
    @FXML
    private Label resultText;
    @FXML
    private Canvas TicTacCanvas;

    private enum Symbol{
        empty,
        o,
        x
    }

    @FXML
    public void initialize() {
        //Установка обработчика событий
        TicTacCanvas.setOnMouseClicked(this::canvasMouseClick);
        TicTacCanvas.setHeight(TicTacCanvas.getWidth());
        drawField();
        drawXO(false,2,2);
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

    private void drawXO(boolean isX,int row, int col){
        GraphicsContext gc = TicTacCanvas.getGraphicsContext2D();
        double offset = TicTacCanvas.getWidth()/3;
        double lineOffset = 5;

        if(isX) {
            gc.setStroke(Color.RED);
            gc.strokeLine(col*offset + lineOffset, row*offset + lineOffset,  offset*row+offset - lineOffset, offset*col+offset - lineOffset);
            gc.strokeLine(col*offset + offset - lineOffset, row*offset +  + lineOffset, col*offset +  lineOffset, row*offset + offset - lineOffset);
        }else
        {
            gc.setStroke(Color.BLUE);
            gc.strokeOval(col*offset + lineOffset, row*offset + lineOffset, offset - lineOffset * 2, offset - lineOffset * 2);
        }
    }

    @FXML
    protected void onResetButtonClick() {
        drawField();
        resultText.setText("-");
    }

    private void canvasMouseClick(MouseEvent event){
        if(event.getButton()== MouseButton.PRIMARY){
            Double x = event.getX();
            Double y = event.getY();
        }
    }

}