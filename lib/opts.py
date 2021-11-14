import argparse
import random


class Opts:
    """Commandline arguments"""

    def __init__(self):
        self._parser = argparse.ArgumentParser(prog="Zyod", description="Scrape dynamic ODs.")
        self._init_args()

    def _init_args(self):
        self._parse_urls()
        self._parse_version()
        self._parse_verbose()
        self._parse_random_wait()
        self._parse_wait()
        self._parse_depth()
        self._parse_driver_type()
        self._parse_driver_path()
        self._parse_output_path()
        self._parse_input_path()
        opts = self._parser.parse_args()
        self._assign_args(opts)

    def _assign_args(self, opts):
        self.urls = opts.urls + Opts._read_input_file(opts)
        self.urls = [url for url in self.urls if url != '']
        self.depth = opts.depth
        self.verbose = opts.verbose
        self.driver_path = opts.driver_path
        self.driver_type = opts.driver
        self.output = opts.output
        self._wait = opts.wait
        self._random_wait = opts.random_wait

    @staticmethod
    def _read_input_file(opts):
        if opts.input is not None:
            links = opts.input.readline()
            urls = []
            while bool(links) or links != '\n':
                if links.endswith('\n'):
                    links = links[:len(links) - 1]
                    urls.append(links)
                    links = opts.input.readline()
                else:
                    urls.append(links)
                    break

            opts.input.close()
            return urls
        else:
            return []

    def get_wait(self):
        if self._wait and self._random_wait:
            return random.randint(int(0.5 * self._wait), int(1.5 * self._wait))
        else:
            return self._wait

    def _parse_urls(self):
        self._parser.add_argument('urls', nargs='+', help='URLs of ODs to scrape from.')

    def _parse_version(self):
        self._parser.add_argument('-V', '--version', action='version', version='%(prog)s 0.1.0',
                                  help="prints version information.")

    def _parse_verbose(self):
        self._parser.add_argument('-v', '--verbose', action='store_true', help="enable verbose "
                                                                               "output.")

    def _parse_wait(self) -> None:
        self._parser.add_argument('-w', '--wait', type=int, help='How long to wait '
                                                                 'before navigating each page.')

    def _parse_random_wait(self):
        self._parser.add_argument('--random-wait', action='store_true', help='randomize --wait '
                                                                             'option; Between '
                                                                             '0.5 * --wait,-w (inclusive) to 1.5 * --wait,-w (exclusive)')

    def _parse_depth(self) -> None:
        self._parser.add_argument('-d', '--depth', default=20, metavar='LEVEL', type=int,
                                  help='how many '
                                       'directories deep to scrape?')

    def _parse_driver_path(self) -> None:
        self._parser.add_argument('--driver-path', help='directory path of your browser driver. '
                                                        'Default: %PATH%')

    def _parse_driver_type(self) -> None:
        self._parser.add_argument('--driver',
                                  choices=['firefox', 'chrome', 'edge', 'opera', 'chromium',
                                           'safari'],
                                  required=True, help='the type of browser driver it is.')

    def _parse_output_path(self) -> None:
        self._parser.add_argument('-o', '--output', default="output.txt",
                                  type=argparse.FileType('a', encoding='UTF-8'), help='the output '
                                                                                      'file path '
                                                                                      'to place '
                                                                                      'all '
                                                                                      'recorded '
                                                                                      'links. '
                                                                                      'Links are '
                                                                                      'appended '
                                                                                      'to file!')

    def _parse_input_path(self):
        self._parser.add_argument('-i', '--input', type=argparse.FileType('r', encoding='UTF-8'),
                                  help="path to an input file containing links to multiple ODs. "
                                       "Each line MUST represent one link to an OD. Line-by-Line "
                                       "format!"
                                  )
