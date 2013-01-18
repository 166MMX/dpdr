package name.harth.dpdr.model.paragraph

import name.harth.dpdr.model.field.Architecture
import name.harth.dpdr.model.field.Md5Checksum
import name.harth.dpdr.model.field.PkgName
import name.harth.dpdr.model.field.PkgReference
import name.harth.dpdr.model.field.PkgVersion
import name.harth.dpdr.model.field.PolicyVersion
import name.harth.dpdr.model.field.Sha1Checksum
import name.harth.dpdr.model.field.Sha256Checksum
import name.harth.dpdr.model.field.UserDefined
import org.apache.maven.scm.provider.bazaar.BazaarScmProvider
import org.apache.maven.scm.provider.cvslib.cvsexe.CvsExeScmProvider
import org.apache.maven.scm.provider.git.gitexe.GitExeScmProvider
import org.apache.maven.scm.provider.hg.HgScmProvider
import org.apache.maven.scm.provider.svn.svnexe.SvnExeScmProvider
import org.criticalsection.maven.scm.provider.monotone.MonotoneScmProvider

import javax.mail.internet.InternetAddress

class DebianSourceParagraph {
    String format
    PkgName source
    PkgName[] binary
    Architecture architecture
    PkgVersion packageVersion
    InternetAddress maintainer
    InternetAddress[] uploaders
    boolean dmUploadAllowed
    URL homepage
    URL[] vcsBrowser
    String[] vcsArch
    BazaarScmProvider[] vcsBzr
    CvsExeScmProvider[] vcsCvs
    String[] vcsDarcs
    GitExeScmProvider[] vcsGit
    HgScmProvider[] vcsHg
    MonotoneScmProvider[] vcsMtn
    SvnExeScmProvider[] vcsSvn
    PolicyVersion standardsVersion
    PkgReference[] depends
    PkgReference[] preDepends
    PkgReference[] recommends
    PkgReference[] suggests
    PkgReference[] breaks
    PkgReference[] conflicts
    PkgReference[] provides
    PkgReference[] replaces
    PkgReference[] enhances
    Sha1Checksum[] checksumsSha1
    Sha256Checksum[] checksumsSha256
    Md5Checksum[] files
    UserDefined[] userDefinedFields
}
