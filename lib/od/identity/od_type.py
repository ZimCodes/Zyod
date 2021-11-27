from enum import Enum


class ODType(Enum):
    """ODType enum class holds the names of all supported ODs"""
    GENERIC = "Generic",
    GO_INDEX = "GoIndex",
    FODI = "FODI"
    ZFILE = "ZFile"
    GD_INDEX = "GDIndex"
    ONEDRIVE_VERCEL_INDEX = "onedriver-vercel-index"
    GONELIST = "GONEList"
    SHARELIST = "ShareList"
    YUKI_DRIVE = "Yuki Drive"
    ALIST = "AList"
    WATCHLIST_ON_FIRE = "Watchlist on fire 🔥🔥🔥"
