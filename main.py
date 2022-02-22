from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait

import time
from datetime import datetime, timedelta
import configparser

long_timeout = 10
short_timeout = 3

class Location:
    def __init__(self, campus, building, room_type=None, size=None, equipment=None):
        self.campus = campus
        self.building = building
        self.room_type = room_type
        self.size = size
        self.equipment = equipment


class Reservation:
    def __init__(self, location, date, duration, description, notes):
        self.location = location
        self.date = date
        self.duration = duration
        self.description = description
        self.notes = notes


def open_page(url, username, password):
    browser = webdriver.Firefox()
    browser.get(url)

    search = WebDriverWait(browser, timeout=long_timeout).until(lambda b: b.find_element(By.ID, "org_selector_filter"))
    search.send_keys("NTNU", Keys.ENTER, Keys.ENTER, Keys.ENTER)

    username_input = WebDriverWait(browser, timeout=short_timeout).until(lambda b: b.find_element(By.ID, "username"))
    username_input.send_keys(username)
    browser.find_element(By.ID, "password").send_keys(password)
    browser.find_element(By.CLASS_NAME, "button-primary").click()

    return browser


def reserve_room(driver, reservation):
    year, month, day, hour, minute, _, _, _, _ = reservation.date.timetuple()

    # Input time of reservation
    # Open the start time input.
    start_time = WebDriverWait(driver, timeout=short_timeout).until(lambda d: d.find_element(By.ID, "startblock"))
    start_time.find_element(By.ID, "select2-start-container").click()

    # The actual input.
    start_time_input = WebDriverWait(driver, timeout=short_timeout).until(
        lambda d: d.find_element(By.CLASS_NAME, "select2-search__field"))
    start_time_input.send_keys(f"{hour}:{minute}", Keys.ENTER)

    # Repeat for end time.
    driver.find_element(By.ID, "durationblock").click()
    end_time_input = WebDriverWait(driver, timeout=short_timeout).until(
        lambda d: d.find_element(By.CLASS_NAME, "select2-search__field"))
    end_time_input.send_keys(f"{reservation.duration.hour}:{reservation.duration.minute}", Keys.ENTER)

    # Input location of reservation.
    location = reservation.location

    if location.campus:
        driver.find_element(By.ID, "select2-area-container").click()
        driver.find_element(By.CLASS_NAME, "select2-search__field").send_keys(location.campus, Keys.ENTER)

    if location.building:
        driver.find_element(By.ID, "select2-building-container").click()
        driver.find_element(By.CLASS_NAME, "select2-search__field").send_keys(location.building, Keys.ENTER)

    if location.room_type:
        driver.find_element(By.ID, "select2-roomtype-container").click()
        room_type_input = WebDriverWait(driver, timeout=short_timeout).until(lambda d: d.find_element(By.CLASS_NAME, "select2-search__field"))
        room_type_input.send_keys(location.room_type, Keys.ENTER)

    # Input minimum room size (number of persons)
    if location.size:
        driver.find_element(By.ID, "size").send_keys(location.size)

    # Input all specified equipment requirements.
    if location.equipment:
        for e in location.equipment:
            driver.find_element(By.ID, "select2-new_equipment-container").click()
            WebDriverWait(driver, timeout=short_timeout).until(
                lambda d: d.find_element(By.CLASS_NAME, "select2-search__field")).send_keys(e, Keys.ENTER)

    # Input date of reservation.
    date_picker = WebDriverWait(driver, timeout=short_timeout).until(
        lambda d: d.find_element(By.CLASS_NAME, "datepicker"))
    date_picker.clear()
    date_picker.send_keys(f"{day}.{month}.{year}")

    # Need to click the highlighted date in the calendar because the website does not accept Enter for some reason.
    WebDriverWait(driver, timeout=short_timeout).until(
        lambda d: d.find_element(By.ID, "ui-datepicker-div")).find_element(By.CLASS_NAME, "ui-state-active").click()

    driver.find_element(By.ID, "preformsubmit").click()

    select_room(driver, reservation)


def select_room(driver, reservation):
    room_choice = WebDriverWait(driver, timeout=short_timeout).until(lambda d: d.find_element(By.ID, "roomChoice"))

    if room_choice.text == "Ingen mulige rom":
        print("No rooms found.")
    else:
        # Selects the first available room in the list of rooms.
        WebDriverWait(driver, timeout=short_timeout).until(
            lambda d: d.find_element(By.ID, "room_table")).find_element(By.TAG_NAME, "input").click()

        driver.find_element(By.ID, "rb-bestill").click()

        # Provide required description
        WebDriverWait(driver, timeout=short_timeout).until(
            lambda d: d.find_element(By.ID, "name")).send_keys(reservation.description)

        # Provide optional notes
        if reservation.notes:
            driver.find_element(By.ID, "notes").send_keys(reservation.notes)

        driver.find_element(By.CLASS_NAME, "button--primary-green").click()


def get_credentials():
    parser = configparser.ConfigParser()
    parser.read("login.credentials")
    return parser.get("config", "LOGIN_USER"), parser.get("config", "LOGIN_PASSWORD")


def main():
    username, password = get_credentials()
    driver = open_page("https://tp.uio.no/ntnu/rombestilling/", username, password)

    date = datetime(2022, 2, 27, 10, 0)
    duration = date + timedelta(hours=2, minutes=15)
    loc = Location("Gløshaugen", None, None, 2, None)
    description = "Kollokvie"
    notes = None
    res = Reservation(loc, date, duration, description, notes)

    reserve_room(driver, res)


if __name__ == '__main__':
    main()
