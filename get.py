import requests
import numpy as np


token = 'Token ' + open('../token').read().strip()
HEADERS = {'Authorization': token}
URL_FIELD = 'https://api.arstand-lab.ru/api/0/game/field/'
# URL_FIELD = 'https://api.arstand-lab.ru/api/0/marker/get_markers/stand'
URL_STATUS = 'https://api.arstand-lab.ru/api/0/game/status/'


def fetch():
    res = requests.get(URL_FIELD, headers=HEADERS)
    if res.ok:
        with open('file.npz', 'wb') as f:
            f.write(res.content)
        return np.load('file.npz')
    print(res.content)
    return False

def status():
    res = requests.get(URL_STATUS, headers=HEADERS)
    if res.ok:
        return res.json()['status']
    return False