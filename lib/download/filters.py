from selenium.webdriver.common.by import By


class Base:
    """Base object for Download filter"""

    def apply(self, elements) -> list:
        """Filter out elements to download

        :param list elements: list of elements to download
        :return: filtered list of elements
        """
        pass


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


class ZFile(Base):
    """ZFile download filter"""

    def apply(self, elements) -> list:
        files = [el.find_element(By.CSS_SELECTOR,
                                 "tr.el-table__row svg use[*|href]") for el in
                 elements]
        filtered_list = []
        for i, el in enumerate(files):
            file_type = el.get_attribute("xlink:href")
            if file_type != "#el-icon-my-folder":
                filtered_list.append(elements[i])
        return filtered_list


class GONEList(Base):
    """Download filter for GONEList"""

    def apply(self, elements) -> list:
        files = [el.find_element(By.CSS_SELECTOR, "span i") for el in
                 elements]
        filtered_list = []
        for i, el in enumerate(files):
            file_type = el.get_attribute("class")
            if "fa-folder-open" not in file_type:
                filtered_list.append(elements[i])
        return filtered_list
