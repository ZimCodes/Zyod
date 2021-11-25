from enum import Enum


class ODType(Enum):
    """ODType enum class holds the names of all supported ODs"""
    GENERIC = "Generic",
    GO_INDEX = "GoIndex",
    FODI = "FODI"
    ZFILE = "ZFile"
