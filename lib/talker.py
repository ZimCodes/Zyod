from .asset.directory import Directory


class Talker:
    """Talker object prints colorful messages to the console"""

    @staticmethod
    def arrow_header_info(title, info, new_line=False) -> None:
        Talker._print(f'{title}: \n'
                      f'--->| {info} |<---', new_line)

    @staticmethod
    def arrow_info(title, info, new_line=False) -> None:
        message = f'{title} --> {info}'
        Talker._print(message, new_line)

    @staticmethod
    def list_info(items, title, new_line=False) -> None:
        for x in items:
            Talker.info(title, x)
        if new_line:
            print()

    @staticmethod
    def list_dir_info(dirs, title, new_line=False) -> None:
        links = [directory.url for directory in dirs if isinstance(directory, Directory)]
        Talker.list_info(links, title, new_line)

    @staticmethod
    def info(title, info, new_line=False) -> None:
        message = f'{title}: {info}'
        Talker._print(message, new_line)

    @staticmethod
    def header(title, new_line=False) -> None:
        message = f'------{title}------'
        Talker._print(message, new_line)

    @staticmethod
    def loading(title, dots=5, new_line=False) -> None:
        dots = '.' * dots
        message = f'{title}{dots}'
        Talker._print(message, new_line)

    @staticmethod
    def complete(title, new_line=False) -> None:
        message = f'*** {title} ***'
        Talker._print(message, new_line)

    @staticmethod
    def warning(title, new_line=False) -> None:
        message = f'!- {title} -!'
        Talker._print(message, new_line)

    @staticmethod
    def divider() -> None:
        message = f"================================================="
        Talker._print(message, True)

    @staticmethod
    def _print(message, new_line) -> None:
        if new_line:
            message += '\n'
        print(message)

    @staticmethod
    def current_directory(verbose, url, depth_level) -> None:
        Talker.header("Current Directory")
        if verbose:
            Talker.arrow_info("level", depth_level)
        Talker.arrow_info("url", url, True)

    @staticmethod
    def file_stats(dirs, files) -> None:
        Talker.header("Directories")
        Talker.list_dir_info(dirs, 'Dir')
        Talker.arrow_info("Total", len(dirs), True)
        Talker.header("Files")
        Talker.list_info(files, 'File')
        Talker.arrow_info("Total", len(files))
        Talker.divider()
