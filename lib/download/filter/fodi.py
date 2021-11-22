from .base import Base
from selenium.webdriver.common.by import By


class FODI(Base):
    """FODI download filter"""

    def apply(self, elements) -> list:
        files = [el.find_element(By.CSS_SELECTOR, "div.file ion-icon[name]") for el in
                 elements]
        filtered_list = []
        for i, el in enumerate(files):
            file_type = el.get_attribute("name")
            if file_type == "document":
                filtered_list.append(elements[i])
        return filtered_list
