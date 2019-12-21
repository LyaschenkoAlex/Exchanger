from __future__ import unicode_literals
import re
import time
import requests
from bs4 import BeautifulSoup


def to_string(USD_buy, USD_sell, EUR_buy, EUR_sell, RUB_buy, RUB_sell, bank_name):
    q = str(bank_name) + ' ' + str(USD_buy) + ' ' + str(USD_sell) + ' ' + str(EUR_buy) + ' ' + str(
        EUR_sell) + ' ' + str(RUB_buy) + ' ' + str(RUB_sell) + '\n'
    return q


def find_privat_bank():
    url = 'https://privatbank.ua/ru'
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'html.parser')
    str_course_container = ''
    for i in soup.find_all('div', attrs='wr_inner course_type_container'):
        str_course_container += str(i)
    EUR_buy = re.findall(r'<td id="EUR_buy">[^<]+', str_course_container)[0][17:].strip()
    EUR_buy = round(float(EUR_buy), 2)
    EUR_sell = re.findall(r'<td id="EUR_sell">[^<]+', str_course_container)[0][18:].strip()
    EUR_sell = round(float(EUR_sell), 2)
    USD_buy = re.findall(r'<td id="USD_buy">[^<]+', str_course_container)[0][17:].strip()
    USD_buy = round(float(USD_buy), 2)
    USD_sell = re.findall(r'<td id="USD_sell">[^<]+', str_course_container)[0][18:].strip()
    USD_sell = round(float(USD_sell), 2)
    RUB_buy = re.findall(r'<td id="RUB_buy">[^<]+', str_course_container)[0][17:].strip()
    RUB_buy = round(float(RUB_buy), 2)
    RUB_sell = re.findall(r'<td id="RUB_sell">[^<]+', str_course_container)[0][18:].strip()
    RUB_sell = round(float(RUB_sell), 2)
    print('PrivatBank')
    print('EUR_buy - ' + str(EUR_buy))
    print('EUR_sell - ' + str(EUR_sell))
    print('USD_buy - ' + str(USD_buy))
    print('USD_sell - ' + str(USD_sell))
    print('RUB_buy - ' + str(RUB_buy))
    print('RUB_sell - ' + str(RUB_sell))
    k = to_string(USD_buy, USD_sell, EUR_buy, EUR_sell, RUB_buy, RUB_sell, 'private')
    return k


def find_bank_gov():
    url = 'https://bank.gov.ua/markets/exchangerates/?period=daily'
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'html.parser')
    str_course_container = ''
    for i in soup.find_all('td'):
        str_course_container += str(i)
    print('BankGov')
    USD = float(
        re.findall(r'Долар США</a></td><td data-label="Курс">[^<]+', str_course_container)[0][40:].strip().replace(',',
                                                                                                                   '.')) / 100
    print('USD - ' + str(USD))
    EUR = float(
        re.findall(r'Євро</a></td><td data-label="Курс">[^<]+', str_course_container)[0][35:].strip().replace(',',
                                                                                                              '.')) / 100
    print('EUR - ' + str(EUR))
    RUB = float(re.findall(r'Російський рубль</a></td><td data-label="Курс">[^<]+', str_course_container)[0][
                47:].strip().replace(',', '.')) / 10
    print('RUB - ' + str(RUB))
    USD = round(USD, 2)
    EUR = round(EUR, 2)
    RUB = round(RUB, 2)

    return 'gov' + ' ' + str(USD) + ' ' + str(EUR) + ' ' + str(RUB) + '\n'


def find_credit_agricole():
    url = 'https://credit-agricole.ua/'
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'html.parser')
    str_course_container = ''
    for i in soup.find_all('div', attrs="exchange-rates-table"):
        str_course_container += str(i)
    qq = str_course_container.split(' ')
    courses_list = []
    for i in qq:
        i = i.strip()
        if i.replace('.', '', 1).isdigit():
            courses_list.append(i)
    arr_course = re.findall(r'[^>]+<span class="up"></span>', str_course_container)
    arr_course_changed = re.findall(r'[^>]+<span class="equal"></span>', str_course_container)
    USD_buy = courses_list[0]
    USD_buy = round(float(USD_buy), 2)
    USD_sell = courses_list[1]
    USD_sell = round(float(USD_sell), 2)
    EUR_buy = courses_list[2]
    EUR_buy = round(float(EUR_buy), 2)
    EUR_sell = courses_list[3]
    EUR_sell = round(float(EUR_sell), 2)
    RUB_buy = courses_list[4]
    RUB_buy = round(float(RUB_buy), 2)
    RUB_sell = courses_list[5]
    RUB_sell = round(float(RUB_sell), 2)
    print('CreditAgricole')
    print('USD_buy ' + str(USD_buy))
    print('USD_sell ' + str(USD_sell))
    print('EUR_buy ' + str(EUR_buy))
    print('EUR_sell ' + str(EUR_sell))
    print('RUB_buy ' + str(RUB_buy))
    print('RUB_sell ' + str(RUB_sell))
    return to_string(USD_buy, USD_sell, EUR_buy, EUR_sell, RUB_buy, RUB_sell, 'agricole')


