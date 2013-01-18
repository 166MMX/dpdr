package name.harth.dpdr.model.paragraph

import name.harth.dpdr.model.field.*
import org.apache.maven.scm.provider.bazaar.BazaarScmProvider
import org.apache.maven.scm.provider.cvslib.cvsexe.CvsExeScmProvider
import org.apache.maven.scm.provider.git.gitexe.GitExeScmProvider
import org.apache.maven.scm.provider.hg.HgScmProvider
import org.apache.maven.scm.provider.svn.svnexe.SvnExeScmProvider
import org.criticalsection.maven.scm.provider.monotone.MonotoneScmProvider

import javax.mail.Header
import javax.mail.internet.InternetAddress

class DebianSourceIndicesParagraph {
    String        format
    PkgName       name
    PkgName[]     binary
    Architecture  architecture
    PkgVersion    version

    InternetAddress    maintainer
    InternetAddress[]  uploaders

    boolean dmUploadAllowed
    URL homepage

    URL[]                  vcsBrowser
    String[]               vcsArch
    BazaarScmProvider[]    vcsBzr
    CvsExeScmProvider[]    vcsCvs
    String[]               vcsDarcs
    GitExeScmProvider[]    vcsGit
    HgScmProvider[]        vcsHg
    MonotoneScmProvider[]  vcsMtn
    SvnExeScmProvider[]    vcsSvn

    PolicyVersion standardsVersion

    PkgReference[]  depends
    PkgReference[]  preDepends
    PkgReference[]  recommends
    PkgReference[]  suggests
    PkgReference[]  breaks
    PkgReference[]  conflicts
    PkgReference[]  provides
    PkgReference[]  replaces
    PkgReference[]  enhances

    Sha1Checksum[]    checksumsSha1
    Sha256Checksum[]  checksumsSha256
    Md5Checksum[]     files

    UserDefined[] userDefinedFields
    String directory
    Priority priority
    Section section

    public static Map<String, String> fieldMap = [
            'Name': 'name',
            'Binary': 'binary',
            'Source': 'source',
    ]
    public static Map<String, PkgField> classMap = [
        'Name': PkgName,
        'Binary': PkgName,
        'Source': PkgReference,
    ]

    public DebianSourceIndicesParagraph(Enumeration<Header> headers)
    {
        headers.toList().each {
            String key = it.name
            String field = fieldMap[key]
            Class<? extends PkgField> clazz = classMap[key]
            this[field] = clazz.newInstance(it)
        }
    }

}
