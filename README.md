# Zyod

**Zyod** is designed to *scrape*, *download*, & *record* files from **dynamic ODs** *(JavaScript 
focused Open Directories)* with the help of **[Selenium](https://github.com/seleniumhq/selenium)**.

For static ODs check out [Zeiver!](https://github.com/ZimCodes/Zeiver)

For ease of use, check out the [Zyod configurator](https://zimtools.vercel.app/zyod).

## Table of Contents
- [Sample Usage](#sample-usage)
- [OD Support](#open-directory-support)
- [How to Use](#how-to-use)
- [Commands](#commands)
- [Things to Note](#things-to-note)
- [License](#license)

## Sample Usage

This command uses the Google Chrome driver to scrape and record links from *https://od.example.com*.
While scraping, Zyod will write more text to the console, wait a maximum of *15 seconds*
before scraping each page, search 3 directory levels deep, interact by scrolling, and do not wait in
between each scroll action.

```commandline
java -jar zyod.jar --driver "chrome" -v -w 15 -d 3 --scroll --scroll-wait 0 https://od.example.com
```

## Open Directory Support
Supported ODs can be found in the [wiki page](https://github.com/ZimCodes/Zyod/wiki/Supported-Open-Directories).

## How to Use
1. Make sure you have *Java* setup on your machine. If not, [download a copy here](https://jdk.java.net/).
2. Download *zyod.jar* file from the [release page](https://github.com/ZimCodes/Zyod/releases).
3. Open up a command line interface.
4. From here type, `java -jar zyod.jar [INSERT YOUR COMMANDS HERE]`. And thats it!


## Commands
Check out the [wiki page](https://github.com/ZimCodes/Zyod/wiki/Commands) for a list of commands to use.

---

## Things to Note
### Double Browser Windows

In non-headless mode, Zyod will open 2 windows. One of the windows is used to quickly identify 
the OD type. Once this is finished, Zyod will close this window automatically. The other window 
is performing the task(s) Zyod has been provided with on the OD.

### Files are played instead of downloaded

When Zyod attempts to download a file (such as mp4, mp3, etc.), it will be played in the 
browser instead. This only happens when you use a Chromium driver. **Use Firefox driver instead.**  

### Recording Incorrect URLs

When recording, incorrect URLs may be generated from certain Open Directory types *(Ex: FODI)*.
This is likely because the *true* URL themselves are *hidden*, *protected*, or *masked*.

### Master `waiting` options

In order to successfully use Zyod, take complete advantage of the **waiting options**. Since
Zyod focuses on dynamic ODs, each OD loads differently the next. Some dynamic ODs' webpages loads
*super slow*, but the resources **ON** the webpages such as images, animations, styles, files, etc
loads very quickly. There's also some dynamic ODs that works vice versa.

### File Detection
Any file passing the below regex is considered a file, otherwise it will be considered a directory.

`[^/=#.]+\.(?:[a-zA-Z0-9]{3,7}|[a-zA-Z][a-zA-Z0-9]|[0-9][a-zA-Z])$`

So in short, file extensions must be `> 1` & `< 8`.

---
## License

Zyod is licensed under the MIT License.

See the [MIT](https://github.com/ZimCodes/Zyod/blob/main/LICENSE) license for more info. 