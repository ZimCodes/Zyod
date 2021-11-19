import argparse
import random
from .writer import Writer
from .talker import Talker
import re


class Opts:
    """Opts object parsing and holding data from commandline arguments"""

    def __init__(self):
        """Initializes Opts object

        _parser: ArgumentParser object
        """
        self._parser = argparse.ArgumentParser(prog="Zyod", description="Scrape dynamic ODs.")
        self._init_args()

    def _init_args(self) -> None:
        """Parse all command line arguments"""
        self._parse_urls()
        self._parse_general_group()
        self._parse_driver_group()
        self._parse_navigator_group()
        self._parse_record_group()
        self._parse_download_group()
        self._parse_interactive_group()
        opts = self._parser.parse_args()
        self._assign_args(opts)

    def _assign_args(self, opts) -> None:
        """Assign arguments to fields

        :param Namespace opts: parsed arguments
        :return: None
        """
        self._args_check(opts)
        self.urls = opts.urls + Writer.read_input_file(opts.input)
        self.urls = [url for url in self.urls if url != '']
        self.depth = abs(opts.depth)
        self.verbose = opts.verbose
        self.driver_path = opts.driver_path
        self.driver_type = opts.driver
        self.output = opts.output
        self._wait = abs(opts.wait)
        self._random_wait = opts.random_wait
        self.download_dir = opts.download_dir
        self.do_download = opts.download
        self._download_wait = abs(opts.download_wait)
        self.dont_record = opts.no_record
        self.headless = opts.headless
        self.accept = opts.accept
        self.reject = opts.reject
        self.scroll = opts.scroll
        self.scroll_wait = opts.scroll_wait

    def _args_check(self, opts) -> None:
        """Checks arguments for conflicts and report them

        :param self opts: Opts class
        :return:
        """
        if opts.headless and opts.download:
            self._parser.exit(message="The option, '--headless', CANNOT be used alongside "
                                      "'--download'!")
        if opts.headless and opts.driver == 'safari':
            Talker.warning("Safari currently does not support headless mode.", True)

    def get_wait(self) -> int:
        """Retrieve wait time

        :return: the appropriate wait time in seconds
        """
        if self._wait and self._random_wait:
            return Opts._get_rand_wait(self._wait)
        else:
            return self._wait

    def get_download_wait(self) -> int:
        """ Retrieve the amount of time to wait for download

        :return: seconds to wait before executing tasks
        """
        return Opts._get_rand_wait(self._download_wait)

    @staticmethod
    def _get_rand_wait(wait) -> int:
        """Randomize the amount of time to wait

        :param wait: amount of seconds to wait
        :return: the randomized seconds to wait
        """
        return random.randint(0.5 * wait, 1.5 * wait)

    def _parse_urls(self) -> None:
        """Adds urls positional argument"""
        self._parser.add_argument('urls', nargs='+', help='URLs of ODs to scrape from.')

    def _parse_general_group(self) -> None:
        """Adds general optional arguments"""
        general_group = self._parser.add_argument_group('general', 'general options')
        general_group.add_argument('-V', '--version', action='version', version='%(prog)s 0.1.0',
                                   help="Prints version information.")
        general_group.add_argument('-v', '--verbose', action='store_true', help="Enable verbose "
                                                                                "output.")

    def _parse_navigator_group(self) -> None:
        """Adds navigator related optional arguments"""
        navigator_group = self._parser.add_argument_group('scraper', 'configure the scraping ODs')
        navigator_group.add_argument('-w', '--wait', type=float, default=0, help='Maximum '
                                                                                 'seconds to '
                                                                                 'wait '
                                                                                 'before navigating '
                                                                                 'each page.')
        navigator_group.add_argument('--random-wait', action='store_true', help='Randomize --wait '
                                                                                'option; Between '
                                                                                '0.5 * --wait,-w '
                                                                                '(inclusive) to 1.5 *'
                                                                                ' --wait,-w '
                                                                                '(exclusive)')
        navigator_group.add_argument('-d', '--depth', default=20, metavar='LEVEL', type=int,
                                     help='How many '
                                          'directories deep to scrape?')
        filter_group = navigator_group.add_mutually_exclusive_group()

        filter_group.add_argument('-a', '--accept', type=re.compile, help="Regex of links to "
                                                                          "accept for recording "
                                                                          "& downloading. Only "
                                                                          "applies to ODs that "
                                                                          "are eligible for "
                                                                          "scraping.")
        filter_group.add_argument('-r', '--reject', type=re.compile,
                                  help="Regex of file links to "
                                       "reject for recording "
                                       "& downloading. Only "
                                       "applies to ODs that "
                                       "are eligible for "
                                       "scraping.")

    def _parse_driver_group(self) -> None:
        """Adds Driver related optional arguments"""
        driver_group = self._parser.add_argument_group('driver',
                                                       'Handles the configurations of a webdriver')
        driver_group.add_argument('--driver-path', help='Directory path of your browser driver. '
                                                        'Default: %PATH%')
        driver_group.add_argument('--driver',
                                  choices=['firefox', 'chrome', 'edge'],
                                  required=True, help='The type of browser driver it is.')
        driver_group.add_argument('--headless', action="store_true", help="Activates "
                                                                          "headless mode. "
                                                                          "Cannot be used "
                                                                          "with "
                                                                          "'--download' "
                                                                          "option.")

    def _parse_download_group(self) -> None:
        """Adds Download group related optional arguments"""
        download_group = self._parser.add_argument_group('download', 'downloading configurations')
        download_group.add_argument('--download-dir', '--dir', help="The directory path to store "
                                                                    "downloaded files. Default: "
                                                                    "Downloads folder.")
        download_group.add_argument('--download', action="store_true", help="Enable feature to "
                                                                            "download")
        download_group.add_argument('--download-wait', type=float, default=0, help="Maximum "
                                                                                   "seconds "
                                                                                   "to wait "
                                                                                   "before each "
                                                                                   "download; "
                                                                                   "This is "
                                                                                   "randomized"
                                                                                   "between 0.5 "
                                                                                   "* "
                                                                                   "--download-wait"
                                                                                   " (inclusive) "
                                                                                   "to 1.5 * "
                                                                                   "--download-wait"
                                                                                   " (exclusive)")

    def _parse_record_group(self) -> None:
        """Adds Record related optional arguments"""
        record_group = self._parser.add_argument_group('record', 'file/directory configurations '
                                                                 'when recording data')
        record_group.add_argument('--no-record', action="store_true", help="Disable feature to "
                                                                           "record links to a "
                                                                           "file")
        record_group.add_argument('-o', '--output', default="output.txt",
                                  type=argparse.FileType('w', encoding='UTF-8'), help='The output '
                                                                                      'file path '
                                                                                      'to place '
                                                                                      'all '
                                                                                      'recorded '
                                                                                      'links. '
                                                                                      'Links are '
                                                                                      'appended '
                                                                                      'to file!')
        record_group.add_argument('-i', '--input', type=argparse.FileType('r', encoding='UTF-8'),
                                  help="Path to an input file containing links to multiple ODs. "
                                       "Each line MUST represent one link to an OD. Line-by-Line "
                                       "format!"
                                  )

    def _parse_interactive_group(self) -> None:
        interact_group = self._parser.add_argument_group('interactive', 'interactivity options')
        interact_group.add_argument('--scroll', action="store_true", help="scroll down the "
                                                                          "page "
                                                                          "repeatedly until the"
                                                                          " last bottom element is "
                                                                          "reached.")
        interact_group.add_argument('--scroll-wait', type=float, default=4.2, help="Amount of "
                                                                                   "seconds to wait"
                                                                                   " before "
                                                                                   "attempting to "
                                                                                   "scroll again.")
