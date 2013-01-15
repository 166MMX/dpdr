package name.harth.ddr.model

import javax.mail.internet.InternetAddress

class DebianPackage {
    PackageName source
    InternetAddress maintainer
    InternetAddress[] uploaders
    InternetAddress changedBy
    Section section
    Priority priority
    PackageName packageName
    Architecture architecture
    boolean essential
    Urgency urgency
    String urgencyComment
    int installedSize
    URL homepage
    boolean dmUploadAllowed
    DebianPackageReference[] depends
    DebianPackageReference[] preDepends
    DebianPackageReference[] recommends
    DebianPackageReference[] suggests
    DebianPackageReference[] breaks
    DebianPackageReference[] conflicts
    DebianPackageReference[] provides
    DebianPackageReference[] replaces
    DebianPackageReference[] enhances
}


/*

    5.6.11 Standards-Version
    5.6.12 Version
    5.6.13 Description
    5.6.14 Distribution
    5.6.15 Date
    5.6.16 Format
    5.6.18 Changes
    5.6.19 Binary
    5.6.21 Files
    5.6.22 Closes
    5.6.24 Checksums-Sha1 and Checksums-Sha256
        Checksums-Sha1
        Checksums-Sha256
    5.6.26 Version Control System (VCS) fields

5.7 User-defined fields

* */