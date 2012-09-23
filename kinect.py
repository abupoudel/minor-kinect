import freenect
import cv
import frame_convert
import time
import cython
import Image

keep_running = True
myAngle = 10

print "Press ESC to exit | \"w\" to tilt sensor up | \"s\" to tilt sensor down"

def detect(image):
    image_size = cv.GetSize(image)
 
    # create grayscale version
    grayscale = cv.CreateImage(image_size, 8, 1)
    cv.CvtColor(image, grayscale, cv.CV_BGR2GRAY)
 
    # create storage
    storage = cv.CreateMemStorage(0)
    #cv.ClearMemStorage(storage)
 
    # equalize histogram
    cv.EqualizeHist(grayscale, grayscale)
 
    # detect objects
    cascade = cv.Load('haarcascade_frontalface_alt.xml')
    faces = cv.HaarDetectObjects(grayscale, cascade, storage, 1.2, 2,cv.CV_HAAR_DO_CANNY_PRUNING, (100,100))
 
    if faces:
        print 'hand detected!'
        for (x,y,w,h),n in faces:
			cv.Rectangle(image, (x,y), (x+w,y+h), 255)
			print "(%d,%d)" % (x , y)

def display_depth(dev, data, timestamp):
    global keep_running
	
    cv.Image = frame_convert.pretty_depth_cv(data)
    cv.Flip(cv.Image,None,1)
    cv.ShowImage('Depth' ,cv.Image)
	
def display_rgb(dev, data, timestamp):
	
    cv.Image = frame_convert.video_cv(data)
    cv.Flip(cv.Image,None,1)
    image = cv.Image
    size = cv.GetSize(image)
    hsv = cv.CreateImage(size, 8, 3)
    hue = cv.CreateImage(size, 8, 1)
    sat = cv.CreateImage(size, 8, 1)
    val = cv.CreateImage(size, 8, 1)
    hands = cv.CreateImage(size, 8, 1)
    cv.CvtColor(image, hsv, cv.CV_BGR2HSV)

    cv.Split(hsv, hue, sat, val, None)

    detect(image)
    cv.ShowImage('Live', image)
    #cv.ShowImage('Hue', hue)
    #cv.ShowImage('Saturation', sat)

    cv.Threshold(hue, hue, 10, 255, cv.CV_THRESH_TOZERO) #set to 0 if <= 10, otherwise leave as is
    cv.Threshold(hue, hue, 244, 255, cv.CV_THRESH_TOZERO_INV) #set to 0 if > 244, otherwise leave as is
    cv.Threshold(hue, hue, 0, 255, cv.CV_THRESH_BINARY_INV) #set to 255 if = 0, otherwise 0
    cv.Threshold(sat, sat, 64, 255, cv.CV_THRESH_TOZERO) #set to 0 if <= 64, otherwise leave as is
    cv.EqualizeHist(sat, sat)

    cv.Threshold(sat, sat, 64, 255, cv.CV_THRESH_BINARY) #set to 0 if <= 64, otherwise 255

    #cv.ShowImage('Saturation threshold', sat)
    #cv.ShowImage('Hue threshold', hue)

    cv.Mul(hue, sat, hands)
    

    #smooth + threshold to filter noise
    #cv.Smooth(hands, hands, smoothtype=cv.CV_GAUSSIAN, param1=13, param2=13)
    cv.Threshold(hands, hands, 200, 255, cv.CV_THRESH_BINARY)
    cv.ShowImage('Hands', hands)
    
    #cv.ShowImage('RGB' ,cv.Image)

def body(dev,ctx):
		global keep_running
		global myAngle
		k = (cv.WaitKey(10) & 255)
		if k == 27:
			print "Exiting..."
			keep_running = False
		elif chr(k) == 'w' or chr(k) == 'W':
			myAngle = myAngle + 5
		elif chr(k) == 's' or chr(k) == 'S':
			myAngle = myAngle - 5
		freenect.set_led(dev,1)
		freenect.set_tilt_degs(dev,myAngle)
		if not keep_running:
			freenect.set_led(dev,0)
			raise freenect.Kill
   
freenect.runloop(video = display_rgb, depth = display_depth, body=body)


# Some tests on RGB
#	dst = cv.CreateImage(cv.GetSize(cv.Image), cv.IPL_DEPTH_16S, 3)
#	laplace = cv.Laplace(cv.Image, dst)
#	cv.SaveImage("laplace.png" , dst)
