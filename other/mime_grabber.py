"""Generate a JSON file with all available MIME types"""
import json

from selenium import webdriver
from selenium.webdriver.firefox.options import Options
from selenium.webdriver.common.by import By


def get_mimes(drive, url, css_select) -> set:
    drive.get(url)
    elems = drive.find_elements(By.CSS_SELECTOR, css_select)
    return {x.text for x in elems}


if __name__ == "__main__":
    """Grabs mime types from IANA database and friends"""
    opt = Options()
    opt.headless = True
    driver = webdriver.Firefox(options=opt)
    site_point_mimes = get_mimes(driver, "https://www.sitepoint.com/mime-types-complete-list/",
                                 "tr td[headers]:nth-child(n+2)")
    iana_mimes = get_mimes(driver,
                           "https://www.iana.org/assignments/media-types/media-types.xhtml",
                           "tr td:nth-child(2n) a")
    digipres = get_mimes(driver, "https://www.digipres.org/formats/mime-types/", "th a")
    uniq = site_point_mimes | iana_mimes | digipres
    mime_to_list = [x for x in uniq]
    mime_to_list.sort()
    with open('./data/mimes.json', 'w') as f:
        new_format = json.dumps(mime_to_list)
        f.write(new_format)
    driver.close()
