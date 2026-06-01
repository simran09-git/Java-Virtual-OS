import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.sound.sampled.*;
import java.net.URI;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Random;

public class VirtualOS {

    JFrame frame;
    JLabel clock;
    JLabel wallpaper;
    Timer moodAnimation;

    public VirtualOS() {

        frame = new JFrame("Java Virtual OS");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(null);

        ImageIcon bg = new ImageIcon("wallpaper.jpg");
        Image img = bg.getImage().getScaledInstance(1920,1080,Image.SCALE_SMOOTH);
        bg = new ImageIcon(img);

        wallpaper = new JLabel(bg);
        wallpaper.setBounds(0,0,1920,1080);
        frame.setContentPane(wallpaper);
        wallpaper.setLayout(null);

        JButton notes = createIcon("Notes","notes.png",80,120);
        JButton calc = createIcon("Calculator","calculator.png",260,120);
        JButton files = createIcon("Files","files.png",440,120);
        JButton music = createIcon("Music","music.png",620,120);
        JButton browser = createIcon("Browser","browser.png",80,230);

        JButton changeWall = new JButton("Change Wallpaper");
        changeWall.setBounds(260,230,160,40);

        JButton sysinfo = new JButton("System Info");
        sysinfo.setBounds(440,230,150,40);

        JButton screenshot = new JButton("Screenshot");
        screenshot.setBounds(620,230,150,40);

        JButton mood = new JButton("Mood Mode");
        mood.setBounds(80,340,150,40);

        wallpaper.add(notes);
        wallpaper.add(calc);
        wallpaper.add(files);
        wallpaper.add(music);
        wallpaper.add(browser);
        wallpaper.add(changeWall);
        wallpaper.add(sysinfo);
        wallpaper.add(screenshot);
        wallpaper.add(mood);

        JPanel taskbar = new JPanel();
        taskbar.setLayout(null);
        taskbar.setBackground(Color.BLACK);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        taskbar.setBounds(0,screen.height-60,screen.width,60);

        JButton start = new JButton("Start");
        start.setBounds(10,15,80,30);
        taskbar.add(start);

        JPopupMenu startMenu = new JPopupMenu();

        JMenuItem openNotes = new JMenuItem("Notes");
        JMenuItem openCalc = new JMenuItem("Calculator");
        JMenuItem openFiles = new JMenuItem("Files");
        JMenuItem openMusic = new JMenuItem("Music Player");
        JMenuItem openBrowser = new JMenuItem("Browser");
        JMenuItem shutdownItem = new JMenuItem("Shutdown");

        startMenu.add(openNotes);
        startMenu.add(openCalc);
        startMenu.add(openFiles);
        startMenu.add(openMusic);
        startMenu.add(openBrowser);
        startMenu.addSeparator();
        startMenu.add(shutdownItem);

        start.addActionListener(e -> startMenu.show(start,0,-150));

        openNotes.addActionListener(e -> openNotes());
        openCalc.addActionListener(e -> openCalculator());
        openFiles.addActionListener(e -> openFiles());
        openMusic.addActionListener(e -> openMusic());
        openBrowser.addActionListener(e -> openBrowser());
        shutdownItem.addActionListener(e -> System.exit(0));

        clock = new JLabel();
        clock.setForeground(Color.WHITE);
        clock.setFont(new Font("Arial",Font.BOLD,16));
        clock.setBounds(screen.width-200,15,200,30);

        taskbar.add(clock);
        wallpaper.add(taskbar);

        startClock();

        notes.addActionListener(e -> openNotes());
        calc.addActionListener(e -> openCalculator());
        files.addActionListener(e -> openFiles());
        music.addActionListener(e -> openMusic());
        browser.addActionListener(e -> openBrowser());

        changeWall.addActionListener(e -> changeWallpaper());
        sysinfo.addActionListener(e -> showSystemInfo());
        screenshot.addActionListener(e -> takeScreenshot());
        mood.addActionListener(e -> openMoodMode());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900,550);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    JButton createIcon(String text,String path,int x,int y){

        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);

