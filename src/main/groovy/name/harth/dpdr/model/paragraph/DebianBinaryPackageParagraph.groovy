package name.harth.dpdr.model.paragraph

import name.harth.dpdr.model.field.Architecture
import name.harth.dpdr.model.field.PkgName
import name.harth.dpdr.model.field.PkgVersion
import name.harth.dpdr.model.field.PkgReference
import name.harth.dpdr.model.field.Priority
import name.harth.dpdr.model.field.Section
import name.harth.dpdr.model.field.UserDefined

import javax.mail.internet.InternetAddress

class DebianBinaryPackageParagraph {
    PkgName packageName
    PkgReference source
    PkgVersion packageVersion
    Section section
    Priority priority
    Architecture architecture
    boolean essential
    PkgReference[] depends
    PkgReference[] preDepends
    PkgReference[] recommends
    PkgReference[] suggests
    PkgReference[] breaks
    PkgReference[] conflicts
    PkgReference[] provides
    PkgReference[] replaces
    PkgReference[] enhances
    PkgReference[] buildDepends
    PkgReference[] buildDependsIndep
    PkgReference[] buildConflicts
    PkgReference[] buildConflictsIndep
    int installedSize
    InternetAddress maintainer
    String description
    URL homepage
    PkgReference[] builtUsing
    UserDefined[] userDefinedFields
}
