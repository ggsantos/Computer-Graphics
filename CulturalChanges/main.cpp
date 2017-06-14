#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <GL/glut.h>
#include <math.h>
#include <person.h>
#include <vector>
#include <fstream>	// para usar file streams (ifstream,ofstream)
#include <iostream>	// para usar cin,cout
#include <sstream>

#define SENS_ROT	5.0
#define SENS_OBS	1.0
#define SENS_TRANSL	1.0

int x_ini,y_ini,bot;
GLfloat rotX, rotY, rotX_ini, rotY_ini;
GLfloat obsX, obsY, obsZ =500, obsX_ini, obsY_ini, obsZ_ini;
GLfloat fAspect = 1, angle = 45;

//std::vector<Person> persons;
Person persons[23];
int tam;

void PosicionaObservador(void)
{
	glMatrixMode(GL_MODELVIEW);

	glLoadIdentity();

	gluLookAt(obsX,obsY,obsZ, 133,0.0,385, 0.0,1.0,0.0);
}

void EspecificaParametrosVisualizacao(void)
{
	glMatrixMode(GL_PROJECTION);

	glLoadIdentity();

	gluPerspective(angle,fAspect,0.5,1500);

	PosicionaObservador();
}

void desenhaChao()
{
	glColor3f(0,0,1);
	glLineWidth(1);
	glBegin(GL_LINES);
	for(float z=-1000; z<=1000; z+=8)
	{
		glVertex3f(-1000,-0.1f,z);
		glVertex3f( 1000,-0.1f,z);
	}
	for(float x=-1000; x<=1000; x+=8)
	{
		glVertex3f(x,-0.1f,-1000);
		glVertex3f(x,-0.1f,1000);
	}
	glEnd();

	glLineWidth(1);
}

void drawPerson(int x, int y)
{
    glColor3f(1,0,1);
    // Front
    glBegin(GL_POLYGON);
        glVertex3f( x-1,  0.0, y-1);       // P1
        glVertex3f( x-1, 5.0, y-1);       // P2
        glVertex3f( x+1, 5.0, y-1);       // P3
        glVertex3f( x+1,  0.0, y-1);       // P4
    glEnd();

    // Back
    glBegin(GL_POLYGON);
        glVertex3f( x-1,  0.0, y+1 );
        glVertex3f( x-1, 5.0, y+1 );
        glVertex3f( x+1, 5.0, y+1 );
        glVertex3f( x+1,  0.0, y+1 );
    glEnd();

    // Right
    glBegin(GL_POLYGON);
        glVertex3f( x+1,  0.0, y-1 );
        glVertex3f( x+1, 5.0, y-1 );
        glVertex3f( x+1, 5.0, y+1 );
        glVertex3f( x+1,  0.0, y+1 );
    glEnd();

    // Left
    glBegin(GL_POLYGON);
        glVertex3f( x-1,  0.0, y-1 );
        glVertex3f( x-1, 5.0, y-1 );
        glVertex3f( x-1, 5.0, y+1 );
        glVertex3f( x-1,  0.0, y+1 );
    glEnd();

    // Top
    glBegin(GL_POLYGON);
        glVertex3f(  x-1, 5.0, y-1 );
        glVertex3f(  x+1, 5.0, y-1 );
        glVertex3f(  x-1, 5.0, y+1 );
        glVertex3f(  x+1, 5.0, y+1 );
    glEnd();

    // Bottom
    glBegin(GL_POLYGON);
        glVertex3f(  x-1, 0.0, y-1 );
        glVertex3f(  x+1, 0.0, y-1 );
        glVertex3f(  x-1, 0.0, y+1 );
        glVertex3f(  x+1, 0.0, y+1 );
    glEnd();
}

void OnDraw()
{
	// clear the screen & depth buffer
	glClear(GL_DEPTH_BUFFER_BIT|GL_COLOR_BUFFER_BIT);

	EspecificaParametrosVisualizacao();

    for(int i = 0; i < 23; i++)
    {
        Person p = persons[i];
        drawPerson(p.getPositionX(0),p.getPositionY(0));
    }

	desenhaChao();

	glutSwapBuffers();
}

void OnInit() {
	// enable depth testing
	glEnable(GL_DEPTH_TEST);
}

void OnReshape(int w, int h)
{
	if (h==0) h = 1;

	// set the drawable region of the window
	glViewport(0,0,w,h);

	// Calcula a corre��o de aspecto
	fAspect = (GLfloat)w/(GLfloat)h;

	EspecificaParametrosVisualizacao();
}

