import cv2
import numpy as np
from matplotlib import pyplot as plt

src = "cell.jpg"
img = cv2.imread(src)
gray_image = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
cv2.imwrite("gray_image.jpg", gray_image)
ret, thresholded_image_binary = cv2.threshold(gray_image, 128, 255, cv2.THRESH_BINARY)
cv2.imwrite("THRESH_BINARY.jpg", thresholded_image_binary)
ret, thresholded_image_binary_inv = cv2.threshold(gray_image, 128, 255, cv2.THRESH_BINARY_INV)
cv2.imwrite("THRESH_BINARY.jpg", thresholded_image_binary_inv)
ret, thresholded_image_trunc = cv2.threshold(gray_image, 128, 255, cv2.THRESH_TRUNC)
cv2.imwrite("THRESH_TRUNC.jpg", thresholded_image_trunc)
ret, thresholded_image_tozero = cv2.threshold(gray_image, 128, 255, cv2.THRESH_TOZERO)
cv2.imwrite("THRESH_TOZERO.jpg", thresholded_image_tozero)
ret, thresholded_image_tozero_inv = cv2.threshold(gray_image, 128, 255, cv2.THRESH_TOZERO_INV)
cv2.imwrite("THRESH_TOZERO_INV.png", thresholded_image_tozero_inv)
th2 = cv2.adaptiveThreshold(gray_image, 255, cv2.ADAPTIVE_THRESH_MEAN_C,
                            cv2.THRESH_BINARY, 11, 2)
cv2.imwrite("THRESH_MEAN.jpg", th2)
th3 = cv2.adaptiveThreshold(gray_image, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY, 11, 2)
cv2.imwrite("THRESH_GAUSS.jpg", th3)
retval2, threshold2 = cv2.threshold(gray_image, 175, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)
cv2.imwrite("THRESH_OTSU.jpg", threshold2)
res1 = cv2.resize(img, None, fx=0.5, fy=0.5, interpolation=cv2.INTER_CUBIC)
cv2.imwrite("smoll.jpg", res1)
height, width = img.shape[:2]
res1 = cv2.resize(img, (2 * width, 2 * height), interpolation=cv2.INTER_CUBIC)
cv2.imwrite("BIK.jpg", res1)

rows, cols = gray_image.shape
M = np.float32([[1, 0, 100], [0, 1, 50]])
dst = cv2.warpAffine(gray_image, M, (cols, rows))
cv2.imwrite("moved.jpg", dst)

rows, cols = gray_image.shape
M = cv2.getRotationMatrix2D((cols / 2, rows / 2), 45, 1)
dst = cv2.warpAffine(gray_image, M, (cols, rows))
cv2.imwrite("rotated.jpg", dst)

dst=cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
cv2.imwrite("hsv.jpg", dst)

img_gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
cv2.imwrite("gray_color_space.jpg", dst)

median = cv2.medianBlur(img,5)
cv2.imwrite("median_filter.jpg", median)

gaussian = cv2.GaussianBlur(img,(5,5),0)
cv2.imwrite("gauss_blur.jpg", gaussian)

bilateral = cv2.bilateralFilter(img,9,75,75)
cv2.imwrite("bilateral_blur.jpg", bilateral)

sobelx = cv2.Sobel(img,cv2.CV_64F,1,0,ksize=5) #krawędzie pionowe
cv2.imwrite("sobelx.jpg", sobelx)
sobely = cv2.Sobel(img,cv2.CV_64F,0,1,ksize=5) #krawędzie poziome
cv2.imwrite("sobely.jpg", sobely)
sobel = cv2.Sobel(img,cv2.CV_64F,1,1,ksize=5)
cv2.imwrite("sobel.jpg", sobel)

laplacian = cv2.Laplacian(img,cv2.CV_64F)
cv2.imwrite("laplacian.jpg", laplacian)

plt.hist(img.ravel(),256,[0,256]);
plt.savefig('histogram.png')
hist,bins = np.histogram(img.ravel(),256,[0,256])
color = ('b','g','r')
for i,col in enumerate(color):
    histr = cv2.calcHist([img],[i],None,[256],[0,256])
    plt.plot(histr,color = col)
plt.xlim([0,256])
plt.savefig('histogram2.png')

mask = np.zeros(img.shape[:2], np.uint8)
mask[100:300, 100:400] = 255
masked_img = cv2.bitwise_and(img,img,mask = mask)
# Calculate histogram with mask and without mask
# Check third argument for mask
hist_full = cv2.calcHist([img],[0],None,[256],[0,256])
hist_mask = cv2.calcHist([img],[0],mask,[256],[0,256])
plt.subplot(221), plt.imshow(img, 'gray')
plt.subplot(222), plt.imshow(mask,'gray')
plt.subplot(223), plt.imshow(masked_img, 'gray')
plt.subplot(224), plt.plot(hist_full), plt.plot(hist_mask)
plt.xlim([0,256])
plt.savefig('histogram3.png')

img = cv2.imread(src,0)
hist,bins = np.histogram(img.flatten(),256,[0,256])
cdf = hist.cumsum()
cdf_normalized = cdf * hist.max()/ cdf.max()
plt.plot(cdf_normalized, color = 'b')
plt.hist(img.flatten(),256,[0,256], color = 'r')
plt.xlim([0,256])
plt.legend(('cdf','histogram'), loc = 'upper left')
plt.savefig('hist_eq.png')

equ = cv2.equalizeHist(img)
#zestawienie obok siebie obrazu przed i po normalizacji histogramu
res = np.hstack((img,equ))
cv2.imwrite('hist_eq.png',res)
# create a CLAHE object (Arguments are optional)
#CLAHE (Contrast Limited Adaptive Histogram Equalization)
clahe = cv2.createCLAHE(clipLimit=2.0, tileGridSize=(8,8))
cl1 = clahe.apply(img)
res2 = np.hstack((img,cl1))
cv2.imwrite('hist_clahe.jpg',res2)
