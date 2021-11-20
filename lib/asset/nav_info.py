class NavInfo:
    """Scraper navigation object to hold scrape data"""

    def __init__(self, nav_type, css_select="", css_link="href", css_download="",
                 wait_err_message="navigation failed!", extra_task=None):
        """Initializes SubNavigation object

        :param extra_task: extra tasks to perform after clicking first element for download
        :param str css_select: the elements used to navigate between files
        :param str css_download: the elements to interact with to download
        :param str css_link: the attribute that contains reference to a file
        :param wait_err_message: error message to show if no elements are found or present
        """
        self.id = nav_type
        self.css_select = css_select
        self.css_download = css_download
        self.css_attr = css_link
        self.wait_err_message = wait_err_message
        self.extra_task = extra_task
