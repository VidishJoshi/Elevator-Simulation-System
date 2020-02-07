
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.swing.JLabel;
import javax.swing.JTextArea;

class Lift extends Thread {

    JLabel label;
    JTextArea lab2;
    boolean exit;
    int time = 5;
    int FloorHeight = 80;
    int currentFloor = 0;
    boolean state;
    int nextFloor = 0;

    public static ArrayList<Pair<Integer, Boolean>> list1 = new ArrayList<Pair<Integer, Boolean>>();

    Lift(JLabel l, ArrayList<Pair<Integer, Boolean>> listvy,  boolean exit, JTextArea j2) {
        label = l;
        this.list1 = listvy;
        this.exit = exit;
        this.lab2 = j2;
    }
    int index = 0;
    int number = 0;
    

    @Override
    public void run() {
        while (!(list1.isEmpty()) && GUI.exit == false) {
            for (Pair<Integer, Boolean> p : list1) {
                nextFloor = p.getKey();
                state = p.getValue();
                break;
            }
            String s = "" + (nextFloor );
                lab2.setText(s);

            

            if (nextFloor > currentFloor) {
                moveUP();
            } else if (nextFloor < currentFloor) {
                moveDOWN();
            }
            if (state == true) {
                label.setBackground(Color.green);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Lift.class.getName()).log(Level.SEVERE, null, ex);
                } 
                label.setBackground(Color.red);

            }
            list1.remove(0);


