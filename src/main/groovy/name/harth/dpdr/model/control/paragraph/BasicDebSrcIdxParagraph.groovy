package name.harth.dpdr.model.control.paragraph

final class BasicDebSrcIdxParagraph implements Serializable
{
    static final enum VcsAddressType
    {
        BROWSER('Vcs-Browser'),
        ARCH('Vcs-Arch'),
        BZR('Vcs-Bzr'),
        CVS('Vcs-Cvs'),
        DARCS('Vcs-Darcs'),
        GIT('Vcs-Git'),
        HG('Vcs-Hg'),
        MTN('Vcs-Mtn'),
        SVN('Vcs-Svn')

        final String name

        VcsAddressType(String name)
        {
            this.name = name
        }

        static VcsAddressType byName(String name)
        {
            switch (name)
            {
                case BROWSER.name:
                    return BROWSER
                case ARCH.name:
                    return ARCH
                case BZR.name:
                    return BZR
                case CVS.name:
                    return CVS
                case DARCS.name:
                    return DARCS
                case GIT.name:
                    return GIT
                case HG.name:
                    return HG
                case MTN.name:
                    return MTN
                case SVN.name:
                    return SVN
                default:
                    return null
            }
        }
    }

    static final enum DependencyType
    {
        BUILD_DEPENDS('Build-Depends'),
        BUILD_DEPENDS_INDEP('Build-Depends-Indep'),
        BUILD_CONFLICTS('Build-Conflicts'),
        BUILD_CONFLICTS_INDEP('Build-Conflicts-Indep')

        final String name

        DependencyType(String name)
        {
            this.name = name
        }

        static DependencyType byName(String name)
        {
            switch (name)
            {
                case BUILD_DEPENDS.name:
                    return BUILD_DEPENDS
                case BUILD_DEPENDS_INDEP.name:
                    return BUILD_DEPENDS_INDEP
                case BUILD_CONFLICTS.name:
                    return BUILD_CONFLICTS
                case BUILD_CONFLICTS_INDEP.name:
                    return BUILD_CONFLICTS_INDEP
                default:
                    return null
            }
        }
    }

    static final class FileEntry implements Serializable
    {
        private static final long serialVersionUID = -1041327635167312836L

        String  checksum
        int     size
        String  fileName
    }

    static final class VcsAddressEntry implements Serializable
    {
        private static final long serialVersionUID = 4207357272037800240L

        VcsAddressType type
        String address
    }

    static final class DependencyEntry implements Serializable
    {
        private static final long serialVersionUID = 513062054266975194L

        DependencyType type
        String dependency
    }

    private static final long serialVersionUID = 2159466615180549994L

    //Format (mandatory)
    String         format
    //Source (mandatory)
    //The "Source" field is renamed to "Package"
    String         name
    //Binary
    Set<String>    binary
    //Architecture
    String         architecture
    //Version (mandatory)
    String         version
    //Maintainer (mandatory)
    String         maintainer
    //Uploaders
    Set<String>    uploaders
    //DM-Upload-Allowed
    boolean        dmUploadAllowed
    //Homepage
    String         homepage
    //Vcs-Browser, Vcs-Git, et al.
    List<VcsAddressEntry>  vcsAddresses
    //Standards-Version (recommended)
    String         standardsVersion
    //Build-Depends et al
    List<DependencyEntry>  dependencies
    //Checksums-Sha1 and Checksums-Sha256 (recommended)
    Set<FileEntry>  checksumsSha1
    Set<FileEntry>  checksumsSha256
    //Files (mandatory)
    Set<FileEntry>  files

    //A new mandatory field "Directory"
    String         directory
    //A new optional field "Priority"
    String         priority
    //A new optional field "Section"
    String         section

    Map<String, String>  userDefinedFields
}