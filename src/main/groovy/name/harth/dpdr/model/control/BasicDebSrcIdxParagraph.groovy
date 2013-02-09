package name.harth.dpdr.model.control

class BasicDebSrcIdxParagraph implements Serializable
{
    private static final long serialVersionUID = 2159466615180549994L

    //Format (mandatory)
    String         format
    //Source (mandatory)
    //The "Source" field is renamed to "Package"
    String         name
    //Binary
    Set<String>    binary = new HashSet<String>()
    //Architecture
    String         architecture
    //Version (mandatory)
    String         version
    //Maintainer (mandatory)
    String         maintainer
    //Uploaders
    Set<String>    uploaders = new HashSet<String>()
    //DM-Upload-Allowed
    boolean        dmUploadAllowed
    //Homepage
    String         homepage
    //Vcs-Browser, Vcs-Git, et al.
    List<VcsAddressEntry>  vcsAddresses = new ArrayList<VcsAddressEntry>()
    //Standards-Version (recommended)
    String         standardsVersion
    //Build-Depends et al
    List<DependencyEntry>  dependencies = new ArrayList<DependencyEntry>()
    //Checksums-Sha1 and Checksums-Sha256 (recommended)
    Set<FileEntry>  checksumsSha1 = new HashSet<FileEntry>()
    Set<FileEntry>  checksumsSha256 = new HashSet<FileEntry>()
    //Files (mandatory)
    Set<FileEntry>  files = new HashSet<FileEntry>()

    //A new mandatory field "Directory"
    String         directory
    //A new optional field "Priority"
    String         priority
    //A new optional field "Section"
    String         section

    Map<String, String>  userDefinedFields = new HashMap<String, String>()

}