void GerenciaMouse(int button, int state, int x, int y)
{
	if(state==GLUT_DOWN)
	{
		x_ini = x;
		y_ini = y;
		obsX_ini = obsX;
		obsY_ini = obsY;
		obsZ_ini = obsZ;
		rotX_ini = rotX;
		rotY_ini = rotY;
		bot = button;
	}
	else bot = -1;
}

void GerenciaMovim(int x, int y)
{
	if(bot==GLUT_LEFT_BUTTON)
	{
		int deltax = x_ini - x;
		int deltay = y_ini - y;

		rotY = rotY_ini - deltax/SENS_ROT;
		rotX = rotX_ini - deltay/SENS_ROT;
	}

	else if(bot==GLUT_RIGHT_BUTTON)
	{
		int deltaz = y_ini - y;

		obsZ = obsZ_ini + deltaz/SENS_OBS;
	}

	else if(bot==GLUT_MIDDLE_BUTTON)
	{
		int deltax = x_ini - x;
		int deltay = y_ini - y;

		obsX = obsX_ini + deltax/SENS_TRANSL;
		obsY = obsY_ini - deltay/SENS_TRANSL;
	}
	PosicionaObservador();
	glutPostRedisplay();
}


// Função callback chamada para gerenciar eventos de teclas
void Teclado (unsigned char key, int x, int y)
{
	if (key == 27)
		exit(0);
}

// Função responsável por inicializar parâmetros e variáveis
void Inicializa(void)
{
	// Define a janela de visualização 2D
	glMatrixMode(GL_PROJECTION);
	gluOrtho2D(-1.0,1.0,-1.0,1.0);
	glMatrixMode(GL_MODELVIEW);
}

// Programa Principal
int main(int argc,char** argv)
{
	// initialize glut
	glutInit(&argc,argv);

	// request a depth buffer, RGBA display mode, and we want double buffering
	glutInitDisplayMode(GLUT_DEPTH|GLUT_RGBA|GLUT_DOUBLE);

	// set the initial window size
	glutInitWindowSize(800,600);

	// create the window
	glutCreateWindow("Cultural Changes");

    // Cria input file stream (ifstream)
	ifstream arq;

	cout << "Abrindo arquivo texto..." << endl;

	// Abre arquivo
	arq.open( "Paths_D_JP1.txt" , ios::in );

	// Se houver erro, sai do programa
	if (!arq.is_open())
		return (0);

    string tamanho;
    stringstream buffer;
    getline(arq, tamanho);
    buffer << tamanho.substr(1,(tamanho.size()-1));
    buffer >> tam;
    int i = 0;

    while (!arq.eof())
    {
        int frames;
        string positions;
        stringstream buffer2;
        int aux = 0;
        int instant = 0, cont = 0,x,y;


        arq >> frames >> positions;
        Person p(i,frames);

        printf("%d", positions.size());

        //cout << positions << endl;


        for(unsigned int j = 0; j < positions.size()-1; j++)
        {
            char c;
            buffer << positions.substr(j,1);
            buffer >> c;
            printf("%3c\n", c);
//            printf("%d\n", j);
            if(c == '('){
                aux = j + 1;
            }
            else
                if(c == ',' && cont == 0)
                {
                    buffer << positions.substr(aux,j-aux);
                    buffer >> x;
                    aux = j + 1;
                    cont++;
                }
            else
                if(c == ',' && cont == 1)
                {
                    buffer << positions.substr(aux,j-aux);
                    buffer >> y;
                    aux = j + 1;
                    cont = 0;
                }
            else
            if(c == ')'){
                buffer << positions.substr(aux,j-aux);
                buffer >> instant;
                aux = j + 1;
            }

            p.setPosition(x,y,instant-1);
        }

        persons[i] = p;
        i++;
    }

    for(int j = 0; j < 23; j++)
    {
        printf("%d,%d\n",persons[i].getPositionX(0),persons[i].getPositionY(0));
    }

    cout << "Fechando o arquivo..." << endl;

    arq.close();

//    Person p(1,3);
//
//    p.setPosition(133,385,0);
//    p.setPosition(139,385,1);
//    p.setPosition(135,381,2);
//
//    persons[0] = p;

	// set the function to use to draw our scene
	glutDisplayFunc(OnDraw);

	// set the function to handle changes in screen size
	glutReshapeFunc(OnReshape);

	// set the function for the key presses
	glutMotionFunc(GerenciaMovim);
	glutMouseFunc(GerenciaMouse);

	// run our custom initialization
	OnInit();

	// this function runs a while loop to keep the program running.
	glutMainLoop();

	return 0;
}
