# Zyod

**Zyod** is designed to scrape, download, & record files from **dynamic ODs** *(JavaScript focused
Open Directories)* with the help of **[Selenium](https://github.com/seleniumhq/selenium)**. 

For static ODs check out [Zeiver!](https://github.com/ZimCodes/Zeiver)

For ease of use, check out the [Zyod configurator](https://zimtools.xyz/zyod).

## Table of Contents
- [Sample Usage](#sample-usage)
- [OD Support](#open-directory-support)
- [Installation](#installation)
- [Commands](#commands)
  - [Positional](#positional)
  - [Options](#options)
    - [General](#general)
    - [WebDriver](#webdriver)
    - [Navigator](#navigator)
    - [Downloading](#downloading)
    - [Recording](#recording)
    - [Interactivity](#interactivity)
    - [Misc.](#miscellaneous)
- [License](#license)

## Sample Usage

This command uses the Google Chrome driver to scrape and record links from *https://od.example. com*
. While scraping, Zyod will write more text to the console, wait a maximum of *15 seconds*
before scraping each page, search 3 directory levels deep, interact by scrolling, and do not wait in
between each scroll action.

`python zyod.py --driver "chrome" -v -w 15 -d 3 --scroll --scroll-wait 0 https://od.example.com`
## Open Directory Support
Supported ODs can be found in [OD.md](https://github.com/ZimCodes/Zyod/blob/main/OD.md).

## Installation
1. This project requires `Python 3.10`. In order to start, please make sure Python is installed.
   1. You can download Python here: https://www.python.org/downloads/
2. Download this repository.
3. Extract zip file.
4. Open a terminal and point it to the Zyod project directory containing `zyod.py`.
5. To run zyod, start you commands using `python zyod.py`.


## Commands
### Positional

__URL*s*...__

Link(*s*) to the OD(*s*) you would like to scrape/record/download content from.
*_This is not needed if you are using `-i, --input`._

---

### Options
#### General

***-h, --help***

Prints help information.

***-V, --version***

Prints version information

***-v, --verbose***

Enable verbose output

---

#### WebDriver

***--driver***

Type of webdriver to use. *Choices:* `firefox`,`chrome`,`edge`. *Default: `firefox`*.

***--driver-path***

Location of the WebDriver in use. *Default: `%PATH%`*

***--headless***

Activates headless mode. **Cannot be used with `--download`.

---

#### Navigator

***-d, --depth***

Specify the maximum depth for recursive scraping. _Default: `20`_. **Depth of`1` is current
directory.**

***-w, --wait***

Wait a maximum number of seconds before scraping.

Most dynamic ODs load content on the page *very slooooowly*. This option allows Zyod to wait a 
certain amount of time before scraping.

***--random-wait***

Wait a random amount of seconds before scraping.

The time before scraping will vary between 0.5 * `--wait,-w` (_inclusive_)
to 1.5 * `--wait,-w` (_exclusive_)

---

#### Downloading
***--download***

Enable downloading feature.

By default, downloading is disabled. Use this option to allow Zyod to download files from ODs. 
**Cannot be used with `--headless`**.


***--dir, --download-dir***

Path to store downloaded files.

The directory path to store download files. *Default:* `Downloads folder`

***--download-wait***

Wait a random amount of seconds before downloading.

Wait between 0.5 * `--download-wait` (_inclusive_) to 1.5 * `--download-wait` 
(_exclusive_) seconds before downloading. *Default:* `0`.

---

#### Recording

***-o, --output***

The file path to store the scraped links. *Default:* `output.txt`

***-i, --input***

Read links from a file.

Read links from a file, which points to a series of ODs. **Each line must represent a link to an
OD**. **This option can be used with the `URL..` positional argument.** To use this option as a
standalone you must provide `URL..` as an empty string `""`:

`python zyod.py -i input.txt ""`

***--no-record***

Disable recording feature.

Recording is enabled by default. Use this option to disable recording.

---

#### Interactivity

***--scroll***

Activates scrolling feature.

Scroll down the page repeatedly until last element is reached. Some dynamic ODs only loads 25, 
50, etc. amount of content on the page at a time. When the bottom of the page is reached, more 
content is loaded. This option will allow Zyod to scroll in order to scrape and download more 
content from the OD.

***--scroll-wait***

Amount of seconds to wait before attempting to scroll again.

 *Default:* `4.2`.

---

#### Miscellaneous

***--web-wait***

Amount of seconds to wait for browser to initially load up each OD before executing Zyod.

When Zyod visits a dynamic OD for the first time, it may take a long time for the OD to load up. 
Use this option to delay execution of Zyod, giving the page time to load up. 

*Default:* `15`.

***-r, --refresh***

Refresh the page and try again upon failure.

Refresh the page when Zyod fails to navigate to a page or fails to locate elements on the page.
Then try again.

***--load-wait***

Amount of seconds to wait for a page load to complete before throwing an error. *Default:* `30`.

---

## License

Zyod is licensed under the MIT License.

See the [MIT](https://github.com/ZimCodes/Zyod/blob/main/LICENSE) license for more info. 