class NavInfo:
    """Scraper navigation object to hold scrape data"""

    def __init__(self, nav_type, css_select="", css_name="", css_attr="href",
                 css_rej_filter="", css_download="",
                 wait_err_message="navigation failed!", extra_task=None):
        """Initializes NavInfo object

        :param extra_task: extra tasks to perform after clicking first element for download
        :param str css_select: the elements used to navigate between files
        :param str css_name: the elements used to identify the name of the file
        :param str css_download: the first element to interact with in order to download
        :param str css_attr: the attribute that contains reference to a file (ex:link)
        :param str css_rej_filter: the elements found in css_select to reject from being scraped
        :param wait_err_message: error message to show if no elements are found or present
        """
        self.id = nav_type
        self.css_select = css_select
        self.css_name = css_name
        self.css_download = css_download
        self.css_attr = css_attr
        self.css_rej_filter = css_rej_filter
        self.wait_err_message = wait_err_message
        self.extra_task = extra_task
