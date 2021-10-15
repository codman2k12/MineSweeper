import javax.swing.*;
import java.awt.*;

import ru.gerasimov.sweeper.Coords;
import ru.gerasimov.sweeper.Game;
import ru.gerasimov.sweeper.Diapazone;
import ru.gerasimov.sweeper.Space;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MineSweeper extends JFrame
{
    private Game game;
    private JPanel panel;
    private JLabel label;
    private JMenu gamer;
    private JMenuItem newGame, exit;;
    private JMenuBar menubar;
    private final int columns = 9;
    private final int rows = 9;
    private final int bombs = 10;
    private final int image_size = 35;


    public static void main(String[] args)
    {
        new MineSweeper();
    }

    private MineSweeper()
    {
        game = new Game(columns, rows, bombs);
        game.start();
        setImages();
        initLabel();
        initMenu();
        initPanel();
        initFrame();
    }

    private void initLabel()
    {
        label = new JLabel("Добро пожаловать!");
        add(label, BorderLayout.SOUTH);
    }

    private void initMenu()
    {
        newGame = new JMenuItem("Новая игра");
        exit=new JMenuItem("Выхоl");
        menubar = new JMenuBar();
        gamer=new JMenu("Игра");
        newGame.addActionListener(this::actionPerformed);
        gamer.add(newGame);
        exit.addActionListener(this::actionPerformed);
        gamer.add(exit);
        menubar.add(gamer);
        add(menubar, BorderLayout.NORTH);


    }

    private void actionPerformed(ActionEvent e) {
        if(e.getSource()==newGame)
            game.start();
        if(e.getSource()==exit)
            System.exit(0);
    }

    private void initPanel()
    {
        panel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                for (Coords coords : Diapazone.getAllCoords()) {
                    g.drawImage((Image) game.getBox(coords).image, coords.x * image_size, coords.y * image_size, this);
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / image_size;
                int y = e.getY() / image_size;
                Coords coords = new Coords(x, y);
                if(e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton(coords);
                if(e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton(coords);
                if(e.getButton() == MouseEvent.BUTTON2)
                    game.start();
                label.setText(getMessage());
                panel.repaint();
            }
        });

        panel.setPreferredSize(new Dimension(Diapazone.getSize().x * image_size, Diapazone.getSize().y * image_size));
        add(panel);
    }

    private String getMessage()
    {
        switch (game.getState()) {
            case PLAYED:
                return "Подумайте дважды";
            case BOMBED:
                return "ВЫ ПРОИГРАЛИ! БОЛЬШОЙ БУМ!";
            case WINNER:
                return "ВЫ ВЫИГРАЛИ!!!!";
            default:
                return "Добро пожаловать!";
        }
    }


    private void initFrame()
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Сапёр");
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setIconImage(getImage("icon"));
    }

    private void setImages()
    {
        for (Space space : Space.values())
        {
            space.image = getImage(space.name().toLowerCase());
        }
    }
    private Image getImage(String name)
    {
        String filename = "img/" + name + ".png" ;
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
}
