package ru.geekbrains.java1.dz.dz8.gubenkoDM;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Map extends JPanel {
    private int linesCount;

    public void setDotsToWin(int dotsToWin) {
        this.dotsToWin = dotsToWin;
    }

    private int dotsToWin;
    private final int PANEL_SIZE = 700;
    private int CELL_SIZE;
    private boolean gameOver;
    private String gameOverMsg;
    private Random rand = new Random();
    private int wX;
    private int wY;

    public int getwX() {
        return wX;
    }

    public int getwY() {
        return wY;
    }

    private int[][] field;
    private final int PLAYER1_DOT = 1;
    private final int PLAYER2_DOT = 2;
    private boolean playMode=true;
    private boolean playerFlag=true;
    private String playModStr;
    private int playSize;

    public String getPlayModStr() {
        return playModStr;
    }

    public void setPlayModStr(String playModStr) {
        this.playModStr = playModStr;
    }

    public int getPlaySize() {
        return playSize;
    }

    public void setPlaySize(int playSize) {
        this.playSize = playSize;
    }

    public boolean isPlayerFlag() {
        return playerFlag;
    }

    public void setPlayerFlag(boolean playerFlag) {
        this.playerFlag = playerFlag;
    }

    public boolean isPlayMode() {
        return playMode;
    }

    public void setPlayMode(boolean playMode) {
        this.playMode = playMode;
    }

    public Map(int wX,int wY) {
        this.wX=wX;
        this.wY=wY;

        //startNewGame(3);
        setBackground(new Color(0,150,0));
        //основной слушатель
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int cmx, cmy;
                //приводим координыты поля к координатам разметки поля
                cmx = e.getX() / CELL_SIZE;
                cmy = e.getY() / CELL_SIZE;
                //проверяем не закончилась ли игра. если нет играем
                if (!gameOver) {
                    //провеяем в каком режиме проходит игра
                    if (isPlayMode()){
                        //проверяем можно ли установить игроку свою фишку
                        if (setDot(cmx, cmy, PLAYER1_DOT)) {
                            //проверяем, а не ничья ли это
                            //проверяем есть ли куда поставить фишку
                            checkFieldFull();
                            //проверяем игру на предмет победы или первого игрока или второго(ПК)
                            checkWin(PLAYER1_DOT);
                            //перерисовываем игровое поле
                            repaint();
                            aiTurn();
                        }
                    }else {
                        //проверяем чья очередь ходить
                        if (isPlayerFlag()){
                            //очередь 1 игрока
                            //проверяем можно ли установить игроку свою фишку
                            if (setDot(cmx, cmy, PLAYER1_DOT)) {
                                //проверяем, а не ничья ли это
                                //проверяем есть ли куда поставить фишку
                                checkFieldFull();
                                //проверяем игру на предмет победы или первого игрока или второго(ПК)
                                checkWin(PLAYER1_DOT);
                                //перерисовываем игровое поле
                                repaint();
                            }
                        }else{
                            //очердь 2 игрока
                            //проверяем можно ли установить игроку свою фишку
                            if (setDot(cmx, cmy, PLAYER2_DOT)) {
                                //проверяем, а не ничья ли это
                                //проверяем есть ли куда поставить фишку
                                checkFieldFull();
                                //проверяем игру на предмет победы или первого игрока или второго(ПК)
                                checkWin(PLAYER2_DOT);
                                //перерисовываем игровое поле
                                repaint();
                            }
                        }
                        setPlayerFlag(!isPlayerFlag());
                    }
                }
            }
        });
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public void aiTurn() {
        //проверяем а не закончена ли игра, если да то ПК не надо ходить
        if (gameOver) return;
        int x, y;
        do {
            x = rand.nextInt(linesCount);
            y = rand.nextInt(linesCount);
        } while (!setDot(x, y, PLAYER2_DOT));
        //проверили а если ли куда ходить дальше, если нет то ничья
        checkFieldFull();
        //проверили а не выиграл ли ПК
        checkWin(PLAYER2_DOT);
        //перерисовали игровое поле
        repaint();
    }

    public void startNewGame(int linesCount) {
        this.linesCount = linesCount;
        CELL_SIZE = PANEL_SIZE / linesCount;
        gameOver = false;
        field = new int[linesCount][linesCount];
        repaint();
    }

    public void checkFieldFull() {
        boolean b = true;
        //пробегаем по двумерному массиву игрового поля, ищем хоть 1 не заполненное
        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                if (field[i][j] == 0) {
                    b = false;
                }
            }
        }
        if (b) {
            //все поля заполнены
            gameOver = true;
            gameOverMsg = "DRAW";
        }
    }

    public boolean checkWin(int ox) {
        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                if (    checkLine(i, j, 1, 0, dotsToWin, ox)||
                        checkLine(i, j, 0, 1, dotsToWin, ox)||
                        checkLine(i, j, 1, 1, dotsToWin, ox)||
                        checkLine(i, j, 1, -1, dotsToWin, ox)) {
                    //кто то выиграл
                    gameOver = true;
                    if (PLAYER1_DOT == ox) {
                        gameOverMsg = "PLAYER 1 WIN";
                    }
                    if (PLAYER2_DOT == ox) {
                        gameOverMsg = "PLAYER 2 WIN";
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkLine(int cx, int cy, int vx, int vy, int l, int ox) {
        if (cx + l * vx > linesCount || cy + l * vy > linesCount || cy + l * vy < -1) {
            return false;
        }
        for (int i = 0; i < l; i++) {
            if (field[cx + i * vx][cy + i * vy] != ox){
                return false;
            }
        }
        return true;
    }

    public boolean setDot(int x, int y, int dot) {
        //проверяем можно ли установить тут игроку фишку
        if (field[x][y] == 0) {
            field[x][y] = dot;
            return true;
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //метод перерисовки игрового поля
        //игровое поле в этой реализации всегда перерисовывается полностью
        //установка цвета разметки игрового поля
        //для задания толщины линии приводим наш объект для отрисовки графики Graphics к объекту  Graphics2D
        Graphics2D g2d = (Graphics2D) g;
        //задаем цвет отрисовки
        g2d.setColor(new Color(255,255,255));
        //задаем толщину линии разметки игрового поля
        g2d.setStroke(new BasicStroke(5));


        //рисуем разметку у поля
        for (int i = 0; i <= linesCount; i++) {
            g2d.drawLine(0, i * CELL_SIZE, PANEL_SIZE, i * CELL_SIZE);
            g2d.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, PANEL_SIZE);
        }

        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                if (field[i][j] > 0) {
                    if (field[i][j] == PLAYER1_DOT) {
                        g.setColor(Color.red);
                    }
                    if (field[i][j] == PLAYER2_DOT) {
                        g.setColor(Color.blue);
                    }
                    g.fillOval(i * CELL_SIZE + 2, j * CELL_SIZE + 2, CELL_SIZE - 4, CELL_SIZE - 4);
                }
            }
        }
//      в случае если игра закончена отрисовываем поверх окошко об окончании игры
        if (gameOver) {
            //устнавливаем шрифт для надписи
            g.setFont(new Font("Arial", Font.BOLD, 64));
            //рисуем само окошко надписи и заполняем его цветом
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0,getwY()/2-80, 757, 70);
            //выводим надпись 2 раза для достижения 3D эффекта

            if(gameOverMsg.equals("DRAW")){
                g.setColor(Color.black);
                g.drawString(gameOverMsg, getwX()/8, getwY()/2-20);
                g.setColor(Color.yellow);
                g.drawString(gameOverMsg, getwX()/8-2, getwY()/2-22);
            }else{
                g.setColor(Color.black);
                g.drawString(gameOverMsg, getwX()/6, getwY()/2-20);
                g.setColor(Color.yellow);
                g.drawString(gameOverMsg, getwX()/6-2, getwY()/2-22);
            }
        }
    }
}