            currentFloor = nextFloor;
        }
        // System.out.println("> "+this.getName()+"is running, LIFT (X: "+label.getX()+", Y: "+label.getY()+")");
    }

    void moveUP() {
        for (int i = 1; i <= FloorHeight; i++) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException ex) {
                Logger.getLogger(Lift_System.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.label.setLocation(this.label.getX(), this.label.getY() - 1);
        }
    }

    void moveDOWN() {
        for (int i = 1; i <= FloorHeight; i++) {

            try {
                Thread.sleep(time);
            } catch (InterruptedException ex) {
                Logger.getLogger(Lift_System.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.label.setLocation(this.label.getX(), this.label.getY() + 1);
        }
    }
}

public class Lift_System extends javax.swing.JFrame {

    public Lift_System() {
        initComponents();
        this.getContentPane().setBackground(Color.white);
    }

    static ArrayList call = new ArrayList();
    static ArrayList dest = new ArrayList();
    boolean holdAt[] = new boolean[11];
    public static int FloorHeight = 80;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        outL0 = new javax.swing.JLabel();
        outL1 = new javax.swing.JLabel();
        outL2 = new javax.swing.JLabel();
        outL3 = new javax.swing.JLabel();
        outL4 = new javax.swing.JLabel();
        outL5 = new javax.swing.JLabel();
        outL6 = new javax.swing.JLabel();
        outL7 = new javax.swing.JLabel();
        outL8 = new javax.swing.JLabel();
        outL9 = new javax.swing.JLabel();
        outL10 = new javax.swing.JLabel();
        inL0 = new javax.swing.JLabel();
        inL1 = new javax.swing.JLabel();
        inL2 = new javax.swing.JLabel();
        inL3 = new javax.swing.JLabel();
        inL4 = new javax.swing.JLabel();
        inL5 = new javax.swing.JLabel();
        inL6 = new javax.swing.JLabel();
        inL7 = new javax.swing.JLabel();
        inL8 = new javax.swing.JLabel();
        inL9 = new javax.swing.JLabel();
        inL10 = new javax.swing.JLabel();
        LIFT = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        outL0.setBackground(new java.awt.Color(255, 102, 0));
        outL0.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        outL0.setForeground(new java.awt.Color(255, 255, 255));
        outL0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        outL0.setText("Level 0");
        outL0.setOpaque(true);
        outL0.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                outL0MouseClicked(evt);
            }
        });

        outL1.setBackground(new java.awt.Color(255, 102, 0));
        outL1.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        outL1.setForeground(new java.awt.Color(255, 255, 255));
        outL1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        outL1.setText("Level 1");
        outL1.setOpaque(true);
        outL1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                outL1MouseClicked(evt);
            }
        });

        outL2.setBackground(new java.awt.Color(255, 102, 0));
        outL2.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        outL2.setForeground(new java.awt.Color(255, 255, 255));
        outL2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        outL2.setText("Level 2");
        outL2.setOpaque(true);
        outL2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                outL2MouseClicked(evt);
            }
        });

        outL3.setBackground(new java.awt.Color(255, 102, 0));
        outL3.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        outL3.setForeground(new java.awt.Color(255, 255, 255));
        outL3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        outL3.setText("Level 3");
        outL3.setOpaque(true);
        outL3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                outL3MouseClicked(evt);
            }
        });

        outL4.setBackground(new java.awt.Color(255, 102, 0));
        outL4.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        outL4.setForeground(new java.awt.Color(255, 255, 255));
        outL4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        outL4.setText("Level 4");
        outL4.setOpaque(true);
        outL4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                outL4MouseClicked(evt);
            }
        });

        outL5.setBackground(new java.awt.Color(255, 102, 0));
        outL5.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        outL5.setForeground(new java.awt.Color(255, 255, 255));
        outL5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        outL5.setText("Level 5");
        outL5.setOpaque(true);
        outL5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                outL5MouseClicked(evt);
            }
        });

        outL6.setBackground(new java.awt.Color(255, 102, 0));
        outL6.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        outL6.setForeground(new java.awt.Color(255, 255, 255));
        outL6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        outL6.setText("Level 6");
        outL6.setOpaque(true);
        outL6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                outL6MouseClicked(evt);
            }
        });

        outL7.setBackground(new java.awt.Color(255, 102, 0));
        outL7.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        outL7.setForeground(new java.awt.Color(255, 255, 255));
        outL7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        outL7.setText("Level 7");
        outL7.setOpaque(true);
        outL7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                outL7MouseClicked(evt);
            }
        });

        outL8.setBackground(new java.awt.Color(255, 102, 0));
        outL8.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        outL8.setForeground(new java.awt.Color(255, 255, 255));
        outL8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        outL8.setText("Level 8");
        outL8.setOpaque(true);
        outL8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                outL8MouseClicked(evt);
            }
        });

        outL9.setBackground(new java.awt.Color(255, 102, 0));
        outL9.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        outL9.setForeground(new java.awt.Color(255, 255, 255));
        outL9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        outL9.setText("Level 9");
        outL9.setOpaque(true);
        outL9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                outL9MouseClicked(evt);
            }
        });

        outL10.setBackground(new java.awt.Color(255, 102, 0));
        outL10.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        outL10.setForeground(new java.awt.Color(255, 255, 255));
        outL10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        outL10.setText("Level 10");
        outL10.setOpaque(true);
        outL10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                outL10MouseClicked(evt);
            }
        });

        inL0.setBackground(new java.awt.Color(255, 0, 0));
        inL0.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        inL0.setForeground(new java.awt.Color(255, 255, 255));
        inL0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inL0.setText("Level 0");
        inL0.setOpaque(true);
        inL0.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inL0MouseClicked(evt);
            }
        });

        inL1.setBackground(new java.awt.Color(255, 0, 0));
        inL1.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        inL1.setForeground(new java.awt.Color(255, 255, 255));
        inL1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inL1.setText("Level 1");
        inL1.setOpaque(true);
        inL1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inL1MouseClicked(evt);
            }
        });

        inL2.setBackground(new java.awt.Color(255, 0, 0));
        inL2.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        inL2.setForeground(new java.awt.Color(255, 255, 255));
        inL2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inL2.setText("Level 2");
        inL2.setOpaque(true);
        inL2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inL2MouseClicked(evt);
            }
        });

        inL3.setBackground(new java.awt.Color(255, 0, 0));
        inL3.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        inL3.setForeground(new java.awt.Color(255, 255, 255));
        inL3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inL3.setText("Level 3");
        inL3.setOpaque(true);
        inL3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inL3MouseClicked(evt);
            }
        });

        inL4.setBackground(new java.awt.Color(255, 0, 0));
        inL4.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        inL4.setForeground(new java.awt.Color(255, 255, 255));
        inL4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inL4.setText("Level 4");
        inL4.setOpaque(true);
        inL4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inL4MouseClicked(evt);
            }
        });

        inL5.setBackground(new java.awt.Color(255, 0, 0));
        inL5.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        inL5.setForeground(new java.awt.Color(255, 255, 255));
        inL5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inL5.setText("Level 5");
        inL5.setOpaque(true);
        inL5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inL5MouseClicked(evt);
            }
        });

        inL6.setBackground(new java.awt.Color(255, 0, 0));
        inL6.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        inL6.setForeground(new java.awt.Color(255, 255, 255));
        inL6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inL6.setText("Level 6");
        inL6.setOpaque(true);
        inL6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inL6MouseClicked(evt);
            }
        });

        inL7.setBackground(new java.awt.Color(255, 0, 0));
        inL7.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        inL7.setForeground(new java.awt.Color(255, 255, 255));
        inL7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inL7.setText("Level 7");
        inL7.setOpaque(true);
        inL7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inL7MouseClicked(evt);
            }
        });

        inL8.setBackground(new java.awt.Color(255, 0, 0));
        inL8.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        inL8.setForeground(new java.awt.Color(255, 255, 255));
        inL8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inL8.setText("Level 8");
        inL8.setOpaque(true);
        inL8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inL8MouseClicked(evt);
            }
        });

        inL9.setBackground(new java.awt.Color(255, 0, 0));
        inL9.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        inL9.setForeground(new java.awt.Color(255, 255, 255));
        inL9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inL9.setText("Level 9");
        inL9.setOpaque(true);
        inL9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inL9MouseClicked(evt);
            }
        });

        inL10.setBackground(new java.awt.Color(255, 0, 0));
        inL10.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        inL10.setForeground(new java.awt.Color(255, 255, 255));
        inL10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inL10.setText("Level 10");
        inL10.setOpaque(true);
        inL10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inL10MouseClicked(evt);
            }
        });

        LIFT.setBackground(new java.awt.Color(0, 255, 0));
        LIFT.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        LIFT.setForeground(new java.awt.Color(255, 255, 255));
        LIFT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LIFT.setText("LIFT");
        LIFT.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(326, 326, 326)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(outL10, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outL9, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outL8, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outL7, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outL6, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outL5, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outL4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outL3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outL2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outL1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(LIFT, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outL0, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inL10, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL0, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL5, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL6, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL7, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL8, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL9, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(350, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(73, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outL10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outL9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outL8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outL7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outL6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outL5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outL4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outL3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outL2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outL1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outL0, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL0, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LIFT, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(249, 249, 249))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked

    private void outL0MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outL0MouseClicked
        // TODO add your handling code here:

//        Lift lift = new Lift(LIFT);
//        System.out.println("" + outL10.getX() + ", " + outL10.getY());
//        if (!lift.isAlive()) {
//            lift.start();
//        }
    }//GEN-LAST:event_outL0MouseClicked

    private void outL1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outL1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_outL1MouseClicked

    private void outL2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outL2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_outL2MouseClicked

    private void outL3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outL3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_outL3MouseClicked

    private void outL4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outL4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_outL4MouseClicked

    private void outL5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outL5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_outL5MouseClicked

    private void outL6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outL6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_outL6MouseClicked

    private void outL7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outL7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_outL7MouseClicked

    private void outL8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outL8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_outL8MouseClicked

    private void outL9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outL9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_outL9MouseClicked

    private void outL10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outL10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_outL10MouseClicked

    private void inL0MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inL0MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_inL0MouseClicked

    private void inL1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inL1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_inL1MouseClicked

    private void inL2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inL2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_inL2MouseClicked

    private void inL3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inL3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_inL3MouseClicked

    private void inL4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inL4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_inL4MouseClicked

    private void inL5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inL5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_inL5MouseClicked

    private void inL6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inL6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_inL6MouseClicked

    private void inL7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inL7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_inL7MouseClicked

    private void inL8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inL8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_inL8MouseClicked

    private void inL9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inL9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_inL9MouseClicked

    private void inL10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inL10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_inL10MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Lift_System.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Lift_System.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Lift_System.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Lift_System.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Lift_System().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    static javax.swing.JLabel LIFT;
    private javax.swing.JLabel inL0;
    private javax.swing.JLabel inL1;
    private javax.swing.JLabel inL10;
    private javax.swing.JLabel inL2;
    private javax.swing.JLabel inL3;
    private javax.swing.JLabel inL4;
    private javax.swing.JLabel inL5;
    private javax.swing.JLabel inL6;
    private javax.swing.JLabel inL7;
    private javax.swing.JLabel inL8;
    private javax.swing.JLabel inL9;
    javax.swing.JLabel outL0;
    private javax.swing.JLabel outL1;
    private javax.swing.JLabel outL10;
    private javax.swing.JLabel outL2;
    private javax.swing.JLabel outL3;
    private javax.swing.JLabel outL4;
    private javax.swing.JLabel outL5;
    private javax.swing.JLabel outL6;
    private javax.swing.JLabel outL7;
    private javax.swing.JLabel outL8;
    private javax.swing.JLabel outL9;
    // End of variables declaration//GEN-END:variables
}
