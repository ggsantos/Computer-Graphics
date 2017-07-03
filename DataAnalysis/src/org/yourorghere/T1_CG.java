package org.yourorghere;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.GLUT;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * T1_CG.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class T1_CG implements GLEventListener {

    static float rotX, rotY, rotX_ini, rotY_ini;
    static float obsX, obsY, obsZ =200, obsX_ini, obsY_ini, obsZ_ini;
    float fAspect = 1, angle = 44;
    static int x_ini,y_ini,bot;
    int i = 1;
    
    static Events e = new Events();
    
    public static void main(String[] args) throws FileNotFoundException {
        
        Scanner tec = new Scanner(System.in);
        
        System.out.println("Digite o nome do arquivo que deseja utilizar");
        
        File path_d = new File(tec.nextLine());
        
        
        
        e.createMaps(path_d);
        System.out.println("Aproximações:");
        System.out.println(e.searchApproximation());
        System.out.println("Encontros:");
        ArrayList<String> s = e.searchEncounter();
        for(int i = 0; i < s.size(); i++){
            System.out.println(s.get(i));
        }
        System.out.println("Grupos:");
        ArrayList<String> s2 = e.searchGroup();
        for(int i = 0; i < s2.size(); i++){
            System.out.println(s2.get(i));
        }
        
        Frame frame = new Frame("Crowd");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new T1_CG());
        MouseMotionListener l = new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                if(e.getButton() == 1){
                    int deltaz = y_ini - 1;

                    obsZ = obsZ_ini + deltaz/10f;
                } else if(e.getButton() == 3){
                    int deltaz = y_ini + 1;

                    obsZ = obsZ_ini + deltaz/10f;
                }
            }

            public void mouseMoved(MouseEvent e) {
                int deltax = x_ini - 1;
		int deltay = y_ini - 1;

		rotY = rotY_ini - deltax/5.0f;
		rotX = rotX_ini - deltay/5.0f;
            }
        };
        canvas.addMouseMotionListener(l);
        frame.add(canvas);
        frame.setSize(1000, 1000);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }
    
    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!
        
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
    
    void PosicionaObservador(GLAutoDrawable drawable){

        GL gl = drawable.getGL();
        GLU glu = new GLU();

        gl.glMatrixMode(GL.GL_MODELVIEW);

        gl.glLoadIdentity();

        gl.glTranslatef(-obsX,-obsY,-obsZ);

        gl.glRotatef(rotX,1,0,0);
        gl.glRotatef(rotY,0,1,0);


        glu.gluLookAt(400.0,80.0,300.0, 950.0,0.0,421.0, 0.0,1.0,0.0);
    }
    
    void EspecificaParametrosVisualizacao(GLAutoDrawable drawable){

        GL gl = drawable.getGL();
        GLU glu = new GLU();
        
        gl.glMatrixMode(GL.GL_PROJECTION);

        gl.glLoadIdentity();


        glu.gluPerspective(angle,fAspect,0.5,800);

        PosicionaObservador(drawable);

    }
    
    void desenhaChao(GLAutoDrawable drawable){
        GL gl = drawable.getGL();
        
        gl.glColor3f(0,0,0);
        gl.glLineWidth(3);
        gl.glBegin(GL.GL_LINES);
        for(float z=-2000; z<=2000; z+=10)
        {
            gl.glVertex3f(-2000,-0.1f,z);
            gl.glVertex3f( 2000,-0.1f,z);
        }
        for(float x=-2000; x<=2000; x+=10)
        {
            gl.glVertex3f(x,-0.1f,-2000);
            gl.glVertex3f(x,-0.1f,2000);
        }
        gl.glEnd();
        gl.glLineWidth(1);
    }
    
    public void display(GLAutoDrawable drawable){

        GL gl = drawable.getGL();
        GLUT glut = new GLUT();
        
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );

        EspecificaParametrosVisualizacao(drawable);


        gl.glColor3f(1.0f, 0.0f, 1.0f);
        for(Node n : e.getCrowd()){
            Integer[] array = n.place.get(i);
            if(array != null){
                gl.glPushMatrix();
                gl.glTranslatef(array[0],0,array[1]);

                glut.glutWireTeapot(25);

                gl.glPopMatrix();
            }
        }
        
        i++;

        desenhaChao(drawable);


        gl.glFlush();
    }

//    void AlteraTamanhoJanela(GLsizei w, GLsizei h)
//    {
//        // Para previnir uma divisão por zero
//        if ( h == 0 ) h = 1;
//
//        // Especifica as dimensões da viewport
//        glViewport(0, 0, w, h);
//
//        // Calcula a correção de aspecto
//        fAspect = (GL.GL_FLOAT)w/(GL.GL_FLOAT)h;
//
//        EspecificaParametrosVisualizacao();
//    }
//
//
//    void Teclado (char key, int x, int y)
//    {
//        if (key == 27)
//            System.exit(0);
//    }
//
//
//    void Inicializa (GLAutoDrawable drawable)
//    {
//        GL gl = drawable.getGL();
//        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
//        gl.glLineWidth(2.0f);
//    }
//
//    void GerenciaMouse(int button, int state, int x, int y)
//    {
//        GLUT glut = new GLUT();
//        if(state==GLUT_DOWN)
//        {
//            // Salva os parâmetros atuais
//            x_ini = x;
//            y_ini = y;
//            obsX_ini = obsX;
//            obsY_ini = obsY;
//            obsZ_ini = obsZ;
//            rotX_ini = rotX;
//            rotY_ini = rotY;
//            bot = button;
//        }
//        else bot = -1;
//    }
//    void GerenciaMovim(int x, int y)
//    {
//
//        if(bot==GLUT_LEFT_BUTTON)
//        {
//
//            int deltax = x_ini - x;
//            int deltay = y_ini - y;
//
//            rotY = rotY_ini - deltax/5.0f;
//            rotX = rotX_ini - deltay/5.0f;
//        }
//
//        else if(bot==GLUT_RIGHT_BUTTON)
//        {
//
//            int deltaz = y_ini - y;
//
//            obsZ = obsZ_ini + deltaz/10.0f;
//
//
//        }
//
//        else if(bot==GLUT_MIDDLE_BUTTON)
//        {
//
//            int deltax = x_ini - x;
//            int deltay = y_ini - y;
//
//            obsX = obsX_ini + deltax/30.0f;
//            obsY = obsY_ini - deltay/30.0f;
//        }
//        PosicionaObservador();
//        glutPostRedisplay();
//    }
}

