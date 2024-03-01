enum TabbySubtype:
  case Classic
  case Ticked
  case Mackerel
  case Spotted
  case Patched

enum ShadingSubtype:
  case Chinchilla
  case Shaded
  case Smoke

enum BicolorSubtype:
  case Tuxedo
  case Van

enum TricolorSubtype:
  case Calico
  case Tortie

enum Pattern:
  case Tabby(val subType: TabbySubtype)
  case Pointed
  case Shading(val subType: ShadingSubtype)
  case SolidColor
  case Bicolor(val subType: BicolorSubtype)
  case Tricolor(val subType: TricolorSubtype)
  case Spots
