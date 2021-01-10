import cv2
import numpy as np

def threshold(img):
    return cv2.threshold(img, 120, 255, cv2.THRESH_BINARY)[1]

def boundingRect(img):
    img = np.array(img) // 255
    top = 0
    bottom = img.shape[0] - 1
    left = 0
    right = img.shape[1] - 1
    while 1 not in img[top]:
        top += 1
    while 1 not in img[bottom]:
        bottom -= 1
    while 1 not in img[:, left]:
        left += 1
    while 1 not in img[:, right]:
        right -= 1
    return (left, top), (right, bottom)

def prepare(orig_markers, offset=20):
    markers = []
    for marker in orig_markers:
        marker = threshold(marker)
        marker = marker[offset:-offset, offset:-offset]
        p1, p2 = boundingRect(marker)
        rect = marker[p1[1]:p2[1], p1[0]:p2[0]].copy()
        marker[:, :] = 0
        w = p2[0] - p1[0]
        h = p2[1] - p1[1]
        x = (marker.shape[1] - w) // 2
        y = (marker.shape[0] - h) // 2
        marker[y:y + h, x:x + w] = rect
        markers.append([marker])
    return np.concatenate(markers, axis=0)

CIRCLE_POS = slice(0, 80, 1)

def circle_error(circle):
    contours, _ = cv2.findContours(
        circle,
        cv2.RETR_LIST,
        cv2.CHAIN_APPROX_SIMPLE
    )
    best = float('-inf')
    for c in contours:
        peri = cv2.arcLength(c, True)
        approx = cv2.approxPolyDP(c, 0.04 * peri, True)
        x, y, w, h = cv2.boundingRect(approx)
        if w < 7 or h < 7:
            continue
        ratio = (circle[y:y+h, x:x+w] > 200).sum() / (w * h)
        if  ratio <= 0.25 or ratio > .93:
            continue
        if not (0.5 < (w / h) < 1.5):
            continue
        if w * h < 300:
            continue
        best = max(best, len(approx))
    return -best, circle

def get_rotation(marker):
    circles = []
    res = {0: 0, 90: 0, 180: 0, 270: 0}
    for rot in (0, 90, 180, 270):
        circle = marker[CIRCLE_POS, CIRCLE_POS]
        res[rot], circle = circle_error(circle)
        circles.append(circle)
        marker = np.rot90(marker)
    return min(res, key=lambda rot: res[rot]), circles

def rotate(marker):
    m = marker.copy()
    rot, _ = get_rotation(m)
    for _ in range(rot // 90):
        marker = np.rot90(marker)
    return marker, rot
