package name.harth.dpdr.model.gexf

class SrcPkgNode extends PkgNode
{
    // Attributes
    // DP:   Format (mandatory)
    String format

    // DP:   DM-Upload-Allowed
    boolean dmUploadAllowed

    // DP:   Standards-Version (recommended)
    String standardsVersion

    // DP:   Vcs-Browser, Vcs-Git, et al.
    String vcsBrowser

    // DP:   Checksums-Sha1 (recommended)
    boolean checksumsSha1

    // DP:   Checksums-Sha256 (recommended)
    boolean checksumsSha256

    // DP:   Files (mandatory)
    int files


    // Relationships
    // DP:   Uploaders
    List<MaintainerNode> uploaders = new ArrayList<MaintainerNode>()

    // DP:   Binary
    List<BinPkgNode> binary = new ArrayList<BinPkgNode>()

    // DP:   Build-Depends
    List<BinPkgNode> buildDepends = new ArrayList<BinPkgNode>()

    // DP:   Build-Depends-Indep
    List<BinPkgNode> buildDependsIndep = new ArrayList<BinPkgNode>()

    // DP:   Build-Conflicts
    List<BinPkgNode> buildConflicts = new ArrayList<BinPkgNode>()

    // DP:   Build-Conflicts-Indep
    List<BinPkgNode> buildConflictsIndep = new ArrayList<BinPkgNode>()

}
