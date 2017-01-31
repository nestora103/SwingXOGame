package ru.geekbrains.java1.dz.dz8.gubenkoDM;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyWindow extends JFrame {

    private Map jpMap;
    private Font btnFont = new Font("Times New Roman", Font.PLAIN, 16);
    private JButton jbSM3x3;
    private JButton jbSM5x5;
    private JButton jbSM10x10;
    private JButton jbWin3;
    private JButton jbWin4;
    private JButton jbWin5;


    public MyWindow() {
        //размер окна на момент начала игры
        setSize(700, 80);
        //изменение масштабируемости окна
        setResizable(false);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        //позиционирование окна всередине экрана
        setLocation(screenWidth/3,screenHeight/5);
        //название окна
        setTitle("NestoraXO");
        //завершение программы при закрытии
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //создание игрового поля игры
        jpMap = new Map(705,787);
        //задание игровых размеров поля. 3*3, 5*5,10*10
        //jpMap.startNewGame(3);
        //устанавливаем менеджер компановки окна
        setLayout(new BorderLayout());
        //игровое поле разместим в центре
        add(jpMap, BorderLayout.CENTER);

        // Основная нижняя панель
        CardLayout cardLayout = new CardLayout();
        JPanel jpBottom = new JPanel(cardLayout);
        //создание панели нижнего меню
        //JPanel jpBottom = new JPanel(new CardLayout());
        //указание предпочтительных размеров окна
        jpBottom.setPreferredSize(new Dimension(1, 60));
        //разместим ее над игровым полем
        add(jpBottom, BorderLayout.NORTH);

        // Создание панели  с кнопками Старт/Выход (1)
        JPanel jpStartExit = new JPanel(new GridLayout());
        JButton jbStart = new JButton("Начать новую игру");
        //установка шрифта для надписи
        jbStart.setFont(btnFont);
        //обработка кнопки запуска новой игры
        jbStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //при нажатии на кнопку показываем панель с кнопками для выбора колич. игроков
                // для показа передаем родительскую панель в которой будем показывать нужную нам панель
                // и имя нужной панели
                cardLayout.show(jpBottom,"jpSelectPlayers");
                setSize(700, 80);
            }
        });
        jpStartExit.add(jbStart);
        JButton jbExit = new JButton("Выйти из игры");
        jbExit.setFont(btnFont);
        //обработка кнопки выхода из игры
        jbExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //завершаем приложение
                System.exit(0);
            }
        });
        jpStartExit.add(jbExit);
        jpBottom.add(jpStartExit, "jpStartExit");

        // Создание панели выбора игроков (2)
        JPanel jpSelectPlayers = new JPanel(new GridLayout());
        JButton jbHumanVsHuman = new JButton("Человек vs. Человек");
        //обработка кнопки для игры человек против человека
        jbHumanVsHuman.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jpMap.setPlayMode(false);
                jpMap.setPlayModStr(new String("H vs H"));
                jbSM3x3.setText(new String("Режим:"+jpMap.getPlayModStr())+"Поле:3x3");
                jbSM5x5.setText(new String("Режим:"+jpMap.getPlayModStr())+"Поле:5x5");
                jbSM10x10.setText(new String("Режим:"+jpMap.getPlayModStr())+"Поле:10x10");
                cardLayout.show(jpBottom,"jpSelectMapSize");
            }
        });
        JButton jbHumanVsAI = new JButton("Человек vs. AI");
        jbHumanVsAI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jpMap.setPlayModStr(new String("H vs AI"));
                jbSM3x3.setText(new String("Режим:"+jpMap.getPlayModStr())+" Поле:3x3");
                jbSM5x5.setText(new String("Режим:"+jpMap.getPlayModStr())+" Поле:5x5");
                jbSM10x10.setText(new String("Режим:"+jpMap.getPlayModStr())+" Поле:10x10");
                cardLayout.show(jpBottom,"jpSelectMapSize");
            }
        });
        jpSelectPlayers.add(jbHumanVsHuman);
        jpSelectPlayers.add(jbHumanVsAI);
        jpBottom.add(jpSelectPlayers, "jpSelectPlayers");

        //Выбор размера поля
        // Создание панели выбора размера поля (3)
        JPanel jpSelectMapSize = new JPanel(new GridLayout());
        jbSM3x3 = new JButton();
        jbSM5x5 = new JButton();
        jbSM10x10 = new JButton();

        jpSelectMapSize.add(jbSM3x3);
        //обработка варианта игры 3*3
        jbSM3x3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int size=3;
                jpMap.setPlaySize(size);
                setButtons(size);
                cardLayout.show(jpBottom,"jpSelectWin");
            }
        });

        jpSelectMapSize.add(jbSM5x5);
        //обработка варианта игры 5*5
        jbSM5x5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int size=5;
                jpMap.setPlaySize(size);
                setButtons(size);
                cardLayout.show(jpBottom,"jpSelectWin");
            }
        });

        jpSelectMapSize.add(jbSM10x10);
        //обработка варианта игры 10*10
        jbSM10x10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int size=10;
                jpMap.setPlaySize(size);
                setButtons(size);
                cardLayout.show(jpBottom,"jpSelectWin");
            }
        });
        jpBottom.add(jpSelectMapSize, "jpSelectMapSize");


        // Создание панели выбора размера выигрышной последов. (4)
        JPanel jpSelectWin = new JPanel(new GridLayout());
        jbWin3 = new JButton();
        jbWin4 = new JButton();
        jbWin5 = new JButton();

        jpSelectWin.add(jbWin3);
        //обработка варианта игры 3*3
        jbWin3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(jpBottom,"jpStartExit");
                jpMap.setDotsToWin(3);
                createMap(jpMap.getPlaySize());
            }
        });
        jpSelectWin.add(jbWin4);
        //обработка варианта игры 5*5
        jbWin4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(jpBottom,"jpStartExit");
                jpMap.setDotsToWin(4);
                createMap(jpMap.getPlaySize());
            }
        });
        jpSelectWin.add(jbWin5);
        //обработка варианта игры 10*10
        jbWin5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(jpBottom,"jpStartExit");
                jpMap.setDotsToWin(5);
                createMap(jpMap.getPlaySize());
            }
        });

        jpBottom.add(jpSelectWin, "jpSelectWin");
        cardLayout.show(jpBottom,"jpStartExit");
        //pack();

    }

    public void createMap(int size) {
        //показываем игровое окно полностью
        showGameW();
        //
        jpMap.startNewGame(size);
    }

    private void showGameW(){
        setSize(706, 789);
    }

    private void setButtons(int size){
        jbWin3.setText(new String("Режим:"+jpMap.getPlayModStr())+" Поле:"+Integer.toString(size).concat("x").concat(Integer.toString(size))+" Серия:3");
        jbWin4.setText(new String("Режим:"+jpMap.getPlayModStr())+" Поле:"+Integer.toString(size).concat("x").concat(Integer.toString(size))+" Серия:4");
        jbWin5.setText(new String("Режим:"+jpMap.getPlayModStr())+" Поле:"+Integer.toString(size).concat("x").concat(Integer.toString(size))+" Серия:5");
    }


}
