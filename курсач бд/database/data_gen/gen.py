from sqlite3 import Date
from certifi import contents
from faker import Faker
from random import randint, shuffle
from random import uniform
from random import choice
import datetime
import bcrypt


MAX_N = 1000

role = ["admin", "analyst", "user"]
news_titles = ["СЛУЧИЛОСЬ НЕВЕРОЯТНОЕ!", "ТАКОГО ЕЩЕ НЕ БЫЛО!", "НОВИНКА!", "Наконец-то свершилось", "Сокрушающий эффект", "Последняя новость", "Феноменальный прорыв", "Неожиданная новость", "Революционная формула", "Только что появился", "Неожиданный поворот", "Совершен прорыв",
"Свершилось чудо", "Срочное сообщение", "Коротко о главном", "Шокирующая правда", "Достаточно ли вы знаете?", "Вас обманывают", "Волнующий факт", "Сколько стоит?", "Достоинства и недостатки", "Закулисные тайны", "О чем умалчивают?", "Это нужно знать", "В это сложно поверить", "Что на самом деле означает",
"Что скрывается за дверью?", "Сплошное разочарование", "ОСТОРОЖНО!", "СЕНСАЦИЯ!", "Один из лучших", "Один из худших", "Все ли так хорошо?", "Проблемы внутри компании", "Генеральный директор ушел в отставку", "Ужасные новости"]

contents = ["Сегодня стало известо о провальной операции...", "Власти опасаются, что компанию ждут нелучшине времена...", "В эту неделю на складе произошел пожар...", "Стало известо о печальных последствиях...",
"Производство налаживается соответственно дальнейнише возможности...", "Большая часть клиентов опсается, что масштабная политика ...", "Ровно в эту минуту на заводе произошло ЧП", "На днях мы получили известия о ...", "Стало намного лучше чем раньше - заявляет генеральный директор...",
"В связи с пандемией заводы компании будут остановлены соответветственно...", "Инвесторы ожидают прорыв компании на горизонте ...", "Компания смогла выбраться из кризиса и продолжить дальнейший путь...", "Не для кого уже не секрет, что эта компания занимает лидерские позиции в области...",
"Генеральный директор оказался искусным правителем...", "Широкомасштабное обновление ассортимента произойдет с минуты на минуту....", "Компания вновь показала шокирующие результаты прибыли для инвесторов...", "Все уже стали забывать, насколько эта компания масштабна в пределах страны...",
"Все данные о сотрудниках являются откровенной правдой - заявляет пресс-секретарь...", "Производство налаживается, а соответственно прибыль компании увеличится в разы..."]

media = ["The New York Times", "The Guardian", "The Daily Mail", "China Daily", "The Washington Post", "The Daily Telegraph", "The Wall Street Journal", "USA Today", "The Times of India", "The Independent", "Los Angeles Times", "Financial Times", "RT", "РИА Новости",
"Комсомольская правда", "Lenta.ru", "РосБизнесКонсалтинг", "Московский Комсомолец", "Российская газета", "Газета.ru", "Russia Today", "Известия.Ru", "Вести.ru", "Аргументы и факты", "ТАСС", "Коммерсантъ", "Regnum", "Ура.ру", "Федеральное агентство новостей", "НТВ", "Эхо Москвы",
"Первый канал", "Sputnik"]

invest_houses = ["ВТБ", "Сбер", "Газпромбанк", "Альфа-банк", "Россельхозбанк", "Почта банк", "Росбанк", "UBS", "Wells Fargo", "Jefferies", "Citigroup", "Stifel Nicolaus", "Raymond James", "Truist Financial", "Deutsche Bank", 
"Robert W. Baird", "Morgan Stanley", "Benchmark Co.", "MKM Partners", "Barrington", "Barrington", "Needham", "Bernstein", "Kepler Capital", "Stephens", "Nomura", "Scotiabank", "Piper Sandler", "Susquehanna", "Craig-Hallum", "CIBC", "TD Securities", "Guggenheim", "National Bank"]


contents_forecast = ["Наши аналитики считают...", "Мы думаем что дальнейший рост компании...", "Сегодня нельзя представить что прибыль компании...", "Мы считаем что цена данной акции...", "Наши аналитики рассчитывают на ...", "Я думаю что причина такой выручки заключается в следующем...",
"Наше издание рекомендует вам ...", "В данной обстановке мы считаем, что компания...", "Я не думаю что сегодня можно с уверенностью сказать...", "Наше бюро рассчитывает, что показатели компании...", "Прогноз основывается на следующих показателях...", "Мы все уверенны, что данная компания...",
"Данный прогноз является отражением...", "Нельзя сказать иначе в текущей ситуации, но выручка компании ..."]


def generateAdminAccount(acc_id):
    f = open('data/adminaccount.csv', 'a')
    line = "{0}\n".format(acc_id)
    f.write(line)
    f.close()
    
def generateAnalystAccount(acc_id):
    f = open('data/analystaccount.csv', 'a')
    line = "{0}\n".format(acc_id)
    f.write(line)
    f.close()
    
