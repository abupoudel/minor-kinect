import freenect
import cv
import frame_convert
import sys
import random
import string

k = 0
keep_running = True
myAngle = 10

print "Press ESC to exit | \"w\" to tilt sensor up | \"s\" to tilt sensor down | \"p\" to Save Positive images and \"n\" to Save Negative images"

def rand_string(length=5, chr_set=string.ascii_uppercase + string.digits):
    output = ''
    for n in range(length):
        output += random.choice(chr_set)
    return output

def display_depth(dev, data, timestamp):
	global k
	
def display_rgb(dev, data, timestamp):
	global image
	cv.Image = frame_convert.video_cv(data)
	#cv.Flip(cv.Image,None,1)
	image = cv.Image
	cv.ShowImage('Live', cv.Image)
	
def body(dev,ctx):
	if sys.argv < 2:
		exit(0);
	global keep_running
	global myAngle
	global k 
	global image
	char_set = string.ascii_uppercase + string.digits
	k = (cv.WaitKey(10) & 255)
	if k == 27:
		print "Exiting..."
		keep_running = False
	elif chr(k) == 'w' or chr(k) == 'W':
		myAngle = myAngle + 5
	elif chr(k) == 's' or chr(k) == 'S':
		myAngle = myAngle - 5
	elif chr(k) == 'p' or chr(k) == 'P':
		cv.SaveImage('positive/pos-'+ rand_string(10) + '.jpg',image)
		print "Positive image saved"
	elif chr(k) == 'n' or chr(k) == 'N':
		cv.SaveImage('negative/neg-'+ rand_string(10) + '.jpg',image)
		print "Negative image saved"
	freenect.set_led(dev,1)
	freenect.set_tilt_degs(dev,myAngle)
	if not keep_running:
		freenect.set_led(dev,0)
		raise freenect.Kill
   
freenect.runloop(video = display_rgb, depth = display_depth, body=body)
