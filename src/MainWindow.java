import javax.swing.*;
public class MainWindow extends JFrame{//JFrame - основной класс от которого наследуется MainWindow
    public MainWindow(){ //Конструктор
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //При нажатие на крестик - конец игры
        setSize(320,345);//Игровое окно
        setLocation(400,400);//
        add(new GameField());
        setVisible(true);//Отображаетигровое окно
    }
    public static void main(String[] args){
        MainWindow mv = new MainWindow();
        // mv - Экземляр класса MainWindow, то откуда программа
        //начинает свою работу
    }
}