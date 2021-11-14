class Browser:
    """Browser Driver class"""

    def __init__(self, driver_opts, opts):
        self._driver_opts = driver_opts
        self._set_browser_location(opts)

    def _set_browser_location(self, opts):
        if opts.driver_path:
            self._driver_opts.binary_location = opts.driver_path

    def get_driver(self):
        pass
