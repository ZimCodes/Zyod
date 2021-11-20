from .base_navigator import BaseNavigator


class DownloadNavigator(BaseNavigator):
    """DownloadNavigator object with a special type of download"""

    def download(self, directory=None) -> tuple[list, list]:
        self._setup_navigate(directory, False)
        return self._scraper.get_results()
