#! /usr/bin/python
import os
from openni import *

global old_x
global old_y

context = Context()
context.init()

depth_generator = DepthGenerator()
depth_generator.create(context)
depth_generator.set_resolution_preset(RES_VGA)
depth_generator.fps = 30

gesture_generator = GestureGenerator()
gesture_generator.create(context)
gesture_generator.add_gesture('Wave')

hands_generator = HandsGenerator()
hands_generator.create(context)

# Declare the callbacks
# gesture
def gesture_detected(src, gesture, id, end_point):
    print "Detected gesture:", gesture
    hands_generator.start_tracking(end_point)
# gesture_detected

def gesture_progress(src, gesture, point, progress): pass
# gesture_progress

def create(src, id, pos, time):
    global old_x,old_y
    print 'Create ', id, pos
    old_x = int((640-pos[0])*2.134375);
    old_y = int((480-pos[1])*1.6);
# create

def update(src, id, pos, time):
    global old_x, old_y
    #print 'Update ', id, pos
    x = int((320-pos[0])*2.134375);
    y = int((480-pos[1])*1.6);
    print x, y, (old_x-x)*(old_x-x) + (old_y-y)*(old_y-y)
    if((old_x-x)*(old_x-x) + (old_y-y)*(old_y-y)) > 150 :
        old_x = x
        old_y = y
    command = "xdotool mousemove {} {}".format(old_x, old_y)
    os.system(command)
# update

def destroy(src, id, time):
    print 'Destroy ', id
# destroy

# Register the callbacks
gesture_generator.register_gesture_cb(gesture_detected, gesture_progress)
hands_generator.register_hand_cb(create, update, destroy)

# Start generating
context.start_generating_all()

print 'Make a Wave to start tracking...'

while True:
    context.wait_any_update_all()
# while
