import json

from .driver.support.driver_support import DriverSupport


class Writer:
    """Writer object handles all file operations"""

    def __init__(self, f):
        """Writer constructor to initialize object

        :param File f: File object
        """
        self._f = f
        Writer._init_mimes()

    @staticmethod
    def _init_mimes():
        """Initialize MIME types data file"""
        with open("./data/mimes.json", "r") as f:
            mime_list = json.load(f)
            DriverSupport.MIMES = ','.join(mime_list)

    def write(self, files) -> None:
        """Write links to a file

        :param list files: list of file links
        :return: None
        """
        output = ''
        for file in files:
            output += f"{file}\n"
        self._f.write(output)

    def close(self) -> None:
        """Close File resource"""
        self._f.close()

    @staticmethod
    def read_input_file(file) -> list:
        """Read links from an file

        :param File file: file containing links ordered line by line
        :return: list of OD URLs
        """
        if file is not None:
            links = file.readline()
            urls = []
            while bool(links) or links != '\n':
                if links.endswith('\n'):
                    links = links[:len(links) - 1]
                    urls.append(links)
                    links = file.readline()
                else:
                    urls.append(links)
                    break

            file.close()
            return urls
        else:
            return []