def find_ukrsibbank():
    url = 'https://my.ukrsibbank.com/ua/personal/'
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'html.parser')
    str_course_container = str(soup.find_all('div', attrs="rate__wrapper")[0])
    arr_course = re.findall(r'class="rate__mob">[^<]+', str_course_container)[2:]
    USD_buy = arr_course[0][18:].strip()
    USD_buy = round(float(USD_buy), 2)
    USD_sell = arr_course[1][18:].strip()
    USD_sell = round(float(USD_sell), 2)
    EUR_buy = arr_course[2][18:].strip()
    EUR_buy = round(float(EUR_buy), 2)
    EUR_sell = arr_course[3][18:].strip()
    EUR_sell = round(float(EUR_sell), 2)
    RUB_buy = arr_course[4][18:].strip()
    RUB_buy = round(float(RUB_buy), 2)
    RUB_sell = arr_course[5][18:].strip()
    RUB_sell = round(float(RUB_sell), 2)
    print('Ukrsibbank')
    print('USD_buy ' + str(USD_buy))
    print('USD_sell ' + str(USD_sell))
    print('EUR_buy ' + str(EUR_buy))
    print('EUR_sell ' + str(EUR_sell))
    print('RUB_buy ' + str(RUB_buy))
    print('RUB_sell ' + str(RUB_sell))
    return to_string(USD_buy, USD_sell, EUR_buy, EUR_sell, RUB_buy, RUB_sell, 'ukrsibbank')


def find_oschadbank():
    url = 'https://www.oschadbank.ua/ua'
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'html.parser')
    str_course_container = ''
    for i in soup.find_all('div',
                           attrs="paragraph paragraph--type--exchange-rates paragraph--view-mode--default currency-item")[
             :3]:
        str_course_container += str(i)
    arr_course_buy = re.findall(r'<strong[^>]+data-buy="[^"]+', str_course_container)
    arr_course_sell = re.findall(r'<strong[^>]+data-sell="[^"]+', str_course_container)
    USD_buy = arr_course_buy[0].split('"')[-1]
    USD_buy = round(float(USD_buy), 2)
    EUR_buy = arr_course_buy[1].split('"')[-1]
    EUR_buy = round(float(EUR_buy), 2)
    RUB_buy = arr_course_buy[2].split('"')[-1]
    RUB_buy = round(float(RUB_buy), 2)
    USD_sell = arr_course_sell[0].split('"')[-1]
    USD_sell = round(float(USD_sell), 2)
    EUR_sell = arr_course_sell[1].split('"')[-1]
    EUR_sell = round(float(EUR_sell), 2)
    RUB_sell = arr_course_sell[2].split('"')[-1]
    RUB_sell = round(float(RUB_sell), 2)
    print('Oschadbank')
    print('USD_buy - ' + str(USD_buy))
    print('USD_sell - ' + str(USD_sell))
    print('EUR_buy - ' + str(EUR_buy))
    print('EUR_sell - ' + str(EUR_sell))
    print('RUB_buy - ' + str(RUB_buy))
    print('RUB_sell - ' + str(RUB_sell))
    k = to_string(USD_buy, USD_sell, EUR_buy, EUR_sell, RUB_buy, RUB_sell, 'oschadbank')
    return k


if __name__ == '__main__':
    while True:
        try:
            with open('C:/Users/lyasc/IdeaProjects/Exchanger/output.txt', 'w') as f:
                f.write(find_bank_gov())
                f.write(find_privat_bank())
                f.write(find_credit_agricole())
                f.write(find_ukrsibbank())
                f.write(find_oschadbank())
            print('file closed')
            time.sleep(60 * 60 * 6)
        except:
            pass
