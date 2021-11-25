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
