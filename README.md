# TankGame

TankGame is a simple game writen in Java for DCHS Programming Club

TankGame was built with [LWJGL2](http://legacy.lwjgl.org), [Slick-Util](http://slick.ninjacave.com), [AStar](https://code.google.com/p/a-star-java), and [Open Sans](http://www.fontsquirrel.com/fonts/open-sans).

## Running

For a executable for this game look under [releases](https://github.com/dominikWin/TankGame/releases)

To run on a non-linux based operating system change LWJGLs native librarys to the folder with the operating systems name.

TankGame was built to run on eclipse without any modification; No other IDEs were tested.

## Controls

### Menus

All menus can be navigated with the arrow keys or WASD, and Enter/Return is for select.

### Gameplay
##### General

Escape key pauses the game.
##### Movement

WASD moves the tank in the direction relative to the direction of the body.
##### Firing

The mouse location on screen controls direction and the space key fires.

## Performance

TankGame requires a graphics processor that suports OpenGl.
Currently the game only uses a single core, but a multithreaded update is in development.

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

To load a map change the constructor peramiters of the map in the World class constructor (On the latest release, v1.0-alpha, this line 16 of World.java).
