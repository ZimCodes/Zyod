from .base import Base
from selenium.webdriver.common.by import By


class FODI(Base):
    """FODI download filter"""

    def apply(self, elements) -> list:
        files = elements.find_elements(By.CSS_SELECTOR, "div.file ion-icon[name]")
        filtered_list = []
        for i, el in enumerate(files):
            file_type = el.get_attributes("name")
            if file_type == "document":
                filtered_list.append(elements[i])
        return filtered_list
