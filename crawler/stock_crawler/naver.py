import pandas as pd
import requests
from bs4 import BeautifulSoup


def get_prices(stock_code: str, num_days: int = 7):
    """
    네이버 증권에서 가격 정보를 불러온다.

    https://m.blog.naver.com/myisiq999/221850414486

    Args:
        stock_code: 종목코드
        count: 가져올 일수
    """
    base_url = 'https://fchart.stock.naver.com/sise.nhn'
    params = {
        'requestType': 0,
        'symbol': stock_code,
        'timeframe': 'day', # day/month/year
        'count': num_days
    }

    res = requests.get(base_url, params=params)

    data = BeautifulSoup(res.text, 'html.parser')
    items = data.find_all('item')
    dates = []
    prices = []
    for item in items:
        _data = item['data'].split('|')
        date, price = _data[0], _data[4]
        date = pd.to_datetime(str(date), format='%Y%m%d')
        dates.append(date)
        prices.append(price)

    date_index = pd.Index(name='date', data=dates)

    df = pd.DataFrame({'price': prices}, index=date_index)

    return df.sort_index(ascending=False)