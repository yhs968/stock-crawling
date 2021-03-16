from stock_crawler.naver import get_prices


def test_crawler():
    code = '005930'  # 삼성전자
    prices = get_prices(code)
    print(prices)
    assert True