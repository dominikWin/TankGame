# TankGame

TankGame is a simple game writen in Java for DCHS Programming Club

TankGame was built with [LWJGL2](http://legacy.lwjgl.org), [Slick-Util](http://slick.ninjacave.com), and [AStar](https://code.google.com/p/a-star-java).

## Running

To run on a non-linux based operating system change LWJGLs native librarys to the folder with the operating systems name.

TankGame was built to run on eclipse without and modification; No other IDEs were tested.

## Licence

TankGame is released under GPL3 Licence.
Anyone is free to use this code for any reason.

## Map Creation

Maps for TankGame are stored in CSV files in the res/maps directory.

It is highly recomended to outline the map with walls. Only rectangle map types are permited.

```
0 represents an empty square
1 represents a wall
2 marks the players spawn location, the first one found is used
3-255 are reserved for future additions
```
After 255 an even number is the spawn point of an enemy, and the following odd number is the location it will patrol.
The enemy numbers must be in order for them to be read, so the first one is 256, then 258 and so on...
All players and enemies spawn facing to the right.

An example:
```
1,1,1,1,1,1
1,0,0,0,2,1
1,0,1,1,1,1
1,0,256,0,0,1
1,0,0,0,0,1
1,0,0,0,257,1
1,1,1,1,1,1
```