def generateUserAccount(acc_id):
    f = open('data/useraccount.csv', 'a')
    line = "{0}\n".format(acc_id)
    f.write(line)
    f.close()
    
def generateAccounts():
    faker = Faker()
    faker = Faker(locale="ru")

    f = open('data/users.csv', 'w')
    emails = [faker.unique.email() for i in range(MAX_N)]
    user_names = [faker.user_name() for i in range(MAX_N)]
    f2 = open('data/users_roles.csv', 'w')
    f3 = open('data/user_passwords.csv', 'w')

    for i in range(MAX_N):
        choice = randint(0, 100)
        password = faker.password().replace(';', '')

        password_hashed = str(bcrypt.hashpw(password.encode(), bcrypt.gensalt())).replace('\'', '')[1:]

        line = "{0};{1};{2};{3};{4};{5}\n".format((i + 1), user_names[i], password_hashed, emails[i], faker.first_name(), faker.last_name())
        f3.write("{0};{1}\n".format((i + 1), password))
        print(i)
        if choice < 1:
            choice_roles = randint(0, 3)

            if choice_roles == 0:
                role_line = "{0};{1}\n".format((i + 1), 1)
            elif choice_roles == 1:
                role_line = "{0};{1}\n{2};{3}\n".format((i + 1), 1, (i + 1), 2)
            else:
                role_line = "{0};{1}\n{2};{3}\n{4};{5}\n".format((i + 1), 1, (i + 1), 2, (i + 1), 3)

        elif choice >= 1 and choice < 3:
            choice_roles = randint(0, 2)

            if choice_roles == 0:
                role_line = "{0};{1}\n".format((i + 1), 1)
            else:
                role_line = "{0};{1}\n{2};{3}\n".format((i + 1), 2, (i + 1), 3)
        else:
                role_line = "{0};{1}\n".format((i + 1), 3)

        f2.write(role_line)
        f.write(line)
    f3.close()
    f.close()
    f2.close()

def generateIndicators():
    f = open('data/indicators.csv', 'w')

    for i in range(MAX_N):
        price = uniform(0.1, 1000)
        market_cap = uniform(1000000, 1000000000)
        income = randint(0, 100)
        revenue = randint(1000000, 10000000000)

        line = "{0};{1};{2};{3};{4}\n".format((i + 1), price, market_cap, income, revenue)

        f.write(line)
    f.close()



def generateNews():
    f = open('data/news.csv', 'w')
    faker = Faker()
    faker = Faker(locale="ru")

    for i in range(MAX_N):
        date_publishing = faker.date_time_between(datetime.datetime(2021, 1, 1, 1, 1, 1), datetime.datetime(2022, 6, 1, 23, 59, 59))

        title = choice(news_titles)
        content = choice(contents)
        url = faker.url()
        author = choice(media)
        company_id = randint(1, 1000)

        line = "{0};{1};{2};{3};{4};{5};{6}\n".format((i + 1), title, date_publishing, content, url, author, company_id)
        f.write(line)
    f.close()


def generateForecasts():
    f = open('data/forecasts.csv', 'w')
    faker = Faker()
    faker = Faker(locale="ru")

    for i in range(MAX_N):
        date_start = faker.date_time_between(datetime.datetime(2020, 1, 1, 1, 1, 1), datetime.datetime(2022, 1, 1, 23, 59, 59))
        date_end = faker.date_time_between(datetime.datetime(2022, 1, 1, 1, 1, 1), datetime.datetime(2023, 1, 1, 23, 59, 59))
        date_update = faker.date_between_dates(date_start, datetime.datetime(2023, 1, 1, 23, 59, 59))
        invest_house = choice(invest_houses)
        goal_price = uniform(0.1, 1000)
        forecast = choice(["Strong Buy", "Buy", "Sell", "Hold", "Sell", "Strong Sell"])
        content = choice(contents_forecast)
        company_id = randint(1, 1000)

        line = "{0};{1};{2};{3};{4};{5};{6};{7};{8}\n".format((i + 1), invest_house, date_start, date_end, date_update, goal_price, forecast, content, company_id)
        f.write(line)
    f.close()


def generateCompanies():
    f = open('data/company.csv', 'w')
    faker_ru = Faker()
    faker_ru = Faker(locale="ru")

    faker_en = Faker()
    faker_en = Faker(locale="en")

    indicators = [x for x in range(1, 1001)]
    shuffle(indicators)

    for i in range(MAX_N):
        name_choice = randint(0, 10)
        if (name_choice <= 8):
            name = faker_en.company()
            desc = faker_en.bs()
        else:
            name = faker_ru.company()
            desc = faker_ru.bs()

        logo = faker_en.url()
        ticker = faker_en.bothify(text='??????')    
        indicators_id = indicators[i]

        line = "{0};{1};{2};{3};{4};{5}\n".format((i + 1), name, logo, desc, ticker, indicators_id)
        f.write(line)
    f.close()


generateAccounts()
generateIndicators()
generateNews()
generateForecasts()
generateCompanies()