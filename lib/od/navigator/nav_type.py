"""ODs that have multiple variants"""
from enum import Enum


class GoIndex(Enum):
    """Types of Navigations for GoIndex"""
    THUMBNAIL_VIEW = 1,
    LIST_VIEW = 2,
    OLDER = 3


class FODI(Enum):
    """Types of Navigations for FODI"""
    MAIN = 1


class ZFile(Enum):
    """Types of Navigator for ZFile"""
    MAIN = 1


class GDIndex(Enum):
    """Types of Navigations for GDIndex"""
    MAIN = 1


class OneDriveVercelIndex(Enum):
    """Types of Navigations for oneindex-vercel-index"""
    MAIN = 1


class GONEList(Enum):
    """Types of Navigations for GONEList"""
    MAIN = 1


class ShareList(Enum):
    """Types of Navigations for ShareList"""
    PREVIEW_QUERY = 1
    DOWNLOAD_QUERY = 2
    INTERACTIVE = 3


class YukiDrive(Enum):
    """Types of Navigations for YukiDrive"""
    MAIN = 1


class AList(Enum):
    """Types of Navigations for AList"""
    ORIGINAL = 1
    WEB = 2
