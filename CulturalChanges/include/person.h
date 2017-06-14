#include <vector>
#include <iostream>
using namespace std;

class Person{

     int name;
     int frames;
     //std::vector<int> positionX;
     //std::vector<int> positionY;
     int positionX[99];
     int positionY[99];

public:

     Person();
     Person(int n, int m);
     void setPosition(int x, int y, int m);
     int getPositionX(int m);
     int getPositionY(int m);
     int getName();

};
