import argparse
import time
from pathlib import Path

import pandas as pd
import requests
import tqdm
from bs4 import BeautifulSoup


def get_krx_stock_codes():
    """
    KRX에 상장된 모든 주식들의 메타데이터 가져오기
    https://wendys.tistory.com/173
    """
    stock_meta = pd.read_html('http://kind.krx.co.kr/corpgeneral/corpList.do?method=download', header=0)[0]
    stock_codes = stock_meta[['종목코드']].astype(int).values.reshape(-1).tolist()
    return [f'{stock_code:06d}' for stock_code in stock_codes]


def get_prices(stock_code: str, num_days: int = 30):
    """
    네이버 증권에서 가격 정보를 불러온다.

    https://m.blog.naver.com/myisiq999/221850414486

    Args:
        stock_code: 종목코드
        count: 가져올 일수

    Returns:
        df
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

    df = pd.DataFrame({'price': prices, 'date': dates})
    df['code'] = stock_code
    df = df[['code', 'date', 'price']]

    return df.sort_values(by='date', ascending=False)


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('-d', '--days', type=int, default=30)
    parser.add_argument('-o', '--output', type=str, default='prices.csv', help='output file')
    parser.add_argument('-i', '--interval', type=int, default=1)
    args = parser.parse_args()

    output_file = args.output
    num_days = args.days
    interval = args.interval
    print(f"Output file: {output_file}")
    print("-" * 40)

    if Path(output_file).exists():
        # overwrite the existing output file
        Path(output_file).unlink()

    krx_stock_codes = get_krx_stock_codes()

    for stock_code in tqdm.tqdm(krx_stock_codes):
        df = get_prices(stock_code=stock_code, num_days=num_days)
        mode = 'w' if not Path(output_file).exists() else 'a'
        header = mode == 'w'
        df.to_csv(
            output_file,
            header=header,
            index=False,
            sep=',',
            mode=mode
        )
        print(f"Appended the data for the stock code [{stock_code}] for [{num_days}] days")
        time.sleep(interval)
    
    print('-' * 40)
    print("Finished writing the output.")


if __name__ == "__main__":
    main()
