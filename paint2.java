/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;

public class paint2 extends JFrame {
 public  static Color c ;
    ArrayList<ActionPaint> lines = new ArrayList<ActionPaint>();
    ActionPaint currentLine = new ActionPaint();
    GLCanvas glc;
    public paint2() {
        super("PAINT");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         glc = new GLCanvas();
        glc.addGLEventListener(currentLine);
        JPanel pnl = new JPanel();
        JButton B1 = new JButton("Color");
        B1.setActionCommand("Color");
        B1.addActionListener(currentLine);
        JButton B2 = new JButton("Clear");
        B2.setActionCommand("Clear");
        B2.addActionListener(currentLine);
        JButton B3 = new JButton("undo");
        B3.setActionCommand("undo");
        B3.addActionListener(currentLine);
        JButton B4 = new JButton("redo");
        B4.setActionCommand("redo");
        B4.addActionListener(currentLine);
        pnl.add(B1);pnl.add(B2);pnl.add(B3);pnl.add(B4);
        getContentPane().add("South",pnl);
        glc.addMouseListener(currentLine);
        glc.addMouseMotionListener(currentLine);
       glc.addGLEventListener(currentLine);
       pnl.setBackground(Color.blue);
       this.setSize(1000, 800);
       this.setLocation(500, 200);
       getContentPane().add( glc,BorderLayout.CENTER);      

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                new Runnable() {
            public void run() {
                new paint2();
            }
        }
        );
    }

    class ActionPaint implements GLEventListener, MouseListener, MouseMotionListener,ActionListener {

        List<Integer> xline;
        List<Integer> yline;

        ActionPaint() {
            xline = new ArrayList<>();
            yline = new ArrayList<>();;
        }

        public void addPoint(int x, int y) {
            xline.add(x);
            yline.add(y);
        }

        public void draw(GL g) {
            for (int i = 0; i < xline.size() - 1; ++i) {
               g.glBegin(GL.GL_LINE_STRIP);
               g.glVertex2d( (double)xline.get(i ),(double) yline.get(i));
               g.glVertex2d((double) xline.get(i+1 ), (double) yline.get(i+1)); 
               g.glEnd();
            }
        }
        
        @Override
        public void mouseClicked(MouseEvent me) {

        }

        @Override
        public void mousePressed(MouseEvent me) {
           currentLine = new ActionPaint();
            lines.add(currentLine);
            currentLine.addPoint(me.getX(),800- me.getY());
            glc.repaint();
           
        }

        @Override
        public void mouseReleased(MouseEvent me) {

        }

        @Override
        public void mouseEntered(MouseEvent me) {

        }

        @Override
        public void mouseExited(MouseEvent me) {

        }

        @Override
        public void mouseDragged(MouseEvent me) {
            glc.repaint();
            currentLine.addPoint(me.getX(),800- me.getY());
            glc.repaint();
        }

        @Override
        public void mouseMoved(MouseEvent me) {

        }

        @Override
        public void init(GLAutoDrawable glad) {
         GL gl = glad.getGL();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glLineWidth(3.0f);
        gl.glPointSize(3.0f);
        gl.glViewport(0, 800, 0, 800);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, 800, 0, 800, -1, 1);

        }

        @Override
        public void display(GLAutoDrawable drawable) {
            GL gl = drawable.getGL();
             
            gl.glClear(GL.GL_COLOR_BUFFER_BIT);
             paintComponent( gl );
        
        }
        

        @Override
        public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
           
        }

        @Override
        public void displayChanged(GLAutoDrawable glad, boolean bln, boolean bln1) {
            
        }
    public void paintComponent(GL g) {
            for (ActionPaint lin : lines) {
                lin.draw(g);
            }
    
    }

   

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getActionCommand().equals("Color")) {
                c = JColorChooser.showDialog(glc, "Select a Color", Color.RED);
            glc.repaint();
            }
        if(ae.getActionCommand().equals("Clear")){
            lines = new ArrayList<ActionPaint>();
             //lines=newlines;
             glc.repaint();
       
        }
    }
} }
