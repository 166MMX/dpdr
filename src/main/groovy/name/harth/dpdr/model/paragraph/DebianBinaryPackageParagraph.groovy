package name.harth.dpdr.model.paragraph

import name.harth.dpdr.model.field.Architecture
import name.harth.dpdr.model.field.PackageName
import name.harth.dpdr.model.field.PackageReference
import name.harth.dpdr.model.field.PackageVersion
import name.harth.dpdr.model.field.Priority
import name.harth.dpdr.model.field.Section
import name.harth.dpdr.model.field.UserDefined

import javax.mail.internet.InternetAddress

class DebianBinaryPackageParagraph {
    PackageName packageName
    PackageName source
    PackageVersion packageVersion
    Section section
    Priority priority
    Architecture architecture
    boolean essential
    PackageReference[] depends
    PackageReference[] preDepends
    PackageReference[] recommends
    PackageReference[] suggests
    PackageReference[] breaks
    PackageReference[] conflicts
    PackageReference[] provides
    PackageReference[] replaces
    PackageReference[] enhances
    PackageReference[] buildDepends
    PackageReference[] buildDependsIndep
    PackageReference[] buildConflicts
    PackageReference[] buildConflictsIndep
    int installedSize
    InternetAddress maintainer
    String description
    URL homepage
    PackageReference[] builtUsing
    UserDefined[] userDefinedFields
}
