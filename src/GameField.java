import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;


public class GameField extends JPanel implements ActionListener {
    //JPanel - основной класс от которого наследуется GameField
    private final int SIZE = 320;//Размер поля
    private final int DOT_SIZE = 16;//Размер пикселя, которую
    //занимает змейка и яблок
    private final int ALL_DOTS = 400;//Игровые единицы
    private Image dot;//Импортировать из авт
    private Image apple;//Импортировать
    private int appleX;//x - позиция яблока
    private int appleY;//y - позиция яблока
    private int[] x = new int[ALL_DOTS];
    //Массив для позиции змейки по x
    private int[] y = new int[ALL_DOTS];
    //Массив для позиции змейки по y
    private int dots; //Размер змейки
    private Timer timer;
    //Поля типо boolean, которые отвечают за направление змейки
    private boolean up = false;
    private boolean left = false;
    private boolean right = true;
    private boolean down = false;
    private boolean inGame = true;

    public GameField() { //Конструктор игрового окна
        setBackground(Color.black); //Вызов метода заполнения черным цветом
        loadImages(); //Вызов методkа загрузки картинок
        initGame(); //Вызов метода инициализации(запуска) игры
        addKeyListener( new FieldKeyListener()); //Вызов обработчик событий
        setFocusable(true); //Устанавливается фокус на игровое поле
    }

    public void initGame() {//Создание метода
        //Для основного игрового процесса
        //Он же инициализирует начало игры
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            //Начальная позиция начинается с №
            y[i] = 48;//Всё время будет на одной линии
        }
        timer = new Timer(150, this);
        //250 - частота таймера
        //this - класс GameField будет отвечать за обратоку таймера
        timer.start();//Создать таймер
        creatApple();//Создать новое яблоко
    }

    public void creatApple() {//Создание метода createApple
        appleX = new Random().nextInt(20)*DOT_SIZE;
        appleY = new Random().nextInt(20)*DOT_SIZE;
        //Может поместиться 20 игровых квадратов по 16 пиклесей 16*20=320
    }

    public void loadImages() {
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }

    @Override //Перерисовывает окно
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//Техническая перерисовка
        //Перерисовывается то, что касается игры
        if(inGame){
            g.drawImage(apple,appleX,appleY,this);//Рисование яблока
            for(int i = 0; i < dots; i++){ //Перерисовывается вся змейка
                g.drawImage(dot,x[i],y[i],this); //Добавляется часть к змейки
            }
        } else{
            String str = "Game Over";
            //    Font f = new  Font("Arial", 14, Font.BOLD);//Стиль текста
            g.setColor((Color.white));//Фон текста - белый
            //    g.setFont(f);//Вызов метода стиля текста
            g.drawString(str,125, SIZE/2);//Расположение текста
        }
    }


    public void move(){ //move - метод для движения змейки
        for (int i = dots; i > 0; i--){ //Передвижение змейки
            x[i] = x[i-1];//движение по x координатам
            y[i] = y[i-1];//движение по y координатам
            //Это перемещение в сторону головы
        }
        //Условие перемещение для головы
        if (left){
            x[0] -= DOT_SIZE;
        }
        if (right){
            x[0] += DOT_SIZE;
        }
        if (up){
            y[0] -= DOT_SIZE;
        }
        if (down){
            y[0] += DOT_SIZE;
        }
    }


    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){ //Проверка если голова змейки коснулась яблока
            dots++;
            creatApple();//Создание нового яблока
        }
    }

    public void checkColisions(){
        for( int i = dots; i > 0; i--){//Проверка на столкновение змейки самой с собой
            if(i > 4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;// Столкновение самим с собой
            }
        }
        if (x[0] > SIZE){
            inGame = false;
        }
        if (x[0] < 0){
            inGame = false;
        }
        if (y[0]  > SIZE){
            inGame = false;
        }
        if (y[0] < 0){
            inGame = false;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Обрабатываеся метод в соответсвие с таймером каждые 250 мсек
        if (inGame) {
            checkApple(); //Проверка на косание с яблоком
            checkColisions();//Проверка на косание игрового поля
            move();//Движение змейки
        }
        repaint();//repaint вызывает paintComponent(стандартный метод для отрисовки в Swing)
        //paintComponent - перерисовка всего игрового поля
    }


    //Нажатие клавиш
    class FieldKeyListener extends KeyAdapter{ //Наследование класса KeyAdapter - нажатие на клавишу
        @Override //Переопределение метода нажатия на клавиши
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key  = e.getKeyCode();//Определение клавиши, которая была нажата
            if (key == KeyEvent.VK_LEFT && !right) { //Нажата клавиша влева и при этом не двигаюсь вправо
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) { //Нажата клавиша вправо и при этом не двигаюсь влево
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) { //Нажата клавиша вверх и при этом не двигаюсь вниз
                right = false;
                up = true;
                down = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) { //Нажата клавиша вниз и при этом не двигаюсь вверх
                right = false;
                left = false;
                down = true;
            }
        }
    }
}
