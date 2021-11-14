# Zyod
**Zyod** is designed to scrape file links from **dynamic ODs** *(JavaScript only Open Directories)* 
with the help of **[Selenium](https://github.com/seleniumhq/selenium)**. 
Primarily, Zyod was created to fulfill the missing features not available in Zeiver. 

For **static ODs**, check out [Zeiver](https://github.com/ZimCodes/Zeiver).

## Workflow
Since Zyod was created to work alongside Zeiver, it is ideal to use these tools together. This 
workflow focuses on a scenario in which Zeiver is unable to scrape/download files from an unknown 
*(probably dynamic)* OD. As a solution, Zyod will scrape the OD *for* Zeiver and prepare the 
links for download.

1. Input the OD URL in Zyod. By default, the scraped links will be placed in the *output.txt* file.
   1. Ex: `python zyod.py --driver chrome -o output.txt https://example.com/cool`
2. Now let Zeiver read the links from the *output.txt* file. This will invoke Zeiver to download 
the files from the OD.
   1. Ex `zeiver -i output.txt -o ./save_dir`
3. That's it.

## Installation

## Commands
### Positional
__URL*s*...__

Link(*s*) to the OD(*s*) you would like to scrape content from.
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

Type of webdriver in use. *Choices:* `firefox`,`chrome`,`chromium`,`opera`,`safari`,`edge`

***--driver-path***

Location of the WebDriver in use. *Default: `%PATH%`*

---

#### Scraper
***-d, --depth***

Specify the maximum depth for recursive scraping. _Default: `20`_. **Depth of`1` is current 
directory.**

***-w, --wait***

Wait a specified number of seconds before scraping.

***--random-wait***

Wait a random amount of seconds before scraping.

The time before scraping will vary between 0.5 * `--wait,-w` (_inclusive_) 
to 1.5 * `--wait,-w` (_exclusive_)

---

#### Files/Directories
***-o, --output***

The file path to store the scraped links. *Default:* `output.txt`

***-i, --input***

Read links from a file, which points to a series of ODs. **Each line must represent a link to an 
OD**. This option can be used with the `URL..` positional option. To use this option as a 
standalone you must provide `URL..` as an empty string `""`:

`python zyod.py -i input.txt ""`

---

## License
Zyod is licensed under the MIT License.

See the [MIT](https://github.com/ZimCodes/Zyod/blob/main/LICENSE) license for more info. 