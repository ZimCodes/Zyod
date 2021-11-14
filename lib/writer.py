class Writer:
    """Record links to an output file"""

    def __init__(self, f):
        self._f = f

    def write(self, files):
        output = ''
        for file in files:
            output += f"{file}\n"
        self._f.write(output)

    def close(self):
        self._f.close()
