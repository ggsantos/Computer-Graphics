#include <person.h>
#include <vector>

Person::Person(){}

Person::Person(int n, int m){
     name = n;
     frames = m;
//     positionX[m];
//     positionY[m];
}

void Person::setPosition(int x, int y, int m){
     positionX[m] = x;
     positionY[m] = y;
}

int Person::getPositionX(int m){
     return positionX[m];
}

int Person::getPositionY(int m){
     return positionY[m];
}

int Person::getName(){
     return name;
}
