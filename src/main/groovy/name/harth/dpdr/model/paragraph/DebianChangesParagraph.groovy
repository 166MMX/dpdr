package name.harth.dpdr.model.paragraph

import name.harth.dpdr.model.field.*

import javax.mail.internet.InternetAddress

class DebianChangesParagraph {
    String format
    Date date
    PackageName source
    PackageName[] binary
    Architecture architecture
    PackageVersion packageVersion
    Distribution distribution
    Urgency urgency
    String urgencyComment
    InternetAddress maintainer
    InternetAddress changedBy
    String description
    String[] closes
    String changes
    Sha1Checksum[] checksumsSha1
    Sha256Checksum[] checksumsSha256
    Md5Checksum[] files
    UserDefined[] userDefinedFields
}
