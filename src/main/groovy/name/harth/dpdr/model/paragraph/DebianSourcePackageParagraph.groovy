package name.harth.dpdr.model.paragraph

import name.harth.dpdr.model.field.PkgName
import name.harth.dpdr.model.field.PkgReference
import name.harth.dpdr.model.field.PolicyVersion
import name.harth.dpdr.model.field.Priority
import name.harth.dpdr.model.field.Section
import name.harth.dpdr.model.field.UserDefined
import org.apache.maven.scm.provider.bazaar.BazaarScmProvider
import org.apache.maven.scm.provider.cvslib.cvsexe.CvsExeScmProvider
import org.apache.maven.scm.provider.git.gitexe.GitExeScmProvider
import org.apache.maven.scm.provider.hg.HgScmProvider
import org.apache.maven.scm.provider.svn.svnexe.SvnExeScmProvider
import org.criticalsection.maven.scm.provider.monotone.MonotoneScmProvider

import javax.mail.internet.InternetAddress

class DebianSourcePackageParagraph {
    PkgName source
    InternetAddress maintainer
    InternetAddress[] uploaders
    boolean dmUploadAllowed
    Section section
    Priority priority
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
    PkgReference[] builtUsing
    PolicyVersion standardsVersion
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
    UserDefined[] userDefinedFields
}
