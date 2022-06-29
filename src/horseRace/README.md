
# Horse Race

Develop a horse race in Java.

A horse is a thread.
A horse has a name, the distance it has covered in the race so far and the total length of the race.
When the distance covered is equal to the length of the race, the horse has finished.

Each horse will cover a random distance every random milliseconds.

The monitor is a thread.
Every 2 seconds, the monitor will display the status of the race as long as any of the horse is still running:

Horse A             #-------------------------X----#\
Horse B             #----------------X-------------#\
Horse C             #--------X---------------------#


In this example, the race length is 30 (29 dashes + X for the position of the horse).

The main java thread will start the monitor and then start all the horses.
The main java thread will wait that the race is complete before ending.

The main thread (not the monitor thread !) will print “Race Completed” at the end of the race.

Example of race:

Horse A             #X-----------------------------#\
Horse B             #X-----------------------------#\
Horse C             #-X----------------------------#

Horse A             #--------X---------------------#\
Horse B             #-----X------------------------#\
Horse C             #--------X---------------------#

Horse A             #--------------------X---------#\
Horse B             #-----------------------X------#\
Horse C             #-----------------X------------#

Horse A             #-----------------------------X#\
Horse B             #-----------------------------X#\
Horse C             #-----------------------------X#

Race Completed


Optional:
---------

After "Race Completed" display the horses in the winning order.\
Example:

1. Horse B
2. Horse A
3. Horse C