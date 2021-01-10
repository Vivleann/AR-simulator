import cv2
import requests


token = 'Token ' + open('../token').read().strip()
HEADERS = {'Authorization': token}
URL_PM = 'https://api.arstand-lab.ru/api/0/game/field/'
# URL_PM = 'https://api.arstand-lab.ru/api/0/task/test_task/'
URL_FIGURES = 'https://api.arstand-lab.ru/api/0/game/figures/'


def send(img):
    cv2.imwrite('tmp.jpg', img)
    res = requests.post(
        URL_PM,
        headers=HEADERS,
        files={'answer': open('tmp.jpg', 'rb')}
    )
    return res


def figures(pyramids, mapper):
    body = {
        'sender': 'cv',
        'figures': []
    }
    for x, y, rot, id_ in pyramids:
        body['figures'].append({
            'x': x,
            'y': y,
            'angle': rot // 90,
            'modifier': mapper[id_]
        })
    res = requests.post(URL_FIGURES, headers=HEADERS, json=body)
    return res