        JButton btn = new JButton(text,icon);
        btn.setBounds(x,y,130,60);
        btn.setHorizontalTextPosition(JButton.CENTER);
        btn.setVerticalTextPosition(JButton.BOTTOM);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);

        return btn;
    }

    void startClock(){

        new Timer(1000,e->{

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            clock.setText(sdf.format(new Date()));

        }).start();
    }

    void openMoodMode(){

        if(moodAnimation != null){
            moodAnimation.stop();
        }

        String[] moods = {"Happy","Sleepy","Angry","Music"};

        String mood = (String)JOptionPane.showInputDialog(
                frame,"Select Mood","Mood Mode",
                JOptionPane.PLAIN_MESSAGE,null,moods,moods[0]);

        if(mood == null) return;

        if(mood.equals("Happy")){
			wallpaper.setIcon(null);
            wallpaper.setBackground(new Color(255,230,150));
            wallpaper.setOpaque(true);
            Graphics g = wallpaper.getGraphics();
            g.setColor(Color.ORANGE);
            g.setFont(new Font("Arial",Font.BOLD,120));
            g.drawString("HAPPY",700,400);
        }

        if(mood.equals("Sleepy")){
			wallpaper.setIcon(null);
            wallpaper.setBackground(new Color(10,10,40));
            wallpaper.setOpaque(true);

            moodAnimation = new Timer(300,e->{

                Graphics g = wallpaper.getGraphics();
                g.setColor(Color.WHITE);

                Random r = new Random();

                int x = r.nextInt(wallpaper.getWidth());
                int y = r.nextInt(wallpaper.getHeight());

                g.fillOval(x,y,3,3);

            });

            moodAnimation.start();
        }

        if(mood.equals("Angry")){
			wallpaper.setIcon(null);
            wallpaper.setBackground(new Color(120,0,0));
            wallpaper.setOpaque(true);
            Graphics g = wallpaper.getGraphics();
            g.setFont(new Font("Arial",Font.BOLD,120));
            g.setColor(Color.WHITE);
            g.drawString("ANGRY",700,400);
        }

        if(mood.equals("Music")){
			wallpaper.setIcon(null);

            wallpaper.setBackground(new Color(60,0,120));
            wallpaper.setOpaque(true);

            String[] icons = {"sound.png",
                           "headphone.png"
             };

            moodAnimation = new Timer(500, e -> {

                Random r = new Random();

                int x = r.nextInt(wallpaper.getWidth()-50);
                int y = r.nextInt(wallpaper.getHeight()-50);

                ImageIcon icon = new ImageIcon(icons[r.nextInt(icons.length)]);
                Image img = icon.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH);
                icon = new ImageIcon(img);

                JLabel label = new JLabel(icon);
                label.setBounds(x,y,40,40);

                wallpaper.add(label);
                wallpaper.repaint();

            });

            moodAnimation.start();
        }
    }

    void changeWallpaper(){

        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);

        try{
            File file = chooser.getSelectedFile();
            ImageIcon bg = new ImageIcon(file.getAbsolutePath());

            Image img = bg.getImage().getScaledInstance(1920,1080,Image.SCALE_SMOOTH);
            wallpaper.setIcon(new ImageIcon(img));
        }catch(Exception ex){}
    }

    void showSystemInfo(){

        String os = System.getProperty("os.name");
        String javaVersion = System.getProperty("java.version");
        long memory = Runtime.getRuntime().totalMemory()/1024/1024;

        JOptionPane.showMessageDialog(frame,
                "Operating System: "+os+
                "\nJava Version: "+javaVersion+
                "\nMemory: "+memory+" MB");
    }

    void takeScreenshot(){

        try{

            Robot robot = new Robot();
            Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

            BufferedImage img = robot.createScreenCapture(screen);
            ImageIO.write(img,"png",new File("screenshot.png"));

            JOptionPane.showMessageDialog(frame,"Screenshot Saved!");

        }catch(Exception ex){}
    }

    void openNotes(){

	    JFrame noteFrame = new JFrame("Notes");
	    noteFrame.setSize(400,350);

	    JTextArea area = new JTextArea();
	    area.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));

	    JButton save = new JButton("Save Note");
	    JButton delete = new JButton("Delete Note");

	    JButton e1 = new JButton("😊");
	    JButton e2 = new JButton("😂");
	    JButton e3 = new JButton("❤️");
	    JButton e4 = new JButton("🔥");
	    JButton e5 = new JButton("🎉");
	    JButton e6 = new JButton("🎧");
	    JButton e7 = new JButton("👍");
	    JButton e8 = new JButton("😢");

	    DefaultListModel<String> model = new DefaultListModel<>();
	    JList<String> list = new JList<>(model);

	    try{
	        BufferedReader br = new BufferedReader(new FileReader("notes.txt"));
	        String line;

	        while((line = br.readLine()) != null){
	            model.addElement(line);
	        }

	        br.close();
	    }
	    catch(Exception ex){}

	    save.addActionListener(e -> {

	        try{

	            String text = area.getText();

	            SimpleDateFormat sdf =
	            new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	            String time = sdf.format(new Date());

	            String note = time + " : " + text;

	            FileWriter fw =
	            new FileWriter("notes.txt", true);

	            fw.write(note + "\n");
	            fw.close();

	            model.addElement(note);

	            area.setText("");

	        }
	        catch(Exception ex){}
	    });

	    delete.addActionListener(ev -> {

	        int index = list.getSelectedIndex();

	        if(index == -1){

	            JOptionPane.showMessageDialog(
	            noteFrame,
	            "Select a note to delete");

	            return;
	        }

	        model.remove(index);

	        try{

	            BufferedWriter bw =
	            new BufferedWriter(
	            new FileWriter("notes.txt"));

	            for(int i=0;i<model.size();i++){

	                bw.write(model.getElementAt(i));
	                bw.newLine();
	            }

	            bw.close();

	        }
	        catch(Exception ex){
	            ex.printStackTrace();
	        }
	    });

	    e1.addActionListener(e -> area.append("😊"));
	    e2.addActionListener(e -> area.append("😂"));
	    e3.addActionListener(e -> area.append("❤️"));
	    e4.addActionListener(e -> area.append("🔥"));
	    e5.addActionListener(e -> area.append("🎉"));
	    e6.addActionListener(e -> area.append("🎧"));
	    e7.addActionListener(e -> area.append("👍"));
	    e8.addActionListener(e -> area.append("😢"));

	    JPanel emojiPanel = new JPanel();
	    emojiPanel.add(e1);
	    emojiPanel.add(e2);
	    emojiPanel.add(e3);
	    emojiPanel.add(e4);
	    emojiPanel.add(e5);
	    emojiPanel.add(e6);
	    emojiPanel.add(e7);
	    emojiPanel.add(e8);

	    noteFrame.setLayout(new GridLayout(5,1));

	    noteFrame.add(new JScrollPane(area));
	    noteFrame.add(save);
	    noteFrame.add(delete);
	    noteFrame.add(emojiPanel);
	    noteFrame.add(new JScrollPane(list));

	    noteFrame.setVisible(true);
}


    void openCalculator(){

        JFrame calc = new JFrame("Calculator");
        calc.setSize(320,260);
        calc.setLayout(null);

        JTextField a = new JTextField();
        JTextField b = new JTextField();

        JLabel result = new JLabel("Result: ");

        JButton add = new JButton("+");
        JButton sub = new JButton("-");
        JButton mul = new JButton("*");
        JButton div = new JButton("/");

        a.setBounds(30,30,110,30);
        b.setBounds(170,30,110,30);

        add.setBounds(30,90,50,30);
        sub.setBounds(90,90,50,30);
        mul.setBounds(150,90,50,30);
        div.setBounds(210,90,50,30);

        result.setBounds(30,150,200,30);

        calc.add(a);
        calc.add(b);
        calc.add(add);
        calc.add(sub);
        calc.add(mul);
        calc.add(div);
        calc.add(result);

        ActionListener act = e->{

            try{

                double x = Double.parseDouble(a.getText());
                double y = Double.parseDouble(b.getText());
                double r=0;

                if(e.getSource()==add) r=x+y;
                if(e.getSource()==sub) r=x-y;
                if(e.getSource()==mul) r=x*y;
                if(e.getSource()==div) r=x/y;

                result.setText("Result: "+r);

            }catch(Exception ex){
                JOptionPane.showMessageDialog(calc,"Enter valid numbers");
            }
        };

        add.addActionListener(act);
        sub.addActionListener(act);
        mul.addActionListener(act);
        div.addActionListener(act);

        calc.setVisible(true);
    }

    void openFiles(){

        JFrame f = new JFrame("Files");
        f.setSize(400,350);

        File folder = new File(".");
        JList<File> list = new JList<>(folder.listFiles());

        list.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount()==2)
				{
					File selectedFile=list.getSelectedValue();
					try{
						Desktop.getDesktop().open(selectedFile);
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
		});

        f.add(new JScrollPane(list));
        f.setVisible(true);
    }

    void openMusic(){

        JFrame music = new JFrame("Music Player");
        music.setSize(300,200);

        JButton choose = new JButton("Choose Music");

        choose.addActionListener(e->{

            JFileChooser fc = new JFileChooser();
            fc.showOpenDialog(null);

            try{

                File file = fc.getSelectedFile();
                AudioInputStream audio = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.start();

            }catch(Exception ex){}
        });

        music.add(choose);
        music.setVisible(true);
    }

    void openBrowser(){

        try{
            Desktop.getDesktop().browse(new URI("https://www.google.com"));
        }catch(Exception ex){}
    }

    public static void main(String[] args) {

        new VirtualOS();
    }
}