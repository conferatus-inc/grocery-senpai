from selenium import webdriver
from selenium.webdriver import ActionChains
from selenium.webdriver import ChromeOptions
from fastapi import FastAPI
import time
from fastapi.middleware.cors import CORSMiddleware
from selenium.webdriver.common.by import By


def getProducts(raw_qr_code, browser_driver, text_field, button_element):
    ActionChains(browser_driver) \
        .scroll_to_element(text_field) \
        .scroll_by_amount(0, 300) \
        .perform()
    text_field.clear()
    text_field.send_keys(raw_qr_code)
    time.sleep(0.2)
    button_element.click()
    time.sleep(1.7)
    products = browser_driver.find_elements(By.XPATH, "//div[@class='b-check_place']//tr[@class='b-check_item']")
    date = browser_driver.find_element(By.XPATH, "//div[@class='b-check_place']//tr[5]/td").text
    product_list = []
    for product in products:
        product_props = product.find_elements(By.XPATH, ".//td")
        product_list.append([y for y in (map(lambda x: x.text, product_props))])
    return product_list, date


class QrCodeResolver:
    def __init__(self):
        print("creating")
        options = ChromeOptions()
        options.add_argument("--headless=new")
        self.driver = webdriver.Chrome(options=options)
        self.driver.get('https://proverkacheka.com/')
        self.rawQrCodeTab = self.driver.find_element(By.XPATH, "//ul[@class='b-checkform_nav nav nav-tabs']/li[4]")
        self.rawQrCodeTab.click()
        time.sleep(0.2)
        self.textField = self.driver.find_element(By.ID, "b-checkform_qrraw")

        self.button = \
            self.driver.find_element(By.XPATH,
                                     "//div[@class='b-checkform_tab-qrraw tab-pane fade active in']//div[@class='b-checkform_btn col-sm-12']"
                                     "/button[@class='b-checkform_btn-send btn btn-primary btn-sm pull-right']")
        time.sleep(2)

    def __exit__(self, exc_type, exc_val, exc_tb):
        self.driver.close()


resolver = QrCodeResolver()
app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_credentials=True,
    allow_methods=['*'],
    allow_headers=['*'],
)

"""
http://127.0.0.1:8000/api/qr_code/
http://127.0.0.1:8000/api/qr_code/t=20201017T1923&s=1498.00&fn=9282440300669857&i=25151&fp=1186123459&n=1
http://127.0.0.1:8000/api/qr_code/t=20230915T141418&s=160.00&fn=7281440500848943&i=10145&fp=2768303418&n=1

"""


@app.get('/api/qr_code/{raw_qr}')
def getQrData(raw_qr: str):
    print("qwe", raw_qr)
    prods, date = getProducts(raw_qr, resolver.driver, resolver.textField, resolver.button)
    return {'products': prods, 'date': date}