class Browser:
    """Base class for all browser drivers"""

    def __init__(self, driver_opts, opts):
        """Initializes Browser driver object

        :param Options driver_opts: WebDriver Options
        :param Opts opts: Opts class
        """
        self._driver_opts = driver_opts
        self._capabilities = {}
        self._set_browser_location(opts)
        self._set_preferences(opts)
        self._set_desired_capabilities()

    def _set_preferences(self, opts) -> None:
        """Configure Settings/Preferences of WebDriver

        :param Opts opts: Opts class
        :return:
        """
        pass

    def _set_desired_capabilities(self) -> None:
        """Configure Desired Capabilities of WebDriver"""
        self._capabilities = {"acceptInsecureCerts": True}

    def _set_browser_location(self, opts) -> None:
        """Set the location of the driver binary

        :param Opts opts: Opts class
        :return:
        """
        if opts.driver_path:
            self._driver_opts.binary_location = opts.driver_path

    def get_driver(self):
        """Gets the configured WebDriver

        :return: Configured Selenium Webdriver
        """
        pass
