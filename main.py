from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait

import time
from datetime import datetime, timedelta
import configparser


class Location:
    def __init__(self, campus, building, room_type=None, size=None, equipment=None):
        self.campus = campus
        self.building = building
        self.room_type = room_type
        self.size = size
        self.equipment = equipment


class Reservation:
    def __init__(self, location, date, duration):
        self.location = location
        self.date = date
        self.duration = duration


def open_page(url, username, password):
    browser = webdriver.Firefox()
    browser.get(url)

    search = WebDriverWait(browser, timeout=10).until(lambda b: b.find_element(By.ID, "org_selector_filter"))
    search.send_keys("NTNU", Keys.ENTER, Keys.ENTER, Keys.ENTER)

    username_input = WebDriverWait(browser, timeout=3).until(lambda b: b.find_element(By.ID, "username"))
    username_input.send_keys(username)
    browser.find_element(By.ID, "password").send_keys(password)
    browser.find_element(By.CLASS_NAME, "button-primary").click()

    return browser


def reserve_room(driver, reservation):
    year, month, day, hour, minute, _, _, _, _ = reservation.date.timetuple()

    # Input date of reservation.
    date_picker = WebDriverWait(driver, timeout=3).until(lambda d: d.find_element(By.CLASS_NAME, "datepicker"))
    date_picker.clear()
    date_picker.send_keys(f"{day}.{month}.{year}", Keys.ENTER)
    driver.find_element(By.CLASS_NAME, "ui-datepicker-close").click()
    time.sleep(1)

    # Input time of reservation
    start_time = WebDriverWait(driver, timeout=3).until(lambda d: d.find_element(By.ID, "startblock"))
    start_time.find_element(By.ID, "select2-start-container").click()

    start_time_input = WebDriverWait(driver, timeout=3).until(lambda d: d.find_element(By.CLASS_NAME, "select2-search__field"))
    start_time_input.send_keys(f"{hour}:{minute}", Keys.ENTER)

    driver.find_element(By.ID, "durationblock").click()
    end_time_input = WebDriverWait(driver, timeout=3).until(lambda d: d.find_element(By.CLASS_NAME, "select2-search__field"))
    end_time_input.send_keys(f"{reservation.duration.hour}:{reservation.duration.minute}", Keys.ENTER)

    # Input location of reservation.
    location = reservation.location

    driver.find_element(By.ID, "select2-area-container").click()
    driver.find_element(By.CLASS_NAME, "select2-search__field").send_keys(location.campus, Keys.ENTER)
    driver.find_element(By.ID, "select2-building-container").click()
    driver.find_element(By.CLASS_NAME, "select2-search__field").send_keys(location.building, Keys.ENTER)

    if location.room_type:
        driver.find_element(By.ID, "select2-roomtype-container").click()
        room_type_input = WebDriverWait(driver, timeout=3).until(lambda d: d.find_element(By.CLASS_NAME, "select2-search__field"))
        room_type_input.send_keys(location.room_type, Keys.ENTER)

    # Input misc.
    if location.size:
        driver.find_element(By.ID, "size").send_keys(location.size)

    if location.equipment:
        for e in location.equipment:
            driver.find_element(By.ID, "select2-new_equipment-container").click()
            equip_input = WebDriverWait(driver, timeout=3).until(lambda d: d.find_element(By.CLASS_NAME, "select2-search__field"))
            equip_input.send_keys(e, Keys.ENTER)

    driver.find_element(By.ID, "preformsubmit").click()


def select_room():
    pass


def get_credidentials():
    parser = configparser.ConfigParser()
    parser.read("login.credentials")
    return parser.get("config", "LOGIN_USER"), parser.get("config", "LOGIN_PASSWORD")

def main():
    username, password = get_credidentials()
    driver = open_page("https://tp.uio.no/ntnu/rombestilling/", username, password)

    date = datetime(2022, 2, 27, 10, 0)
    duration = date + timedelta(hours=2, minutes=15)
    loc = Location("Gløshaugen", "Hovedbygningen", "Grupperom", 2, ["Piano", "Dvd-spiller"])
    res = Reservation(loc, date, duration)

    reserve_room(driver, res)


if __name__ == '__main__':
    main()
